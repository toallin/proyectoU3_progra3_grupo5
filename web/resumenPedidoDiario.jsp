<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, java.util.*, Conexion.conexion" %>
<%
    HttpSession sesion = request.getSession(false);
    Map<String, Integer> seleccion = (Map<String, Integer>) sesion.getAttribute("seleccion");
    Double total = (Double) sesion.getAttribute("total");
    Integer pedidoId = (Integer) sesion.getAttribute("pedidoId");
    String emailUsuario = (String) sesion.getAttribute("usuarioEmail");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resumen Pedido Diario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <% if (seleccion == null || total == null || pedidoId == null || emailUsuario == null) { %>
        <div class="alert alert-warning">No hay información del pedido diario disponible.</div>
    <% } else {
        try {
            Connection con = conexion.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT nombre, descripcion, precio FROM platos WHERE id = ?");
    %>

    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white text-center">
            <h4>Resumen del Pedido Diario</h4>
        </div>
        <div class="card-body">
            <ul class="list-group mb-3">
            <% for (Map.Entry<String, Integer> entry : seleccion.entrySet()) {
                   ps.setInt(1, entry.getValue());
                   ResultSet rs = ps.executeQuery();
                   if (rs.next()) {
            %>
                <li class="list-group-item">
                    <strong><%= entry.getKey() %></strong>: <%= rs.getString("nombre") %> - 
                    <%= rs.getString("descripcion") %> (S/ <%= String.format("%.2f", rs.getDouble("precio")) %>)
                </li>
            <% } rs.close(); } %>
            </ul>

            <div class="alert alert-info">
                <strong>Total a pagar:</strong> S/ <%= String.format("%.2f", total) %>
            </div>

            <form action="pago.jsp" method="post" class="text-center">
                <input type="hidden" name="pedidoId" value="<%= pedidoId %>">
                <input type="hidden" name="monto" value="<%= total %>">
                <button type="submit" class="btn btn-success w-100">Proceder al Pago</button>
            </form>
        </div>
    </div>

    <%
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>Error al cargar los datos del pedido.</div>");
            e.printStackTrace();
        }
    } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
