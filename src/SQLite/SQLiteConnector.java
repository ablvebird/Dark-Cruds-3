package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona las conexiones con la base de datos SQLite.
 * Proporciona métodos para conectar y desconectar de la base de datos.
 */
public class SQLiteConnector {
    // URL base para conexión con SQLite
    private static final String DB_URL = "jdbc:sqlite:";
    // Ruta donde se encuentra nuestra base de datos
    private static final String FULL_PATH = "resources/DarkCruds.db";

    /**
     * Establece una conexión con la base de datos SQLite.
     *
     * @return Objeto Connection que representa la conexión con la base de datos
     * @throws SQLException si hay algún error al acceder a la base de datos
     */
    public static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            // Cargar el driver de SQLite (necesario para conectar con la base de datos)
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver SQLite cargado correctamente");

            // Crear la conexión usando la URL y la ruta de la base de datos
            connection = DriverManager.getConnection(DB_URL + FULL_PATH);
            System.out.println("Conexión establecida con la base de datos");
        } catch (ClassNotFoundException e) {
            // Si no se encuentra el driver de SQLite, mostrar el error
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Cierra la conexión con la base de datos.
     * Es importante cerrar la conexión cuando ya no se necesita.
     *
     * @param connection La conexión que queremos cerrar
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                // Si hay error al cerrar la conexión, mostrar el error
                e.printStackTrace();
            }
        }
    }
}