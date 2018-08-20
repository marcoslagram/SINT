package beans;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.servlet.http.*;

import org.w3c.dom.*;
import org.w3c.dom.Document;

/* import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.PageSequenceResults; */

public class GetDocs implements Serializable{

    private Map<String, Document> validos;
    private String numeroAnio;
    private boolean html;
    private boolean rss;

    public GetDocs () {}

    public Map<String, Document> getValidos () {
        return validos;
    }

    public void setValidos (Map<String,Document> validos) {
        this.validos = validos;
    }

	public String getNumeroAnio() {
		return numeroAnio;
	}

	public void setNumeroAnio(String numeroAnio) {
		this.numeroAnio = numeroAnio;
    }
    
    public boolean verifyRequest(HttpServletRequest req){
        
        String consulta = req.getParameter("archivos");
        if (consulta == null){
            return false;
        }
        String archivo = req.getParameter("archivo");
        if (archivo == null || !validos.containsKey(archivo)){
            return false;
        }
        String formato = req.getParameter("formato");
        if (formato == null || (!formato.equals("html") && (!formato.equals(("rss"))))){
            return false;
        }

        //Selecciona el formato para convertir
        if (formato.equals("html")){
            html = true;
            rss = false;
        } else {
            html = false;
            rss = true;
        }
        return true;
    }

    public void procesaPeticion(HttpServletResponse res, String servlet_url) throws Exception{
        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = null;
        if(html){
            URL xslt_loc = new URL(new URL(servlet_url), "p3/mml-html.xslt");
            //System.out.println("*************"+xslt_loc.toString());
            res.setContentType("text/html");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            trans = transFact.newTransformer(new StreamSource(xslt_loc.toString()));
            trans.transform(new DOMSource(validos.get(numeroAnio)), new StreamResult(out));
            return;
        }

        if (rss){
            URL xslt_loc = new URL(new URL(servlet_url), "p3/mml-rss.xslt");
            res.setContentType("application/rss+xml");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            trans = transFact.newTransformer(new StreamSource(xslt_loc.toString()));
            trans.transform(new DOMSource(validos.get(numeroAnio)), new StreamResult(out));
            return;
        }
    }
}