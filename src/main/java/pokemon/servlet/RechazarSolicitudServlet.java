package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.java.pokemon.dao.SolicitudDAO;
import main.java.pokemon.model.Constantes;
import main.java.pokemon.model.Solicitud;

import java.io.IOException;

@WebServlet("/solicitud/rechazar")
public class RechazarSolicitudServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");
        String idSolicitudStr = request.getParameter("idSolicitud");

        if (idSolicitudStr == null || idSolicitudStr.trim().isEmpty()) {
            request.setAttribute("error", "Debe seleccionar una solicitud.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
            return;
        }

        int idSolicitud = Integer.parseInt(idSolicitudStr);
        SolicitudDAO dao = new SolicitudDAO();
        Solicitud solicitud = dao.obtenerPorId(idSolicitud);

        if (solicitud == null || !usuario.equals(solicitud.getDuenoOriginal())) {
            request.setAttribute("error", "La solicitud no existe o no te pertenece.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
            return;
        }

        if (!Constantes.SOLICITUD_PENDIENTE.equals(solicitud.getEstado())) {
            request.setAttribute("error", "Solo se pueden rechazar solicitudes en estado Pendiente.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
            return;
        }

        boolean exito = dao.actualizarEstado(idSolicitud, Constantes.SOLICITUD_RECHAZADA);

        if (exito) {
            response.sendRedirect(request.getContextPath() + "/solicitudes/recibidas");
        } else {
            request.setAttribute("error", "Error al intentar rechazar la solicitud.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
        }
    }
}