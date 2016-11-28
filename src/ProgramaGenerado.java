import Automata.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by aa-ol on 11/27/2016.
 */
public class ProgramaGenerado {
    /*Declaracion de atributos generados por lexer*/
    private ArrayList<NcCharcters> ncCharcterss = new ArrayList<>();
    private ArrayList<NcTokens> ncTokenss = new ArrayList<>();
    private ArrayList<NcKeywords> ncKeywordss = new ArrayList<>();
    ArrayList<Automata> automatasCreados = new ArrayList<>();
    ArrayList<SimuladorAFN> simuladorAFNS = new ArrayList<>();





    /*Constructor*/

    public ProgramaGenerado(ArrayList<NcCharcters> ncCharcterss, ArrayList<NcTokens> ncTokenss, ArrayList<NcKeywords> ncKeywordss) {
        this.ncCharcterss = ncCharcterss;
        this.ncTokenss = ncTokenss;
        this.ncKeywordss = ncKeywordss;
    }

    /**
     * Este metodo crea los automatas de todas las expresiones regulares que fueron creadas pr el lexer
     */
    public void crearAtuomatas(){


        //Characters
        for (int i=0; i<ncCharcterss.size();i++){
            /*Jalo el nombre de cada Character y se imprime*/
            String nombre = ncCharcterss.get(i).getNombre();
            System.out.println("nombre: "+nombre);

            /*Jalo el regex de cada Character y se imprime*/
            String lexema= ncCharcterss.get(i).getRegex();
            System.out.println("lexema: "+lexema);

            /*Creamos un automata en base al lexema del Character*/
            Generador_de_Automatas generador_de_automatas = new Generador_de_Automatas();
            Automata automata = generador_de_automatas.createAutomata(lexema);
            automata.setNombre(ncCharcterss.get(i).getNombre());

            /*Se le establece que tipo de automata es 0=caracter, 1=kwyword, 2=token*/
            automata.setLexer_kind(0);
            /*Se agrega el automata generado al arreglo de automatas*/
            automatasCreados.add(automata);
        }


        //Keywords
        for (int i=0; i<ncKeywordss.size();i++){
            /*Jalo el nombre de cada Keyword y se imprime*/
            String nombre = ncKeywordss.get(i).getNombre();
            System.out.println("nombre: "+nombre);

            /*Jalo el regex de cada Keyword y se imprime*/
            String lexema= ncKeywordss.get(i).getRegex();
            System.out.println("lexema: "+lexema);

            /*Creamos un automata en base al lexema del Keyword*/
            Generador_de_Automatas generador_de_automatas = new Generador_de_Automatas();
            Automata automata = generador_de_automatas.createAutomata(lexema);
            automata.setNombre(ncKeywordss.get(i).getNombre());

            /*Se le establece que tipo de automata es 0=caracter, 1=kwyword, 2=token*/
            automata.setLexer_kind(1);

            /*Se agrega el automata generado al arreglo de automatas*/
            automatasCreados.add(automata);
        }


        //Tokens
        for (int i=0; i<ncTokenss.size();i++){
            /*Jalo el nombre de cada Character y se imprime*/
            String nombre = ncTokenss.get(i).getNombre();
            System.out.println("nombre: "+nombre);

            /*Jalo el regex de cada Character y se imprime*/
            String lexema= ncTokenss.get(i).getRegex();
            System.out.println("lexema: "+lexema);

            /*Creamos un automata en base al lexema del Character*/
            Generador_de_Automatas generador_de_automatas = new Generador_de_Automatas();
            Automata automata = generador_de_automatas.createAutomata(lexema);
            automata.setNombre(ncTokenss.get(i).getNombre());

            /*Se le establece que tipo de automata es 0=caracter, 1=kwyword, 2=token*/
            automata.setLexer_kind(2);
            /*Se asigna si tiene un ExceptKeyword*/
            automata.setExceptedKeywords(ncTokenss.get(i).exceptKey);

            /*Se agrega el automata generado al arreglo de automatas*/
            automatasCreados.add(automata);
        }

    }

    /*Metodo para crear simuladores para cada uno de los automatas*/
    public void crearSimuladores(){
        /*Se creara un simulador por automata*/
        for(int i=0; i<automatasCreados.size();i++) {
            SimuladorAFN simulador = new SimuladorAFN(automatasCreados.get(i), automatasCreados.get(i).getLexer_kind());
            simuladorAFNS.add(simulador);
        }
    }

    /*Getters y Setters*/

    public ArrayList<NcCharcters> getNcCharcterss() {
        return ncCharcterss;
    }

    public void setNcCharcterss(ArrayList<NcCharcters> ncCharcterss) {
        this.ncCharcterss = ncCharcterss;
    }

    public ArrayList<NcTokens> getNcTokenss() {
        return ncTokenss;
    }

    public void setNcTokenss(ArrayList<NcTokens> ncTokenss) {
        this.ncTokenss = ncTokenss;
    }

    public ArrayList<NcKeywords> getNcKeywordss() {
        return ncKeywordss;
    }

    public void setNcKeywordss(ArrayList<NcKeywords> ncKeywordss) {
        this.ncKeywordss = ncKeywordss;
    }


    /**
     * Eliminar esto luego
     */
    public void testing(){
        boolean exit=false;
        while (!exit) {
            System.out.println("Ingrese un Caracter");
            Scanner sc = new Scanner(System.in);
            String cadena = sc.nextLine();
            System.out.println("el caracter que se obtuvo del usuario fue: " + cadena);

        /*se crea un for para probar en todos los automatas*/
            boolean found = false;
            /*revisa si tiene Except Keyword*/
            boolean exceptKeywordFound = false;
            /*revisa si es un keyword*/
            boolean isKeyword = false;
            /*revisa si es un token*/
            boolean isToken = false;


            for(int i =0; i<simuladorAFNS.size(); i++){

                boolean success = simuladorAFNS.get(i).simular(cadena);
                /*Si se encuentra la cadena en los simuladores, se analiza si pertenece a keywords y a tokens
                sila cadena pertenece a ambos se revisa el token para verificar si tiene Except Keyword*/

                if(success){
                    if(simuladorAFNS.get(i).getLexer_kind()==1){
                        isKeyword=true;
                    }
                    if(simuladorAFNS.get(i).getLexer_kind()==2){
                        isToken=true;
                        if(isToken){
                            exceptKeywordFound=simuladorAFNS.get(i).getAutomata().isExceptedKeywords();

                        }
                    }

                }
            }



            if((isKeyword && isToken && exceptKeywordFound)){
                System.out.println("la cadena: " + cadena + " pertenece al automata: KeyWord ya que se encuentra un Except keyword");
                found= true;
            }

            else {
                for (int i = 0; i < simuladorAFNS.size(); i++) {

                    boolean success = simuladorAFNS.get(i).simular(cadena);
                    if (success) {
                        System.out.println("la cadena: " + cadena + " pertenece al automata: " + simuladorAFNS.get(i).getAutomata().getNombre());
                        found = true;
                    }
                }
                if (found == false) {
                    System.out.println("La cadena: " + cadena + " no se encontro");
                }
                if (cadena.equals("exit")) {
                    exit = true;
                }
            }
        }
    }
}
