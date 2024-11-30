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
 * Clase que permite transformar documentos XML a HTML usando hojas de estilo XSLT.
 * XSLT (eXtensible Stylesheet Language Transformations) es un lenguaje que define
 * cómo debe transformarse un XML a otros formatos como HTML.
 */
public class XMLTransformer {

    /**
     * Transforma un documento XML a HTML utilizando una hoja de estilo XSLT.
     *
     * @param xmlPath  Ruta del archivo XML original
     * @param xslPath  Ruta de la hoja de estilo XSLT que define la transformación
     * @param htmlPath Ruta donde se guardará el archivo HTML resultante
     * @throws TransformerException Si hay errores durante la transformación
     * @throws IOException         Si hay errores de lectura/escritura de archivos
     */
    public static void toHTML(String xmlPath, String xslPath, String htmlPath)
            throws TransformerException, IOException {

        // Vincular los archivos fuente (XML y XSLT)
        StreamSource xslSource = new StreamSource(new File(xslPath));
        StreamSource xmlSource = new StreamSource(new File(xmlPath));

        // Crear el transformador que aplicará los estilos
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);

        // Preparar la salida del archivo HTML
        try (FileOutputStream htmlOutput = new FileOutputStream(htmlPath)) {
            StreamResult htmlResult = new StreamResult(htmlOutput);

            // Aplicar la transformación XSLT para generar el HTML
            transformer.transform(xmlSource, htmlResult);
        }
    }
}