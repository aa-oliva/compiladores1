import java.util.ArrayList;

/**
 * Created by aa-ol on 11/26/2016.
 */
public class NcTokens {
    String nombre = "";
    String lexema = "";
    String regex = "";
    String comillas = "";
    String partesDeLaCadena [];
    ArrayList<String> simbolos = new ArrayList<>();
    boolean exceptKey = false;

    //Constructor
    public NcTokens(ArrayList<NcTokens> tokens,ArrayList <NcCharcters> charcterss,String nombre, String lexema) {
        System.out.println("Constructor de NcTokens\'" + nombre+ "\'" );
        this.nombre = nombre;
        this.lexema = lexema;

        /*Encuentra si hay EXCEPT KEYWORDS, vuelve exceptKey true
            y la saca de la cadena*/
        exceptKey = checkForExceptKeywords(lexema);
        /*Despues de ingresar el nombre y lexema, se procede a identificar el token*/
        separarLasPartes(lexema);
        generaLaExpresionRegular(tokens, charcterss);


    }

    public void separarLasPartes(String cadena){
        partesDeLaCadena = cadena.split("\\{|\\||}|\"");

        //Agrega los simbolos encontrados al arreglo de simbolos
        for (int i = 0; i<cadena.length();i++){
            if (cadena.substring(i, i+1).equals("{") || cadena.substring(i, i+1).equals("}")|| cadena.substring(i, i+1).equals("|")|| cadena.substring(i, i+1).equals("\"")){
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

    private boolean isThisIdent(String parte){
        System.out.println("idi " + parte);


        boolean isIdent = false;
        System.out.println("entra en isThisIdent");
        isIdent = SimuladorIdent.verificarIdent(parte);
        return isIdent;
    }

    private void generaLaExpresionRegular(ArrayList<NcTokens> tokens, ArrayList<NcCharcters> charcterss){
        String regex = "";
        String regex2 ="";
        System.out.println("Dentro de la regex");

        /*Revisa todas las partes de la cadena*/
        int symbolCont =0; //cuenta los simbolos
        for (int i = 0; i<partesDeLaCadena.length;i++){
            System.out.println("el estado actual del regex es : " + regex2);
            //String
            if (isThisStriing(partesDeLaCadena[i])){
                regex = ""; //vaia el regex
                System.out.println("Esta cadena es String");
                regex += partesDeLaCadena[i].replace("\"", "");
                System.out.println("-> \'" + regex + "\'");

                for(int j=0; j< regex.length();j++){
                    regex2+= regex.substring(j,j+1);
                    regex2+="|";
                    comillas=regex2;
                }

                //le quita el ultimo or
                regex2=regex2.substring(0, regex2.length()-1);

            }

            //ident
            else if (isThisIdent(partesDeLaCadena[i])){
                System.out.println("Es un ident");

                /*Se busca entre todos lo idents creados*/
                for (int ii=0; ii<charcterss.size(); ii++){
                    if (partesDeLaCadena[i].equals(charcterss.get(ii).getNombre())){
                        regex=charcterss.get(ii).getRegex();
                        System.out.println("ident encontrado: "+partesDeLaCadena[i]);
                        System.out.println("lexema encontrado: "+regex);
                        regex2+=regex;
                    }
                }
            }

            /*ahora toca revisar los simbolos*/
            /*preguntarle al array que pasa*/
            /*si esta vacio, si no lo esta significa que qe hay un simbolo pendiente*/
            if (simbolos.size() > 0) {
                System.out.println(symbolCont);
                System.out.println(simbolos.size());
                /*se le pregunta si ya llego a su limite*/
                if (simbolos.size()>symbolCont) {
                String simboloQueCorresponde = simbolos.get(symbolCont);
                System.out.println("El simbolo encontrado del array es : " + simboloQueCorresponde);
                /*Si el simbolo que se encontro en el array es '+' entonce se le agrega el simbolo '|' a
                        * la expresión regular*/

                    System.out.println("--> Todavia hay simbolos disponibles para analizar");
                    System.out.println(simboloQueCorresponde);
                    if (simboloQueCorresponde.equals("{")) {
                        regex2 += ")(";

                        System.out.println(regex2);
                    } else if (simboloQueCorresponde.equals("}")) {
                        regex2 += ")*";

                        System.out.println(regex2);

                    } else if (simboloQueCorresponde.equals("|")) {
                        regex2 += "|";

                        System.out.println(regex2);
                    }
                    else if (simboloQueCorresponde.equals("\"")){
                        System.out.println("We found comillas");
                        System.out.println("se encontro : "+partesDeLaCadena[i]);
                        regex2 += partesDeLaCadena[i]; //Se han encontrado comillas
                    }
                    symbolCont++;
                }
            }
        }
        System.out.println("regex: \'"+regex2+"\'");
        this.regex="("+regex2;
    }

    /**
     * Este metodo revisa el lexema para ver si este contiene 'EXCEPT KEYWORDS'
     * @param lexema
     * @return
     */
    private boolean checkForExceptKeywords(String lexema){
        boolean hasExceptKeywords = false;

        if(lexema.contains("EXCEPTKEYWORDS")){
            System.out.println("It has except keywords");
            hasExceptKeywords = true;
        }

        return hasExceptKeywords;
    }

    //Getters y Setters

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


