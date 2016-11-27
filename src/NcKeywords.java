import java.util.ArrayList;

/**
 * Created by aa-ol on 11/26/2016.
 */
public class NcKeywords {
    String nombre = "";
    String lexema = "";
    String regex="";

    //Constructor
    public NcKeywords(ArrayList<NcKeywords> keywords, String nombre, String lexema) {
        this.nombre = nombre;
        this.lexema = lexema;
        regex = lexema.replace("\"", "");
    }

    //getter and setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }


}
