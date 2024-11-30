package SQLite;

import Entities.Boss;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que maneja las operaciones CRUD (Crear, Leer, Actualizar, Borrar)
 * para los jefes en la base de datos SQLite.
 */
public class SQLiteCRUD {
    private Connection connection;

    /**
     * Constructor por defecto.
     */
    public SQLiteCRUD() {}

    /**
     * Añade un nuevo jefe a la base de datos.
     *
     * @param boss El jefe que queremos añadir
     */
    public void insertBoss(Boss boss) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Boss (name, location, hp, poise, souls, drop_name, description) VALUES (?, ?, ?, ?, ?, ?, ?)"
        )) {
            // Preparar los datos del jefe para la inserción
            statement.setString(1, boss.getBossName());
            statement.setString(2, boss.getLocation());
            statement.setInt(3, boss.getHP());
            statement.setDouble(4, boss.getPoise());
            statement.setInt(5, boss.getSouls());
            statement.setString(6, boss.getDropName());
            statement.setString(7, boss.getDescription());

            statement.executeUpdate();
            System.out.println("Jefe añadido a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un jefe de la base de datos usando su ID.
     *
     * @param bossID El ID del jefe que queremos borrar
     */
    public void deleteBoss(int bossID) {
        String query = "DELETE FROM Boss WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bossID);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Jefe con ID " + bossID + " borrado de la base de datos.");
            } else {
                System.out.println("No se encontró ningún jefe con ID " + bossID + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la información de un jefe en la base de datos.
     *
     * @param bossID   ID del jefe a actualizar
     * @param field    Campo que queremos actualizar
     * @param newInfo  Nueva información para ese campo
     */
    public void updateBoss(int bossID, String field, String newInfo) {
        String query = "UPDATE Boss SET " + field + " = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newInfo);
            statement.setInt(2, bossID);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Jefe con ID " + bossID + " actualizado.");
            } else {
                System.out.println("No se encontró ningún jefe con ID " + bossID + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cuenta el número total de jefes en la base de datos.
     *
     * @return El número de jefes en la base de datos
     */
    public int countBosses() {
        String query = "SELECT COUNT(*) FROM Boss";
        int result;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                result = resultSet.getInt(1);
                System.out.println("Total de jefes: " + result);
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = 0;
        System.out.println("Total de jefes: " + result);
        return result;
    }

    /**
     * Obtiene una lista con todos los jefes de la base de datos.
     *
     * @return Lista con todos los jefes
     */
    public List<Boss> getAllBosses() {
        List<Boss> bossList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Boss")) {

            while (resultSet.next()) {
                // Crear un nuevo jefe con los datos de cada fila
                Boss boss = new Boss(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getInt("hp"),
                        resultSet.getDouble("poise"),
                        resultSet.getInt("souls"),
                        resultSet.getString("drop_name"),
                        resultSet.getString("description")
                );
                bossList.add(boss);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bossList;
    }

    /**
     * Borra todos los jefes de la base de datos.
     */
    public void deleteAllBosses() {
        String query = "DELETE FROM Boss";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " jefes borrados de la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un jefe por cualquier campo y devuelve su ID.
     *
     * @param field Campo por el que queremos buscar (nombre, ubicación, etc.)
     * @param match Valor que debe tener ese campo
     * @return ID del jefe si se encuentra, 0 si no
     */
    public int findBoss(String field, String match) {
        String query = "SELECT id FROM Boss WHERE " + field + " = ? LIMIT 1";
        int bossID = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, match);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    bossID = resultSet.getInt("id");
                    System.out.println("ID del jefe: " + bossID);
                } else {
                    System.out.println("No se encontró ningún jefe con " + field + " = " + match);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bossID;
    }

    /**
     * Muestra toda la información de los jefes en la base de datos.
     */
    public void displayBossTable() {
        String countQuery = "SELECT COUNT(*) FROM Boss";
        String selectQuery = "SELECT * FROM Boss";

        try (Statement countStatement = connection.createStatement();
             ResultSet countResultSet = countStatement.executeQuery(countQuery)) {

            if (countResultSet.next()) {
                int rowCount = countResultSet.getInt(1);
                System.out.println("Número de jefes en la base de datos: " + rowCount);

                try (Statement selectStatement = connection.createStatement();
                     ResultSet selectResultSet = selectStatement.executeQuery(selectQuery)) {

                    int rowNumber = 0;
                    while (selectResultSet.next()) {
                        rowNumber++;
                        System.out.println("\nJefe " + rowNumber);
                        System.out.println("ID: " + selectResultSet.getInt("id"));
                        System.out.println("Nombre: " + selectResultSet.getString("name"));
                        System.out.println("Ubicación: " + selectResultSet.getString("location"));
                        System.out.println("Vida: " + selectResultSet.getInt("hp"));
                        System.out.println("Equilibrio: " + selectResultSet.getDouble("poise"));
                        System.out.println("Almas: " + selectResultSet.getInt("souls"));
                        System.out.println("Objeto: " + selectResultSet.getString("drop_name"));
                        System.out.println("Descripción: " + selectResultSet.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra información detallada sobre la estructura de la tabla Boss.
     */
    public void displayColumnMetadata() {
        String query = "SELECT * FROM Boss LIMIT 1"; // Usamos LIMIT 1 para obtener solo una fila

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Número de columnas: " + columnCount);
            System.out.println("\nInformación de las columnas:");

            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Columna " + i + ":");
                System.out.println("  Nombre: " + metaData.getColumnName(i));
                System.out.println("  Tipo: " + metaData.getColumnTypeName(i));
                System.out.println("  Tamaño: " + metaData.getColumnDisplaySize(i));
                System.out.println("  Precisión: " + metaData.getPrecision(i));
                System.out.println("  Escala: " + metaData.getScale(i));
                System.out.println("  Puede ser nulo: " + (metaData.isNullable(i) == ResultSetMetaData.columnNullable ? "Sí" : "No"));
                System.out.println("  Auto incrementable: " + metaData.isAutoIncrement(i));
                System.out.println("  Es moneda: " + metaData.isCurrency(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(SQLiteCRUD.class.getName()).severe("Error al mostrar metadatos de columnas: " + e.getMessage());
        }
    }

    /**
     * Muestra información general sobre la base de datos.
     */
    public void displayDatabaseInfo() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            // Información de la base de datos
            System.out.println("Nombre de la base de datos: " + metaData.getDatabaseProductName());
            System.out.println("Versión de la base de datos: " + metaData.getDatabaseProductVersion());
            System.out.println("URL de la base de datos: " + metaData.getURL());
            System.out.println("Driver: " + metaData.getDriverName());
            System.out.println("Versión del driver: " + metaData.getDriverVersion());

            // Información de las tablas
            ResultSet tables = metaData.getTables(null, null, null, null);
            System.out.println("\nTablas:");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("  " + tableName);
            }

            // Información de las claves primarias
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, "Boss");
            System.out.println("\nClaves primarias de la tabla Boss:");
            while (primaryKeys.next()) {
                String columnName = primaryKeys.getString("COLUMN_NAME");
                System.out.println("  " + columnName);
            }

            // Información de las columnas
            ResultSet columns = metaData.getColumns(null, null, "Boss", null);
            System.out.println("\nColumnas de la tabla Boss:");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                System.out.println("  " + columnName + " - " + columnType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return La conexión actual
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Establece la conexión con la base de datos.
     *
     * @param connection La conexión a establecer
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}