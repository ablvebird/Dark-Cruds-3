package FileManager;

import Entities.Boss;
import Entities.BossManager;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class works with BossManager and Boss classes
 * to create and manage files in different ways.
 */
public class FileManager {


    /**
     * Deletes the specified file or directory.
     *
     * @param filePath The path of the file or directory to be deleted.
     */
    public void deleteFile(String filePath){
        File file = new File(filePath);

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("The file is a directory. Do you want to delete it recursively? (Y/N)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Y")) {
                    deleteRecursively(filePath);
                }
            }

            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }


    /**
     * Creates directories and files recursively based on the specified parameters.
     *
     * @param fileName  The name of the directory to be created.
     * @param depth     The depth of the directory tree.
     * @param numFiles  The number of files to be created in each directory.
     */
    public void createRecursively(String fileName, int depth, int numFiles){
        File directory = new File(fileName);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }

        if (depth > 0) {
            for (int i = 1; i <= numFiles; i++) {
                createFile(directory.getAbsolutePath() + File.separator + "file" + i + ".txt");
            }

            for (int i = 1; i <= depth; i++) {
                String subDirectoryName = directory.getAbsolutePath() + File.separator + "SubFolder" + i;
                createRecursively(subDirectoryName, depth - 1, numFiles);
            }
        }
    }


    /**
     * Creates a file with sample content at the specified path.
     *
     * @param fileName The path of the file to be created.
     */
    private static void createFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Writing some content to the file
            writer.write("This is a sample file content.");
            System.out.println("File created: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }



    /**
     * Deletes the specified file or directory recursively.
     *
     * @param filePath The path of the file or directory to be deleted.
     */
    public void deleteRecursively(String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File child : files) {
                        deleteRecursively(child.getAbsolutePath());
                    }
                }
            }
            file.delete();
            System.out.println("Deleted: " + filePath);
        } else {
            System.out.println("File or directory does not exist: " + filePath);
        }
    }


    /**
     * Reads data from a .dat file and creates a list of Boss objects.
     *
     * @param filePath The path of the .dat file to be read.
     * @return A list of Boss objects read from the .dat file.
     */
    public List<Boss> readDatFile(String filePath){
        List<Boss> bossList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                Boss boss = (Boss) ois.readObject();
                bossList.add(boss);
                BossManager.showBoss(boss);
            }
        } catch (EOFException e) {
            // End of file reached, stop reading.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading the .dat file: " + e.getMessage());
        }
        return bossList;
    }


    public void createDatFile(List<Boss> bossList, String fileName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Boss boss : bossList) {
                oos.writeObject(boss);
            }
            System.out.println("Data written to .dat file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating .dat file: " + e.getMessage());
        }
    }


    public void copyFileInLocation(String sourceFile, String destinationFile){
        Path sourcePath = Paths.get(sourceFile);
        Path destinationPath = Paths.get(destinationFile);

        try {
            Files.copy(sourcePath, destinationPath);
            System.out.println("File copied successfully.");
        } catch (FileAlreadyExistsException e) {
            System.err.println("Destination file already exists: " + destinationFile);
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }    }
}
