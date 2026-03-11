<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.pokemon.model.Solicitud" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Solicitudes Enviadas</title>
</head>
<body>
    <h2>Mis solicitudes enviadas</h2>

    <a href="${pageContext.request.contextPath}/solicitud/nueva">Nueva solicitud</a>

    <br><br>

    <table border="1" cellpadding="6">
        <tr>
            <th>ID</th>
            <th>Carta solicitada</th>
            <th>Carta ofrecida</th>
            <th>Dueño carta solicitada</th>
            <th>Estado</th>
            <th>Fecha</th>
        </tr>

        <%
            List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
            if (solicitudes != null) {
                for (Solicitud s : solicitudes) {
        %>
        <tr>
            <td><%= s.getId() %></td>
            <td><%= s.getNombreCartaSolicitada() %></td>
            <td><%= s.getNombreCartaOfrecida() %></td>
            <td><%= s.getDuenoOriginal() %></td>
            <td><%= s.getEstado() %></td>
            <td><%= s.getFechaSolicitud() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>