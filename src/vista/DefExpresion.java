
package vista;

import java.util.ArrayList;

public class DefExpresion {   
    private String id;
    private int fp = 0;
    private int lp = 0;
    private boolean anulable = false;
    public ArrayList<DefExpresion> hijos = new ArrayList();

    public DefExpresion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }
    
}
