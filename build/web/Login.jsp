<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Usuario</title>
</head>
<body>

<h2>Iniciar sesión</h2>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form action="usuario" method="post">
    <input type="hidden" name="accion" value="login" />

    <label>Email:</label><br/>
    <input type="email" name="email" required /><br/><br/>

    <label>Contraseña:</label><br/>
    <input type="password" name="contrasena" required /><br/><br/>

    <button type="submit">Ingresar</button>
</form>

<p>¿No tienes cuenta? <a href="usuario?accion=register">Regístrate aquí</a></p>

</body>
</html>
