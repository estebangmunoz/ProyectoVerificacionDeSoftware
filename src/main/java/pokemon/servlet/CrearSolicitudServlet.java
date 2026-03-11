package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.pokemon.dao.CartaDAO;
import main.java.pokemon.dao.SolicitudDAO;
import main.java.pokemon.model.Carta;

import java.io.IOException;

@WebServlet("/solicitud/crear")
public class CrearSolicitudServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");

        try {
            int cartaSolicitadaId = Integer.parseInt(request.getParameter("cartaSolicitadaId"));
            int cartaOfrecidaId = Integer.parseInt(request.getParameter("cartaOfrecidaId"));

            CartaDAO cartaDAO = new CartaDAO();
            Carta cartaSolicitada = cartaDAO.obtenerPorId(cartaSolicitadaId);
            Carta cartaOfrecida = cartaDAO.obtenerPorId(cartaOfrecidaId);

            if (cartaSolicitada == null || cartaOfrecida == null) {
                response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
                return;
            }

            if (cartaSolicitada.getDueno().equals(usuario)) {
                response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
                return;
            }

            if (!cartaOfrecida.getDueno().equals(usuario)) {
                response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
                return;
            }

            if (!"Disponible".equals(cartaSolicitada.getEstado()) || !"Disponible".equals(cartaOfrecida.getEstado())) {
                response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
                return;
            }

            SolicitudDAO solicitudDAO = new SolicitudDAO();
            boolean creada = solicitudDAO.crearSolicitud(
                    cartaSolicitadaId,
                    cartaOfrecidaId,
                    usuario,
                    cartaSolicitada.getDueno()
            );

            if (creada) {
                response.sendRedirect(request.getContextPath() + "/solicitudes/enviadas");
            } else {
                response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/solicitud/nueva");
        }
    }
}