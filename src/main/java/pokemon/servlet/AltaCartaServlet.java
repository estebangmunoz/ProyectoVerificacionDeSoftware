package pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pokemon.dao.CartaDAO;
import pokemon.model.Carta;
import pokemon.model.Constantes;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/carta/nueva")
public class AltaCartaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
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
        String nombre = request.getParameter("nombre");
        String puntosStr = request.getParameter("puntos");
        String tipo = request.getParameter("tipo");
        String estado = request.getParameter("estado");

        if (nombre == null || nombre.trim().isEmpty()
                || puntosStr == null || puntosStr.trim().isEmpty()
                || tipo == null || tipo.trim().isEmpty()
                || estado == null || estado.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
            return;
        }

        int puntos;
        try {
            puntos = Integer.parseInt(puntosStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los puntos deben ser un número válido.");
            request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
            return;
        }

        if (puntos < 0 || puntos > 9999) {
            request.setAttribute("error", "Los puntos deben estar entre 0 y 9999.");
            request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
            return;
        }

        if (!estado.equals(Constantes.CARTA_DISPONIBLE)
                && !estado.equals(Constantes.CARTA_RESERVADO)
                && !estado.equals(Constantes.CARTA_INTRANSFERIBLE)) {
            request.setAttribute("error", "Estado de carta no válido.");
            request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
            return;
        }

        Carta carta = new Carta();
        carta.setDueno(usuario);
        carta.setNombre(nombre.trim());
        carta.setPuntos(puntos);
        carta.setTipo(tipo.trim());
        carta.setEstado(estado);
        carta.setFechaAlta(LocalDate.now().toString());

        CartaDAO cartaDAO = new CartaDAO();
        int resultado = cartaDAO.insertar(carta);

        if (resultado <= 0) {
            request.setAttribute("error", "No se pudo guardar la carta.");
            request.getRequestDispatcher("/altaCarta.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/mi-coleccion");
    }
}