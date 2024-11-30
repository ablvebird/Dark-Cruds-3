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
 * Clase que gestiona documentos XML usando DOM (Document Object Model).
 * Permite leer archivos XML, extraer información sobre jefes,
 * leer estructuras XML desconocidas y crear nuevos documentos XML
 * según criterios específicos.
 */
public class DomManager {
    private final File file;
    private Document doc;
    private Element eElement;

    /**
     * Constructor que inicializa el gestor DOM con la ruta del archivo XML.
     *
     * @param filePath Ruta del archivo XML a gestionar
     */
    public DomManager(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Prepara el documento XML para su procesamiento.
     */
    public void parseXML() {
        System.out.println("Ejecutando DomManager.parseXML");
        try {
            // Crear la fábrica de constructores de documentos
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Crear el constructor de documentos
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Procesar el archivo XML y guardarlo en la variable doc
            this.doc = dBuilder.parse(file);

            // Normalizar el documento para asegurar una estructura consistente
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cuenta y muestra el número de jefes en el documento XML.
     *
     * @return Número total de jefes
     */
    public int getNumBosses() {
        System.out.println("Ejecutando DomManager.getNumBosses");
        NodeList nList = doc.getElementsByTagName("boss");
        System.out.println("Número de jefes: " + nList.getLength());
        return nList.getLength();
    }

    /**
     * Muestra información detallada de cada jefe en el XML.
     */
    public void printBossData() {
        System.out.println("Ejecutando DomManager.printBossData");
        // Obtener lista de todos los elementos "boss"
        NodeList nList = doc.getElementsByTagName("boss");

        // Recorrer la lista de jefes
        for (int index = 0; index < nList.getLength(); index++) {
            // Obtener el jefe actual
            Node nNode = nList.item(index);

            // Comprobar si es un elemento válido
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Convertir el nodo a Element para acceder a sus datos
                eElement = (Element) nNode;

                // Mostrar todos los atributos del jefe
                System.out.println("ID del jefe: " + eElement.getAttribute("bossId"));
                System.out.println("Nombre: " + eElement.getElementsByTagName("bossName").item(0).getTextContent());
                System.out.println("Ubicación: " + eElement.getElementsByTagName("location").item(0).getTextContent());
                System.out.println("Puntos de vida: " + eElement.getElementsByTagName("HP").item(0).getTextContent());
                System.out.println("Equilibrio: " + eElement.getElementsByTagName("Poise").item(0).getTextContent());
                System.out.println("Almas: " + eElement.getElementsByTagName("Souls").item(0).getTextContent());
                System.out.println("Objeto que deja: " + eElement.getElementsByTagName("dropName").item(0).getTextContent());
                System.out.println("Descripción del objeto: " + eElement.getElementsByTagName("description").item(0).getTextContent() + "\n");
            }
        }
    }

    /**
     * Lee y muestra el contenido de todos los elementos sin conocer su estructura.
     * Útil para explorar XMLs nuevos o desconocidos.
     */
    public void readUnknownXML() {
        System.out.println("Ejecutando DomManager.readUnknownXML");
        // Obtener todos los elementos "boss"
        NodeList nList = doc.getElementsByTagName("boss");

        // Recorrer cada jefe
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            // Verificar que es un elemento válido
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                // Si el elemento tiene hijos, mostrarlos
                if (eElement.hasChildNodes()) {
                    NodeList nl = nNode.getChildNodes();

                    // Recorrer y mostrar cada hijo
                    for (int index2 = 0; index2 < nl.getLength(); index2++) {
                        Node childNode = nl.item(index2);
                        System.out.println(childNode.getTextContent());
                    }
                }
            }
        }
    }

    /**
     * Crea un nuevo XML que contiene solo los jefes que coinciden con ciertos criterios.
     *
     * @param category    Categoría por la que filtrar (ejemplo: "bossId")
     * @param constraint  Valor que debe tener esa categoría
     * @throws Exception si hay problemas al crear o transformar el documento
     */
    public void newXML(String category, String constraint) throws Exception {
        System.out.println("Ejecutando DomManager.newXML");
        // Preparar el nuevo documento
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document newDoc = dBuilder.newDocument();

        // Crear el elemento raíz
        Element rootElement = newDoc.createElement("bosses");
        newDoc.appendChild(rootElement);

        // Buscar jefes que cumplan el criterio
        NodeList nList = doc.getElementsByTagName("boss");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String bossAttribute = eElement.getAttribute(category);

                // Si coincide, añadirlo al nuevo documento
                if (bossAttribute.equals(constraint)) {
                    Node importedNode = newDoc.importNode(eElement, true);
                    rootElement.appendChild(importedNode);
                }
            }
        }

        // Guardar el nuevo documento
        String newFilePath = "resources/bosses_by_" + category + ".xml";
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File(newFilePath));
        transformer.transform(source, result);
    }
}