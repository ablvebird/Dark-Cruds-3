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
    public List<Boss> readXML() {
        List<Boss> bossList = new ArrayList<>();
        try {
            SAXBuilder sB = new SAXBuilder();
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            Document doc = sB.build(new File(FILENAME));
            Element rootNode = doc.getRootElement();
            List<Element> elementBossList = rootNode.getChildren("boss");

            for (Element bossElement : elementBossList) {
                Boss boss = new Boss();

                String bossId = bossElement.getAttributeValue("bossId");
                boss.setBossID(Integer.parseInt(bossId));
                String bossName = bossElement.getChildText("bossName");
                boss.setBossName(bossName);
                String location = bossElement.getChildText("location");
                boss.setLocation(location);

                //Access Stats elements
                Element statsElement = bossElement.getChild("stats");


                if (statsElement != null) {
                    // Access the individual stats
                    String HP = statsElement.getChildText("HP");
                    String Poise = statsElement.getChildText("Poise");
                    String Souls = statsElement.getChildText("Souls");

                    boss.setHP(Integer.parseInt(HP));
                    boss.setPoise(Double.parseDouble(Poise));
                    boss.setSouls(Integer.parseInt(Souls));
                }

                //Access Drop elements
                Element dropElement = bossElement.getChild("drop");
                if (dropElement != null) {
                    // Access the individual drop details
                    String dropName = dropElement.getChildText("dropName");
                    String description = dropElement.getChildText("description");

                    boss.setDropName(dropName);
                    boss.setDescription(description);
                }

                bossList.add(boss);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bossList;
    }

    /**
     * Reads and prints XML data related to boss entities from the specified XML file.
     *
     * @param fileName The path to the XML file to be read.
     */
    public void printXML(String fileName) {
        try {
            //1)Create a SAXBuilder and prevent XXE (XML External Entity) attacks
            SAXBuilder sB = new SAXBuilder();
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            //2)Set up file and list resources for receiving xml data
            Document doc = sB.build(new File(fileName));
            Element rootNode = doc.getRootElement();
            List<Element> elementBossList = rootNode.getChildren("boss");

            //3)Gets and prints a list of attributes for each boss entity
            for (Element bossElement : elementBossList) {
                Boss boss = new Boss();

                String bossId = bossElement.getAttributeValue("bossId");
                boss.setBossID(Integer.parseInt(bossId));
                String bossName = bossElement.getChildText("bossName");
                boss.setBossName(bossName);
                String location = bossElement.getChildText("location");
                boss.setLocation(location);

                // Navigate to the <stats> element
                Element statsElement = bossElement.getChild("stats");
                if (statsElement != null) {
                    String HP = statsElement.getChildText("HP");
                    boss.setHP(Integer.parseInt(HP));
                    String Poise = statsElement.getChildText("Poise");
                    boss.setPoise(Double.parseDouble(Poise));
                    String Souls = statsElement.getChildText("Souls");
                    boss.setSouls(Integer.parseInt(Souls));
                }

                // Navigate to the <drop> element
                Element dropElement = bossElement.getChild("drop");
                if (dropElement != null) {
                    String dropName = dropElement.getChildText("dropName");
                    boss.setDropName(dropName);
                    String description = dropElement.getChildText("description");
                    boss.setDescription(description);
                }

                System.out.println("Boss ID: " + boss.getBossID());
                System.out.println("Boss Name: " + boss.getBossName());
                System.out.println("Location: " + boss.getLocation());
                System.out.println("HP: " + boss.getHP());
                System.out.println("Poise: " + boss.getPoise());
                System.out.println("Souls: " + boss.getSouls());
                System.out.println("Drop Name: " + boss.getDropName());
                System.out.println("Description: " + boss.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Generates a new XML file with data from a provided list of Boss objects.
     *
     * @param bossList The list of Boss objects to be included in the XML file.
     */
    public void generateXML(List<Boss> bossList, String fileName) {
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
            xml.output(doc, new FileWriter(fileName+".xml"));
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


    //FILENAME Getter
    public String getFilename(){
        return FILENAME;
    }
}
