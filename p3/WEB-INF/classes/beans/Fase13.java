package beans;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collections;

//import javax.swing.text.Document;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;

import beans.Actor;

public class Fase13 implements Serializable, Comparable<Actor>{

/*     private boolean modo; */
    private String passIntroducida;
    private Map<String,Document> validos;
    private LinkedList<Actor> actores;
    private String anio;
    private String pelicula;
    private String nombre;
    private String ciudad_pais;
    private String personaje;
    private String oscar;
    private LinkedList filmografia;

    public Fase13(){}

/*     public boolean getModo(){
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

    public Map<String,Document> getValidos(){
        return validos;
    }

    public void setValidos (Map<String, Document> validos){
        this.validos = validos;
    }

    public List<Actor> getActores(){
        LinkedList<Actor> toRet = new LinkedList<Actor>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList actores = null;
        try {
            actores = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula[Titulo='"+pelicula+"']/Reparto", validos.get(anio), XPathConstants.NODESET);
            for(int i = 0; i < actores.getLength(); i++){
                Actor actor = new Actor();
                actor.setNombre((String) xpath.evaluate("Nombre/text()", actores.item(i), XPathConstants.STRING));
                actor.setCiudadPais((String) xpath.evaluate("text()[normalize-space()]", actores.item(i), XPathConstants.STRING));
                toRet.add(actor);
            }
        } catch(Exception e){
            return toRet;
        }
	Collections.sort(toRet);
        return toRet;
    }

    public void setActores(LinkedList<Actor> actores){
        this.actores = actores;
    }

    public String getNombre (){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setOscar(String oscar) {
        this.oscar = oscar;
    }

    public String getOscar() {
        return oscar;
    }

    public void setCiudadPais(String ciudad_pais) {
        this.ciudad_pais = ciudad_pais;
    }

    public String getCiudadPais() {
        return ciudad_pais;
    }

    public void setFilmografia(LinkedList<String> filmografia) {
        this.filmografia = filmografia;
    }

    public LinkedList getFilmografia() {
        return filmografia;
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
        if (otro instanceof Actor) {
            return  nombre.equals(((Actor) otro).nombre);
        }
        return false;
    }

    @Override
    public int compareTo(Actor otro) {
        return nombre.compareTo(otro.nombre);
    }

}