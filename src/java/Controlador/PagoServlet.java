package Controlador;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import Conexion.conexion;

public class PagoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String metodo = request.getParameter("metodo");
        String montoStr = request.getParameter("monto");
        String pedidoIdStr = request.getParameter("pedidoId");

        HttpSession sesion = request.getSession(false);
        Integer usuarioId = (Integer) sesion.getAttribute("usuarioId");

        // Validación de campos
        if (metodo == null || metodo.isEmpty() || montoStr == null || pedidoIdStr == null || usuarioId == null) {
            request.setAttribute("error", "Faltan datos del pago.");
            request.getRequestDispatcher("resumenPedido.jsp").forward(request, response);
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);
            int pedidoId = Integer.parseInt(pedidoIdStr);

            Connection con = conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO pagos (usuario_id, pedido_id, monto_total, metodo_pago, estado) VALUES (?, ?, ?, ?, 'Pagado')"
            );
            ps.setInt(1, usuarioId);
            ps.setInt(2, pedidoId);
            ps.setDouble(3, monto);
            ps.setString(4, metodo);

            ps.executeUpdate();
            ps.close();
            con.close();

            // Redirige al dashboard luego del pago exitoso
           response.sendRedirect(request.getContextPath() + "/dashboard");


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar el pago.");
            request.getRequestDispatcher("resumenPedido.jsp").forward(request, response);
        }
    }
}
