<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pedidoId = request.getParameter("pedidoId");
    String monto = request.getParameter("monto");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Método de Pago</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white text-center">
                        <h4>Seleccionar Método de Pago</h4>
                    </div>
                    <div class="card-body">
                        <form method="post" action="PagoServlet">
                            <input type="hidden" name="pedidoId" value="<%= pedidoId %>">
                            <input type="hidden" name="monto" value="<%= monto %>">

                            <div class="mb-3">
                                <label for="metodo" class="form-label">Método de Pago:</label>
                                <select name="metodo" id="metodo" class="form-select" required>
                                    <option value="">-- Seleccionar --</option>
                                    <option value="Yape">Yape</option>
                                    <option value="Efectivo">Efectivo</option>
                                    <option value="Plin">Plin</option>
                                    <option value="Tarjeta">Tarjeta</option>
                                </select>
                            </div>

                            <div class="text-center">
                                <button type="submit" class="btn btn-success w-100">Pagar S/ <%= monto %></button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-muted text-center">
                        Pedido ID: <%= pedidoId %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS (opcional si no usas componentes dinámicos) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
