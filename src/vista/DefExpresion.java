
package vista;

import java.util.ArrayList;
import olc1_ptc1_201500332.Siguiente;

public class DefExpresion {   
    private String id;
    private int tipo = 0;
    public  ArrayList fp = new ArrayList();
    public  ArrayList lp = new ArrayList();
    private boolean anulable = false;
    public ArrayList<DefExpresion> hijos = new ArrayList();
    public ArrayList<Siguiente> hojas = new ArrayList();
    int  p = 0;
    
    public DefExpresion(String id,int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public int getTipo() {
        return tipo;
    }
 
    public String dibujarExpresion(DefExpresion raiz){
        String cadena = "N"+raiz.hashCode()+"[label=\""+raiz.getId()+"\"]; \n";   
        if(!raiz.hijos.isEmpty())
        {
            for(int i = 0; i<raiz.hijos.size();i++){
                cadena = cadena +"N"+raiz.hijos.get(i).hashCode()+"[label=\""+raiz.hijos.get(i).getId()+"\"]; \n";
            }
            for(int i = 0; i<raiz.hijos.size();i++){
                cadena = cadena +"N"+raiz.hashCode()+"->"  +"N"+raiz.hijos.get(i).hashCode()+" ; \n";
            }
            for(int i = 0; i<raiz.hijos.size();i++){
                cadena = cadena + dibujarExpresion(raiz.hijos.get(i));          
            }
        }
        return cadena;
    }
    
    public DefExpresion definirPosiciones(DefExpresion exp, int c){
        this.p = c;
        if(!exp.hijos.isEmpty()){
            for (int i = 0; i < exp.hijos.size(); i++) {
                if(exp.hijos.get(i).getTipo()==19){ //si viene una cadena
                    exp.hijos.get(i).fp.add(p); //colocar p como first position
                    exp.hijos.get(i).lp.add(p); //colocar p como last position
                    exp.hijos.get(i).setAnulable(false); //no anulable
                    hojas.add(new Siguiente(exp.hijos.get(i).getId().substring(1,exp.hijos.get(i).getId().length()-1),(int)exp.hijos.get(i).fp.get(0)));
                    p++;
                }else if(exp.hijos.get(i).getTipo()==5){ //si viene un conjunto
                    exp.hijos.get(i).fp.add(p);//colocar p como first position
                    exp.hijos.get(i).lp.add(p);//colocar p como last position
                    exp.hijos.get(i).setAnulable(false);//no anulable
                    hojas.add(new Siguiente(exp.hijos.get(i).getId(),(int)exp.hijos.get(i).fp.get(0)));
                    p++;
                }else if(exp.hijos.get(i).getTipo()==22){ //si viene #
                    exp.hijos.get(i).lp.add(p); //colocar p como last position
                    exp.hijos.get(i).fp.add(p); //colocar p como first position
                    exp.setAnulable(false); //no anulable
                    hojas.add(new Siguiente(exp.hijos.get(i).getId(),(int)exp.hijos.get(i).fp.get(0)));
                } else if(exp.hijos.get(i).getTipo()== 6 ||exp.hijos.get(i).getTipo()== 7  || exp.hijos.get(i).getTipo()== 8 ){//si viene un *, ? o +
                    definirPosiciones(exp.hijos.get(i), p); //buscas sus hojas
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(0).fp.size(); j++) { //solo tiene una hoja, por lo que sus first position igual a los de su hoja
                        exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(0).fp.get(j));
                    }
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(0).lp.size(); j++) { //solo tiene una hoja, por lo que sus last position igual a los de su hoja
                        exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(0).lp.get(j));
                    }
                    if(exp.hijos.get(i).getTipo()== 6 || exp.hijos.get(i).getTipo()== 7) exp.hijos.get(i).setAnulable(true);//si es * o ? es anulable
                    else{
                        if(exp.hijos.get(i).hijos.get(0).isAnulable()) exp.hijos.get(i).setAnulable(true);//si es + y su hoja es anulable, se anula
                        else exp.hijos.get(i).setAnulable(false);
                    }
                }else if(exp.hijos.get(i).getTipo()==10){//si viene |
                    definirPosiciones(exp.hijos.get(i), p);//busca sus hojas
                    if(!exp.hijos.get(i).hijos.get(0).isAnulable() && !exp.hijos.get(i).hijos.get(1).isAnulable()) exp.hijos.get(i).setAnulable(false);//si ninguna de sus dos hojas es anulable entonces no es anulable
                    else exp.hijos.get(i).setAnulable(true);
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(0).fp.size(); j++) {//recibe los first position de sus dos hojas
                        exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(0).fp.get(j));
                    }
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(1).fp.size(); j++) {
                        exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(1).fp.get(j));
                    }
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(0).lp.size(); j++) {//recibe los last position de sus dos hojas
                        exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(0).lp.get(j));
                    }
                    for (int j = 0; j < exp.hijos.get(i).hijos.get(1).lp.size(); j++) {
                        exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(1).lp.get(j));
                    }
                }else if(exp.hijos.get(i).getTipo()==11){//si viene .
                    definirPosiciones(exp.hijos.get(i), p);//busca sus hojas
                    if(exp.hijos.get(i).hijos.get(0).isAnulable()){ //si la primera hoja es anulable
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(0).fp.size(); j++) {//recibe los first position de sus dos hojas
                            exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(0).fp.get(j));
                        }
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(1).fp.size(); j++) {
                            exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(1).fp.get(j));
                        }                        
                    }else{
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(0).fp.size(); j++) { //sino solo recibe los first position de su primer hoja
                            exp.hijos.get(i).fp.add(exp.hijos.get(i).hijos.get(0).fp.get(j));
                        }
                    }
                    if(exp.hijos.get(i).hijos.get(1).isAnulable()){//si su segunda hoja es anulable
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(0).lp.size(); j++) {//recibe los last position de sus dos hojas
                            exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(0).lp.get(j));
                        }
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(1).lp.size(); j++) {
                            exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(1).lp.get(j));
                        }
                    }else{
                        for (int j = 0; j < exp.hijos.get(i).hijos.get(1).lp.size(); j++) {//sino solo recibe los last position de su segunda hoja
                            exp.hijos.get(i).lp.add(exp.hijos.get(i).hijos.get(1).lp.get(j)); //
                        }
                    }
                    if(exp.hijos.get(i).hijos.get(0).isAnulable() && exp.hijos.get(i).hijos.get(1).isAnulable()) exp.hijos.get(i).setAnulable(true);
                    else exp.hijos.get(i).setAnulable(false);
                }
            }
        }
        return exp;
    }
    
    public void terminarPosiciones(ArrayList<DefExpresion> exp){
        if(!exp.isEmpty()){            
            for (int i = 0; i < exp.size(); i++) {
                hojas.clear();
                definirPosiciones(exp.get(i), 0);
                exp.get(i).hojas = (ArrayList) hojas.clone();
                if(!exp.get(i).hijos.isEmpty()){
                    if(exp.get(i).hijos.get(0).isAnulable()){
                        for (int j = 0; j < exp.get(i).hijos.get(0).fp.size(); j++) {
                            exp.get(i).fp.add(exp.get(i).hijos.get(0).fp.get(j));
                        }
                        for (int j = 0; j < exp.get(i).hijos.get(1).fp.size(); j++) {
                            exp.get(i).fp.add(exp.get(i).hijos.get(1).fp.get(j));
                        }
                    }else{
                        for (int j = 0; j < exp.get(i).hijos.get(0).fp.size(); j++) {
                            exp.get(i).fp.add(exp.get(i).hijos.get(0).fp.get(j));
                        }
                    }
                    if(exp.get(i).hijos.get(1).isAnulable()){
                        for (int j = 0; j < exp.get(i).hijos.get(0).lp.size(); j++) {
                            exp.get(i).lp.add(exp.get(i).hijos.get(0).lp.get(j));
                        }
                        for (int j = 0; j < exp.get(i).hijos.get(1).lp.size(); j++) {
                            exp.get(i).lp.add(exp.get(i).hijos.get(1).lp.get(j));
                        }
                    }else{
                        for (int j = 0; j < exp.get(i).hijos.get(1).lp.size(); j++) {
                            exp.get(i).lp.add(exp.get(i).hijos.get(1).lp.get(j));
                        }
                    }
                    exp.get(i).setAnulable(false);
                }           
            }
        }
        
    }
}
