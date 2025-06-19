<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, java.util.*, Conexion.conexion" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resumen del Pedido</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Resumen del Pedido</h2>

<%
    HttpSession sesion = request.getSession();
    Connection con = null;

    Map<String, Integer> seleccion = (Map<String, Integer>) sesion.getAttribute("seleccion");
    Map<String, Map<String, Integer>> seleccionPorDia = (Map<String, Map<String, Integer>>) sesion.getAttribute("seleccionPorDia");

    Double total = (Double) sesion.getAttribute("total");
    Double descuento = (Double) sesion.getAttribute("descuento");
    Double totalConDescuento = (Double) sesion.getAttribute("totalConDescuento");
    String fechaEntrega = (String) sesion.getAttribute("fechaEntrega");
    String emailUsuario = (String) sesion.getAttribute("usuarioEmail");
    Integer pedidoId = (Integer) sesion.getAttribute("pedidoId");

    if ((seleccion == null && seleccionPorDia == null) || total == null || pedidoId == null) {
%>
    <div class="alert alert-warning">No hay un pedido confirmado o el usuario no está autenticado.</div>
<%
    } else {
        try {
            con = conexion.getConnection();

            // Dirección
            String direccion = "No disponible";
            if (emailUsuario != null) {
                PreparedStatement psDireccion = con.prepareStatement("SELECT direccion FROM usuarios WHERE email = ?");
                psDireccion.setString(1, emailUsuario);
                ResultSet rsDireccion = psDireccion.executeQuery();
                if (rsDireccion.next()) direccion = rsDireccion.getString("direccion");
                rsDireccion.close();
                psDireccion.close();
            }

            // Consulta de platos
            PreparedStatement psPlato = con.prepareStatement("SELECT nombre, descripcion, precio FROM platos WHERE id = ?");

            if (seleccionPorDia != null) {
%>
    <h4>Pedido Semanal</h4>
    <table class="table table-bordered">
        <thead><tr><th>Día</th><th>Desayuno</th><th>Almuerzo</th><th>Cena</th></tr></thead>
        <tbody>
<%
        for (Map.Entry<String, Map<String, Integer>> entry : seleccionPorDia.entrySet()) {
            String dia = entry.getKey();
            Map<String, Integer> comidas = entry.getValue();

            String desayuno = "No seleccionado", almuerzo = "No seleccionado", cena = "No seleccionado";

            for (String tipo : comidas.keySet()) {
                int id = comidas.get(tipo);
                psPlato.setInt(1, id);
                ResultSet rs = psPlato.executeQuery();
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    if (tipo.equalsIgnoreCase("desayuno")) desayuno = nombre;
                    else if (tipo.equalsIgnoreCase("almuerzo")) almuerzo = nombre;
                    else if (tipo.equalsIgnoreCase("cena")) cena = nombre;
                }
                rs.close();
            }
%>
            <tr>
                <td><%= dia %></td>
                <td><%= desayuno %></td>
                <td><%= almuerzo %></td>
                <td><%= cena %></td>
            </tr>
<%
        }
%>
        </tbody>
    </table>
    <div class="alert alert-info">
        <strong>Total sin descuento:</strong> S/ <%= String.format("%.2f", total) %><br>
        <strong>Descuento (15%):</strong> S/ <%= String.format("%.2f", descuento) %><br>
        <strong>Total a pagar:</strong> S/ <%= String.format("%.2f", totalConDescuento) %>
    </div>
<%
            } else if (seleccion != null) {
%>
    <h4>Pedido Diario</h4>
    <ul class="list-group mb-3">
<%
        for (Map.Entry<String, Integer> entrada : seleccion.entrySet()) {
            String comida = entrada.getKey();
            int id = entrada.getValue();
            psPlato.setInt(1, id);
            ResultSet rs = psPlato.executeQuery();
            if (rs.next()) {
%>
        <li class="list-group-item">
            <strong><%= comida %>:</strong> <%= rs.getString("nombre") %> - <%= rs.getString("descripcion") %> (S/ <%= rs.getDouble("precio") %>)
        </li>
<%
            }
            rs.close();
        }
%>
    </ul>
    <div class="alert alert-info">
        <strong>Total:</strong> S/ <%= String.format("%.2f", total) %>
    </div>
<%
            }
            psPlato.close();
%>

    <%-- Mostrar dirección y fecha de entrega --%>
    <% if (fechaEntrega != null) { %>
    <div class="alert alert-secondary"><strong>Fecha de entrega:</strong> <%= fechaEntrega %></div>
    <% } %>

    <div class="alert alert-success"><strong>Dirección de entrega:</strong> <%= direccion %></div>

    <%-- Formulario redirige a pago.jsp para seleccionar método de pago --%>
    <form method="post" action="pago.jsp" class="mt-3">
        <input type="hidden" name="monto" value="<%= totalConDescuento != null ? totalConDescuento : total %>">
        <input type="hidden" name="pedidoId" value="<%= pedidoId %>">
        <button type="submit" class="btn btn-primary">Proceder al Pago</button>
    </form>

<%
        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>Error al cargar los datos del pedido.</div>");
            e.printStackTrace();
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }
%>
</div>
</body>
</html>
