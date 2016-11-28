/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

/**
 * Clase que modela una transicion entre estados
 * @author Moises Samuel Urias Moreno
 */
public class Transicion {
    private int nodoInicial; /*Nodo inicial*/
    private int nodoFinal;   /*Nodo final*/
    private String simbolo;  /*Simbolo de la transicion*/
    
    
    /**
     * Este metodo se utiliza como constructor de transicion y crea una <p>
     * transicion de con la forma T(nodoInicial, simbolo, nodofinal).
     * 
     * @param nodoinicial
     * @param simbolo
     * @param nodofinal 
     */
    public Transicion(int nodoinicial, String simbolo, int nodofinal)
    {
        this.nodoInicial=nodoinicial;
        this.nodoFinal = nodofinal;
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return "Transicion{" + "nodoInicial=" + nodoInicial + ", simbolo=" + simbolo + ", nodoFinal=" + nodoFinal +'}';
    }

    public int getNodoInicial() {
        return nodoInicial;
    }

    public int getNodoFinal() {
        return nodoFinal;
    }

    public String getSimbolo() {
        return simbolo;
    }
    
    
    
}
