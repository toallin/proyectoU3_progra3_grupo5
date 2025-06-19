package Controlador;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import Conexion.conexion;

public class ConfirmarPedidoSemanalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Integer usuarioId = (Integer) sesion.getAttribute("usuarioId");

        if (usuarioId == null) {
            response.sendRedirect("usuario?accion=login");
            return;
        }

        double total = 0.0;
        Map<String, Map<String, Integer>> seleccionPorDia = new LinkedHashMap<>();
        Connection con = null;

        try {
            con = conexion.getConnection();

            // Día actual + 1 (mañana)
            LocalDate hoy = LocalDate.now().plusDays(1);

            // 1. Insertar pedido semanal
            String sqlPedido = "INSERT INTO pedidos (usuario_id, fecha_pedido, fecha_entrega, tipo_pedido, estado) " +
                               "VALUES (?, CURRENT_DATE, ?, 'Semanal', 'Pendiente')";
            PreparedStatement psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, usuarioId);
            psPedido.setDate(2, java.sql.Date.valueOf(hoy.plusDays(6))); // entrega final en 7 días
            psPedido.executeUpdate();

            ResultSet rsPedido = psPedido.getGeneratedKeys();
            int pedidoId = -1;
            if (rsPedido.next()) {
                pedidoId = rsPedido.getInt(1);
            }
            psPedido.close();
            rsPedido.close();

            // 2. Preparar statements
            PreparedStatement psPrecio = con.prepareStatement("SELECT precio FROM platos WHERE id = ?");
            PreparedStatement psDetalle = con.prepareStatement(
                "INSERT INTO detalle_pedido (pedido_id, comida, plato_id) VALUES (?, ?, ?)"
            );

            // 3. Procesar los 7 días desde mañana
            for (int i = 0; i < 7; i++) {
                LocalDate diaActual = hoy.plusDays(i);
               String nombreDia = diaActual.getDayOfWeek()
    .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
    .toLowerCase(); // Capitaliza: Lunes, Martes...

                Map<String, Integer> seleccionDia = new HashMap<>();

                for (String comida : List.of("desayuno", "almuerzo", "cena")) {
                    String param = request.getParameter(nombreDia + "_" + comida);
                    if (param == null || param.isEmpty()) {
                        continue; // ignorar si falta
                    }

                    int platoId = Integer.parseInt(param);
                    seleccionDia.put(comida, platoId);

                    // Precio
                    psPrecio.setInt(1, platoId);
                    ResultSet rs = psPrecio.executeQuery();
                    if (rs.next()) {
                        total += rs.getDouble("precio");
                    }
                    rs.close();

                    // Insertar detalle
                    psDetalle.setInt(1, pedidoId);
                    psDetalle.setString(2, capitalize(comida)); // Desayuno, Almuerzo, Cena
                    psDetalle.setInt(3, platoId);
                    psDetalle.executeUpdate();
                }

                seleccionPorDia.put(nombreDia, seleccionDia);
            }

            psPrecio.close();
            psDetalle.close();

            // 4. Aplicar descuento
            double descuento = total * 0.15;
            double totalConDescuento = total - descuento;

            // 5. Guardar en sesión para mostrar resumen
            sesion.setAttribute("seleccionPorDia", seleccionPorDia);
            sesion.setAttribute("total", total);
            sesion.setAttribute("descuento", descuento);
            sesion.setAttribute("totalConDescuento", totalConDescuento);
            sesion.setAttribute("pedidoId", pedidoId);
            sesion.setAttribute("fechaEntrega", hoy.toString());

            response.sendRedirect("resumenPedido.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar pedido semanal.");
            request.getRequestDispatcher("seleccion-platos-semanal.jsp").forward(request, response);
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }

    private String capitalize(String texto) {
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
