//XML packages
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DomMain {
    public static void main(String[] args) {
        //1)Create DocumentBuilder
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        //2)Create document from file
        try {
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append("<?xml version="1.0"?> <bosses> </bosses>");
            ByteArrayInputStream input = null;
            input = new ByteArrayInputStream(
                    xmlStringBuilder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Document doc = builder.parse(input);

    }
}