<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Tipo de Pedido</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .card {
            max-width: 600px;
            margin: 0 auto;
        }
        .btn-option {
            min-width: 200px;
            padding: 1.5rem;
            font-size: 1.2rem;
        }
        .highlight {
            position: relative;
            overflow: hidden;
        }
        .highlight:after {
            content: "¡15% DE DESCUENTO!";
            position: absolute;
            top: -10px;
            right: -30px;
            background: #ffc107;
            color: #000;
            padding: 5px 30px;
            transform: rotate(45deg);
            font-size: 12px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card text-center shadow-lg">
            <div class="card-header bg-primary text-white">
                <h3><i class="bi bi-ui-checks"></i> ¿Qué tipo de pedido deseas hacer?</h3>
            </div>
            <div class="card-body">
                <div class="d-flex flex-column flex-md-row justify-content-center">
                    <form method="get" action="seleccionar-fecha.jsp" class="m-2">
                        <button type="submit" name="tipo" value="diario" class="btn btn-success btn-option">
                            <i class="bi bi-calendar-day"></i> Pedido Diario
                        </button>
                    </form>
                    
                    <form method="get" action="SeleccionSemanalServlet" class="m-2 highlight">
                        <button type="submit" name="tipo" value="semanal" class="btn btn-warning btn-option">
                            <i class="bi bi-calendar-week"></i> Pedido Semanal<br>
                            <small class="text-dark">(Ahorra 15%)</small>
                        </button>
                    </form>
                </div>
                
                <div class="mt-4 alert alert-info">
                    <i class="bi bi-info-circle"></i> El pedido semanal incluye 7 almuerzos y 7 cenas con descuento especial.
                </div>
            </div>
        </div>
    </div>
</body>
</html>