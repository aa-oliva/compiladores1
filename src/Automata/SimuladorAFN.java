package Automata;

import java.util.Scanner;

/**
 * Created by Samuel on 18/09/2016.
 * Esta clase simulador AFN esta basada en la anterior clase simulador para tener un codigo más ordenado y limpio.
 * Esta clase es la que se va a utilzar para la fase del lexer.
 * @version 2
 */
public class SimuladorAFN {
    /*La cadena es aceptada por el atuomata*/
    private boolean cadenaAceptada;

    /*Estado inicial y estado de aceptacion del AFN (Automata finito no determnista)*/
    private int estadoInicial;
    private int estadoAceptacion;

    /*Automata que se estara simlando*/
    private Automata automata;

    /**
     * 0=Character
     * 1=Keyword
     * 2=Token
     * Esta variable se utiliza para saber que tipo de elemento se esta simulando en el programa generado del lexer
     */
    private int lexer_kind;


    /**
     * Constructor del simulador de AFN (Automatas finitos no determinstas)
     * @param automata
     */
    public SimuladorAFN(Automata automata) {
        this.automata = automata;
    }

    /**
     * Constructor el simulador de afn
     * este constructor acepta un automata  una localizacion para el programa generado
     */
    public SimuladorAFN(Automata automata, int lexer_kind){
        this.automata = automata;
        this.lexer_kind = lexer_kind;
    }

    /**
     * Constructor del simulador del AFN (Automata finito no determinista).
     * Este constructor tiene como parametro un AFN y tambien la cadena que se va a simular.
     * Esto se hace asi con el fin de ahorrar una linea de codigo en otras partes del programa.
     * @param automata
     * @param  cadena
     */
    public SimuladorAFN(Automata automata, String cadena){
        this.automata = automata;
        this.cadenaAceptada = this.simular(cadena);
    }

    /**
     * Este metodo es lo que otros metodos pueden llamar para hacer una simulacion de AFN.
     * @param cadena
     * @return
     */
    public boolean hacerSimulacion(String cadena){
        /*Se llama al metodo privado que hace la simulacion del AFN y devuelve este resutlado*/
        return  this.simular(cadena);
    }


    /**
     * Este metodo hace la simulacion del AFN
     */
    public boolean simular(String cadena){
        /*Esta variable almacena si la cadena es aceptada o no por el automata*/
        boolean aceptada = false;

        /*Se encuentran los estados inciales y finales del automata*/
        this.estadoInicial = automata.getEstadoInicial();
        this.estadoAceptacion = automata.getEstadoFinal();

        /*Se genera un nuevo subset con el estado inicial del automata*/
        Subset subset = new Subset(estadoInicial, automata.getTransiciones());

        /*Se le realiza un eclosure al subset*/
        subset = OpExtra.eClosure(subset);
        String c = cadena.substring(0,1); //SiguienteCaracter

        for (int i = 1; i<=cadena.length();i++   ) {
            subset = OpExtra.mover(subset, c);
            subset = OpExtra.eClosure(subset);
            if (i < cadena.length())
                c = cadena.substring(i,i+1); //SiguienteCaracter
        }

        //Para ahorrar lineas de codigo el if de la verificacion se hace con un operador ternario.
        aceptada = (subset.Nodos.contains(estadoAceptacion)) ? true : false;
        return aceptada;
    }


    /****     GETTERS AND SETTERS *****/
    public boolean isCadenaAceptada() {
        return cadenaAceptada;
    }

    public void setCadenaAceptada(boolean cadenaAceptada) {
        this.cadenaAceptada = cadenaAceptada;
    }

    public Automata getAutomata() {
        return automata;
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }

    public int getLexer_kind() {
        return lexer_kind;
    }

    public void setLexer_kind(int lexer_kind) {
        this.lexer_kind = lexer_kind;
    }
}
