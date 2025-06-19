<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String metodo = request.getParameter("metodo");
    String monto = request.getParameter("monto");
    String pedidoId = request.getParameter("pedidoId");

    if (metodo == null || monto == null || pedidoId == null) {
%>
    <p style="color:red;">Faltan datos para confirmar el pago.</p>
    <a href="pago.jsp">Volver al Pago</a>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmar Pago</title>
</head>
<body>
    <h2>Confirmación de Pago</h2>
    <p><strong>Método:</strong> <%= metodo %></p>
    <p><strong>Total:</strong> S/ <%= monto %></p>

    <form method="post" action="PagoServlet">
        <input type="hidden" name="metodo" value="<%= metodo %>">
        <input type="hidden" name="monto" value="<%= monto %>">
        <input type="hidden" name="pedidoId" value="<%= pedidoId %>">
        <button type="submit">Confirmar y Pagar</button>
    </form>
</body>
</html>
