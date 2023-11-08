package XMLManagers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * A SAX (Simple API for XML) manager for parsing XML data related to boss entities.
 */
public class SaxManager extends DefaultHandler {

    private File file;
    private SAXParser parser;

    //Booleans to track element states
    boolean bBoss = false;
    boolean bBossId = false;
    boolean bBossName = false;
    boolean bLocation = false;
    boolean bHP = false;
    boolean bPoise = false;
    boolean bSouls = false;
    boolean bDropName = false;
    boolean bDescription = false;

    /**
     * Constructs a SaxManager with the specified XML file path.
     *
     * @param filePath The path to the XML file to be parsed.
     */
    public SaxManager(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    //1)Start of element
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("boss")) {
            String bossId = attributes.getValue("bossId");
            System.out.println("Entities.Boss ID : " + bossId);
            bBoss = true;
        } else if (bBoss) {
            //2)Inside a <boss> element, set booleans based on element names
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
    //3)End of element
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("boss")) {
            System.out.println("End Element :" + qName);
        }
    }

    @Override
    //4)Characters between XML tags
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bBossName) {
            System.out.println("Name: " + new String(ch, start, length));
            bBossName = false;
        } else if (bLocation) {
            System.out.println("Location: " + new String(ch, start, length));
            bLocation = false;
        } else if (bHP) {
            System.out.println("Hit Points: " + new String(ch, start, length));
            bHP = false;
        } else if (bPoise) {
            System.out.println("Poise: " + new String(ch, start, length));
            bPoise = false;
        } else if (bSouls) {
            System.out.println("Souls: " + new String(ch, start, length));
            bSouls = false;
        } else if (bDropName) {
            System.out.println("Item dropped: " + new String(ch, start, length));
            bDropName = false;
        } else if (bDescription) {
            System.out.println("Item description: " + new String(ch, start, length));
            bDescription = false;
        }
    }


    /**
     * Get the XML file associated with this manager.
     *
     * @return The XML file being processed by this manager.
     */
    public File getFile() {
        return this.file;
    }


}
