/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

/**
 * Clase singleton que controla la cantidad de nodos en el automata
 * @author Moises Samuel Urias Moreno 
 */
public class ContadorNodo {
    private static int contador = 0; /*Contador del numero de nodos*/
    private static ContadorNodo contadornodo = null;
    
    private ContadorNodo(){}


    /*Este metodo se ejecuta cuando se crea una nueva instancia, y 'crea' una nueva instancia*/
    public static synchronized ContadorNodo getInstance(){
        contador++; /*Aumenta en uno el contado de nodos*/
        
        if (contadornodo == null)
        {
            contadornodo = new ContadorNodo();
        }
        
        return contadornodo;
    }
    
    public static int getContador(){
        return ContadorNodo.contador;
    }

    /**
     * Regresa el valor del contador de nodos a cero
     * */
    public static void reset(){
        contador = 0;
    }
}
