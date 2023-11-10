import Entities.Boss;
import Entities.BossManager;
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
        JDOMTest();
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
    public static void SQLiteTest(){}

}