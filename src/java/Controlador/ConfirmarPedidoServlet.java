package Controlador;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import Conexion.conexion;

public class ConfirmarPedidoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipoPedido = request.getParameter("tipoPedido"); // "Diario" o "Semanal"
        if (tipoPedido == null) tipoPedido = "Diario";

        Connection con = null;

        try {
            con = conexion.getConnection();
            PreparedStatement psPrecio = con.prepareStatement("SELECT precio FROM platos WHERE id = ?");

            HttpSession sesion = request.getSession();
            Integer usuarioId = (Integer) sesion.getAttribute("usuarioId");
            if (usuarioId == null) {
                response.sendRedirect("usuario?accion=login");
                return;
            }

            double total = 0.0;
            int pedidoId = -1;

            if (tipoPedido.equalsIgnoreCase("Semanal")) {
                // Obtener todos los platos por día y comida
                Map<String, Map<String, Integer>> seleccionPorDia = new LinkedHashMap<>();

                for (int i = 0; i < 7; i++) {
                    String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, i + 1);
                    String fecha = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                    String nombreDia = dias[cal.get(Calendar.DAY_OF_WEEK) - 1];
                    String claveDia = nombreDia + " - " + fecha;

                    String desayuno = request.getParameter(claveDia + "_desayuno");
                    String almuerzo = request.getParameter(claveDia + "_almuerzo");
                    String cena = request.getParameter(claveDia + "_cena");

                    if (desayuno == null || almuerzo == null || cena == null) continue;

                    Map<String, Integer> comidas = new HashMap<>();
                    comidas.put("desayuno", Integer.parseInt(desayuno));
                    comidas.put("almuerzo", Integer.parseInt(almuerzo));
                    comidas.put("cena", Integer.parseInt(cena));

                    seleccionPorDia.put(claveDia, comidas);

                    // Calcular total
                    for (int id : comidas.values()) {
                        psPrecio.setInt(1, id);
                        ResultSet rs = psPrecio.executeQuery();
                        if (rs.next()) {
                            total += rs.getDouble("precio");
                        }
                        rs.close();
                    }
                }

                // Insertar pedido
                PreparedStatement psPedido = con.prepareStatement(
                        "INSERT INTO pedidos (usuario_id, fecha_pedido, fecha_entrega, tipo_pedido, estado) VALUES (?, CURRENT_DATE, CURRENT_DATE, ?, 'Pendiente')",
                        Statement.RETURN_GENERATED_KEYS
                );
                psPedido.setInt(1, usuarioId);
                psPedido.setString(2, tipoPedido);
                psPedido.executeUpdate();

                ResultSet rsPedido = psPedido.getGeneratedKeys();
                if (rsPedido.next()) pedidoId = rsPedido.getInt(1);
                psPedido.close();
                rsPedido.close();

                // Insertar detalle pedido
                PreparedStatement psDetalle = con.prepareStatement("INSERT INTO detalle_pedido (pedido_id, comida, plato_id) VALUES (?, ?, ?)");
                for (Map<String, Integer> comidas : seleccionPorDia.values()) {
                    for (Map.Entry<String, Integer> entry : comidas.entrySet()) {
                        psDetalle.setInt(1, pedidoId);
                        psDetalle.setString(2, entry.getKey());
                        psDetalle.setInt(3, entry.getValue());
                        psDetalle.executeUpdate();
                    }
                }
                psDetalle.close();

                double descuento = total * 0.15;
                double totalFinal = total - descuento;

                // Guardar en sesión
                sesion.setAttribute("tipoPedido", tipoPedido);
                sesion.setAttribute("seleccionPorDia", seleccionPorDia);
                sesion.setAttribute("total", total);
                sesion.setAttribute("descuento", descuento);
                sesion.setAttribute("totalConDescuento", totalFinal);
                sesion.setAttribute("pedidoId", pedidoId);
                sesion.setAttribute("fechaEntrega", new java.util.Date().toString());

                response.sendRedirect("resumenPedido.jsp");

            } else {
                // Pedido Diario
                String desayunoStr = request.getParameter("desayuno");
                String almuerzoStr = request.getParameter("almuerzo");
                String cenaStr = request.getParameter("cena");

                if (desayunoStr == null || almuerzoStr == null || cenaStr == null) {
                    request.setAttribute("error", "Debe seleccionar un plato para cada comida.");
                    request.getRequestDispatcher("elegir-Platos.jsp").forward(request, response);
                    return;
                }

                Map<String, Integer> comidas = new HashMap<>();
                comidas.put("Desayuno", Integer.parseInt(desayunoStr));
                comidas.put("Almuerzo", Integer.parseInt(almuerzoStr));
                comidas.put("Cena", Integer.parseInt(cenaStr));

                for (int id : comidas.values()) {
                    psPrecio.setInt(1, id);
                    ResultSet rs = psPrecio.executeQuery();
                    if (rs.next()) total += rs.getDouble("precio");
                    rs.close();
                }

                // Insertar pedido
                PreparedStatement psPedido = con.prepareStatement(
                        "INSERT INTO pedidos (usuario_id, fecha_pedido, fecha_entrega, tipo_pedido, estado) VALUES (?, CURRENT_DATE, CURRENT_DATE, ?, 'Pendiente')",
                        Statement.RETURN_GENERATED_KEYS
                );
                psPedido.setInt(1, usuarioId);
                psPedido.setString(2, tipoPedido);
                psPedido.executeUpdate();

                ResultSet rsPedido = psPedido.getGeneratedKeys();
                if (rsPedido.next()) pedidoId = rsPedido.getInt(1);
                psPedido.close();
                rsPedido.close();

                // Insertar detalle pedido
                PreparedStatement psDetalle = con.prepareStatement("INSERT INTO detalle_pedido (pedido_id, comida, plato_id) VALUES (?, ?, ?)");
                for (Map.Entry<String, Integer> entry : comidas.entrySet()) {
                    psDetalle.setInt(1, pedidoId);
                    psDetalle.setString(2, entry.getKey());
                    psDetalle.setInt(3, entry.getValue());
                    psDetalle.executeUpdate();
                }
                psDetalle.close();

                sesion.setAttribute("tipoPedido", tipoPedido);
                sesion.setAttribute("seleccion", comidas);
                sesion.setAttribute("total", total);
                sesion.setAttribute("pedidoId", pedidoId);
                sesion.setAttribute("fechaEntrega", new java.util.Date().toString());

                response.sendRedirect("resumenPedidoDiario.jsp");
            }

            psPrecio.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error al confirmar el pedido", e);
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }
}
