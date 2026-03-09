package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.pokemon.dao.CartaDAO;
import main.java.pokemon.model.Carta;
import main.java.pokemon.model.Constantes;

import java.io.IOException;

@WebServlet("/carta/editar")
public class ModificarCartaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");
        String idCartaParam = request.getParameter("idCarta");

        if (idCartaParam == null || idCartaParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/mi-coleccion");
            return;
        }

        int idCarta;
        try {
            idCarta = Integer.parseInt(idCartaParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/mi-coleccion");
            return;
        }

        CartaDAO cartaDAO = new CartaDAO();
        Carta carta = cartaDAO.obtenerPorId(idCarta);

        if (carta == null || !usuario.equals(carta.getDueno())) {
            response.sendRedirect(request.getContextPath() + "/mi-coleccion");
            return;
        }

        request.setAttribute("carta", carta);
        request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");
        String idCartaParam = request.getParameter("idCarta");
        String nombre = request.getParameter("nombre");
        String puntosStr = request.getParameter("puntos");
        String tipo = request.getParameter("tipo");
        String estado = request.getParameter("estado");

        if (idCartaParam == null || idCartaParam.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || puntosStr == null || puntosStr.trim().isEmpty()
                || tipo == null || tipo.trim().isEmpty()
                || estado == null || estado.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
            return;
        }

        int idCarta;
        int puntos;
        try {
            idCarta = Integer.parseInt(idCartaParam);
            puntos = Integer.parseInt(puntosStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID o puntos no válidos.");
            request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
            return;
        }

        if (puntos < 0 || puntos > 9999) {
            request.setAttribute("error", "Los puntos deben estar entre 0 y 9999.");
            request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
            return;
        }

        if (!estado.equals(Constantes.CARTA_DISPONIBLE)
                && !estado.equals(Constantes.CARTA_RESERVADO)
                && !estado.equals(Constantes.CARTA_INTRANSFERIBLE)) {
            request.setAttribute("error", "Estado de carta no válido.");
            request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
            return;
        }

        CartaDAO cartaDAO = new CartaDAO();
        Carta cartaExistente = cartaDAO.obtenerPorId(idCarta);

        if (cartaExistente == null || !usuario.equals(cartaExistente.getDueno())) {
            response.sendRedirect(request.getContextPath() + "/mi-coleccion");
            return;
        }

        Carta cartaActualizada = new Carta();
        cartaActualizada.setId(idCarta);
        cartaActualizada.setNombre(nombre.trim());
        cartaActualizada.setPuntos(puntos);
        cartaActualizada.setTipo(tipo.trim());
        cartaActualizada.setEstado(estado);

        boolean actualizado = cartaDAO.actualizar(cartaActualizada, usuario);

        if (!actualizado) {
            Carta cartaRecargada = cartaDAO.obtenerPorId(idCarta);
            request.setAttribute("error", "No se pudo actualizar la carta.");
            request.setAttribute("carta", cartaRecargada);
            request.getRequestDispatcher("/modificarCarta.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/mi-coleccion");
    }
}