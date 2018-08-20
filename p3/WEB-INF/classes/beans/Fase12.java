package beans;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;

public class Fase12 implements Serializable, Comparable<Pelicula> {

/*     private boolean modo; */
    private String passIntroducida;
    private String anio;
    private Map<String,Document> validos;
    private LinkedList<Pelicula> peliculas;
    private String titulo;
    private String duracion;
    private String bandasonora;
    private LinkedList<Actor> actor;
    private String reqParam;

    public Fase12() {}

/*     public boolean getModo() {
        return modo;
    }

    public void setModo (boolean modo){
        this.modo = modo;
    } */

    public String getPass(){
        return passIntroducida;
    }

    public void setPass(String passIntroducida){
        this.passIntroducida = passIntroducida;
    }

    public String getTitulo() {
        return titulo;
    }
 
    public void setTitulo (String titulo){
        this.titulo = titulo;
    }

    public String getAnio() {
        return anio;
    }
 
    public void setAnio (String anio){
        this.anio = anio;
    }

    public String getDuracion () {
        return duracion;
    }

    public void setDuracion (String duracion){
        this.duracion = duracion;
    }

    public String getBandasonora(){
        return bandasonora;
    }

    public void setBandasonora(String bandasonora){
        this.bandasonora = bandasonora;
    }

    public Map<String,Document> getValidos(){
        return validos;
    }

    public void setValidos(Map<String,Document> validos){
        this.validos = validos;
    }

    public List<Pelicula> getPeliculas(){
        LinkedList<Pelicula> toRet = new LinkedList<Pelicula>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList peliculas = null;
        try {
            peliculas = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula", validos.get(anio), XPathConstants.NODESET);
        } catch(Exception e) {
            return toRet;
        }
        for (int i = 0; i < peliculas.getLength(); i++) {
            try {
                Pelicula pelicula = new Pelicula();
                pelicula.setTitulo((String) xpath.evaluate("Titulo/text()", peliculas.item(i), XPathConstants.STRING));
                pelicula.setDuracion((String) xpath.evaluate("Duracion/text()", peliculas.item(i), XPathConstants.STRING));
                String langs = ((String) xpath.evaluate("@langs", peliculas.item(i), XPathConstants.STRING));
                //out.println(langs);
                if (langs == null) {
                    //aviso = langs; // Borrar luego
                    pelicula.setBandasonora((String) xpath.evaluate("../@lang", peliculas.item(i), XPathConstants.STRING));
                } else {
                    //aviso = langs; // Borrar luego
                    pelicula.setBandasonora((String) xpath.evaluate("@langs", peliculas.item(i), XPathConstants.STRING));
                }
                toRet.add(pelicula);
            } catch(Exception e) {

            }
        }
        Collections.sort(toRet);
        return toRet;
    }

    public void setPeliculas(LinkedList<Pelicula> peliculas) {
        this.peliculas = peliculas;
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
        if (otro instanceof Pelicula) {
            return  titulo.equals(((Pelicula) otro).titulo);
        }
        return false;
    }

    @Override
    public int compareTo(Pelicula otra) {
        if (duracion.equals(otra.duracion)) {
            return -titulo.compareTo(otra.titulo);
        } else {
            return new Integer(duracion).compareTo(new Integer(otra.duracion));
        }
    }

}