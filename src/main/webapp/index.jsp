<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Si ya hay sesión iniciada, la limpiamos al entrar al login
    session.invalidate();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
<div class="login-container">

    <!-- Ahora el formulario usa POST y va al servlet /login -->
    <form class="login-form" method="post" action="login">
        <h2>Inicio de Sesión</h2>

        <label for="usuario">Usuario:</label>
        <input type="text" id="usuario" name="usuario" maxlength="8"
               placeholder="Máx. 8 caracteres" required>

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" maxlength="8"
               placeholder="Máx. 8 caracteres" required>

        <button type="submit">Aceptar</button>

        <!-- Mostrar error si viene del LoginServlet -->
        <% if (request.getAttribute("error") != null) { %>
        <p class="mensaje_error"><%= request.getAttribute("error") %></p>
        <% } %>
    </form>

</div>
</body>
</html>
