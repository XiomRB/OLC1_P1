package vista;

import java.io.IOException;
import java.io.PrintWriter;
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
    
    public String imprimirConjuntos(ArrayList<Conjunto> conj){
        String cadena = "";
        if(!conj.isEmpty()){
            cadena = "node1 [shape = record label=\"{ID|";
            int i = 0;
            while(i < conj.size()){
                if(i == conj.size()-1) cadena = cadena +  conj.get(i).getId() + "}|";               
                else cadena = cadena + conj.get(i).getId() + "|";
                i++;
            }
            i = 0;
            cadena = cadena + "{SIMBOLOS|";
            while(i<conj.size()){
                if(i == conj.size()-1) cadena = cadena +  conj.get(i).getConjunto()+ "}\"];\r\n"; 
                else cadena = cadena + conj.get(i).getConjunto()+ "|";
                i++;
            }
        }
        
        return cadena;
    }
}
