package XMLManagers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * The DomManager class provides methods for parsing, analyzing, and manipulating XML documents using DOM (Document Object Model).
 * It can extract information from XML files, print detailed data about bosses, read unknown XML structures,
 * and create new XML documents based on category and constraint criteria.
 */
public class DomManager {
    private File file;
    private Document doc;
    private Element eElement;


    /**
     * Constructs a DomManager with the specified XML file path.
     *
     * @param filePath The path to the XML file to be managed.
     */
    public DomManager(String filePath) {
        this.file = new File(filePath);
    }


    /**
     * Parses the XML document, making it ready for further processing.
     */
    public void parseXML() {
        System.out.println("Executing DomManager.parseXML");
        try {
            //1) Create a new DocumentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            //2) Create a new DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //3) Parse the XML file and store the resulting Document in the 'doc' member variable
            this.doc = dBuilder.parse(file);

            //4) Normalize the XML document to ensure consistent structure
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves and prints the number of bosses in the XML document.
     *
     * @return The number of bosses in the XML document.
     */
    public int getNumBosses() {
        System.out.println("Executing DomManager.getNumBosses");
        NodeList nList = doc.getElementsByTagName("boss");
        System.out.println("Number of bosses: " + nList.getLength());
        return nList.getLength();
    }


    /**
     * Prints detailed information about each boss in the XML document.
     */
    public void printBossData() {
        System.out.println("Executing DomManager.printBossData");
        //1)Retrieve a NodeList containing all "boss" elements from the XML document
        NodeList nList = doc.getElementsByTagName("boss");

        //2)Iterate through the "boss" elements
        for (int index = 0; index < nList.getLength(); index++) {
            //3) Get the current "boss" element
            Node nNode = nList.item(index);

            //4) Check if the current node is an ELEMENT_NODE
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Cast the current node to an Element
                eElement = (Element) nNode;

                //5)Print the attributes and text content for each boss
                System.out.println("Entities.Boss id: " + eElement.getAttribute("bossId"));
                System.out.println("Name: " + eElement.getElementsByTagName("bossName").item(0).getTextContent());
                System.out.println("Location: " + eElement.getElementsByTagName("location").item(0).getTextContent());
                System.out.println("Hit Points: " + eElement.getElementsByTagName("HP").item(0).getTextContent());
                System.out.println("Poise: " + eElement.getElementsByTagName("Poise").item(0).getTextContent());
                System.out.println("Souls: " + eElement.getElementsByTagName("Souls").item(0).getTextContent());
                System.out.println("Item dropped: " + eElement.getElementsByTagName("dropName").item(0).getTextContent());
                System.out.println("Item description: " + eElement.getElementsByTagName("description").item(0).getTextContent() + "\n");
            }
        }
    }


    /**
     * Reads and prints the content of all child elements without knowing the structure.
     */
    public void readUnknownXML() {
        System.out.println("Executing DomManager.readUnknownXML");
        //1)Retrieve a NodeList containing all "boss" elements from the XML document
        NodeList nList = doc.getElementsByTagName("boss");

        //2)Iterate through the "boss" elements
        for (int index = 0; index < nList.getLength(); index++) {
            // Get the current "boss" element
            Node nNode = nList.item(index);

            //3)Check if the current node is an ELEMENT_NODE
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Cast the current node to an Element
                Element eElement = (Element) nNode;

                //4)Check if the current "boss" element has child nodes
                if (eElement.hasChildNodes()) {
                    // Get a NodeList of child nodes for the current "boss" element
                    NodeList nl = nNode.getChildNodes();

                    //5)Iterate through the child nodes
                    for (int index2 = 0; index2 < nl.getLength(); index2++) {
                        // Get the current child node
                        Node childNode = nl.item(index2);

                        //6)Print the text content of the child node
                        System.out.println(childNode.getTextContent());
                    }
                }
            }
        }
    }


    /**
     * Creates a new XML document containing bosses whose attribute matches the specified category and constraint.
     *
     * @param category    The category by which to filter bosses.
     * @param constraint  The constraint to match against the specified category.
     * @throws Exception if an error occurs during document creation or transformation.
     */
    public void newXML(String category, String constraint) throws Exception {
        System.out.println("Executing DomManager.newXML");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document newDoc = dBuilder.newDocument();

        Element rootElement = newDoc.createElement("bosses");
        newDoc.appendChild(rootElement);

        NodeList nList = doc.getElementsByTagName("boss");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                String bossAttribute = eElement.getAttribute(category);

                if (bossAttribute.equals(constraint)) {
                    Node importedNode = newDoc.importNode(eElement, true);
                    rootElement.appendChild(importedNode);
                }
            }
        }

        String newFilePath = "bosses_by_" + category + ".xml";
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File(newFilePath));
        transformer.transform(source, result);
    }
}
