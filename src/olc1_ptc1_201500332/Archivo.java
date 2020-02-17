
package olc1_ptc1_201500332;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import vista.Conjunto;

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
    
    public void generarGrafica(String cont,String nombre) throws IOException 
    {        
        String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";        
        String direccionSalida = "C:\\Users\\OLGA BARRIOS\\Desktop\\GraficasOLC1P1\\ "+ nombre + ".png";
        String direccionEntrada = "C:\\Users\\OLGA BARRIOS\\Desktop\\GraficasOLC1P1\\" + nombre + ".dot";
        
        String cadena = "digraph G{\n" + cont + "}";
        cadena = cadena.replace("\"\"","\"");
        cadena = cadena.replace("\\","\\\\");
        
        try (  PrintWriter writer = new PrintWriter(direccionEntrada)) {
            writer.print(cadena);            
        } 
        String tParam = "-Tpng";
        String tOParam = "-o";      
        String[] cmd = new String[5];
              cmd[0] = dotPath;
              cmd[1] = tParam;
              cmd[2] = direccionEntrada;
              cmd[3] = tOParam;
              cmd[4] = direccionSalida;
              Runtime rt = Runtime.getRuntime();
              rt.exec( cmd );
    }
}
