import Entities.Boss;
import UnhospitalDB.HospitalEntities.Patient;
import UnhospitalDB.SQLiteCRUD;
import UnhospitalDB.SQLiteConnector;
import XMLManagers.DomManager;
import XMLManagers.JDomManager;
import XMLManagers.SaxManager;
import XMLManagers.XMLTransformer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //DOMTest();
        //SAXTest();
        //JDOMTest();
        //XSLTTest();
        SQLiteTest();

    }

    //DOM
    public static void DOMTest(){
        //Build DOM manager on selected XML
        DomManager domManager = new DomManager("bosses.xml");

        //Parsing
        domManager.parseXML();

        //Simple query: logs number of bosses
        System.out.println("Number of bosses: "+domManager.getNumBosses());

        //Printing XML in console
        domManager.printBossData();

        //Printing XML of unknown structure
        domManager.readUnknownXML();

        //Creating XML from DOM using category constraints
        try {
            domManager.newXML("bossId","3");
        }
        catch (Exception e){e.printStackTrace();}
    }


    //SAX
    public static void SAXTest(){
        try {
            // Create a new XMLManagers.SaxManager instance with the path to your XML file
            SaxManager saxManager = new SaxManager("bosses.xml");

            // Create a SAXParserFactory and obtain a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Parse the XML file using your XMLManagers.SaxManager as the handler
            saxParser.parse(saxManager.getFile(), saxManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //JDOM
    public static void JDOMTest(){
        JDomManager jDomManager = new JDomManager();
        List<Boss> bL = new ArrayList<>();
        bL=jDomManager.createBossList();

        //Print XML
        jDomManager.printXML();

        //Generate new XML
        jDomManager.generateXML(bL);
    }


    //XSLT
    public static void XSLTTest(){
        try{
            String xmlPath = "src/bosses.xml";
            String xslPath = "src/bosses.xsl";
            String htmlPath = "bossesTransformed.html";

            XMLTransformer.toHTML(xmlPath, xslPath, htmlPath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //SQLite
    public static void SQLiteTest(){
        try{
            Patient patient = new Patient("John", "Doe", "New York", "NY", "10001", "555-123-4567", Patient.Gender.Male, "1990-05-15");
            SQLiteCRUD.addPatient(patient, SQLiteConnector.connect());

            //SQLiteCRUD.updatePatientData(13, SQLiteConnector.connect());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}