package olc1_ptc1_201500332;

import java.util.ArrayList;

public class PasoTransicion {
    private String id;
    public ArrayList<Integer> sigs = new ArrayList();
    private int estado = 0;
    public boolean encontrado = false;
    
    public PasoTransicion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
