
package olc1_ptc1_201500332;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Archivo {
    FileInputStream entrada;
    FileOutputStream salida;
    File archivo;

    public Archivo() {
    }
    
    public String Abrir(File archivo){
        String texto ="";
        try {
            entrada = new FileInputStream(archivo);
            int ascci;
            while((ascci = entrada.read()) != -1){
                char caracter = (char) ascci;
                texto += caracter;
            }
        } catch (Exception e) {
        }
        return texto;
    }
}
