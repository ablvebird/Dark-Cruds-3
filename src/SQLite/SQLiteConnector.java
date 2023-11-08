package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class for connecting to an SQLite database.
 */
public class SQLiteConnector {
    private static final String DB_URL = "jdbc:sqlite:";
    private static final String FULL_PATH = "DarkCruds.db";

    /**
     * Connect to the SQLite database.
     *
     * @return A Connection object representing the database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC driver loaded");

            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL + FULL_PATH);
            System.out.println("Connection to the database established");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the database connection.
     *
     * @param connection The Connection object to be closed.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to the database closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
