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
 * Clase que maneja operaciones con archivos para los jefes del juego.
 * Permite crear, eliminar y modificar archivos y directorios,
 * así como gestionar archivos .dat con información de los jefes.
 */
public class FileManager {

    /**
     * Borra un archivo o directorio. Si es un directorio, pregunta si se quiere
     * borrar recursivamente.
     *
     * @param filePath Ruta del archivo o directorio a borrar
     */
    public void deleteFile(String filePath){
        File file = new File(filePath);

        if (file.exists()) {
            // Si es un directorio, preguntar si borrar recursivamente
            if (file.isDirectory()) {
                System.out.println("Es un directorio. ¿Quieres borrarlo y todo su contenido? (S/N)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("S")) {
                    deleteRecursively(filePath);
                }
            }

            // Intentar borrar el archivo/directorio
            if (file.delete()) {
                System.out.println("Archivo borrado con éxito.");
            } else {
                System.out.println("No se pudo borrar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }

    /**
     * Crea una estructura de directorios y archivos de forma recursiva.
     *
     * @param fileName Nombre del directorio base
     * @param depth Profundidad de subdirectorios a crear
     * @param numFiles Número de archivos a crear en cada directorio
     */
    public void createRecursively(String fileName, int depth, int numFiles){
        File directory = new File(fileName);

        // Crear el directorio si no existe
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directorio creado: " + directory.getAbsolutePath());
            } else {
                System.err.println("Error al crear directorio: " + directory.getAbsolutePath());
                return;
            }
        }

        // Si aún hay niveles por crear
        if (depth > 0) {
            // Crear archivos en el directorio actual
            for (int i = 1; i <= numFiles; i++) {
                createFile(directory.getAbsolutePath() + File.separator + "archivo" + i + ".txt");
            }

            // Crear subdirectorios y repetir el proceso
            for (int i = 1; i <= depth; i++) {
                String subDirectoryName = directory.getAbsolutePath() + File.separator + "Subcarpeta" + i;
                createRecursively(subDirectoryName, depth - 1, numFiles);
            }
        }
    }

    /**
     * Crea un archivo de texto con contenido de ejemplo.
     *
     * @param fileName Ruta donde crear el archivo
     */
    private static void createFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Escribir contenido de ejemplo
            writer.write("Este es un contenido de ejemplo.");
            System.out.println("Archivo creado: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + e.getMessage());
        }
    }

    /**
     * Borra un directorio y todo su contenido de forma recursiva.
     *
     * @param filePath Ruta del directorio a borrar
     */
    public void deleteRecursively(String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            // Si es directorio, borrar primero su contenido
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File child : files) {
                        deleteRecursively(child.getAbsolutePath());
                    }
                }
            }
            file.delete();
            System.out.println("Borrado: " + filePath);
        } else {
            System.out.println("El archivo o directorio no existe: " + filePath);
        }
    }

    /**
     * Lee un archivo .dat y crea una lista de jefes con su contenido.
     *
     * @param filePath Ruta del archivo .dat a leer
     * @return Lista de jefes leídos del archivo
     */
    public List<Boss> readDatFile(String filePath){
        List<Boss> bossList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Leer objetos hasta llegar al final del archivo
            while (true) {
                Boss boss = (Boss) ois.readObject();
                bossList.add(boss);
                BossManager.showBoss(boss);
            }
        } catch (EOFException e) {
            // Final del archivo alcanzado, es normal
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo .dat: " + e.getMessage());
        }
        return bossList;
    }

    /**
     * Crea un archivo .dat con una lista de jefes.
     *
     * @param bossList Lista de jefes a guardar
     * @param fileName Nombre del archivo a crear
     */
    public void createDatFile(List<Boss> bossList, String fileName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Boss boss : bossList) {
                oos.writeObject(boss);
            }
            System.out.println("Datos guardados en archivo .dat: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al crear archivo .dat: " + e.getMessage());
        }
    }

    /**
     * Copia un archivo de una ubicación a otra.
     *
     * @param sourceFile Archivo origen
     * @param destinationFile Archivo destino
     */
    public void copyFileInLocation(String sourceFile, String destinationFile){
        Path sourcePath = Paths.get(sourceFile);
        Path destinationPath = Paths.get(destinationFile);

        try {
            Files.copy(sourcePath, destinationPath);
            System.out.println("Archivo copiado con éxito.");
        } catch (FileAlreadyExistsException e) {
            System.err.println("El archivo destino ya existe: " + destinationFile);
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
    }
}