package vista;

import java.util.ArrayList;

public class Conjunto {
    private String id;
    private String conjunto;

    public Conjunto(String id, String conjunto) {
        this.id = id;
        this.conjunto = conjunto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConjunto() {
        return conjunto;
    }

    public void setConjunto(String conjunto) {
        this.conjunto = conjunto;
    }
    
    public void imprimirConjuntos(ArrayList conj){
        
    }
}
