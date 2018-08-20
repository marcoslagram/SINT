package beans;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;
import java.util.List;
//import java.util.TreeMap;
//import java.util.HashMap;
import java.util.LinkedList;
import java.io.Serializable;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.util.Collections;

//import beans.XSD_Handler;;

public class Parser implements Serializable {
    public static String inicioURL;

    private static List<SAXParseException> warnings;
    private static List<SAXParseException> errors;
    private static List<SAXParseException> fatalerrors;
    
    private static Map<String, Document> validos;

    private LinkedList<URL> toParse = new LinkedList<URL>();
    private LinkedList<URL> parsed = new LinkedList<URL>();
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder docBuilder = null;
    private XPath xpath = XPathFactory.newInstance().newXPath();
    private SchemaFactory xsdFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private Schema xsd = null;
    public Parser() {}

    public String getInicioURL(){
        return inicioURL;
    }

    public void setInicioURL(String INIT_DOC){
        this.inicioURL = INIT_DOC;
    }
    
    public List<SAXParseException> getWarnings() {
        return warnings;
    }
    public void setWarnings(List<SAXParseException> warnings) {
        this.warnings = warnings;
    }

    public List<SAXParseException> getErrors() {
        return warnings;
    }
    public void setErrors(List<SAXParseException> errors) {
        this.errors = errors;
    }

    public List<SAXParseException> getFatalerrors() {
        return fatalerrors;
    }
    public void setFatalerrors(List<SAXParseException> fatalerrors) {
        this.fatalerrors = fatalerrors;
    }

    public Map<String, Document> getValidos(){
        return validos;
    }

    public void setValidos (Map<String, Document> validos){
        this.validos = validos;
    }

    public static void parseXML(URL mmlUrl) throws Exception {
        
        LinkedList<URL> toParse = new LinkedList<URL>();
        LinkedList<URL> parsed = new LinkedList<URL>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        XPath xpath = XPathFactory.newInstance().newXPath();
        SchemaFactory xsdFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema xsd = null;
        try {
            xsd = xsdFactory.newSchema(mmlUrl);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //schema for validation of docs
        factory.setSchema(xsd);

        try {
            docBuilder = factory.newDocumentBuilder();
        } catch(Exception e) {
            e.printStackTrace();
        }
        docBuilder.setErrorHandler(new XML_ErrorHandler(warnings, errors, fatalerrors)); //Casteado 
        URL initUrl = null;
        try {
            initUrl = new URL(inicioURL);
            
        } catch (MalformedURLException e) {
            //this can't happen unless you're a bug in the matrix
        }

        URLConnection docSource = null;
        toParse.add(initUrl);
        while(!toParse.isEmpty()) {
            Document doc = null;
            NodeList outrosMMLs = null;
            String anio = null;
            try {
                docSource = toParse.pop().openConnection();
                doc = docBuilder.parse(docSource.getInputStream(), docSource.getURL().toString());
                parsed.add(docSource.getURL());
                anio = (String) xpath.evaluate("/Movies/Anio/text()", doc, XPathConstants.STRING);
                outrosMMLs = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto/OtraPelicula/MML", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                //esta excepción ocorre cando o documento é incorrecto ou
                //non existe a url que referencia dito documento
                //ou as marmotas rillaron os cables da internete
                parsed.add(docSource.getURL());
                continue;
            }
            validos.put(anio, doc);
            //exploramos todos os documentos dispoñibles a partires deste recén parseado
            for (int i = 0; i < outrosMMLs.getLength(); i++) {
                String mml = null;
                try {
                    mml = (String) xpath.evaluate("text()", outrosMMLs.item(i), XPathConstants.STRING);
                } catch(Exception e) {
                    continue;
                }

                URL novoMML = null;
                try {
                    novoMML = new URL(docSource.getURL(), mml);
                } catch(Exception e) {
                    e.printStackTrace();
                    continue;
                }

                if (!parsed.contains(novoMML) && !toParse.contains(novoMML)) {
                    toParse.add(novoMML);
                }
            }
        }
    }

}