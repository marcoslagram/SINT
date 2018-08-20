package beans;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

public class MostrarErrors {

/*     private boolean modo; */
    private ArrayList<SAXParseException> warnings;
    private ArrayList<SAXParseException> errors;
    private ArrayList<SAXParseException> fatalerrors;

    private String passIntroducida;
    
    public MostrarErrors() {}

    /*getters y setters de atributos*/
/*     public boolean getModo() {
        return modo;
    }
    public void setModo(boolean modo) {
        this.modo = modo;
    } */

    public String getPass() {
        return passIntroducida;
    }

    public void setPass(String pass) {
        passIntroducida = pass;
    }

    public ArrayList<SAXParseException> getWarnings() {
        return warnings;
    }
    public void setWarnings(ArrayList<SAXParseException> warnings) {
        this.warnings = warnings;
    }

    public ArrayList<SAXParseException> getErrors() {
        return errors;
    }
    public void setErrors(ArrayList<SAXParseException> errors) {
        this.errors = errors;
    }

    public ArrayList<SAXParseException> getFatalerrors() {
        return fatalerrors;
    }
    public void setFatalerrors(ArrayList<SAXParseException> fatalerrors) {
        this.fatalerrors = fatalerrors;
    }

}