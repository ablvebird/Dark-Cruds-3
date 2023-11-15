import Entities.Boss;
import Entities.BossManager;
import FileManager.FileManager;
import SQLite.SQLiteCRUD;
import SQLite.SQLiteConnector;
import XMLManagers.DomManager;
import XMLManagers.JDomManager;
import XMLManagers.SaxManager;
import XMLManagers.XMLTransformer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileTest();
        //DOMTest();
        //SAXTest();
        //JDOMTest();
        //XSLTTest();
        //SQLiteTest();
    }

    //FileManager
    public static void FileTest(){
        //General instancing
        FileManager fileManager = new FileManager();
        BossManager bossManager = new BossManager();
        bossManager.setBossList(new JDomManager().readXML());

        //Ej1: Receive file name from cmd and delete with all its contents
        //1) Create files to delete
        fileManager.createRecursively("directoryTesting", 2, 2);
        fileManager.deleteFile("directoryTesting");

        //Ej2: Create binary .dat file to store data from serialized object
        fileManager.createDatFile(bossManager.getBossList(), "bosses.dat");

        //Ej3: Modify data receiving ID and new info from cmd, then print old and new data.
        List<Boss> newBossList = fileManager.readDatFile("bosses.dat");
        bossManager.modifyBoss(3, "New Name", "New Location");
        fileManager.createDatFile(newBossList, "updatedBosses.dat");


        //Ej4: Eliminate a boss with ID from cmd
        bossManager.deleteBoss(1);
        bossManager.deleteBoss(2);
        bossManager.deleteBoss(3);
        fileManager.createDatFile(bossManager.getBossList(), "lessBosses.dat");

        //Ej5: Copy a file in a location, both source and destination are introducen in cmd
        fileManager.copyFileInLocation("bosses.dat", "src/copyOfBosses.dat");

    }

    //DOM
    public static void DOMTest(){
        //1)Build DOM manager on selected XML
        DomManager domManager = new DomManager("src/bosses.xml");

        //2)Parsing
        domManager.parseXML();

        //3)Simple query: logs number of bosses
        System.out.println("Number of bosses: "+domManager.getNumBosses());

        //4)Printing XML in console
        domManager.printBossData();

        //5)Printing XML of unknown structure
        domManager.readUnknownXML();

        //6)Creating XML from DOM using category constraints
        try {
            domManager.newXML("bossId","3");
        }
        catch (Exception e){e.printStackTrace();}
    }


    //SAX
    public static void SAXTest(){
        try {
            //1)Create a new XMLManagers.SaxManager instance with the path to your XML file
            SaxManager saxManager = new SaxManager("src/bosses.xml");

            //2)Create a SAXParserFactory and obtain a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            //3)Parse the XML file using your XMLManagers.SaxManager as the handler
            saxParser.parse(saxManager.getFile(), saxManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //JDOM
    public static void JDOMTest(){

        //1)Instancing managers
        BossManager bossManager = new BossManager();
        JDomManager jDomManager = new JDomManager();

        //2)Setting Boss List from XML and iterating elements
        bossManager.setBossList(jDomManager.readXML());
        System.out.println("List size: "+bossManager.getBossList().size());
        bossManager.showList(bossManager.getBossList());

        //3)Creating new Boss List from XML, generating and printing
        List<Boss> newBossList=bossManager.createBossList();
        System.out.println("List size: "+newBossList.size());
        bossManager.showList(newBossList);

        //4)Generating new XML from list and printing
        String fileName="newTestBosses";
        jDomManager.generateXML(newBossList, fileName);
        jDomManager.printXML(fileName);

        jDomManager.printXML(jDomManager.getFilename());
    }


    //XSLT
    public static void XSLTTest(){
        try{
            //1)Resource linking
            String xmlPath = "src/bosses.xml";
            String xslPath = "src/bosses.xsl";
            String htmlPath = "bossesTransformed.html";

            //2)Transforming
            XMLTransformer.toHTML(xmlPath, xslPath, htmlPath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //SQLite
    public static void SQLiteTest(){
        //Setting boss list:
        BossManager bossManager = new BossManager();
        JDomManager jDomManager = new JDomManager();
        bossManager.setBossList(jDomManager.readXML());
        List<Boss> bossList=bossManager.getBossList();

        //Initializing SQLite CRUD
        SQLiteCRUD sqLiteCRUD = new SQLiteCRUD();

        try{
            //1)Connection
            sqLiteCRUD.setConnection(SQLiteConnector.connect());

            //2)Boss count testing
            sqLiteCRUD.countBosses();
            sqLiteCRUD.insertBoss(bossList.get(1));
            System.out.println("Boss added: "+bossList.get(1).getBossName());
            sqLiteCRUD.countBosses();

            //3)Clean Database
            sqLiteCRUD.deleteAllBosses();

            //4)Add bosses from list
            for (Boss boss : bossList){
                sqLiteCRUD.insertBoss(boss);
                System.out.println("Boss added: "+boss.getBossName());
            }

            //5)Insert New Boss
            Boss newBoss = new Boss("Darkeater Miguel", "The Ringed City", 15860, 500, 150000, "Soul of Darkeater Midir", "Midir, descendant of Archdragons, was raised by the gods, and owing to his immortality was given a duty to eternally battle the dark, a duty that he would never forget, even after the gods perished.");
            sqLiteCRUD.insertBoss(newBoss);

            //6)Find boss by Name
            int lastBossID = sqLiteCRUD.findBoss("name", "Darkeater Miguel");

            //7)Update Boss with correct information
            sqLiteCRUD.updateBoss(lastBossID, "name", "Darkeater Midir");

            //8)Show Boss table, printing Boss data and row number
            sqLiteCRUD.displayBossTable();

            //9)Show Boss table metadata
            sqLiteCRUD.displayColumnMetadata();

            //10)Display DB info
            sqLiteCRUD.displayDatabaseInfo();

        }
        catch (Exception e){e.printStackTrace();}
    }

}