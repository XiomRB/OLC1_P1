
package olc1_ptc1_201500332;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class Analizador {
    public ArrayList<Token> tokens = new ArrayList();
    char caracter;
    int estado = 0;
    boolean error = false;
    String tok = "";
    
    public String analizar(JTextArea texto){
        String analisis = "";
        tokens.clear();
        String archivo = texto.getText() + "#";
        int i = 0;
        int j = 0;
        int total_caract = archivo.length();
        if(total_caract > 1){
            while(!error && j < total_caract){
                caracter = archivo.charAt(j);
                switch(estado){
                    case 0:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(Character.isLetter(caracter)){
                            estado = 1;
                            tok += caracter;
                        }else if(Character.isDigit(caracter)){
                            estado = 6;
                            tok += caracter;
                        }else if(caracter == '>'||caracter =='.'||caracter==';'||caracter==':'||caracter=='?'||caracter=='+'||caracter=='*'||caracter=='|'||caracter=='%'||caracter=='{'||caracter=='}'){
                            estado = 8;
                            tok += caracter;
                        }else if(caracter == '-'){
                            estado = 5;
                            tok += caracter;
                        }else if(caracter == '<'){
                            estado = 4;
                            tok += caracter;
                        }else if(caracter == '"'){
                            estado = 2;
                            tok += caracter;
                        }else if(caracter == '/'){
                            estado = 3;
                            tok += caracter;                            
                        }else if(Character.isWhitespace(caracter) || caracter == '\t' ||caracter == '\r'){
                            estado = 0;
                        }else if(caracter == '\n'){
                            estado = 0;
                            i++;
                        } else if(caracter == '#' && total_caract-1 == j){
                            analisis = "Archivo analizado";
                        }                      
                        else if(caracter != ','){
                            estado = 7;
                            tok += caracter;
                        }else{
                            analisis = mensajeError(i, caracter);
                        }
                        break;
                    case 1:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(Character.isLetterOrDigit(caracter) || caracter == '_'){
                            estado = 1;
                            tok += caracter;
                        }else {
                            if(tok.equalsIgnoreCase("conj")) validarToken(4, i, tok, j);
                            else {
                                validarToken(3, i, tok, j);
                                j--;
                            }
                        }
                        break;
                    case 2:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != '"'){
                            estado = 9;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 3:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == '/'){
                            estado = 10;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 4:
                        
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == '!'){
                            estado = 11;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 5:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(Character.isDigit(caracter)){
                            estado = 6;
                            tok += caracter;
                        }else {
                            validarToken(9, i, tok, j);
                            j--;
                        }
                        break;
                    case 6:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(Character.isDigit(caracter)){
                            estado = 6;
                            tok += caracter;
                        }else if(caracter == '.'){
                            estado = 12;
                            tok += caracter;
                        }else {
                            validarToken(16, i, tok, j);
                            j--;
                        }
                        break;
                    case 7:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == ','){
                            estado = 13;
                            tok += caracter;
                        } else if(caracter == '-'){
                            estado = 14;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 8:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(tok.length()==1){
                            if(tok.equals(">")) validarToken(12, i, tok, j);
                            else if(tok.equals("."))validarToken(11, i, tok, j);
                            else if(tok.equals(";")) validarToken(18, i, tok, j);
                            else if(tok.equals(":")) validarToken(17, i, tok, j);
                            else if(tok.equals("?")) validarToken(7, i, tok, j);
                            else if(tok.equals("+"))validarToken(8, i, tok, j);
                            else if(tok.equals("*")) validarToken(6, i, tok, j);
                            else if(tok.equals("|")) validarToken(10, i, tok, j);
                            else if(tok.equals("%")) validarToken(15, i, tok, j);
                            else if(tok.equals("{")) validarToken(1, i, tok, j);
                            else if(tok.equals("}"))validarToken(2, i, tok, j);
                        }else{
                            if(tok.endsWith(";")) validarToken(5, i, tok, j);
                            else if(tok.endsWith(">")) validarToken(21, i, tok, j);
                            else if(tok.endsWith("\"")) validarToken(19, i, tok, j);
                        }
                        j--;
                        break;
                    case 9:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != '"'){
                            estado = 9;
                            tok += caracter;
                        }else{
                            estado = 8;
                            tok += caracter;
                        }
                        break;
                    case 10:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != '\n'){
                            estado = 10;
                            tok += caracter;
                        }else {
                            validarToken(20, i, tok, j);
                            j--;
                        }
                        break;
                    case 11:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != '!'){
                            estado = 11;
                            tok += caracter;
                        }else{
                            estado = 15;
                            tok += caracter;
                        }break;
                    case 12:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(Character.isDigit(caracter)){
                            estado = 6;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 13:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != ','){
                            estado = 16;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 14:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter != '-'){
                            estado = 17;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 15:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == '>'){
                            estado = 8;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 16:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == ','){
                            estado = 13;
                            tok += caracter;
                        }else if(caracter == ';'){
                            estado = 8;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
                        break;
                    case 17:
                        System.out.println(caracter + "estado: " + String.valueOf(estado));
                        if(caracter == ';'){
                            estado = 8;
                            tok += caracter;
                        }else analisis = mensajeError(i, caracter);
            }
                j++;
        }
        
    }
        return analisis;
    }
    
    public void validarToken(int t,int i,String tok,int j){
        estado = 0;
        tokens.add(new Token(t, tok, i));
        this.tok = ""; 
    }
    public String mensajeError(int linea, char error){
        this.error = true;
        return "Error en la linea: " + String.valueOf(linea) + "el caracter invalido es: " + error;
    }
}
