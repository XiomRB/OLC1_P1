package olc1_ptc1_201500332;

import java.util.ArrayList;
import vista.Conjunto;
import vista.DefExpresion;

public class AnalizadorSint {

    public ArrayList<DefExpresion> defexpresion = new ArrayList();
    public ArrayList<Conjunto> conjunto = new ArrayList();
    public ArrayList<Expresion> expresion = new ArrayList();
    public boolean mal = false;

    public String obtenerDatos(ArrayList<Token> tokens) {
        String mensaje = "";
        int tipo = 0;
        int i = 0;

        while (i < tokens.size() && !mal) {
            tipo = tokens.get(i).getTipo();
            if (tipo == 3) {
                if (tokens.get(i + 1).getTipo() == 17) {
                    if (tokens.get(i + 2).getTipo() == 19) {
                        expresion.add(new Expresion(tokens.get(i).getLexema(), tokens.get(i + 2).getLexema()));
                        i += 3;
                    } else mensaje = mensajeError(tokens.get(i).getLinea());                   
                } else if (tokens.get(i + 1).getTipo() == 9) {
                    if(tokens.get(i + 2).getTipo() == 12){
                        if(tokens.get(i + 3).getTipo() == 5){
                            conjunto.add(new Conjunto(tokens.get(i).getLexema(), tokens.get(i+3).getLexema()));
                            i += 4;
                        }else{
                            tipo = tokens.get(i+3).getTipo();
                            while(i < tokens.size() && !mal && tokens.get(i).getTipo() != 18){
                                switch(tipo){
                                    case 6:
                                        break;
                                    case 7:
                                        break;
                                    case 8:
                                        break;
                                    case 10:
                                        break;
                                    case 11:
                                        break;
                                }
                            }
                        }
                    }
                    
                }
                    /*tipo = tokens.get(i+3).tipo;
                        switch(tipo){
                            case 19:
                                break;
                            case 5:
                                break;
                            case 6:
                            case 7:
                            case 8:
                            case 10:
                            case 11:
                                break;
                            default:
                                
                        }
                    }*/
                }
            }
            return mensaje;
        }
    
    

    public String mensajeError(int i) {
        mal = true;
        return "Se esperaba una cadena, en la linea: " + i;
    }
    
    public ArrayList definirArbol(ArrayList<DefExpresion> raiz,ArrayList<Token> tokens, int i){
        if(tokens.get(i).getTipo()== 19){
            raiz.add(new DefExpresion(tokens.get(i).getLexema()));
        }else if(tokens.get(i).getTipo() == 1 && tokens.get(i+1).getTipo() == 3 && tokens.get(i+2).getTipo() == 2){
            raiz.add(new DefExpresion(tokens.get(i+1).getLexema()));
        }else if(tokens.get(i).getTipo() == 6 || tokens.get(i).getTipo() == 7 || tokens.get(i).getTipo() == 8){
            raiz.add(new DefExpresion(tokens.get(i).getLexema()));
            definirArbol(raiz.get(raiz.size()-1).hijos, tokens, i+1);
        }else if(tokens.get(i).getTipo() == 10 || tokens.get(i).getTipo() == 11){
            raiz.add(new DefExpresion(tokens.get(i).getLexema()));
            
        }
    }
}
