
package olc1_ptc1_201500332;

import java.util.ArrayList;
import java.util.Collections;
import vista.DefExpresion;

public class Siguiente {
    private String id;
    private int hoja;
    public ArrayList siguientes = new ArrayList();

    public Siguiente(String id, int hoja) {
        this.id = id;
        this.hoja = hoja;
    }

    public String getId() {
        return id;
    }

    public int getHoja() {
        return hoja;
    }

    public void setHoja(int hoja) {
        this.hoja = hoja;
    }
    
    public void darSiguientesHojas(DefExpresion exp, ArrayList<Siguiente> sig){
        if(!exp.hijos.isEmpty()){
            for (int i = 0; i < exp.hijos.size(); i++) {
                if(exp.hijos.get(i).getTipo() == 6 || exp.hijos.get(i).getTipo() == 8){ // si viene un * o un +
                    darSiguientesHojas(exp.hijos.get(i),sig);
                    for (int j = 0; j < exp.hijos.get(i).lp.size(); j++) {
                        sig.get((int)exp.hijos.get(i).lp.get(j)).siguientes.addAll(exp.hijos.get(i).fp); //segun cada hoja que tiene como lp se le asignan los fp
                        Collections.sort(sig.get((int)exp.hijos.get(i).lp.get(j)).siguientes);
                    }
                }else if(exp.hijos.get(i).getTipo() == 11){ //si viene un .
                    darSiguientesHojas(exp.hijos.get(i),sig);
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(0).lp.size(); j++) {
                        sig.get((int)exp.hijos.get(i).hijos.get(0).lp.get(j)).siguientes.addAll(exp.hijos.get(i).hijos.get(1).fp); // le da como siguientes a la lp del hijo 0 los fp del hijo 1
                        Collections.sort(sig.get((int)exp.hijos.get(i).hijos.get(0).lp.get(j)).siguientes);
                    }
                }else if(exp.hijos.get(i).getTipo() == 10 || exp.hijos.get(i).getTipo() == 7){ //si viene un | o un ?
                    darSiguientesHojas(exp.hijos.get(i),sig);
                }
            }
        }
    }
    
    public void darSiguienteRaiz(DefExpresion exp, ArrayList<Siguiente> sig){
        darSiguientesHojas(exp,sig);
        if(!exp.hijos.isEmpty()){
            for (int j = 0; j < exp.hijos.get(0).lp.size(); j++) {
                exp.hojas.get((int)exp.hijos.get(0).lp.get(j)).siguientes.addAll(exp.hijos.get(1).fp); // le da como siguientes a la lp del hijo 0 los fp del hijo 1
                Collections.sort(exp.hojas.get((int)exp.hijos.get(0).lp.get(j)).siguientes);
            }
        }
    }
    
    public String dibujarSiguientes(ArrayList<Siguiente> sig){
        String cadena = "";
        if(!sig.isEmpty()){
            cadena = "node1 [shape = record label=\"{HOJA|";
            for (int i = 0; i < sig.size(); i++) {
                if(i != sig.size()-1) cadena = cadena + "{" + sig.get(i).getId() + "|" + sig.get(i).getHoja() + "}|";
                else cadena = cadena + "{" + sig.get(i).getId() + "|" + sig.get(i).getHoja() + "}}|{SIGUIENTES|";
            }
            for (int i = 0; i < sig.size(); i++) {
                for (int j = 0; j < sig.get(i).siguientes.size(); j++) {
                    if(j == sig.get(i).siguientes.size()-1) cadena = cadena + sig.get(i).siguientes.get(j);
                    else cadena = cadena + sig.get(i).siguientes.get(j) + ",";
                }
               if(i!=sig.size()-1) cadena = cadena + "|";
               else cadena = cadena + "}\"];";
            }
        }
        return cadena;
    }
    
}
