import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import java.util.ArrayList;

/**
 * Created by aa-ol on 11/24/2016.
 */
public class NcCharcters {
    String nombre = "";
    String lexema = "";
    String regex="";
    String partesDeLaCadena[];
    String hola="";
    int numero = 0;
    ArrayList<String> simbolos = new ArrayList<>();

    int cont5=0,cont6=0;
    //Esto es para saber si es o no un String/Idet/Char
    boolean isString = false;
    boolean isIdent = false;
    boolean isChar = false;



    //AQUI***************************************************************************************
    //Constructor
    public NcCharcters(ArrayList<NcCharcters> characters, String nombre, String lexema) {
        System.out.println("Constructor de NcChar \'" + nombre+ "\'" );
        this.nombre = nombre;
        this.lexema = lexema;

        /*Despues de que se ingresa el id y el lexema, se procede a separar las partes
        * del lexema.*/
        separarLasPartes(lexema);
        System.out.println("We are here after separate the lines");
        generarLaExpresionRegular(characters);
        System.out.println("ya se genero la regex");
    }



    /**
     * Separa la cadena en partes con base en los signos mas y menos
     * @param cadena
     */
    private void separarLasPartes(String cadena){
        partesDeLaCadena = cadena.split("-|\\+");

        //Agrega los simbolos encontrados al arreglo de simbolos
        for (int i = 0; i<cadena.length()-1;i++){
            if (cadena.substring(i, i+1).equals("+") || cadena.substring(i, i+1).equals("-")){
                System.out.println("Se ha encontrado: " + cadena.substring(i, i+1));
                simbolos.add(cadena.substring(i, i+1));
            }
        }

        System.out.println("Los simbolos son:");
        for(int i = 0; i < simbolos.size();i++){
            System.out.println(simbolos.get(i));
        }

        //Imprime las partes de la cadena
        System.out.println("Partes de la cadena");
        for (int i = 0; i<partesDeLaCadena.length; i++){
            System.out.println(partesDeLaCadena[i]);
        }
        System.out.println("...");

    }

    private boolean isThisStriing(String parte){
        boolean isString = false;

        if(parte.indexOf("\"") != -1){
         isString = true;
        }
        else{
          isString = false;
        }

        return isString;
    }

    private boolean isthisCHR(String parte){
        boolean isCHR = false;
        System.out.println("Entra en isThisCHR");
        if(parte.indexOf("CHR(")!=1){
            isCHR = true;
        }
        else if(parte.indexOf("\'")!=1){
            parte.replace("'","\"");
        }
        else{
            isCHR = false;
        }
        return isCHR;
    }

    private boolean isThisIdent(String parte){
        System.out.println("ident: " + parte);
        boolean isIdent = false;
        System.out.println("entra en isThisIdent");
        isIdent = SimuladorIdent.verificarIdent(parte);
        return isIdent;
    }




    /**
     * Este metodo geera la expresion regular.
     * Se revisan todas las partes de la cadena, y con base en eso se decide la forma de generar la expresion
     * regular
     */
    private void generarLaExpresionRegular(ArrayList<NcCharcters> charcterss){
        String regex = "";
        String regex2 = ""; //Fix me! -.-

        System.out.println("Dentro de la Regex");
        System.out.println(partesDeLaCadena.length);


        /*Revisa todas las partes de la cadena*/
        int symbolCont = 0; //cuenta los sibolos
        for(int i = 0; i<partesDeLaCadena.length; i++){
            System.out.println("El estado actual del regex2 es: " + regex2);


            System.out.println(partesDeLaCadena[i]);

            //String
            if (isThisStriing(partesDeLaCadena[i])){
                regex = ""; //Vacia el reges
                System.out.println("Esta cadena es un string =D");
                regex += partesDeLaCadena[i].replace("\"", "");
                System.out.println("-> \'" + regex + "\'");

                for(int j = 0; j < regex.length();j++){
                    regex2 += regex.substring(j, j+1);
                    regex2 += "|";
                }

                //Le quita el ultimo Or
                regex2=regex2.substring(0,regex2.length()-1);
                System.out.println(regex2);


            }

            //Ident
            else if (isThisIdent(partesDeLaCadena[i])){
                System.out.println("Es un ident =D");

                /*Se busca entre todos los caracteres creados previamente y se agrega esto a la expresion regular*/
                for(int ii=0; ii<charcterss.size();ii++){
                    if (partesDeLaCadena[i].equals(charcterss.get(ii).getNombre())){
                        regex=charcterss.get(ii).getRegex();
                        System.out.println("ident encotrado: " + partesDeLaCadena[i]);
                        System.out.println("Lexema encontrado: " + regex);

                        regex2 += regex;
                    }
                }
            }

            //Charr "CHR"

            else if(isthisCHR(partesDeLaCadena[i])){
                System.out.println("Es un CHR");

                System.out.println(partesDeLaCadena[i]);
                hola=partesDeLaCadena[i];
                System.out.println(hola);
                hola = hola.replace("C","");
                hola = hola.replace("H","");
                hola = hola.replace("R","");
                hola = hola.replace("(","");
                hola = hola.replace(")","");
                System.out.println("el nuermo : " + hola);
                numero = Integer.parseInt(hola);
                char c = (char)numero;

                regex2+=c;
                numero=0;
                System.out.println(regex2);
            }




            /*Ahora toca revisar los simbolos*/
            /*Preguntarle al array que onda*/
            //1.Preguntarle si esta vacio, si no esta vacio siginica que hay simbolos que trabajar
            if (simbolos.size()>0){
                //Se le pregunta si ya llego a su limite
                if(symbolCont<simbolos.size()){
                    //Si no ha llegado a su limite, entonces se procede a solicitar el simbolo que corresponde
                    String simboloQueCorresponde = simbolos.get(symbolCont);
                    System.out.println("El simbolo encontrado del array es: " + simboloQueCorresponde);


                    /*Si el simbolo que se encontro en el array es '+' entonce se le agrega el simbolo '|' a
                    * la expresión regular*/
                    regex2 += "|";
                    symbolCont++; //Suma uno al contador de simbolo
                    System.out.println(regex2);

                }
            }
        }


        this.regex=regex2;
    }

    //Getter and Setter
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
