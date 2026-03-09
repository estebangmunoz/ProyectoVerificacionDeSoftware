package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.java.pokemon.dao.SolicitudDAO;
import main.java.pokemon.model.Solicitud;

import java.io.IOException;
import java.util.List;

@WebServlet("/solicitudes/recibidas")
public class SolicitudesRecibidasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");
        SolicitudDAO dao = new SolicitudDAO();
        List<Solicitud> solicitudes = dao.listarRecibidas(usuario);

        request.setAttribute("solicitudes", solicitudes);
        request.getRequestDispatcher("/solicitudesRecibidas.jsp").forward(request, response);
    }
}
