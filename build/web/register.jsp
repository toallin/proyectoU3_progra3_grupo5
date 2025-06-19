<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro Usuario</title>
</head>
<body>
<h2>Registrar nuevo usuario</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="usuario" method="post">
    <input type="hidden" name="accion" value="register" />
    
    <label for="nombre">Nombre completo:</label><br/>
    <input type="text" id="nombre" name="nombre" required /><br/><br/>
    
    <label for="email">Email:</label><br/>
    <input type="email" id="email" name="email" required /><br/><br/>
    
    <label for="contrasena">Contraseña:</label><br/>
    <input type="password" id="contrasena" name="contrasena" required /><br/><br/>
    
    <label for="direccion">Dirección:</label><br/>
    <input type="text" id="direccion" name="direccion" required /><br/><br/>
    
    <button type="submit">Registrar</button>
</form>

<p>¿Ya tienes cuenta? <a href="usuario">Inicia sesión aquí</a></p>
</body>
</html>
