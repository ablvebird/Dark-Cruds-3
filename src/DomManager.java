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


public class DomManager {
    private File file;
    private Document doc;
    private Element eElement;

    //Constructor with File Path
    public DomManager(String filePath){
        this.file = new File(filePath);
    }

    //Parses XML with DOM
    public void parseXML(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Returns and prints number of bosses in XML
    public int getNumBosses(){
        NodeList nList = doc.getElementsByTagName("boss");
        System.out.println("Number of bosses: " +nList.getLength());
        return nList.getLength();
    }

    //Prints detailed XML info
    public void printBossData(){
        NodeList nList = doc.getElementsByTagName("boss");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                eElement = (Element) nNode;

                System.out.println("Boss id: " + eElement.getAttribute("bossId"));
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

    //Rread and print content of all child elements without knowing structure
    public void readUnknownXML(){
        NodeList nList = doc.getElementsByTagName("boss");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if (eElement.hasChildNodes()) {
                    NodeList nl = nNode.getChildNodes();
                    for (int index2 = 0; index2 < nl.getLength(); index2++) {
                        Node nd = nl.item(index2);
                        System.out.println(nd.getTextContent());
                    }
                }
            }
        }
    }

    //Create new XML from domManager and the bosses whose attribute matches the constraints by category
    public void newXML(String category, String constraint) throws Exception {
        // 1. Create a new Document and specify the XML structure
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document newDoc = dBuilder.newDocument();

        // 2. Create the root element for the new document
        Element rootElement = newDoc.createElement("bosses");
        newDoc.appendChild(rootElement);

        // 3. Iterate through the bosses in the original XML, filter those with a matching attribute, and add them to the new Document.
        NodeList nList = doc.getElementsByTagName("boss");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                // Get the value of the specified attribute (e.g., "location")
                String bossAttribute = eElement.getAttribute(category);

                if (bossAttribute.equals(constraint)) {
                    // Import the whole boss element to the new document
                    Node importedNode = newDoc.importNode(eElement, true);
                    rootElement.appendChild(importedNode);
                }
            }
        }

        // 4. Save the new Document as an XML file with a suitable name.
        String newFilePath = "bosses_by_" + category + ".xml";
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File(newFilePath));
        transformer.transform(source, result);

        //Future update --> Handle exceptions appropriately.
    }

    //Helper method for cloning elements to new XML
    private void cloneElement(Element sourceElement, Element destinationElement, String tagName) {
        Element sourceChildElement = (Element) sourceElement.getElementsByTagName(tagName).item(0);
        Element clonedChildElement = (Element) sourceChildElement.cloneNode(true);
        destinationElement.appendChild(clonedChildElement);
    }
}
