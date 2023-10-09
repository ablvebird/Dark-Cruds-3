import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLTransformer {

    public static void toHTML(String xmlPath, String xslPath, String htmlPath)
            throws TransformerException, IOException{

        //Source linking
        StreamSource xslSource = new StreamSource(new File(xslPath));
        StreamSource xmlSource = new StreamSource(new File(xmlPath));

        //Factory and Transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);

        //Output stream for HTML
        try(FileOutputStream htmlOutput = new FileOutputStream(htmlPath)){
            StreamResult htmlResult = new StreamResult(htmlOutput);

            //Applies XSLT transformation
            transformer.transform(xmlSource, htmlResult);
        }
    }
}
