package pokemon.dao;

// 1. Corregido el import (quitamos main.java)
import pokemon.model.Carta;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

// 2. FALTA ESTA LÍNEA: Define la clase
public class CartaDAO {

    // Método para tus compañeros
    public List<Carta> listarDisponiblesOtros(String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        List<Carta> lista = new ArrayList<>();
        String sql = "SELECT * FROM carta WHERE dueno <> '" + usuario + "' AND estado = 'Disponible'";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                lista.add(mapearCarta(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { db.disconnect(); }
        return lista;
    }

    // TU MÉTODO: Listar mis cartas (para MiColeccionServlet)
    public List<Carta> listarCartasPorUsuario(String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        List<Carta> lista = new ArrayList<>();
        String sql = "SELECT * FROM carta WHERE dueno = '" + usuario + "'";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs != null && rs.next()) {
                lista.add(mapearCarta(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { db.disconnect(); }
        return lista;
    }

    // TU MÉTODO: Borrar carta (para BorrarCartaServlet)
    public boolean borrar(int id, String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        String sql = "DELETE FROM carta WHERE id_carta = " + id + " AND dueno = '" + usuario + "'";
        int filas = db.executeUpdate(sql);
        db.disconnect();
        return filas > 0;
    }

    // Método auxiliar para no repetir código (mapear datos de la BD a objeto Java)
    private Carta mapearCarta(ResultSet rs) throws SQLException {
        Carta c = new Carta();
        c.setId(rs.getInt("id_carta")); // Asegúrate de que en SQL se llama id_carta
        c.setDueno(rs.getString("dueno"));
        c.setNombre(rs.getString("nombre"));
        c.setPuntos(rs.getInt("puntos"));
        c.setTipo(rs.getString("tipo"));
        c.setEstado(rs.getString("estado"));
        return c;
    }
    public int insertar(Carta carta) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "INSERT INTO carta (dueno, nombre, puntos, tipo, estado) VALUES (" +
                "'" + carta.getDueno() + "', " +
                "'" + carta.getNombre() + "', " +
                carta.getPuntos() + ", " +
                "'" + carta.getTipo() + "', " +
                "'" + carta.getEstado() + "'" +
                ")";

        int resultado = db.executeUpdate(sql);
        db.disconnect();
        return resultado;
    }
    public Carta obtenerPorId(int id) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "SELECT * FROM carta WHERE id_carta = " + id;
        ResultSet rs = db.executeQuery(sql);

        Carta carta = null;

        try {
            if (rs != null && rs.next()) {
                carta = mapearCarta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }

        return carta;
    }
    public boolean actualizar(Carta carta, String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "UPDATE carta SET " +
                "nombre = '" + carta.getNombre() + "', " +
                "puntos = " + carta.getPuntos() + ", " +
                "tipo = '" + carta.getTipo() + "', " +
                "estado = '" + carta.getEstado() + "' " +
                "WHERE id_carta = " + carta.getId() + " " +
                "AND dueno = '" + usuario + "'";

        int filas = db.executeUpdate(sql);
        db.disconnect();

        return filas > 0;
    }
    public List<Carta> listarDisponiblesMias(String usuario) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        List<Carta> lista = new ArrayList<>();

        // Buscamos solo las cartas del usuario que tengan estado 'Disponible'
        String sql = "SELECT * FROM carta WHERE dueno = '" + usuario + "' AND estado = 'Disponible'";

        ResultSet rs = db.executeQuery(sql);

        try {
            while (rs != null && rs.next()) {
                lista.add(mapearCarta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }

        return lista;
    }

}