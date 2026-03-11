package main.java.pokemon.dao;

import main.java.pokemon.model.Carta;

import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDAO {

    public int insertar(Carta carta) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "INSERT INTO cartas (dueno, nombre, puntos, tipo, fecha_alta, estado) VALUES (" +
                "'" + escape(carta.getDueno()) + "', " +
                "'" + escape(carta.getNombre()) + "', " +
                carta.getPuntos() + ", " +
                "'" + escape(carta.getTipo()) + "', " +
                "'" + escape(carta.getFechaAlta()) + "', " +
                "'" + escape(carta.getEstado()) + "'" +
                ")";

        int resultado = db.executeUpdate(sql);
        db.disconnect();
        return resultado;
    }

    public Carta obtenerPorId(int id) {
        DatabaseManager db = new DatabaseManager();
        db.connect();

        String sql = "SELECT * FROM cartas WHERE id = " + id;
        ResultSet rs = db.executeQuery(sql);

        Carta carta = null;

        try {
            if (rs != null && rs.next()) {
                carta = new Carta();
                carta.setId(rs.getInt("id"));
                carta.setDueno(rs.getString("dueno"));
                carta.setNombre(rs.getString("nombre"));
                carta.setPuntos(rs.getInt("puntos"));
                carta.setTipo(rs.getString("tipo"));
                carta.setFechaAlta(rs.getString("fecha_alta"));
                carta.setEstado(rs.getString("estado"));
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

        String sql = "UPDATE cartas SET " +
                "nombre = '" + escape(carta.getNombre()) + "', " +
                "puntos = " + carta.getPuntos() + ", " +
                "tipo = '" + escape(carta.getTipo()) + "', " +
                "estado = '" + escape(carta.getEstado()) + "' " +
                "WHERE id = " + carta.getId() + " " +
                "AND dueno = '" + escape(usuario) + "'";

        int filas = db.executeUpdate(sql);
        db.disconnect();

        return filas > 0;
    }

    private String escape(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("'", "''");
    }
}

public List<Carta> listarDisponiblesOtros(String usuario) {

    DatabaseManager db = new DatabaseManager();
    db.connect();

    List<Carta> lista = new ArrayList<>();

    String sql = "SELECT * FROM cartas WHERE dueno <> '" + usuario + "' AND estado = 'Disponible'";

    ResultSet rs = db.executeQuery(sql);

    try {
        while (rs != null && rs.next()) {
            Carta c = new Carta();
            c.setId(rs.getInt("id"));
            c.setDueno(rs.getString("dueno"));
            c.setNombre(rs.getString("nombre"));
            c.setPuntos(rs.getInt("puntos"));
            c.setTipo(rs.getString("tipo"));
            c.setFechaAlta(rs.getString("fecha_alta"));
            c.setEstado(rs.getString("estado"));
            lista.add(c);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.disconnect();
    }

    return lista;
}

public List<Carta> listarDisponiblesMias(String usuario) {

    DatabaseManager db = new DatabaseManager();
    db.connect();

    List<Carta> lista = new ArrayList<>();

    String sql = "SELECT * FROM cartas WHERE dueno = '" + usuario + "' AND estado = 'Disponible'";

    ResultSet rs = db.executeQuery(sql);

    try {
        while (rs != null && rs.next()) {
            Carta c = new Carta();
            c.setId(rs.getInt("id"));
            c.setDueno(rs.getString("dueno"));
            c.setNombre(rs.getString("nombre"));
            c.setPuntos(rs.getInt("puntos"));
            c.setTipo(rs.getString("tipo"));
            c.setFechaAlta(rs.getString("fecha_alta"));
            c.setEstado(rs.getString("estado"));
            lista.add(c);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.disconnect();
    }

    return lista;
}