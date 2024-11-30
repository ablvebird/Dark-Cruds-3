import Entities.Boss;
import Entities.BossManager;
import SQLite.SQLiteCRUD;
import SQLite.SQLiteConnector;
import XMLManagers.DomManager;
import XMLManagers.JDomManager;
import XMLManagers.SaxManager;
import XMLManagers.XMLTransformer;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import FileManager.*;

/**
 * Dark Cruds 3 - Gestor de Datos de Jefes de Dark Souls 3

 * Esta aplicación proporciona diferentes implementaciones para gestionar datos de los jefes (bosses)
 * del juego Dark Souls 3, demostrando varios métodos de persistencia y manipulación de datos:

 * - FileTest(): Manejo de archivos para guardar/cargar datos en formato .dat
 * - DOMTest(): Procesamiento de XML usando DOM
 * - SAXTest(): Lectura de XML usando SAX
 * - JDOMTest(): Manipulación de XML usando JDOM
 * - XSLTTest(): Transformación de XML a HTML usando XSLT
 * - SQLiteTest(): Operaciones CRUD en base de datos SQLite
 */
public class Main {
    public static void main(String[] args) {
        // Bloques de ejercicios - Descomenta el que quieras probar
        //FileTest();
        //DOMTest();
        //SAXTest();
        //JDOMTest();
        //XSLTTest();
        //SQLiteTest();
    }

    // Pruebas del gestor de archivos
    public static void FileTest(){
        // Creamos las instancias necesarias para trabajar con archivos
        FileManager fileManager = new FileManager();
        BossManager bossManager = new BossManager();
        bossManager.setBossList(new JDomManager().readXML());

        // Ejercicio 1: Crear y borrar directorios
        // Primero creamos una estructura de directorios para probar
        fileManager.createRecursively("directoryTesting", 2, 2);
        fileManager.deleteFile("directoryTesting");

        // Ejercicio 2: Crear archivo binario con datos de jefes
        fileManager.createDatFile(bossManager.getBossList(), "resources/bosses.dat");

        // Ejercicio 3: Modificar datos de un jefe y guardar cambios
        List<Boss> newBossList = fileManager.readDatFile("resources/bosses.dat");
        bossManager.modifyBoss(3, "New Name", "New Location");
        fileManager.createDatFile(newBossList, "resources/updatedBosses.dat");

        // Ejercicio 4: Eliminar varios jefes por ID
        bossManager.deleteBoss(1);
        bossManager.deleteBoss(2);
        bossManager.deleteBoss(3);
        fileManager.createDatFile(bossManager.getBossList(), "resources/lessBosses.dat");

        // Ejercicio 5: Copiar un archivo a otra ubicación
        fileManager.copyFileInLocation("resources/bosses.dat", "resources/copyOfBosses.dat");
    }

    // Pruebas de DOM (Document Object Model)
    public static void DOMTest(){
        // Crear gestor DOM y apuntarlo al archivo XML de jefes
        DomManager domManager = new DomManager("resources/bosses.xml");

        // Procesar el archivo XML
        domManager.parseXML();

        // Mostrar número total de jefes en el XML
        System.out.println("Number of bosses: "+domManager.getNumBosses());

        // Mostrar información detallada de cada jefe
        domManager.printBossData();

        // Leer XML sin conocer su estructura previamente
        domManager.readUnknownXML();

        // Crear nuevo XML filtrando por ID de jefe = 3
        try {
            domManager.newXML("bossId","3");
        }
        catch (Exception e){e.printStackTrace();}
    }

    // Pruebas de SAX (Simple API for XML)
    public static void SAXTest(){
        try {
            // Crear gestor SAX para nuestro archivo XML
            SaxManager saxManager = new SaxManager("resources/bosses.xml");

            // Preparar el parser SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Procesar el archivo XML usando nuestro gestor
            saxParser.parse(saxManager.getFile(), saxManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Pruebas de JDOM (Java DOM - alternativa más fácil a DOM)
    public static void JDOMTest(){
        // Crear gestores necesarios
        BossManager bossManager = new BossManager();
        JDomManager jDomManager = new JDomManager();

        // Cargar lista de jefes desde XML y mostrarla
        bossManager.setBossList(jDomManager.readXML());
        System.out.println("List size: "+bossManager.getBossList().size());
        BossManager.showList(bossManager.getBossList());

        // Crear nueva lista de jefes y mostrarla
        List<Boss> newBossList=bossManager.createBossList();
        System.out.println("List size: "+newBossList.size());
        BossManager.showList(newBossList);

        // Generar nuevo archivo XML con la lista nueva
        String fileName="resources/newTestBosses";
        jDomManager.generateXML(newBossList, fileName);
        jDomManager.printXML(jDomManager.getFilename());
    }

    // Pruebas de XSLT (transformación de XML a HTML)
    public static void XSLTTest(){
        try{
            // Definir rutas de los archivos
            String xmlPath = "resources/bosses.xml";
            String xslPath = "resources/bosses.xsl";
            String htmlPath = "resources/bossesTransformed.html";

            // Transformar XML a HTML
            XMLTransformer.toHTML(xmlPath, xslPath, htmlPath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // Pruebas de base de datos SQLite
    public static void SQLiteTest(){
        // Preparar datos iniciales desde XML
        BossManager bossManager = new BossManager();
        JDomManager jDomManager = new JDomManager();
        bossManager.setBossList(jDomManager.readXML());
        List<Boss> bossList=bossManager.getBossList();

        // Crear gestor de base de datos
        SQLiteCRUD sqLiteCRUD = new SQLiteCRUD();

        try{
            // Conectar a la base de datos
            sqLiteCRUD.setConnection(SQLiteConnector.connect());

            // Probar contador de jefes e insertar uno nuevo
            sqLiteCRUD.countBosses();
            sqLiteCRUD.insertBoss(bossList.get(1));
            System.out.println("Boss added: "+bossList.get(1).getBossName());
            sqLiteCRUD.countBosses();

            // Limpiar base de datos
            sqLiteCRUD.deleteAllBosses();

            // Insertar todos los jefes de la lista
            for (Boss boss : bossList){
                sqLiteCRUD.insertBoss(boss);
                System.out.println("Boss added: "+boss.getBossName());
            }

            // Insertar un nuevo jefe personalizado
            Boss newBoss = new Boss("Darkeater Miguel", "The Ringed City", 15860, 500, 150000, "Soul of Darkeater Midir", "Midir, descendant of Archdragons, was raised by the gods, and owing to his immortality was given a duty to eternally battle the dark, a duty that he would never forget, even after the gods perished.");
            sqLiteCRUD.insertBoss(newBoss);

            // Buscar el jefe por nombre
            int lastBossID = sqLiteCRUD.findBoss("name", "Darkeater Miguel");

            // Actualizar el nombre del jefe
            sqLiteCRUD.updateBoss(lastBossID, "name", "Darkeater Midir");

            // Mostrar información de la base de datos
            sqLiteCRUD.displayBossTable();
            sqLiteCRUD.displayColumnMetadata();
            sqLiteCRUD.displayDatabaseInfo();
        }
        catch (Exception e){e.printStackTrace();}
    }
}