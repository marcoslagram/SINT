package beans;

import java.util.*;

public class Actor implements Comparable<Actor>{
    String nombre;
    String personaje;
    String oscar;
    String ciudad_pais;
    LinkedList filmografia;

    public Actor() {
    }

    public Actor(String nombre, String personaje, String oscar, String ciudad_pais,
            LinkedList<String> filmografia) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.oscar = oscar;
        this.ciudad_pais = ciudad_pais;
        this.filmografia = filmografia;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
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
}