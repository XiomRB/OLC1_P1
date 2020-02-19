package olc1_ptc1_201500332;

import java.util.ArrayList;
import vista.DefExpresion;

public class Transicion {
    private int id;
    public ArrayList actual = new ArrayList();
    public ArrayList<PasoTransicion> futuro = new ArrayList();
    private boolean acepta = false;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAcepta() {
        return acepta;
    }

    public void setAcepta(boolean acepta) {
        this.acepta = acepta;
    }
    
    public ArrayList<Transicion> darEstado(DefExpresion raiz){
        ArrayList<Transicion> transiciones = new ArrayList();
        transiciones.add(new Transicion());
        transiciones.get(0).actual = (ArrayList) raiz.fp.clone();
        return transiciones;
    }
    
    public void darTransicion(ArrayList<Transicion> transiciones, ArrayList<Siguiente> sigs){
        String cadena = "";
        int e = 0;
        for (int i = 0; i < transiciones.size(); i++) {
            for (int j = 0; j < transiciones.get(i).actual.size(); j++) {
                cadena = sigs.get((int)transiciones.get(i).actual.get(j)).getId(); //guarda el conjunto o cadena que le pertence a la posicion en siguientes
                if(transiciones.get(i).futuro.isEmpty()){ //si el futuro esta vacio
                    transiciones.get(i).futuro.add(new PasoTransicion(cadena)); //agrega un nuevo paso
                    transiciones.get(i).futuro.get(0).sigs.addAll(sigs.get((int)transiciones.get(i).actual.get(j)).siguientes); // a ese paso se le dan los siguientes de la posicion 
                }
                else{
                    e = encontrarPaso(cadena, transiciones.get(i).futuro);
                    if(e==transiciones.get(i).futuro.size()) {
                        transiciones.get(i).futuro.add(new PasoTransicion(cadena));
                        transiciones.get(i).futuro.get(transiciones.get(i).futuro.size()-1).sigs.addAll(sigs.get((int)transiciones.get(i).actual.get(j)).siguientes);
                    }
                    else{
                        transiciones.get(i).futuro.get(e).sigs.addAll(sigs.get((int)transiciones.get(i).actual.get(j)).siguientes);
                    }
                }
            }
        }
    }
    
    public int encontrarEstado(ArrayList<Transicion> transiciones, ArrayList<Integer> sigs){
        int i = 0;
        boolean encontrado = false;
        while(i < transiciones.size() && !encontrado){
            if(sigs.containsAll(transiciones.get(i).actual)) encontrado = true;
            else i++;
        }
        return i;
    }
    
    public int encontrarPaso(String cadena,ArrayList<PasoTransicion> paso){
        int i = 0;
        boolean encontrado = false;
        while(i<paso.size() && !encontrado){
            if(paso.get(i).getId().equalsIgnoreCase(cadena)) encontrado = true;
            else i++;
        }
        return i;
    }
}
