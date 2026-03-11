package pokemon.dao;

import pokemon.model.Solicitud;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    public boolean crearSolicitud(int cartaSolicitadaId, int cartaOfrecidaId, String duenoSolicita, String duenoCartaSolicitada) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        String sql = "INSERT INTO solicitudes (id_carta1, Dueno1, id_carta2, Dueno2, Estado, FechaSolicitud) VALUES (" +
                cartaSolicitadaId + ", '" + duenoCartaSolicitada + "', " +
                cartaOfrecidaId + ", '" + duenoSolicita + "', 'Pendiente', CURRENT_DATE)";
        int res = db.executeUpdate(sql);
        db.disconnect();
        return res > 0;
    }

    public List<Solicitud> listarEnviadas(String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        List<Solicitud> lista = new ArrayList<>();
        // OJO: Asegúrate de que tus tablas en SQL se llaman 'solicitudes' y 'cartas'
        String sql = "SELECT s.id_solicitud, s.id_carta1, c1.nombre AS nombreSolicitada, " +
                "s.id_carta2, c2.nombre AS nombreOfrecida, s.Dueno2, s.Estado, s.FechaSolicitud " +
                "FROM solicitudes s " +
                "JOIN carta c1 ON s.id_carta1 = c1.id_carta " +
                "JOIN carta c2 ON s.id_carta2 = c2.id_carta " +
                "WHERE s.Dueno2 = '" + escape(usuario) + "'";

        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                Solicitud s = new Solicitud();
                s.setId(rs.getInt("id_solicitud"));
                s.setCartaSolicitada(rs.getInt("id_carta1"));
                s.setNombreCartaSolicitada(rs.getString("nombreSolicitada"));
                s.setCartaOfrecida(rs.getInt("id_carta2"));
                s.setNombreCartaOfrecida(rs.getString("nombreOfrecida"));
                s.setUsuarioSolicita(rs.getString("Dueno2"));
                s.setEstado(rs.getString("Estado"));
                s.setFechaSolicitud(rs.getString("FechaSolicitud"));
                lista.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return lista;
    }

    private String escape(String valor) {
        if (valor == null) return "";
        return valor.replace("'", "''");
    }
    public Solicitud obtenerPorId(int id) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        String sql = "SELECT * FROM solicitudes WHERE id_solicitud = " + id;
        ResultSet rs = db.executeQuery(sql);
        Solicitud s = null;
        try {
            if (rs != null && rs.next()) {
                s = new Solicitud();
                s.setId(rs.getInt("id_solicitud"));
                s.setCartaSolicitada(rs.getInt("id_carta1"));
                s.setCartaOfrecida(rs.getInt("id_carta2"));
                s.setUsuarioSolicita(rs.getString("Dueno2"));
                s.setDuenoOriginal(rs.getString("Dueno1"));
                s.setEstado(rs.getString("Estado"));
                s.setFechaSolicitud(rs.getString("FechaSolicitud"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return s;
    }
    public boolean actualizarEstado(int id, String estado) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "UPDATE solicitudes SET Estado = '" + escape(estado) + "' WHERE id_solicitud = " + id;

        int res = db.executeUpdate(sql);
        db.disconnect();

        return res > 0;
    }
    public List<Solicitud> listarRecibidas(String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        List<Solicitud> lista = new ArrayList<>();

        // SQL que busca las solicitudes donde el usuario actual es el dueño de la carta solicitada (Dueno1)
        String sql = "SELECT s.id_solicitud, s.id_carta1, c1.nombre AS nombreSolicitada, " +
                "s.id_carta2, c2.nombre AS nombreOfrecida, s.Dueno2, s.Estado, s.FechaSolicitud " +
                "FROM solicitudes s " +
                "JOIN carta c1 ON s.id_carta1 = c1.id_carta " +
                "JOIN carta c2 ON s.id_carta2 = c2.id_carta " +
                "WHERE s.Dueno1 = '" + escape(usuario) + "'";

        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                Solicitud s = new Solicitud();
                s.setId(rs.getInt("id_solicitud"));
                s.setCartaSolicitada(rs.getInt("id_carta1"));
                s.setNombreCartaSolicitada(rs.getString("nombreSolicitada"));
                s.setCartaOfrecida(rs.getInt("id_carta2"));
                s.setNombreCartaOfrecida(rs.getString("nombreOfrecida"));
                s.setUsuarioSolicita(rs.getString("Dueno2"));
                s.setEstado(rs.getString("Estado"));
                s.setFechaSolicitud(rs.getString("FechaSolicitud"));
                lista.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return lista;
    }
} // <--- CERRAR CLASE