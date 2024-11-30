package XMLManagers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * Gestor que utiliza SAX (Simple API for XML) para procesar archivos XML.
 * SAX lee el XML secuencialmente y es más eficiente en memoria que DOM.
 */
public class SaxManager extends DefaultHandler {

    private final File file;
    private SAXParser parser;

    // Banderas para rastrear en qué elemento estamos durante la lectura
    boolean bBoss = false;        // Dentro de un elemento boss
    boolean bBossId = false;      // En el ID del jefe
    boolean bBossName = false;    // En el nombre del jefe
    boolean bLocation = false;    // En la ubicación
    boolean bHP = false;          // En los puntos de vida
    boolean bPoise = false;       // En el equilibrio
    boolean bSouls = false;       // En las almas
    boolean bDropName = false;    // En el nombre del objeto
    boolean bDescription = false; // En la descripción

    /**
     * Constructor del gestor SAX.
     *
     * @param filePath Ruta del archivo XML a procesar
     */
    public SaxManager(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    // Se llama al encontrar el inicio de un elemento XML
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {

        // Si encontramos un jefe, guardamos su ID y marcamos que estamos dentro
        if (qName.equalsIgnoreCase("boss")) {
            String bossId = attributes.getValue("bossId");
            System.out.println("ID del jefe: " + bossId);
            bBoss = true;
        } else if (bBoss) {
            // Si estamos dentro de un jefe, activar la bandera correspondiente
            // según el elemento que encontremos
            if (qName.equalsIgnoreCase("bossId")) {
                bBossId = true;
            } else if (qName.equalsIgnoreCase("bossName")) {
                bBossName = true;
            } else if (qName.equalsIgnoreCase("location")) {
                bLocation = true;
            } else if (qName.equalsIgnoreCase("HP")) {
                bHP = true;
            } else if (qName.equalsIgnoreCase("Poise")) {
                bPoise = true;
            } else if (qName.equalsIgnoreCase("Souls")) {
                bSouls = true;
            } else if (qName.equalsIgnoreCase("dropName")) {
                bDropName = true;
            } else if (qName.equalsIgnoreCase("description")) {
                bDescription = true;
            }
        }
    }

    @Override
    // Se llama al encontrar el final de un elemento XML
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("boss")) {
            System.out.println("Fin del jefe: " + qName);
        }
    }

    @Override
    // Se llama al encontrar texto entre las etiquetas XML
    public void characters(char[] ch, int start, int length) {
        // Según la bandera activa, mostramos el contenido correspondiente
        if (bBossName) {
            System.out.println("Nombre: " + new String(ch, start, length));
            bBossName = false;
        } else if (bLocation) {
            System.out.println("Ubicación: " + new String(ch, start, length));
            bLocation = false;
        } else if (bHP) {
            System.out.println("Puntos de vida: " + new String(ch, start, length));
            bHP = false;
        } else if (bPoise) {
            System.out.println("Equilibrio: " + new String(ch, start, length));
            bPoise = false;
        } else if (bSouls) {
            System.out.println("Almas: " + new String(ch, start, length));
            bSouls = false;
        } else if (bDropName) {
            System.out.println("Objeto que deja: " + new String(ch, start, length));
            bDropName = false;
        } else if (bDescription) {
            System.out.println("Descripción del objeto: " + new String(ch, start, length));
            bDescription = false;
        }
    }

    /**
     * Obtiene el archivo XML asociado a este gestor.
     *
     * @return El archivo XML que está siendo procesado
     */
    public File getFile() {
        return this.file;
    }
}