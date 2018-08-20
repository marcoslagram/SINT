package beans;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.servlet.http.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

import java.io.Serializable;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;

public class Fase11 implements Serializable{
    
    private static Map<String, Document> validos;

/*     private boolean modo; */
    private String passIntroducida;
    private LinkedList<Anio> anios;
//    private String reqParam;

    public Fase11() {}

/*     public boolean getModo() {
        return modo;        
    }

    public void setModo(boolean modo) {
        this.modo = modo;
    } */

    public String getPass(){
        return passIntroducida;
    }

    public void setPass(String passIntroducida){
        this.passIntroducida = passIntroducida;
    }

    public List<Anio> getAnios(){
        LinkedList<Anio> toRet = new LinkedList<Anio>();
        for (String key : validos.keySet()) {
            toRet.add(new Anio(key));
        }
        return toRet;
    }

    public void setAnios(LinkedList<Anio> anios) {
        this.anios = anios;
    }

    public Map<String,Document> getValidos(){
        return validos;
    }

    public void setValidos(Map<String,Document> validos){
        this.validos = validos;
    }

/*     public String getReqParam (String reqParam) {
        return reqParam;
    }

    public void setReqParam (String reqParam) {
        this.reqParam = reqParam;
    } */

    //QUIzás haya que poner el verifyRequest
    public Object getNodes(String expresion, Object data, QName xpathconstant) {
		XPathFactory xpfact = XPathFactory.newInstance();
		XPath xpath = xpfact.newXPath();
		Object res = null;
		try {
			res = xpath.evaluate(expresion, data, xpathconstant);
		} catch (Exception e) {
            res = null; //error ejecutando expresion XPath
		}
		return res;
	}
}