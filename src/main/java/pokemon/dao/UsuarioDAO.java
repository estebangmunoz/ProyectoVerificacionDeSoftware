package pokemon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean validar(String usuario, String password) {
        DatabaseManager db = new DatabaseManager();
        boolean valido = false;

        try {
            db.connect();

            String sql = "SELECT * FROM usuario WHERE usuario='" + usuario +
                    "' AND password='" + password + "'";

            ResultSet rs = db.executeQuery(sql);

            if (rs != null && rs.next()) {
                valido = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }

        return valido;
    }
}
