package beans;

import java.util.*;

public class Pelicula implements Comparable<Pelicula> {
    String titulo;
    String duracion;
    String bandasonora;
    LinkedList<Actor> actor;

    public Pelicula() {
    }

    public Pelicula(String titulo, String duracion, String bandasonora) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.bandasonora = bandasonora;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setBandasonora(String bandasonora) {
        this.bandasonora = bandasonora;
    }

    public String getBandasonora() {
        return bandasonora;
    }

    public LinkedList<Actor> getActor() {
        return actor;
    }

}