package XMLManagers;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The XMLTransformer class provides a method to transform an XML document using an XSLT stylesheet
 * and generate an HTML document as output.
 */
public class XMLTransformer {

    /**
     * Transforms an XML document to HTML using the provided XSLT stylesheet.
     *
     * @param xmlPath  The path to the source XML document.
     * @param xslPath  The path to the XSLT stylesheet for transformation.
     * @param htmlPath The path where the resulting HTML document will be saved.
     * @throws TransformerException If any errors occur during the transformation process.
     * @throws IOException         If there are any input/output related errors.
     */
    public static void toHTML(String xmlPath, String xslPath, String htmlPath)
            throws TransformerException, IOException {

        //1)Source linking
        StreamSource xslSource = new StreamSource(new File(xslPath));
        StreamSource xmlSource = new StreamSource(new File(xmlPath));

        //2)Factory and Transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);

        //3)Output stream for HTML
        try (FileOutputStream htmlOutput = new FileOutputStream(htmlPath)) {
            StreamResult htmlResult = new StreamResult(htmlOutput);

            //4)Applies XSLT transformation
            transformer.transform(xmlSource, htmlResult);
        }
    }
}
