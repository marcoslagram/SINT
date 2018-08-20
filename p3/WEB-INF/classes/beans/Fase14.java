package beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;

//import javax.swing.text.Document;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;

public class Fase14 implements Serializable, Comparable<Filmografia>{

/*     private boolean modo; */
    private String passIntroducida;
    private String anio;
    private String pelicula;

    private Map<String,Document> validos;
    private List<Filmografia> filmografia;
    private Filmografia thisFilm;

    String titulo;
    String oscar;
    String personaje;
    String actor;

    public Fase14(){}
    
/*     public boolean getModo(){
        return modo;
    }

    public void setModo (boolean modo){
        this.modo = modo;
    } */

    public String getPersonaje() {
        getFilmografia();
        return thisFilm.getPersonaje();
    }

    public String getPass(){
        return passIntroducida;
    }

    public void setPass(String passIntroducida){
        this.passIntroducida = passIntroducida;
    }

    public String getActor(){
        return actor;
    }

    public void setActor(String actor){
        this.actor = actor;
    }

    public String getAnio(){
        return anio;
    }

    public void setAnio(String anio){
        this.anio = anio;
    }

    public String getPelicula(){
        return pelicula;
    }

    public void setPelicula(String pelicula){
        this.pelicula = pelicula;
    }

    public Map<String,Document> getValidos(){
        return validos;
    }

    public void setValidos (Map<String, Document> validos){
        this.validos = validos;
    }

    public List<Filmografia> getFilmografia(){
    
        LinkedList<Filmografia> toRet = new LinkedList<Filmografia>();
        LinkedList<Filmografia> nonOscar = new LinkedList<Filmografia>();
        LinkedList<Filmografia> oscarPrin = new LinkedList<Filmografia>();
        LinkedList<Filmografia> oscarSec = new LinkedList<Filmografia>();
    
        XPath xpath = XPathFactory.newInstance().newXPath();
        thisFilm = new Filmografia();
            for (Document doc : validos.values()) {
                NodeList filmografias = null;
                try {
                    filmografias = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto[Nombre='"+ actor +"']", doc, XPathConstants.NODESET);
                    for (int i = 0; i < filmografias.getLength(); i++){
                        Filmografia filmografia = new Filmografia();
    
                        filmografia.setTitulo((String) xpath.evaluate("../Titulo/text()", filmografias.item(i), XPathConstants.STRING));
                        if((xpath.evaluate("Oscar/text()", filmografias.item(i), XPathConstants.STRING)) != null) {
                            filmografia.setOscar((String) xpath.evaluate("Oscar/text()", filmografias.item(i), XPathConstants.STRING));
                        } else{
                            filmografia.setOscar(null);
                        }
                        filmografia.setPersonaje((String) xpath.evaluate("Personaje/text()", filmografias.item(i), XPathConstants.STRING));
                        toRet.add(filmografia);
                    }
                } catch (Exception e ){
                    return toRet;
                }
            }
        try {
            Node temp = (Node) xpath.evaluate("/Movies/Pais/Pelicula[Titulo='"+ pelicula +"']/Reparto[Nombre='"+ actor +"']", validos.get(anio), XPathConstants.NODE);
            thisFilm.setTitulo((String) xpath.evaluate("../Titulo/text()", temp, XPathConstants.STRING));
                        if((xpath.evaluate("Oscar/text()", temp, XPathConstants.STRING)) != null) {
                            thisFilm.setOscar((String) xpath.evaluate("Oscar/text()", temp, XPathConstants.STRING));
                        } else{
                            thisFilm.setOscar(null);
                        }
                        thisFilm.setPersonaje((String) xpath.evaluate("Personaje/text()", temp, XPathConstants.STRING));
        } catch(Exception e) {
            
        }
    
        for (Filmografia f : toRet) {
            if (f.getOscar() == null || f.getOscar().trim().isEmpty()) {
                nonOscar.add(f);
            }
            else if (f.getOscar().trim().equals("Principal")) {
                oscarPrin.add(f);
            }
            else {
                oscarSec.add(f);
            }
        }
        toRet.clear();
            Collections.sort(nonOscar);
            Collections.sort(oscarPrin);
            Collections.sort(oscarSec);
        for (Filmografia f : nonOscar) {
            toRet.add(f);
        }
        for (Filmografia f : oscarPrin) {
            toRet.add(f);
        }
        for (Filmografia f : oscarSec) {
            toRet.add(f);
        }
            return toRet;
    }

    public void setFilmografia(List<Filmografia> filmografia){
        this.filmografia = filmografia;
    }

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
    
    public boolean equals(Object otro) {
        if (otro == null) return false;
        if (otro instanceof Filmografia) {
            return  oscar.equals(((Filmografia) otro).oscar);
        }
        return false;
    }

    @Override
    public int compareTo(Filmografia otro) {
   return titulo.compareTo(otro.titulo);
    }

}