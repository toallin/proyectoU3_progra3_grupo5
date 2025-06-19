<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="conn" class="Conexion.conexion" scope="page" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Platos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .plato-card {
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }
        .plato-card img {
            width: 100px;
            height: 100px;
            margin-right: 15px;
            object-fit: cover;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Selecciona un plato por comida</h2>
    <form method="post" action="ConfirmarPedidoServlet">
        <%
            Connection con = conn.getConnection();
            String[] categorias = {"Desayuno", "Almuerzo", "Cena"};
            PreparedStatement ps = null;
            ResultSet rs = null;

            for (String categoria : categorias) {
                out.println("<h3>" + categoria + "</h3>");

                ps = con.prepareStatement("SELECT id, nombre, descripcion, porcion, calorias, ingredientes, precio FROM platos WHERE categoria = ?");
                ps.setString(1, categoria);
                rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    String porcion = rs.getString("porcion");
                    int calorias = rs.getInt("calorias");
                    String ingredientes = rs.getString("ingredientes");
                    double precio = rs.getDouble("precio");

                    String imgPath = "platos/" + nombre.replaceAll(" ", "_") + ".jpg";
        %>
                    <div class="plato-card">
                        <input type="radio" name="<%= categoria.toLowerCase() %>" value="<%= id %>" class="form-check-input me-2">
                        <img src="<%= imgPath %>" alt="Imagen de <%= nombre %>" onerror="this.src='platos/default.jpg'">
                        <div>
                            <h5><%= nombre %></h5>
                            <p><strong>Descripción:</strong> <%= descripcion %></p>
                            <p><strong>Porción:</strong> <%= porcion %> | <strong>Calorías:</strong> <%= calorias %></p>
                            <p><strong>Ingredientes:</strong> <%= ingredientes %></p>
                            <p><strong>Precio:</strong> S/ <%= String.format("%.2f", precio) %></p>
                        </div>
                    </div>
        <%
                }
                rs.close();
                ps.close();
            }
            con.close();
        %>
        <button type="submit" class="btn btn-success mt-3">Confirmar Pedido</button>
    </form>
</div>
</body>
</html>
