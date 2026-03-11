package pokemon.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());

    private Connection connection;

    // CONFIGURACIÓN CORREGIDA
    // 1. Nombre de BD en minúsculas (como salió en tu terminal)
    // 2. Usuario 'root' es más seguro para evitar fallos de permisos ahora mismo
    private String url = "jdbc:mysql://localhost:3306/cartaspokemon?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private String user = "root";
    private String password = "12345678"; // SI TU ROOT TIENE CONTRASEÑA, PONLA AQUÍ

    public DatabaseManager() {
        // Forzamos la conexión al instanciar para evitar el NullPointerException
        connect();
    }

    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                logger.info("¡Conexión establecida con éxito a cartaspokemon!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "ERROR CRÍTICO: No se pudo conectar a la base de datos. " +
                    "Revisa si el nombre 'cartaspokemon', el usuario '" + user + "' o la contraseña son correctos.", e);
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Conexión cerrada.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cerrar la conexión", e);
        }
    }

    public int executeUpdate(String sql) {
        // Aseguramos que la conexión esté abierta antes de usarla
        if (connection == null) connect();

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error en update: " + sql, e);
            return -1;
        }
    }

    public ResultSet executeQuery(String sql) {
        if (connection == null) connect();

        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error en query: " + sql, e);
            return null;
        }
    }

    public Connection getConnection() {
        if (connection == null) connect();
        return connection;
    }

    public boolean executeTransaction(String[] sqlStatements) {
        boolean resultado = false;
        if (sqlStatements != null) {
            if (connection == null) connect();
            try {
                connection.setAutoCommit(false);
                try (Statement stmt = connection.createStatement()) {
                    for (String sql : sqlStatements) {
                        stmt.executeUpdate(sql);
                    }
                }
                connection.commit();
                logger.info("Transacción completada con éxito.");
                resultado = true;
            } catch (SQLException e) {
                try {
                    if (connection != null) connection.rollback();
                    logger.log(Level.SEVERE, "Error en la transacción, haciendo rollback", e);
                } catch (SQLException rollbackEx) {
                    logger.log(Level.SEVERE, "Error al hacer rollback", rollbackEx);
                }
            } finally {
                try {
                    if (connection != null) connection.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Error al restaurar autocommit", e);
                }
            }
        }
        return resultado;
    }
}