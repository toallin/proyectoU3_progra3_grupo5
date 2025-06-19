<%@page import="java.sql.*, Conexion.conexion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Galería de Platos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .plato-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Galería de Platos</h2>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <%
            Connection con = null;
            try {
                con = conexion.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT nombre, descripcion, porcion, calorias, ingredientes, categoria FROM platos");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    String porcion = rs.getString("porcion");
                    int calorias = rs.getInt("calorias");
                    String ingredientes = rs.getString("ingredientes");
                    String categoria = rs.getString("categoria");

                    String imgPath = "platos/" + nombre.replaceAll(" ", "_") + ".jpg";
        %>
        <div class="col">
            <div class="card plato-card shadow-sm h-100">
                <img src="<%= imgPath %>" alt="<%= nombre %>" class="card-img-top">
                <div class="card-body">
                    <h5 class="card-title"><%= nombre %> (<%= categoria %>)</h5>
                    <p class="card-text"><strong>Descripción:</strong> <%= descripcion %></p>
                    <p class="card-text"><strong>Porción:</strong> <%= porcion %></p>
                    <p class="card-text"><strong>Calorías:</strong> <%= calorias %> kcal</p>
                    <p class="card-text"><strong>Ingredientes:</strong> <%= ingredientes %></p>
                </div>
            </div>
        </div>
        <%
                }

                rs.close();
                ps.close();
            } catch (Exception e) {
                out.println("<div class='alert alert-danger'>Error al cargar los platos.</div>");
                e.printStackTrace();
            } finally {
                if (con != null) try { con.close(); } catch (SQLException ignore) {}
            }
        %>
    </div>
</div>
</body>
</html>
