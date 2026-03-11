package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.pokemon.dao.SolicitudDAO;
import main.java.pokemon.model.Solicitud;

import java.io.IOException;
import java.util.List;

@WebServlet("/solicitudes/enviadas")
public class SolicitudesEnviadasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");

        SolicitudDAO dao = new SolicitudDAO();
        List<Solicitud> solicitudes = dao.listarEnviadas(usuario);

        request.setAttribute("solicitudes", solicitudes);
        request.getRequestDispatcher("/solicitudesEnviadas.jsp").forward(request, response);
    }
}