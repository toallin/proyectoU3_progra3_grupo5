package Controlador;

import Modelo.Pedido;
import DAO.PedidoDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        // Redirigir si no hay sesión iniciada
        if (idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        try {
            // Obtener la conexión desde el contexto
            Connection conexion = (Connection) getServletContext().getAttribute("conexion");
            PedidoDAO pedidoDao = new PedidoDAO(conexion);

            // Obtener el último pedido pagado
            Pedido ultimoPedido = pedidoDao.obtenerUltimoPedidoPagado(idUsuario);
            request.setAttribute("ultimoPedido", ultimoPedido);

            // Obtener historial completo de pedidos pagados
            List<Pedido> pedidosAnteriores = pedidoDao.obtenerPedidosAnteriores(idUsuario);
            request.setAttribute("pedidosAnteriores", pedidosAnteriores);

            // Enviar a dashboard.jsp
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("nuevoPedido".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/seleccion-tipo-pedido");
            return;
        }

        // Si no hay acción específica, procesa como GET
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para el dashboard del usuario";
    }
}
