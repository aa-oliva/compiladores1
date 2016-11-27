import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by aaom on 29/10/16.
 */
public class Lexer {
    int cont=0;
    int whereKeywordsStarts = 0;
    int whereTokenStarts = 0;
    int whereEndStarts = 0;
    int estadoProyecto =0;
    String nombreDelArchivo="";
    String expected = "";

    ArrayList <String> lCharacters = new ArrayList<>();
    ArrayList <NcCharcters> nCharacters = new ArrayList<>();
    ArrayList <String> lKeywords = new ArrayList<>();
    ArrayList <NcKeywords> nKeywords = new ArrayList<>();
    ArrayList <String> lTokens = new ArrayList<>();
    ArrayList <NcTokens> nTokens = new ArrayList<>();

    //Banderas
    //whitespace char(9),"\n", char(32)
    boolean whiteSpace = false;
    boolean compiler = false;
    boolean nombre = false;
    boolean characters = false;
    boolean keywords = false;
    boolean tokens = false;
    boolean end = false;

    //metodos
    public String whitesPaceF(String archivo){
        System.out.println("Entra en el if de whitespace");
        while (!whiteSpace){
            expected += archivo.charAt(cont);
            while(expected.trim().length()==0)
            {
                cont ++;
                expected += archivo.charAt(cont);
            }
            whiteSpace = true;

        }
        System.out.println("paso whitespace \n");
        expected = expected.trim();
        return expected;
    }

    /*Busca el 'COMPILER'*/
    public String compilerf(String archivo){
        System.out.println("Entra en el if de compiler");

        while (!expected.equals("COMPILER")){
            expected += archivo.charAt(cont);
            cont ++;
        }

        System.out.println("paso "+ expected + "\n");
        compiler = true;
        return expected;
    }


    public String nombref(String archivo){
        cont = cont -1;
        System.out.println("Entra en el if de nombre de archivo");

         /*Busca el 'nombre'*/
        while (!archivo.substring(cont,cont+1).equals("\n")) {
            expected += archivo.substring(cont, cont + 1);
            cont++;
        }


        System.out.println("paso "+ expected + "\n");
        nombre = true;
        whiteSpace = false;
        return expected;
    }

    /**
     * Debido a que ya no hay whitespaces, se supone que la palabra
     * 'CHARACTERS' continúna exactamente donde se quedo el puntero.
     */
    public String charactersf(String archivo){

        System.out.println("Entra en el if de CHARACTERS");
        cont = cont -1;
        String cadena ="";
        while (!expected.equals("CHARACTERS")){
            expected += archivo.substring(cont, cont+1);
            cont ++;

        }
        estadoProyecto = 1;
        System.out.println(cadena);
        while (!cadena.equals("KEYWORDS")){
            cadena = "";
            while(!archivo.substring(cont,cont+1).equals(".")) {
                cadena += archivo.substring(cont, cont + 1);
                cont ++;
                cadena=cadena.trim();

                if (cadena.equals("KEYWORDS")){
                    //System.out.println(cadena);
                    whereKeywordsStarts = cont;

                    break;
                }

            }
            lCharacters.add(cadena);



            //System.out.println(cadena);
            cont ++;
            //System.out.println("\n");
        }

        System.out.println("\nSe agrega los Charracters a la clase\n");

        for (int i=0; i < lCharacters.size(); i++){
            cadena=lCharacters.get(i);
            int equal_position =  cadena.indexOf("=");
            if (equal_position !=-1){
                String name =  cadena.substring(0, equal_position);
                String lexema = cadena.substring(equal_position+1, cadena.length());

                System.out.println("nombre: "+name+"\nlexema: "+lexema);
                nCharacters.add(new NcCharcters(nCharacters, name, lexema));
            }
        }



        System.out.println("paso "+ expected + "\n");
        characters = true;
        whiteSpace = false;
        return expected;
    }

    /**
     * Esta mierda esta buscando la palabra 'KEYWORDS'  >:@
     * @param archivo
     * @return
     */
    public String keyWordsf(String archivo){
        System.out.println("Entra en el if de KEYWORDS");

        cont = whereKeywordsStarts;
        cont  = cont-8;
        String cadena ="";

        //lee hasta que encuentra keywords
        while (!expected.equals("KEYWORDS")){
            expected += archivo.substring(cont, cont+1);
            cont ++;

        }

        estadoProyecto = 2;
        System.out.println(cadena);
        while (!cadena.equals("TOKENS")){
            cadena = "";
            while(!archivo.substring(cont,cont+1).equals(".")) {
                cadena += archivo.substring(cont, cont + 1);
                cont ++;
                cadena=cadena.trim();

                if (cadena.equals("TOKENS")){
                    whereTokenStarts = cont;
                    //System.out.println(cadena);
                    break;
                }


            }
            lKeywords.add(cadena);
            cont ++;
        }

        System.out.println("\nSe agrega los Keywords a la clase\n");

        for (int i=0; i < lKeywords.size(); i++){
            cadena=lKeywords.get(i);
            int equal_position =  cadena.indexOf("=");
            if (equal_position !=-1){
                String name =  cadena.substring(0, equal_position);
                String lexema = cadena.substring(equal_position+1, cadena.length());

                System.out.println("nombre: "+name+"\nlexema: "+lexema);
                nKeywords.add(new NcKeywords(nKeywords, name, lexema));
            }
        }

        System.out.println("paso "+ expected + "\n");
        keywords = true;
        whiteSpace = false;
        return expected;
    }

    public String tokensf(String archivo){
        System.out.println("Entra en el if de KEYWORDS");

        cont = whereTokenStarts;
        cont  = cont-6;
        String cadena ="";
        String regex = "";

        //lee hasta que encuentra keywords
        while (!expected.equals("TOKENS")){
            expected += archivo.substring(cont, cont+1);
            cont ++;

        }

        estadoProyecto = 2;
        System.out.println(cadena);
        while (!cadena.equals("END")){
            cadena = "";
            while(!archivo.substring(cont,cont+1).equals(".")) {
                cadena += archivo.substring(cont, cont + 1);
                cont ++;
                cadena=cadena.trim();

                if (cadena.equals("END")){
                    //System.out.println(cadena);
                    break;
                }


            }
            lTokens.add(cadena);
            cont ++;
        }

        System.out.println("\nSe agrega los Keywords a la clase\n");

        for (int i=0; i < lTokens.size(); i++){
            cadena=lTokens.get(i);
            int equal_position =  cadena.indexOf("=");
            if (equal_position !=-1){
                String name =  cadena.substring(0, equal_position);
                String lexema = cadena.substring(equal_position+1, cadena.length());

                System.out.println("nombre: "+name+"\nlexema: "+lexema);
                nTokens.add(new NcTokens(nTokens,nCharacters,name,lexema));

            }
        }




        System.out.println("paso "+ expected + "\n");
        tokens = true;
        whiteSpace = false;
        return expected;
    }

    public String endf(String archivo,String nombre){
        cont = cont-2;
        System.out.println("Entra en el if de nombre de archivo");

         /*Busca el 'nombre'*/
        while (!archivo.substring(cont,cont+1).equals("\n")) {
            expected += archivo.substring(cont, cont + 1);
            cont++;
        }
        expected=expected.replace(".","");

        if(expected.equals(nombreDelArchivo)) {

            System.out.println("paso " + expected + "\n");
        }
        else if(!expected.equals(nombreDelArchivo)){
            System.out.println("El archivo no tiene el mismo nombre");
            System.out.println(expected);
            System.out.println(nombreDelArchivo);
        }

        return expected;
    }


    /**
     * Este metodo se encarga de guiar el proceso de lexeo.
     * Esto se hace mediante el proceso de lexeo de los characters
     * el proceso de lexeo de los keywords
     * El proceso de lexeo de los tokens
     */
    public Lexer(String archivo) {
        System.out.println("\n\n------------------------------------");
        System.out.println("*************Construtor*************");
        System.out.println("------------------------------------\n\n");

        /*Esta el la verificacion de errores inicial del lexer.
            1.Verifica ... (pendiente enlistar)
            */
        while (cont<archivo.length()){

            if (compiler == false){
                compilerf(archivo);
                expected = "";
            }


            else if (whiteSpace == false){
                whitesPaceF(archivo);
                expected = "";
            }

            else if (nombre == false) {
                nombref(archivo);
                nombreDelArchivo=expected;
                expected = "";
            }


            else if (characters == false){
                charactersf(archivo);
                expected = "";
            }

            else if (keywords == false){
                keyWordsf(archivo);
                expected = "";
            }

            else if (tokens == false){
                tokensf(archivo);
                expected = "";
            }


            else if (end == false){
                endf(archivo, nombreDelArchivo);
                expected ="";
            }

            cont++;
        }
    }


}

