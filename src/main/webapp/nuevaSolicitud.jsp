<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.pokemon.model.Carta" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nueva Solicitud</title>
</head>
<body>
    <h2>Crear nueva solicitud</h2>

    <form method="post" action="${pageContext.request.contextPath}/solicitud/crear">
        <div>
            <label for="cartaSolicitadaId">Carta que quiero:</label>
            <select name="cartaSolicitadaId" id="cartaSolicitadaId" required>
                <%
                    List<Carta> cartasOtros = (List<Carta>) request.getAttribute("cartasOtros");
                    if (cartasOtros != null) {
                        for (Carta c : cartasOtros) {
                %>
                    <option value="<%= c.getId() %>">
                        <%= c.getNombre() %> - dueño: <%= c.getDueno() %> - estado: <%= c.getEstado() %>
                    </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <br>

        <div>
            <label for="cartaOfrecidaId">Carta que ofrezco:</label>
            <select name="cartaOfrecidaId" id="cartaOfrecidaId" required>
                <%
                    List<Carta> misCartas = (List<Carta>) request.getAttribute("misCartas");
                    if (misCartas != null) {
                        for (Carta c : misCartas) {
                %>
                    <option value="<%= c.getId() %>">
                        <%= c.getNombre() %> - estado: <%= c.getEstado() %>
                    </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <br>

        <button type="submit">Crear solicitud</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/solicitudes/enviadas">Ver mis solicitudes enviadas</a>
</body>
</html>