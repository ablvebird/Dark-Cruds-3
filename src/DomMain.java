//XML packages
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class DomMain {
    public static void main(String[] args) {
        //0)Resources
        int index, index2;
        NodeList nList;
        Node nNode;
        Element eElement;

        //1)Connect file with .xml
        File file = new File("src/bosses.xml");

        //2)Instancing resources
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            //3)Access root element and normalizing (remove empty nodes and combines adjacent ones)
            doc.getDocumentElement().normalize();

            //4)Query: get number of bosses
            nList = doc.getElementsByTagName("boss");
            System.out.println("Number of bosses: " + nList.getLength());

            //5)Query: Get boss data
            for (index=0; index < nList.getLength();index++){
                nNode = nList.item(index);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    System.out.println("Boss id: "+eElement.getAttribute("bossId"));
                    System.out.println("Name: "+eElement.getElementsByTagName("bossName").item(0).getTextContent());
                    System.out.println("Location: "+eElement.getElementsByTagName("location").item(0).getTextContent());
                    System.out.println("Hit Points: "+eElement.getElementsByTagName("HP").item(0).getTextContent());
                    System.out.println("Poise: "+eElement.getElementsByTagName("Poise").item(0).getTextContent());
                    System.out.println("Souls: "+eElement.getElementsByTagName("Souls").item(0).getTextContent());
                    System.out.println("Item dropped: "+eElement.getElementsByTagName("dropName").item(0).getTextContent());
                    System.out.println("Item description: "+eElement.getElementsByTagName("description").item(0).getTextContent()+"\n");
                }
            }

            //6)Reading xml of unknown structure
            nList = doc.getElementsByTagName("boss");

            for (index = 0; index < nList.getLength(); index++) {
                nNode = nList.item(index);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    if(eElement.hasChildNodes()) {
                        NodeList nl = nNode.getChildNodes();
                        for(index2=0; index2<nl.getLength(); index2++) {
                            Node nd = nl.item(index);
                            System.out.println(nd.getTextContent());
                        }
                    }
                }
            }


        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}