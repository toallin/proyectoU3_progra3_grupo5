<%@ page import="java.util.*, Modelo.Plato" %>
<%
    Map<String, List<Plato>> platosPorTipo = (Map<String, List<Plato>>) request.getAttribute("platosPorTipo");

    String[] nombresDias = {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
    List<String> nombresDiasMostrar = new ArrayList<>();
    List<String> diasKeys = new ArrayList<>();

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, 1);

    for (int i = 0; i < 7; i++) {
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        String nombreDia = nombresDias[diaSemana - 1];
        String fecha = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        nombresDiasMostrar.add(nombreDia.substring(0, 1).toUpperCase() + nombreDia.substring(1) + " - " + fecha);
        diasKeys.add(nombreDia);
        cal.add(Calendar.DAY_OF_MONTH, 1);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pedido Semanal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container my-5">
    <h2 class="text-center mb-4">Selecciona tus platos para cada día</h2>
    <form action="ConfirmarPedidoSemanalServlet" method="post">

        <% for (int i = 0; i < 7; i++) {
            String diaKey = diasKeys.get(i);
            String diaMostrar = nombresDiasMostrar.get(i);
        %>
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><%= diaMostrar %></h5>
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label for="<%= diaKey %>_desayuno" class="form-label">Desayuno:</label>
                    <select class="form-select" name="<%= diaKey %>_desayuno" required>
                        <option value="">-- Selecciona --</option>
                        <% for (Plato p : platosPorTipo.get("Desayuno")) { %>
                            <option value="<%= p.getId() %>"><%= p.getNombre() %> - S/ <%= p.getPrecio() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="<%= diaKey %>_almuerzo" class="form-label">Almuerzo:</label>
                    <select class="form-select" name="<%= diaKey %>_almuerzo" required>
                        <option value="">-- Selecciona --</option>
                        <% for (Plato p : platosPorTipo.get("Almuerzo")) { %>
                            <option value="<%= p.getId() %>"><%= p.getNombre() %> - S/ <%= p.getPrecio() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="<%= diaKey %>_cena" class="form-label">Cena:</label>
                    <select class="form-select" name="<%= diaKey %>_cena" required>
                        <option value="">-- Selecciona --</option>
                        <% for (Plato p : platosPorTipo.get("Cena")) { %>
                            <option value="<%= p.getId() %>"><%= p.getNombre() %> - S/ <%= p.getPrecio() %></option>
                        <% } %>
                    </select>
                </div>
            </div>
        </div>
        <% } %>

        <div class="text-center">
            <button type="submit" class="btn btn-success btn-lg">Confirmar Pedido Semanal</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
