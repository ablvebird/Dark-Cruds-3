import Entities.Boss;
import XMLManagers.DomManager;
import XMLManagers.JDomManager;
import XMLManagers.SaxManager;
import XMLManagers.XMLTransformer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DOMTest();
        SAXTest();
        DOMTest();
        XSLTTest();
        //SQLiteTest();

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

        JDomManager jDomManager = new JDomManager();
        List<Boss> bL;
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
    public static void SQLiteTest(){}

}