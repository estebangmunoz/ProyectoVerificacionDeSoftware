package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.java.pokemon.dao.DatabaseManager;
import main.java.pokemon.dao.SolicitudDAO;
import main.java.pokemon.model.Constantes;
import main.java.pokemon.model.Solicitud;

import java.io.IOException;

@WebServlet("/solicitud/aceptar")
public class AceptarSolicitudServlet extends HttpServlet {

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

        // Validaciones previas
        if (solicitud == null || !usuario.equals(solicitud.getDuenoOriginal())) {
            request.setAttribute("error", "La solicitud no existe o no te pertenece.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
            return;
        }

        if (!Constantes.SOLICITUD_PENDIENTE.equals(solicitud.getEstado())) {
            request.setAttribute("error", "Solo se pueden aceptar solicitudes en estado Pendiente.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
            return;
        }

        DatabaseManager db = new DatabaseManager();
        db.connect();

        // 3 sentencias para la transacción
        String[] sqlStatements = new String[3];

        // 1. Carta Solicitada pasa a ser del usuario que la pidió
        sqlStatements[0] = "UPDATE cartas SET dueno = '" + escape(solicitud.getUsuarioSolicita()) + "' WHERE id = " + solicitud.getCartaSolicitada();

        // 2. Carta Ofrecida pasa a ser del dueño original (el usuario logueado)
        sqlStatements[1] = "UPDATE cartas SET dueno = '" + escape(solicitud.getDuenoOriginal()) + "' WHERE id = " + solicitud.getCartaOfrecida();

        // 3. Solicitud pasa a Aceptada
        sqlStatements[2] = "UPDATE solicitudes SET estado = '" + Constantes.SOLICITUD_ACEPTADA + "' WHERE id_solicitud = " + idSolicitud;

        boolean exito = db.executeTransaction(sqlStatements);
        db.disconnect();

        if (exito) {
            response.sendRedirect(request.getContextPath() + "/solicitudes/recibidas");
        } else {
            request.setAttribute("error", "Error al procesar la transacción de intercambio.");
            request.getRequestDispatcher("/solicitudes/recibidas").forward(request, response);
        }
    }

    private String escape(String valor) {
        if (valor == null) return "";
        return valor.replace("'", "''");
    }
}