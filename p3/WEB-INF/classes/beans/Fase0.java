package beans;

import java.io.Serializable;

public class Fase0 implements Serializable{
    
/*     private boolean modo; */
    private String passIntroducida;

    public Fase0() {}
    
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

}