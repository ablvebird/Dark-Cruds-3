package SQLite;

import Entities.Boss;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides CRUD operations for the Boss entity in an SQLite database.
 */
public class SQLiteCRUD {
    private Connection connection;

    /**
     * Default constructor.
     */
    public SQLiteCRUD() {}

    /**
     * Inserts a new Boss into the database.
     *
     * @param boss The Boss object to be inserted.
     */
    public void insertBoss(Boss boss) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Boss (name, location, hp, poise, souls, drop_name, description) VALUES (?, ?, ?, ?, ?, ?, ?)"
        )) {
            statement.setString(1, boss.getBossName());
            statement.setString(2, boss.getLocation());
            statement.setInt(3, boss.getHP());
            statement.setDouble(4, boss.getPoise());
            statement.setInt(5, boss.getSouls());
            statement.setString(6, boss.getDropName());
            statement.setString(7, boss.getDescription());

            statement.executeUpdate();
            System.out.println("Boss inserted into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }
    }

    /**
     * Deletes a Boss from the database based on the provided ID.
     *
     * @param bossID The ID of the Boss to be deleted.
     */
    public void deleteBoss(int bossID) {
        String query = "DELETE FROM Boss WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bossID);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Boss with ID " + bossID + " deleted from the database.");
            } else {
                System.out.println("No boss with ID " + bossID + " found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }
    }

    /**
     * Updates a Boss in the database based on the provided ID and field.
     *
     * @param bossID   The ID of the Boss to be updated.
     * @param field    The field to be updated.
     * @param newInfo  The new information for the specified field.
     */
    public void updateBoss(int bossID, String field, String newInfo) {
        String query = "UPDATE Boss SET " + field + " = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newInfo);
            statement.setInt(2, bossID);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Boss with ID " + bossID + " updated in the database.");
            } else {
                System.out.println("No boss with ID " + bossID + " found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }
    }

    /**
     * Counts the number of Bosses in the database.
     *
     * @return The number of Bosses in the database.
     */
    public int countBosses() {
        String query = "SELECT COUNT(*) FROM Boss";
        int result;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                result = resultSet.getInt(1);
                System.out.println("Count: " + result);
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }

        result = 0;
        System.out.println("Count: " + result);
        return result;
    }


    /**
     * Retrieves a list of all Bosses from the database.
     *
     * @return A List containing all Bosses in the database.
     */
    public List<Boss> getAllBosses() {
        List<Boss> bossList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Boss")) {

            while (resultSet.next()) {
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
            // Log or handle the exception as needed
        }
        return bossList;
    }


    /**
     * Deletes all Bosses from the database.
     */
    public void deleteAllBosses() {
        String query = "DELETE FROM Boss";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " bosses deleted from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }
    }


    /**
     * Finds the ID of a Boss based on a specified field and match.
     *
     * @param field The field to search for.
     * @param match The value to match in the specified field.
     * @return The ID of the Boss if found, otherwise 0.
     */
    public int findBoss(String field, String match) {
        String query = "SELECT id FROM Boss WHERE " + field + " = ? LIMIT 1";
        int bossID = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, match);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    bossID = resultSet.getInt("id");
                    System.out.println("BossID: " + bossID);
                } else {
                    System.out.println("No boss found with " + field + " = " + match);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception for tracking
        }
        return bossID;
    }


    /**
     * Displays the contents of the Boss table, including the row number.
     */
    public void displayBossTable() {
        String countQuery = "SELECT COUNT(*) FROM Boss";
        String selectQuery = "SELECT * FROM Boss";

        try (Statement countStatement = connection.createStatement();
             ResultSet countResultSet = countStatement.executeQuery(countQuery)) {

            if (countResultSet.next()) {
                int rowCount = countResultSet.getInt(1);
                System.out.println("Number of records in Boss table: " + rowCount);

                try (Statement selectStatement = connection.createStatement();
                     ResultSet selectResultSet = selectStatement.executeQuery(selectQuery)) {

                    int rowNumber = 0;
                    while (selectResultSet.next()) {
                        rowNumber++;
                        System.out.println("\nRow " + rowNumber);
                        System.out.println("Boss ID: " + selectResultSet.getInt("id"));
                        System.out.println("Name: " + selectResultSet.getString("name"));
                        System.out.println("Location: " + selectResultSet.getString("location"));
                        System.out.println("HP: " + selectResultSet.getInt("hp"));
                        System.out.println("Poise: " + selectResultSet.getDouble("poise"));
                        System.out.println("Souls: " + selectResultSet.getInt("souls"));
                        System.out.println("Drop Name: " + selectResultSet.getString("drop_name"));
                        System.out.println("Description: " + selectResultSet.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception as needed
        }
    }



    /**
     * Gets the current database connection.
     *
     * @return The current database connection.
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Sets the database connection.
     *
     * @param connection The database connection to set.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
