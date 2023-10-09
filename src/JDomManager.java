import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class JDomManager {
    private static final String FILENAME = "src/bosses.xml";

    public void printXML(){
        try{
            SAXBuilder sB = new SAXBuilder();

            //Prevent XXE (XMl External Entity) attacks
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            // XML is a local file
            Document doc = sB.build(new File(FILENAME));

            Element rootNode = doc.getRootElement();
            List<Element> list = rootNode.getChildren("boss");

            for (Element target : list){
                String bossId=target.getAttributeValue("bossId");
                String bossName = target.getChildText("bossName");
                String location = target.getChildText("location");
                String HP = target.getChildText("HP");
                String Poise = target.getChildText("Poise");
                String Souls = target.getChildText("Souls");
                String dropName = target.getChildText("dropName");
                String description = target.getChildText("description");

                System.out.println("Boss Id: " + bossId);
                System.out.println("Boss Name: " + bossName);
                System.out.println("Location: " + location);
                System.out.println("HP: " + HP);
                System.out.println("Poise: " + Poise);
                System.out.println("Souls: " + Souls);
                System.out.println("Drop Name: " + dropName);
                System.out.println("Description: " + description);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Boss> createBossList() {
        List<Boss> bossList = new ArrayList<>();

        // Create Boss objects using the constructor and add them to the list
        Boss boss1 = new Boss(1, "Boss1", "Location1", 100, 50.0, 200, "Drop1", "Description1");
        Boss boss2 = new Boss(2, "Boss2", "Location2", 150, 60.0, 250, "Drop2", "Description2");
        Boss boss3 = new Boss(3, "Boss3", "Location3", 120, 55.0, 220, "Drop3", "Description3");

        bossList.add(boss1);
        bossList.add(boss2);
        bossList.add(boss3);

        return bossList;
    }

    public void generateXML(List<Boss> bL){
        try{
            //Root
            Element bossesElement = new Element("bosses");
            Document doc = new Document(bossesElement);

            //Boss
            for (Boss boss : bL) {
                Element bossElement = createBossElement(boss);
                bossesElement.addContent(bossElement);
            }

            // Create the XML file
            XMLOutputter xml = new XMLOutputter();
            xml.setFormat(Format.getPrettyFormat());
            xml.output(doc, new FileWriter("newBosses.xml"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private Element createBossElement(Boss boss){
        Element bossElement = new Element("boss");
        bossElement.setAttribute("bossId", String.valueOf(boss.getBossID()));
        bossElement.addContent(createElementWithText("bossName", boss.getBossName()));
        bossElement.addContent(createElementWithText("location", boss.getLocation()));
        bossElement.addContent(createElementWithText("HP", String.valueOf(boss.getHP())));
        bossElement.addContent(createElementWithText("Poise", String.valueOf(boss.getPoise())));
        bossElement.addContent(createElementWithText("Souls", String.valueOf(boss.getSouls())));
        bossElement.addContent(createElementWithText("dropName", boss.getDropName()));
        bossElement.addContent(createElementWithText("description", boss.getDescription()));
        return bossElement;
    }

    private Element createElementWithText(String elementName, String text){
        Element element = new Element(elementName);
        element.setText(text);
        return element;
    }
}
