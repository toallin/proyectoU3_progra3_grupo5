<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
    String tipo = request.getParameter("tipo"); // "diario" o "semanal"
    LocalDate hoy = LocalDate.now();
    LocalDate manana = hoy.plusDays(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Fecha</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-info text-white">
            <h4>Selecciona la fecha de tu pedido <%= tipo.equals("diario") ? "diario" : "semanal" %></h4>
        </div>
        <div class="card-body">
            <form method="post" action="elegir-platos.jsp">
                <input type="hidden" name="tipo" value="<%= tipo %>"/>

                <% if ("diario".equals(tipo)) { %>
                    <label for="fecha">Fecha de entrega:</label>
                    <input type="date" id="fecha" name="fecha" class="form-control" min="<%= manana %>" required>

                <% } else if ("semanal".equals(tipo)) { 
                    LocalDate inicioSemana = manana;
                %>
                    <p>Selecciona una semana para tu pedido:</p>
                    <ul class="list-group">
                        <% for (int i = 0; i < 7; i++) {
                            LocalDate dia = inicioSemana.plusDays(i);
                        %>
                            <li class="list-group-item">
                                <input type="checkbox" name="fechas" value="<%= dia.format(formatter) %>" />
                                <%= dia.getDayOfWeek() %> - <%= dia.format(formatter) %>
                            </li>
                        <% } %>
                    </ul>
                <% } %>

                <button type="submit" class="btn btn-primary mt-4">Siguiente</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
