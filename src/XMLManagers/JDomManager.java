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

/**
 * Clase para gestionar archivos XML usando la librería JDOM.
 * JDOM es una alternativa más sencilla a DOM para trabajar con XML en Java.
 */
public class JDomManager {
    // Ruta del archivo XML principal de jefes
    private static final String FILENAME = "resources/bosses.xml";

    /**
     * Lee el archivo XML y convierte los datos en una lista de jefes.
     *
     * @return Lista de objetos Boss con la información del XML
     */
    public List<Boss> readXML() {
        List<Boss> bossList = new ArrayList<>();
        try {
            // Configurar el constructor SAX con medidas de seguridad
            SAXBuilder sB = new SAXBuilder();
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            // Leer el archivo XML y obtener su estructura
            Document doc = sB.build(new File(FILENAME));
            Element rootNode = doc.getRootElement();
            List<Element> elementBossList = rootNode.getChildren("boss");

            // Procesar cada jefe encontrado en el XML
            for (Element bossElement : elementBossList) {
                Boss boss = new Boss();

                // Obtener y establecer los datos básicos del jefe
                String bossId = bossElement.getAttributeValue("bossId");
                boss.setBossID(Integer.parseInt(bossId));
                String bossName = bossElement.getChildText("bossName");
                boss.setBossName(bossName);
                String location = bossElement.getChildText("location");
                boss.setLocation(location);

                // Obtener las estadísticas del jefe
                Element statsElement = bossElement.getChild("stats");
                if (statsElement != null) {
                    String HP = statsElement.getChildText("HP");
                    String Poise = statsElement.getChildText("Poise");
                    String Souls = statsElement.getChildText("Souls");

                    boss.setHP(Integer.parseInt(HP));
                    boss.setPoise(Double.parseDouble(Poise));
                    boss.setSouls(Integer.parseInt(Souls));
                }

                // Obtener información del objeto que deja el jefe
                Element dropElement = bossElement.getChild("drop");
                if (dropElement != null) {
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
     * Lee y muestra en pantalla la información de los jefes desde un archivo XML.
     *
     * @param fileName Ruta del archivo XML a leer
     */
    public void printXML(String fileName) {
        try {
            // Configurar el constructor SAX de forma segura
            SAXBuilder sB = new SAXBuilder();
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sB.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            // Preparar el documento XML para su lectura
            Document doc = sB.build(new File(fileName));
            Element rootNode = doc.getRootElement();
            List<Element> elementBossList = rootNode.getChildren("boss");

            // Mostrar información de cada jefe
            for (Element bossElement : elementBossList) {
                Boss boss = new Boss();

                // Leer datos básicos
                String bossId = bossElement.getAttributeValue("bossId");
                boss.setBossID(Integer.parseInt(bossId));
                String bossName = bossElement.getChildText("bossName");
                boss.setBossName(bossName);
                String location = bossElement.getChildText("location");
                boss.setLocation(location);

                // Leer estadísticas
                Element statsElement = bossElement.getChild("stats");
                if (statsElement != null) {
                    String HP = statsElement.getChildText("HP");
                    boss.setHP(Integer.parseInt(HP));
                    String Poise = statsElement.getChildText("Poise");
                    boss.setPoise(Double.parseDouble(Poise));
                    String Souls = statsElement.getChildText("Souls");
                    boss.setSouls(Integer.parseInt(Souls));
                }

                // Leer información del objeto que deja
                Element dropElement = bossElement.getChild("drop");
                if (dropElement != null) {
                    String dropName = dropElement.getChildText("dropName");
                    boss.setDropName(dropName);
                    String description = dropElement.getChildText("description");
                    boss.setDescription(description);
                }

                // Mostrar toda la información del jefe
                System.out.println("ID del jefe: " + boss.getBossID());
                System.out.println("Nombre: " + boss.getBossName());
                System.out.println("Ubicación: " + boss.getLocation());
                System.out.println("Vida: " + boss.getHP());
                System.out.println("Equilibrio: " + boss.getPoise());
                System.out.println("Almas: " + boss.getSouls());
                System.out.println("Objeto: " + boss.getDropName());
                System.out.println("Descripción: " + boss.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un nuevo archivo XML con la información de una lista de jefes.
     *
     * @param bossList Lista de jefes a incluir en el XML
     * @param fileName Nombre del archivo XML a crear (sin extensión)
     */
    public void generateXML(List<Boss> bossList, String fileName) {
        try {
            // Crear elemento raíz del documento
            Element bossesElement = new Element("bosses");
            Document doc = new Document(bossesElement);

            // Añadir cada jefe como un elemento del XML
            for (Boss boss : bossList) {
                Element bossElement = createBossElement(boss);
                bossesElement.addContent(bossElement);
            }

            // Guardar el documento con formato bonito
            XMLOutputter xml = new XMLOutputter();
            xml.setFormat(Format.getPrettyFormat());
            xml.output(doc, new FileWriter(fileName+".xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un elemento XML con la información de un jefe.
     *
     * @param boss Jefe del que crear el elemento XML
     * @return Elemento XML con todos los datos del jefe
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
     * Crea un elemento XML simple con texto.
     *
     * @param elementName Nombre del elemento
     * @param text Texto a incluir en el elemento
     * @return Elemento XML con el texto especificado
     */
    private Element createElementWithText(String elementName, String text) {
        Element element = new Element(elementName);
        element.setText(text);
        return element;
    }

    /**
     * Obtiene la ruta del archivo XML principal.
     */
    public String getFilename(){
        return FILENAME;
    }
}