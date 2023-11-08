package XMLManagers;
import Entities.Boss;
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

    /**
     * Reads and prints XML data related to boss entities from the specified XML file.
     */
    public void printXML() {
        try {
            //1)Create a SAXBuilder and prevent XXE (XML External Entity) attacks
            SAXBuilder sB = new SAXBuilder();
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            //2)Set up file and list resources for receiving xml data
            Document doc = sB.build(new File(FILENAME));
            Element rootNode = doc.getRootElement();
            List<Element> elementBossList = rootNode.getChildren("boss");
            List<Boss> bossList=new ArrayList<>();

            //3)Gets and prints a list of attributes for each boss entity
            for (Element bossElement : elementBossList) {
                Boss boss = new Boss();

                String bossId = bossElement.getAttributeValue("bossId");
                boss.setBossID(Integer.parseInt(bossId));
                String bossName = bossElement.getChildText("bossName");
                boss.setBossName(bossName);
                String location = bossElement.getChildText("location");
                boss.setLocation(location);
                String HP = bossElement.getChildText("HP");
                boss.setHP(Integer.parseInt(HP));
                String Poise = bossElement.getChildText("Poise");
                boss.setPoise(Double.parseDouble(Poise));
                String Souls = bossElement.getChildText("Souls");
                boss.setSouls(Integer.parseInt(Souls));
                String dropName = bossElement.getChildText("dropName");
                boss.setDropName(dropName);
                String description = bossElement.getChildText("description");
                boss.setDescription(description);


                System.out.println("Boss ID: " + bossId);
                System.out.println("Boss Name: " + bossName);
                System.out.println("Location: " + location);
                System.out.println("HP: " + HP);
                System.out.println("Poise: " + Poise);
                System.out.println("Souls: " + Souls);
                System.out.println("Drop Name: " + dropName);
                System.out.println("Description: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a list of Boss objects using predefined data and returns the list.
     *
     * @return A list of Boss objects with predefined data.
     */
    public List<Boss> createBossList() {

        //1)Create storing list
        List<Boss> bossList = new ArrayList<>();

        //2)Create Boss objects using the constructor and add them to the list
        Boss boss1 = new Boss(1, "TestBoss1", "TestLocation1", 100, 50.0, 200, "Drop1", "Description1");
        Boss boss2 = new Boss(2, "TestBoss2", "TestLocation2", 150, 60.0, 250, "Drop2", "Description2");
        Boss boss3 = new Boss(3, "TestBoss3", "TestLocation3", 120, 55.0, 220, "Drop3", "Description3");

        bossList.add(boss1);
        bossList.add(boss2);
        bossList.add(boss3);

        return bossList;
    }


    /**
     * Generates a new XML file with data from a provided list of Boss objects.
     *
     * @param bossList The list of Boss objects to be included in the XML file.
     */
    public void generateXML(List<Boss> bossList) {
        try {
            //1)Root element for the XML document
            Element bossesElement = new Element("bosses");
            Document doc = new Document(bossesElement);

            //2)Create boss elements for each Boss object in the list
            for (Boss boss : bossList) {
                Element bossElement = createBossElement(boss);
                bossesElement.addContent(bossElement);
            }

            //3)Create the XML file with formatted output
            XMLOutputter xml = new XMLOutputter();
            xml.setFormat(Format.getPrettyFormat());
            xml.output(doc, new FileWriter("newBosses.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a JDOM Element for a Boss object and populates it with attributes.
     *
     * @param boss The Boss object for which to create an XML element.
     * @return A JDOM Element representing the Boss object with attributes.
     */
    private Element createBossElement(Boss boss) {
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


    /**
     * Creates a JDOM Element with text content.
     *
     * @param elementName The name of the XML element.
     * @param text       The text content to be added to the element.
     * @return A JDOM Element with the specified text content.
     */
    private Element createElementWithText(String elementName, String text) {
        Element element = new Element(elementName);
        element.setText(text);
        return element;
    }
}
