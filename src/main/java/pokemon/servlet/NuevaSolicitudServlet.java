package main.java.pokemon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.pokemon.dao.CartaDAO;
import main.java.pokemon.model.Carta;

import java.io.IOException;
import java.util.List;

@WebServlet("/solicitud/nueva")
public class NuevaSolicitudServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String usuario = (String) session.getAttribute("usuario");

        CartaDAO dao = new CartaDAO();
        List<Carta> cartasOtros = dao.listarDisponiblesOtros(usuario);
        List<Carta> misCartas = dao.listarDisponiblesMias(usuario);

        request.setAttribute("cartasOtros", cartasOtros);
        request.setAttribute("misCartas", misCartas);

        request.getRequestDispatcher("/nuevaSolicitud.jsp").forward(request, response);
    }
}