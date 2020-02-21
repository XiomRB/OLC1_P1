package olc1_ptc1_201500332;

import java.util.ArrayList;
import java.util.Collections;
import vista.DefExpresion;

public class Transicion {
    private int id;
    public ArrayList<Integer> actual = new ArrayList();
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
        transiciones.get(0).setId(0);
        return transiciones;
    }
    
    public void darTransicion(ArrayList<Transicion> transiciones, ArrayList<Siguiente> sigs){
        String cadena = "";
        int e = 0;
        for (int i = 0; i < transiciones.size(); i++) {
            for (int j = 0; j < transiciones.get(i).actual.size(); j++) {
            cadena = sigs.get((int)transiciones.get(i).actual.get(j)).getId();
            e = encontrarPaso(cadena,transiciones.get(i).futuro);
            if(e==transiciones.get(i).futuro.size()){
                if (!cadena.equals("#")) {
                    transiciones.get(i).futuro.add(new PasoTransicion(cadena));
                    transiciones.get(i).futuro.get(transiciones.get(i).futuro.size()-1).sigs.addAll(sigs.get((int)transiciones.get(i).actual.get(j)).siguientes);
                }else transiciones.get(i).setAcepta(true);
                
            }else{
                agregarSigs(transiciones.get(i).futuro.get(e).sigs, sigs.get((int)transiciones.get(i).actual.get(j)).siguientes);
            } 
        }
            crearEstado(transiciones, transiciones.get(i).futuro);
        }
    }
    
    public int encontrarEstado(ArrayList<Transicion> transiciones, ArrayList<Integer> sigs){
        int i = 0;
        boolean encontrado = false;
        while(i < transiciones.size() && !encontrado){
            if(sigs.equals(transiciones.get(i).actual)) encontrado = true;
            else i++;
        }
        return i;
    }
    
    public void crearEstado(ArrayList<Transicion> transiciones,ArrayList<PasoTransicion>fut){
        int tam = 0;
        int estado = 0;
        for (int i = 0; i < fut.size(); i++) {
            if (!fut.get(i).sigs.isEmpty()) {
                estado = encontrarEstado(transiciones, fut.get(i).sigs);
                if(estado==transiciones.size()){//si no encontro un estado
                    transiciones.add(new Transicion());
                    transiciones.get(estado).setId(estado);
                    transiciones.get(estado).actual = (ArrayList) fut.get(i).sigs.clone();
                    fut.get(i).setEstado(estado);
                }else{
                    fut.get(i).setEstado(estado);
                }
            }
            
        }
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
    
    public boolean buscarSig(int n, ArrayList<Integer> sigs){
        int i = 0;
        boolean encontrado = false;
        while(i<sigs.size() && !encontrado){
            if(n==sigs.get(i)) encontrado = true;
            else i++;
        }
        return encontrado;
    }
    
    public void agregarSigs(ArrayList<Integer> sig,ArrayList siguientes){
        if (!siguientes.isEmpty()) {
            for (int i = 0; i < siguientes.size(); i++) {
                if(!buscarSig((int)siguientes.get(i), sig)) sig.add((int)siguientes.get(i));
            }
            Collections.sort(sig);
        }
    }
    
    public String dibujarTransicion(ArrayList<Transicion> trans, ArrayList<Siguiente> h){
        String cadena = "node1 [shape = plaintext label=< \n <table border='1' cellborder='1' cellspacing = '0'>";
        ArrayList<ArrayList> tabla = darTabla(h, trans);
       //System.out.println(tabla.get(0).toString());
        cadena = cadena + "<tr> <td rowspan=\"2\">Estado</td><td colspan = \"" + tabla.get(0).size() + "\">Terminales</td></tr><tr>";
        for (int i = 0; i < tabla.get(0).size(); i++) {
            cadena = cadena + "<td>" + tabla.get(0).get(i) + "</td>";
        }
        cadena = cadena + "</tr>\n";
        for (int i = 0; i < trans.size(); i++) {
            cadena = cadena + "<tr>";
            if(trans.get(i).isAcepta()) cadena  = cadena + "<td bgcolor = \"lightblue\">";
            else cadena = cadena + "<td>";
            cadena = cadena + "S" + trans.get(i).id + " = {";
            if (!trans.get(i).actual.isEmpty()) {
                for (int j = 0; j < trans.get(i).actual.size(); j++) {
                    if (j == trans.get(i).actual.size()-1) cadena = cadena + trans.get(i).actual.get(j) + "}</td>";
                    else cadena = cadena + trans.get(i).actual.get(j) + ",";
                }
            }else cadena = cadena + "</td>";
            for (int k = 0; k < tabla.get(i+1).size(); k++) {
                cadena = cadena + "<td>" + tabla.get(i+1).get(k) + "</td>";
            }
            cadena = cadena + "</tr>\n";
        }
        cadena = cadena + "</table>>];\n";
        return cadena;
    }
    
    public ArrayList<ArrayList> darTabla(ArrayList<Siguiente> s,ArrayList<Transicion>trans){
        ArrayList<ArrayList> tabla = new ArrayList();
        ArrayList<String> terminales = new ArrayList();
        terminales.add(s.get(0).getId());
        for (int i = 1; i < s.size(); i++) {
            if(!s.get(i).getId().equals("#")){
                if (!buscarTerminal(s.get(i).getId(), terminales)) terminales.add(s.get(i).getId());              
            }
        }
        tabla.add(new ArrayList());
        for (int i = 0; i < terminales.size(); i++) {
            tabla.get(0).add(terminales.get(i));
        }
        int size = terminales.size();
        for (int i = 0; i < trans.size(); i++) {
            terminales.clear();
            for (int j = 0; j < size; j++) {
                terminales.add("---");
            }
            for (int j = 0; j < trans.get(i).futuro.size(); j++) {
                for (int k = 0; k < size; k++) {
                    if (!trans.get(i).futuro.isEmpty()) {
                        if(trans.get(i).futuro.get(j).getId().equals(tabla.get(0).get(k))) terminales.set(k, "S"+trans.get(i).futuro.get(j).getEstado());
                              
                    }
                }
            }
            tabla.add(new ArrayList());
            for (int j = 0; j < terminales.size(); j++) {
                tabla.get(tabla.size()-1).add(terminales.get(j));
            }
        }
        return tabla;
    } 
    
    public boolean buscarTerminal(String cadena, ArrayList terminales){
        boolean encontrado = false;
        int i = 0;
        while(i<terminales.size()&&!encontrado){
            if(terminales.get(i).equals(cadena))encontrado = true;
            else i++;
        }
        return encontrado;
    }
}
