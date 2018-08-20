package beans;

import java.util.*;

public class Filmografia implements Comparable<Filmografia>{
    
            String titulo;
            String oscar;
            String personaje;
    
            public Filmografia() {
            }
    
            public Filmografia(String titulo) {
                this.titulo = titulo;
            }
    
            public Filmografia(String titulo, String oscar, String personaje) {
                this.titulo = titulo;
                this.oscar = oscar;
                this.personaje = personaje;
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
                return oscar.compareTo(otro.oscar);
            }
    
            public void setTitulo(String titulo) {
                this.titulo = titulo;
            }
    
            public String getTitulo() {
                return titulo;
            }
    
            public void setOscar(String oscar) {
                this.oscar = oscar;
            }
    
            public String getOscar() {
                return oscar;
            }
            
            public String getPersonaje() {
                return personaje;
            }
    
            public void setPersonaje(String personaje) {
                this.personaje = personaje;
            }
    
        }