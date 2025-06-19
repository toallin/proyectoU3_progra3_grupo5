package Controlador;

import Conexion.conexion;
import DAO.PedidoDAO;
import Modelo.Pedido;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

public class NuevoPedidoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Recoge datos del formulario
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String tipoPedido = request.getParameter("tipoPedido"); // diario o semanal
            String fechasSeleccionadas = request.getParameter("fechas"); // formato: 2025-06-13,2025-06-14,...
            String detalles = request.getParameter("detalles"); // JSON o texto con platos por día y comida
            double total = Double.parseDouble(request.getParameter("total"));

            // Establecer conexión
            Connection con = conexion.getConnection();

            // Crear objeto Pedido
            Pedido pedido = new Pedido();
            pedido.setIdUsuario(idUsuario);
            pedido.setFechaPedido(new Date()); // fecha actual
            pedido.setEstado("pendiente");
            pedido.setTotal(total);
            pedido.setDetalles(detalles); // podría ser JSON: {"2025-06-13":{"desayuno":"Avena",...}}

            // Guardar en BD (necesitas método adicional en PedidoDAO: guardarPedido)
            PedidoDAO pedidoDAO = new PedidoDAO(con);
            boolean registrado = pedidoDAO.guardarPedido(pedido);

            if (registrado) {
                response.sendRedirect("resumenPedido.jsp"); // o mostrar confirmación
            } else {
                response.sendRedirect("error.jsp?msg=No se pudo registrar el pedido");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?msg=" + e.getMessage());
        }
    }
}
