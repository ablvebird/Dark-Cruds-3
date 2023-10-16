import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

    //DOM
        //Build DOM manager on selected XML
        DomManager domManager = new DomManager("src/bosses.xml");

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


    //SAX
        try {
            // Create a new SaxManager instance with the path to your XML file
            SaxManager saxManager = new SaxManager("src/bosses.xml");

            // Create a SAXParserFactory and obtain a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Parse the XML file using your SaxManager as the handler
            saxParser.parse(saxManager.getFile(), saxManager);
        } catch (Exception e) {
            e.printStackTrace();
        }


    //JDOM
        JDomManager jDomManager = new JDomManager();
        List<Boss> bL = new ArrayList<>();
        bL=jDomManager.createBossList();

        //Print XML
        jDomManager.printXML();

        //Generate new XML
        jDomManager.generateXML(bL);

    //XSLT
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
}