package UnhospitalDB;
import UnhospitalDB.HospitalEntities.Visit;

import java.sql.*;

/**
 * A utility class for executing SELECT queries on an SQLite database.
 */
public class SQLiteSelector {

    /**
     * Select all rows from the specified table and display the results.
     *
     * @param connection The database connection.
     * @param tableName  The name of the table to select from.
     */
    public static void selectAll(Connection connection, String tableName) {
        String query = "SELECT * FROM " + tableName;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                System.out.println("Entities from table: " + tableName);
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    System.out.println(columnName + ": " + columnValue);
                }
                System.out.println(); // Separate entities
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select a single entity from the specified table by ID and display the result.
     *
     * @param connection The database connection.
     * @param id         The ID of the entity to select.
     * @param tableName  The name of the table to select from.
     */
    public static void selectOneEntity(Connection connection, int id, String tableName) {
        String query = "SELECT * FROM " + tableName + " WHERE " + tableName.toLowerCase() + "_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                if (resultSet.next()) {
                    System.out.println("Entity from table: " + tableName);
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnValue = resultSet.getString(i);
                        System.out.println(columnName + ": " + columnValue);
                    }
                } else {
                    System.out.println(tableName + " with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select the oldest visit meeting specified criteria and return the result as a Visit object.
     *
     * @param connection  The database connection.
     * @param derivation  The speciality derivation to filter by.
     * @return The oldest Visit meeting the criteria or null if none is found.
     */
    public static Visit selectOldestVisit(Connection connection, String derivation) {
        Visit oldestVisit = null;
        String query = "SELECT * FROM Visit WHERE doctor_id IS NULL " +
                "AND speciality_derivation = ? ORDER BY date LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, derivation);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    oldestVisit = new Visit();
                    oldestVisit.setId(resultSet.getInt("patient_id"));
                    oldestVisit.setDate(resultSet.getString("date"));
                    oldestVisit.setReasonForVisit(resultSet.getString("reason_for_visit"));
                    oldestVisit.setSpecialityDerivation(Visit.Derivation.valueOf(resultSet.getString("speciality_derivation")));

                    System.out.println("Selected oldest visit:");
                    System.out.println("Visit ID: " + oldestVisit.getId());
                    System.out.println("Date: " + oldestVisit.getDate());
                    System.out.println("Reason for Visit: " + oldestVisit.getReasonForVisit());
                    System.out.println("Speciality Derivation: " + oldestVisit.getSpecialityDerivation());
                } else {
                    System.out.println("No oldest visit found for speciality derivation: " + derivation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oldestVisit;
    }

    /**
     * Display patient information for patients born before the specified year.
     *
     * @param connection The database connection.
     * @param year       The birth year to filter by.
     */
    public static void displayPatientsByBirthYear(Connection connection, int year) {
        String query = "SELECT first_name, date_of_birth, " +
                "CASE WHEN strftime('%Y', date_of_birth) < ? THEN 1 ELSE 0 END AS check " +
                "FROM Patient";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, year);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String dateOfBirth = resultSet.getString("date_of_birth");
                    boolean check = resultSet.getBoolean("check");

                    System.out.println("Name: " + firstName);
                    System.out.println("Date of Birth: " + dateOfBirth);
                    System.out.println("Check (born before " + year + "): " + check);
                    System.out.println(); // Separate patient info
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
