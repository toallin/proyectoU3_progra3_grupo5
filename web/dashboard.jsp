<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.Pedido, java.util.List, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Mi Aplicación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .dashboard-card {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
        .welcome-section {
            background-color: #f8f9fa;
        }
        .order-section {
            background-color: #e9ecef;
        }
        .btn-custom {
            margin: 5px;
            min-width: 200px;
        }
        .order-item {
            border-left: 4px solid #0d6efd;
            margin-bottom: 15px;
            padding: 10px;
            background-color: white;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <!-- Barra de navegación -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Mi Tienda</a>
                <div class="d-flex">
                    <span class="navbar-text me-3 text-white">
                        Bienvenido, ${sessionScope.nombreUsuario}
                    </span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Cerrar sesión</a>
                </div>
            </div>
        </nav>

        <!-- Sección de bienvenida -->
        <div class="dashboard-card welcome-section">
            <h2><i class="bi bi-house-door"></i> Panel de Control</h2>
            <p class="lead">Gestiona tus pedidos desde aquí</p>
        </div>

        <!-- Último pedido -->
        <%
            Pedido ultimoPedido = (Pedido) request.getAttribute("ultimoPedido");
        %>
        <div class="dashboard-card order-section">
            <h3><i class="bi bi-cart-check"></i> Tu último pedido</h3>
            
            <% if (ultimoPedido != null) { %>
                <div class="order-item">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Número de pedido:</strong> #<%= ultimoPedido.getIdPedido() %></p>
                            <p><strong>Fecha:</strong> <%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ultimoPedido.getFechaPedido()) %></p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Estado:</strong> <span class="badge bg-info"><%= ultimoPedido.getEstado() %></span></p>
                            <p><strong>Total:</strong> $<%= String.format("%,.2f", ultimoPedido.getTotal()) %></p>
                        </div>
                    </div>
                    <p class="mt-2"><strong>Detalles:</strong> <%= ultimoPedido.getDetalles() %></p>
                </div>
            <% } else { %>
                <div class="alert alert-info">
                    No tienes pedidos recientes.
                </div>
            <% } %>
        </div>

        <!-- Acciones principales -->
        <div class="dashboard-card text-center">
         <a href="${pageContext.request.contextPath}/seleccion-tipo-pedido.jsp" class="btn btn-primary btn-custom">
    <i class="bi bi-plus-circle"></i> Hacer nuevo pedido
</a>


            <a href="${pageContext.request.contextPath}/dashboard?verPedidos=true" class="btn btn-secondary btn-custom">
                <i class="bi bi-list-ul"></i> Ver pedidos anteriores
            </a>
        </div>

        <!-- Listado de pedidos anteriores -->
        <%
            List<Pedido> pedidosAnteriores = (List<Pedido>) request.getAttribute("pedidosAnteriores");
            if (pedidosAnteriores != null) {
        %>
            <div class="dashboard-card">
                <h3><i class="bi bi-clock-history"></i> Historial de pedidos</h3>
                
                <% if (!pedidosAnteriores.isEmpty()) { %>
                    <% for (Pedido pedido : pedidosAnteriores) { %>
                        <div class="order-item">
                            <div class="row">
                                <div class="col-md-4">
                                    <p><strong>Pedido #<%= pedido.getIdPedido() %></strong></p>
                                    <p><%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pedido.getFechaPedido()) %></p>
                                </div>
                                <div class="col-md-4">
                                    <p><span class="badge bg-<%= pedido.getEstado().equals("Entregado") ? "success" : "warning" %>">
                                        <%= pedido.getEstado() %>
                                    </span></p>
                                </div>
                                <div class="col-md-4 text-end">
                                    <p><strong>Total: $<%= String.format("%,.2f", pedido.getTotal()) %></strong></p>
                                    <a href="${pageContext.request.contextPath}/detalle-pedido?id=<%= pedido.getIdPedido() %>" class="btn btn-sm btn-outline-primary">Ver detalles</a>
                                </div>
                            </div>
                        </div>
                    <% } %>
                <% } else { %>
                    <div class="alert alert-secondary">No tienes pedidos anteriores.</div>
                <% } %>
            </div>
        <% } %>
    </div>
</body>
</html>
