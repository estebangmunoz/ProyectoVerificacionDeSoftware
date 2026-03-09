<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pokemon.model.Carta" %>
<%@ page import="pokemon.model.Constantes" %>
<%
    Carta carta = (Carta) request.getAttribute("carta");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Carta</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header class="header">
    <div class="header-left">
        <h1>Pikachu Forever</h1>
    </div>

    <nav class="menu">
        <a class="active" href="${pageContext.request.contextPath}/mi-coleccion">Mi Colección</a>
        <a href="${pageContext.request.contextPath}/solicitudes/recibidas">Solicitudes Recibidas</a>
        <a href="${pageContext.request.contextPath}/solicitudes/enviadas">Solicitudes Enviadas</a>
    </nav>

    <div class="user-info">
        <div class="user-name">${sessionScope.usuario}</div>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Cerrar sesión</a>
    </div>
</header>

<main>
    <div class="content-container">
        <h2>Modificar Carta</h2>

        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <% if (carta != null) { %>
        <form method="post" action="${pageContext.request.contextPath}/carta/editar">
            <input type="hidden" name="idCarta" value="<%= carta.getId() %>">

            <div>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" maxlength="20"
                       value="<%= carta.getNombre() %>" required>
            </div>

            <div>
                <label for="puntos">Puntos:</label>
                <input type="number" id="puntos" name="puntos" min="0" max="9999"
                       value="<%= carta.getPuntos() %>" required>
            </div>

            <div>
                <label for="tipo">Tipo:</label>
                <select id="tipo" name="tipo" required>
                    <option value="Planta" <%= "Planta".equals(carta.getTipo()) ? "selected" : "" %>>Planta</option>
                    <option value="Eléctrico" <%= "Eléctrico".equals(carta.getTipo()) ? "selected" : "" %>>Eléctrico</option>
                    <option value="Agua" <%= "Agua".equals(carta.getTipo()) ? "selected" : "" %>>Agua</option>
                    <option value="Dragón" <%= "Dragón".equals(carta.getTipo()) ? "selected" : "" %>>Dragón</option>
                </select>
            </div>

            <div>
                <label for="estado">Estado:</label>
                <select id="estado" name="estado" required>
                    <option value="<%= Constantes.CARTA_DISPONIBLE %>" <%= Constantes.CARTA_DISPONIBLE.equals(carta.getEstado()) ? "selected" : "" %>><%= Constantes.CARTA_DISPONIBLE %></option>
                    <option value="<%= Constantes.CARTA_RESERVADO %>" <%= Constantes.CARTA_RESERVADO.equals(carta.getEstado()) ? "selected" : "" %>><%= Constantes.CARTA_RESERVADO %></option>
                    <option value="<%= Constantes.CARTA_INTRANSFERIBLE %>" <%= Constantes.CARTA_INTRANSFERIBLE.equals(carta.getEstado()) ? "selected" : "" %>><%= Constantes.CARTA_INTRANSFERIBLE %></option>
                </select>
            </div>

            <div>
                <button class="aceptar" type="submit">Guardar cambios</button>
                <a href="${pageContext.request.contextPath}/mi-coleccion">
                    <button class="cancelar" type="button">Cancelar</button>
                </a>
            </div>
        </form>
        <% } else { %>
        <p>No se ha encontrado la carta.</p>
        <% } %>
    </div>
</main>
</body>
</html>