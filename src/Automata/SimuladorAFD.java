package Automata;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.lang.Exception;
/**
 * Esta clase se encarga de simular el automata AFD
 * Created by Samuel on 07/09/2016.
 */
public class SimuladorAFD {

    /**
     * Si esta propiedad es "true" entonces se ejectuaran todas las lineas que sirven para hacer el dubug al momento
     * de desarrollo
     * */
    private boolean _debug = true;


    /**
     * Esta propiedad contiene el AFD que vamos a simular
     * */
    AFD automata;

    /**
     * Este atributo representa si la cadena es aceptada o no
     * */
    private boolean cadenaAceptada;


    /**
     * Estado inicial del automata
     */
    private int estado_inicial = 0;


    /**
     * Estado actual
     * En la simulacion este atributo modela donde en que estado se encuentra actualmente.
     */
    private int estado_actual = 0;

    /**
     * Estados de aceptacion del automata
     */
    public ArrayList<Integer> estados_de_aceptacion;

    /**
     * Constructor del simulador AFD
     * */
    public SimuladorAFD(AFD automata){
        /*Por defecto al momento de crear el simulador del automata, se establece que la cadena no es aceptada.*/
        this.cadenaAceptada = false;

        //Se asigna el automata
        this.automata = automata;

        //DEBUG
        if (this._debug){                                               //if this._debug == true
            System.out.println("Se ha creado un simulador del AFD. ");
            OpExtra.leerPantalla();
        }
    }


    /**
     * Este metodo recibe como parametro una cadena que es la que se va a simular, y devuelve un booleano true o false
     * dependiendo si la cadena fue aceptada o no por el automata.
     * */
    public boolean SimularAFD(String cadena){
        //Se crea una copia del autmata ingresado para poder hacer la simulacion
        AFD automataTemporal = automata;

        //DEBUG
        if (_debug) {
            System.out.println("Dentro del simulador");
            System.out.print("La cadena que se va a simular es: " + cadena + "\n");
        }


        //Se encuentra el estado inicial del automata
        this.estado_inicial = automata.getNodoInicial();

        //Se encuentran los estados de aceptacion del automata
        this.estados_de_aceptacion = automata.getEstados_de_aceptacion();


        //DEBUG
        if (_debug){
            System.out.println("Se busca el subset (nodo) que sea el nodo inicial del AFD");
        }


        Subset subset = null; //Subset para hacer la simulacion del AFD


        /*Si se encuentra el estado inical del automata, entonces se asigna este estado inicial a la variable 'subset',
        * ya que con esta se hara la simulacion*/
        if (automata.getEstadoInicial() != null){
            subset = automata.getEstadoInicial(); //Se busca el estado inicial del Automata Finito Determinista


            //DEBUG
            if(_debug){
                System.out.println("Se ha encontrado el estado inicial del AFD!!!");
                System.out.println("El estado inicial del AFD es: " + subset.getNombre_subset());
                OpExtra.leerPantalla();
            }
        }



        if (_debug == true){
            OpExtra.imprirLinea();
        }

        /**Esto es lo que hay que arreglar*/
        /*Lo se agregan los estados del automata temporal a cada subset del mismo para poder simularlo*/

        //Se asignas las transiciones del automata a cada subset
        for (int s=0; s<automataTemporal.getEstados().size();s++){
            automataTemporal.getEstados().get(s).setTransiciones(automata.getTranciciones());
        }
        System.out.println("A todos los subset del Automata temporal se la han asignados todas las transiciones del " +
                "automata Finito Determinista");

        System.out.println(".............................................." );
        System.out.println(automata.getTranciciones());
        System.out.println("..............................................");
        OpExtra.leerPantalla();


        /*******************************************
         * *****************************************     mega debug
         * ****************************************
         */


        /*Se le asigna a subset el estado inicial del automata temporal*/
        subset = automataTemporal.getEstadoInicial();


        System.out.println(subset.getTransiciones());
        System.out.println("+++++++++ Aqui se le pasa a subset el estado inicial del automata temporal +++++++++");
        for (int by = 0; by<subset.getTransiciones().size(); by++){
            System.out.println(subset.getTransiciones().get(by));
        }
        OpExtra.leerPantalla();


        System.out.println("+++++++++ Aqui se le asigna el nombre del subset como nodo al automata temporal +++++++++");
        /*A los subset del automata temrporal se le cambian los valores de estos por el nombre del subset.
        * De esta manera al hacer el move, para hacer la simulacion del AFD, este move depende del nombre del subset,
        * ya que para saber si una cadena es aceptada o no, el move debe devolver el 'nombre' de un estado de acetpacion*/

        /*Los nodos del subset se modelan con un ArrayList de enteros entonces se crea un ArrayList que contiene
        * unicamente el nombre del subset, y es ese ArrayListe el que se asigna a los nodos del subset*/
        ArrayList<Integer> nuevoNodo = new ArrayList<>();
        nuevoNodo.add(subset.getNombre_subset());
        subset.setNodos(nuevoNodo);
        OpExtra.leerPantalla();
        /*******************************************
         * *****************************************     mega debug
         * ****************************************
         */

        int contador = 0; //Contador del ciclo
        if(_debug){
            System.out.println("El contador es: " + contador);
        }

        String c = cadena.substring(0,1); //SiguienteCaracter
        while (contador<cadena.length()){
            System.out.println("Largo de la cadena: " + cadena.length());
            System.out.println("C = " + c);

            /*DEBUG*/
            if(_debug){
                System.out.println("El estado antes del move de la simulacion");
                System.out.println(subset);
            }

            subset = OpExtra.mover(subset, c);

            if(_debug){
                System.out.println("C = " + c);
                System.out.println("El estado del subset despues del move");
                System.out.println(subset);
                OpExtra.imprirLinea();
                OpExtra.leerPantalla();
            }


            contador ++;  //Se incremta el cantador, lo que evita el ciclo infinito
            try{
                if (contador + 1 <= cadena.length())
                c = cadena.substring(contador,contador+1); //SiguienteCaracter
            }
            catch (Exception exeption){
                System.out.println("ERROR EN EL PROGRAMA!!! ");
                System.out.println("El largo de la cadena es: " + cadena.length());
                System.out.println("Contador: " + contador);
                System.out.println("Contador + 1: " + contador + 1);
                System.out.println("La cadena no tiene tantos caracteres");
                System.out.println("El programa ha finalizado");
                System.exit(0);
            }


            if(_debug){
                System.out.println("El contador: " + contador);
                System.out.println("El caracter es " + c);
            }

        } //End while

        if (_debug){
            System.out.println("Resolucion de la simulacion del AFD");

            //Se revisan todos los nodos del subset para saber si contiene alguno de los nodos que se encuentran entre
            // los estados de aceptacion
            for (int t=0; t<subset.getNodos().size();t++){
                if (estados_de_aceptacion.contains(subset.getNodos().get(t))){
                    System.out.println("=D");
                    cadenaAceptada = true;
                }


            }
            System.out.println(subset);
            OpExtra.leerPantalla();
        }


        System.out.println("Fuera del while del simulador");
        return this.cadenaAceptada;

    }
}
