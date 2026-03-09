<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.pokemon.model.Solicitud" %>
<%
    List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Solicitudes Recibidas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>Pikachu Forever</h1>
    </div>

    <nav class="menu">
        <a href="${pageContext.request.contextPath}/mi-coleccion">Mi Colección</a>
        <a class="active" href="${pageContext.request.contextPath}/solicitudes/recibidas">Solicitudes Recibidas</a>
        <a href="${pageContext.request.contextPath}/solicitudes/enviadas">Solicitudes Enviadas</a>
    </nav>

    <div class="user-info">
        <div class="user-name">${sessionScope.usuario}</div>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Cerrar sesión</a>
    </div>
</header>

<main>
    <div class="content-container">
        <h2>Solicitudes Recibidas</h2>
        <p>En esta sección verás todas las solicitudes de intercambio que hayas recibido a otros usuarios.</p>

        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <form method="post">
            <div style="margin-bottom: 15px;">
                <button type="submit" formaction="${pageContext.request.contextPath}/solicitud/aceptar" class="aceptar">Aceptar Solicitud</button>
                <button type="submit" formaction="${pageContext.request.contextPath}/solicitud/rechazar" class="cancelar">Rechazar Solicitud</button>
            </div>

            <table class="styled-table" border="1" style="width: 100%; border-collapse: collapse; text-align: center;">
                <thead>
                <tr>
                    <th>Seleccionar</th>
                    <th>Carta Solicitada</th>
                    <th>Carta a cambio</th>
                    <th>Dueño</th>
                    <th>Estado</th>
                    <th>Fecha Solicitud</th>
                </tr>
                </thead>
                <tbody>
                <% if (solicitudes != null && !solicitudes.isEmpty()) {
                    for (Solicitud s : solicitudes) { %>
                <tr>
                    <td><input type="radio" name="idSolicitud" value="<%= s.getId() %>" required></td>
                    <td><%= s.getNombreCartaSolicitada() %></td>
                    <td><%= s.getNombreCartaOfrecida() %></td>
                    <td><%= s.getUsuarioSolicita() %></td>
                    <td><%= s.getEstado() %></td>
                    <td><%= s.getFechaSolicitud() %></td>
                </tr>
                <%  }
                } else { %>
                <tr>
                    <td colspan="6">No tienes solicitudes recibidas.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </form>
    </div>
</main>
</body>
</html>