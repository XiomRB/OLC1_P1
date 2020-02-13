package olc1_ptc1_201500332;

import java.util.ArrayList;
import vista.Conjunto;
import vista.DefExpresion;

public class AnalizadorSint {

    public ArrayList<DefExpresion> defexpresion = new ArrayList();
    public ArrayList<Conjunto> conjunto = new ArrayList();
    public ArrayList<Expresion> expresion = new ArrayList();
    public boolean mal = false;
    public int i;
    String mensaje = "";
    
    public String obtenerDatos(ArrayList<Token> tokens) {
        defexpresion.clear();
        conjunto.clear();
        expresion.clear();
        mensaje = "";
        int tipo = 0;
        i = 0;
        mal = false;
        while (i < tokens.size() && !mal) {
            tipo = tokens.get(i).getTipo();
            if (tipo == 3) { //corroborar si hay un id
                if (tokens.get(i + 1).getTipo() == 17) { //corroborar si viene :
                    if (tokens.get(i + 2).getTipo() == 19) {//corroborar si viene una cadena
                        expresion.add(new Expresion(tokens.get(i).getLexema(), tokens.get(i + 2).getLexema()));
                        i += 3;
                    } else mensajeError(tokens.get(i).getLinea());                   
                } else if (tokens.get(i + 1).getTipo() == 9) {//corroborar si viene un guion
                    if(tokens.get(i + 2).getTipo() == 12){//comprobar que venga >
                        if(tokens.get(i + 3).getTipo() == 5){//comprobar que venga una definicion de conjunto
                            conjunto.add(new Conjunto(tokens.get(i).getLexema(), tokens.get(i+3).getLexema()));
                            i += 4;
                        }else{
                            i += 3;
                            defexpresion.add(new DefExpresion("."));
                            while(i < tokens.size() && !mal && tokens.get(i).getTipo() != 18){                             
                                definirArbol(defexpresion.get(defexpresion.size()-1).hijos, tokens);
                            }
                        }
                    }else mensajeError(tokens.get(i).getLinea());                
                } else mensajeError(tokens.get(i).getLinea());
                }else i++;
            }
            if(!mal) mensaje = "Tablas creadas";
            return mensaje;
        }
    
    

    public void mensajeError(int i) {
        mal = true;
        mensaje = "Mal sintaxis en la linea: " + i;
    }
    
    public ArrayList definirArbol(ArrayList<DefExpresion> raiz,ArrayList<Token> tokens){
        if(!mal){
            if(tokens.get(i).getTipo()== 19){
                raiz.add(new DefExpresion(tokens.get(i).getLexema())); //agrega la cadena al nodo raiz
                i++;
            }else if(tokens.get(i).getTipo() == 1 && tokens.get(i+1).getTipo() == 3 && tokens.get(i+2).getTipo() == 2){ //corrobora que sea un conjunto
                int j = encontrarConj(tokens.get(i+1).getLexema());
                if(j != -1){
                    raiz.add(new DefExpresion(conjunto.get(j).getConjunto())); // agrega el conjunto al nodo raiz
                i += 3;
                }else {
                    mensaje = "Conjunto no definido";
                    mal = true;
                }           
            }else if(tokens.get(i).getTipo() == 6 || tokens.get(i).getTipo() == 7 || tokens.get(i).getTipo() == 8){
                raiz.add(new DefExpresion(tokens.get(i).getLexema())); //agrega + o * o ? al nodo raiz
                i++;
                definirArbol(raiz.get(raiz.size()-1).hijos, tokens); // agrega 
            }else if(tokens.get(i).getTipo() == 10 || tokens.get(i).getTipo() == 11){
                raiz.add(new DefExpresion(tokens.get(i).getLexema())); //agrega . o | al nodo raiz
                i++;
                definirArbol(raiz.get(raiz.size()-1).hijos, tokens); // agrega el primer nodo de . o de |
                definirArbol(raiz.get(raiz.size()-1).hijos, tokens); // agrega el segundo nodo de . o de |
            }else {
                mal = true;
                mensajeError(tokens.get(i).getLinea());
                defexpresion.remove(defexpresion.size()-1);
            }
        }       
        return raiz;
    }
    
    public int encontrarConj(String c){
        int j = 0;
        boolean encontrado = false;
        while(!encontrado && j < conjunto.size()){
            if(conjunto.get(j).getId().equalsIgnoreCase(c)) encontrado = true;
            j++;
        }
        j--;
        if(!encontrado) j = -1;
        return j;
    }
}
