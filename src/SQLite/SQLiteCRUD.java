package SQLite;

import Entities.Boss;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteCRUD {
    private Connection connection;

    public SQLiteCRUD(){}


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
        }
    }

    public void deleteBoss(int bossID){
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
        }
    }


    public void updateBoss(int bossID, String field, String newInfo){
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
        }
    }


    public int countBosses() {
        String query = "SELECT COUNT(*) FROM Boss";
        int result;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                result = resultSet.getInt(1);
                System.out.println("Count: "+result);
                return result; // The result of COUNT(*) is in the first column
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        result=0;
        System.out.println("Count: "+result);
        return result; // Return 0 if an exception occurs or if no result is found
    }


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


    public void deleteAllBosses() {
        String query = "DELETE FROM Boss";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " bosses deleted from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int findBoss(String field, String match){
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
        }
        return bossID;
    }

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
