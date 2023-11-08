package UnhospitalDB;

import UnhospitalDB.HospitalEntities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * A utility class for performing CRUD (Create, Read, Update, Delete) operations on an SQLite database.
 */
public class SQLiteCRUD {

    public static void addPatient(Patient patient, Connection connection) {
        String insertSQL = "INSERT INTO Patient (" +
                "first_name, last_name, city, province, postal_code, phone, date_of_birth, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getFamilyName());
            preparedStatement.setString(3, patient.getCity());
            preparedStatement.setString(4, patient.getProvince());
            preparedStatement.setString(5, patient.getPostalCode());
            preparedStatement.setString(6, patient.getPhoneNumber());
            preparedStatement.setString(7, patient.getDateOfBirth());
            preparedStatement.setString(8, patient.getGender().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Patient record inserted successfully.");
            } else {
                System.out.println("Failed to insert patient record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }

    /**
     * Update patient information in the database.
     *
     * @param targetID    The ID of the patient to be updated.
     * @param connection  The database connection.
     */
    public static void updatePatientData(int targetID, Connection connection) {
        String updateSQL = "UPDATE Patient " +
                "SET first_name = ?, last_name = ?, city = ?, province = ?, " +
                "postal_code = ?, phone = ?, date_of_birth = ?, gender = ? " +
                "WHERE patient_id = ?";
        Scanner scanner = new Scanner(System.in);

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            // Prompt the user to enter new patient information with formatting advice
            System.out.print("Enter new first name (e.g., John): ");
            preparedStatement.setString(1, scanner.nextLine());

            System.out.print("Enter new last name (e.g., Doe): ");
            preparedStatement.setString(2, scanner.nextLine());

            System.out.print("Enter new city (e.g., New York): ");
            preparedStatement.setString(3, scanner.nextLine());

            System.out.print("Enter new province (e.g., NY): ");
            preparedStatement.setString(4, scanner.nextLine());

            System.out.print("Enter new postal code (e.g., 12345): ");
            preparedStatement.setString(5, scanner.nextLine());

            System.out.print("Enter new phone number (e.g., 555-555-5555): ");
            preparedStatement.setString(6, scanner.nextLine());

            System.out.print("Enter new date of birth (e.g., 1990-01-15): ");
            preparedStatement.setString(7, scanner.nextLine());

            System.out.print("Enter new gender (Male/Female/Non_Binary): ");
            preparedStatement.setString(8, scanner.nextLine());

            // Set the target patient ID
            preparedStatement.setInt(9, targetID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Patient record updated successfully.");
            } else {
                System.out.println("Failed to update patient record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
            scanner.close();
        }
    }

    /**
     * Delete a patient record from the database by patient ID.
     *
     * @param patientId   The ID of the patient to be deleted.
     * @param connection  The database connection.
     */
    public static void deletePatient(int patientId, Connection connection) {
        String deleteSQL = "DELETE FROM Patient WHERE patient_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, patientId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Patient record deleted successfully.");
            } else {
                System.out.println("Failed to delete patient record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }

    /**
     * Delete patients with no visits from the database.
     *
     * @param connection  The database connection.
     */
    public static void deleteNewPatientsWithNoVisits(Connection connection) {
        // Start a transaction
        try {
            connection.setAutoCommit(false);

            // SQL statement to delete patients with ID > 15 not referenced in any Visit
            String deleteSQL = "DELETE FROM Patient " +
                    "WHERE patient_id > 12 " +
                    "AND patient_id NOT IN (SELECT DISTINCT patient_id FROM Visit)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                int rowsDeleted = preparedStatement.executeUpdate();
                connection.commit();

                System.out.println(rowsDeleted + " patients deleted successfully.");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SQLiteConnector.closeConnection(connection);
        }
    }


    public static void addVisit(Visit visit, Connection connection) {
        String insertSQL = "INSERT INTO Visit (" +
                "patient_id, date, reason_for_visit, speciality_derivation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, visit.getPatientID());
            preparedStatement.setString(2, visit.getDate());
            preparedStatement.setString(3, visit.getReasonForVisit());
            preparedStatement.setString(4, visit.getSpecialityDerivation().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Visit record inserted successfully.");
            } else {
                System.out.println("Failed to insert visit record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }


    public static void addEvaluation(Evaluation evaluation, Connection connection) {
        String insertSQL = "INSERT INTO Evaluation (" +
                "visit_id, physical_examination, medical_history) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, evaluation.getVisitID());
            preparedStatement.setString(2, evaluation.getPhysicalExamination());
            preparedStatement.setString(3, evaluation.getMedicalHistory());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Evaluation record inserted successfully.");
            } else {
                System.out.println("Failed to insert evaluation record.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }

    public static void addDiagnostic(Diagnostic diagnostic, Connection connection) {
        String insertSQL = "INSERT INTO Diagnostic (" +
                "visit_id, test_name, test_results) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, diagnostic.getVisitID());
            preparedStatement.setString(2, diagnostic.getTestName());
            preparedStatement.setString(3, diagnostic.getTestResults());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Diagnostic record inserted successfully.");
            } else {
                System.out.println("Failed to insert diagnostic record.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }

    public static void addTreatment(Treatment treatment, Connection connection) {
        String insertSQL = "INSERT INTO Treatment (" +
                "visit_id, test_name, test_results) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, treatment.getVisitID());
            preparedStatement.setString(2, treatment.getTreatmentName());
            preparedStatement.setString(3, treatment.getTreatmentDescription());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Treatment record inserted successfully.");
            } else {
                System.out.println("Failed to insert treatment record.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLiteConnector.closeConnection(connection);
        }
    }
}
