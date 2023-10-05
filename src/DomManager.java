import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomManager {
    private File file;
    private Document doc;
    private Element eElement;


    public DomManager(String filePath){
        this.file = new File(filePath);
    }


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


    public int getNumBosses(){
        NodeList nList = doc.getElementsByTagName("boss");
        return nList.getLength();
    }


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
}
