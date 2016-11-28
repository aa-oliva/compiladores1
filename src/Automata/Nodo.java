/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import java.util.ArrayList;

/**
 *
 * @author Moises Urias
 */
public class  Nodo {
    private ContadorNodo contador; /*Contador del numero de nodos*/
    
    private int id; /*Identificador*/
    private boolean eFinal = false;     /*Nodo final*/
    private boolean eInicial = true;    /*Nodo inicial*/
    
    
    
    public Nodo()
    {
       
        /*Se toma un nuevo valor de contador*/
        this.id = contador.getInstance().getContador();
        this.eInicial = false;
        this.eFinal = false;
        //System.out.println("El valor del contador es: " + id);
    }
    
    /**
     * Crea un nuevo nodo, utilizando un contador automatico.
     * @param einicial
     * @param efinal 
     */
    public Nodo(boolean einicial, boolean efinal)
    {
        this.id = contador.getInstance().getContador();
        
        this.eInicial = einicial;
        this.eFinal = efinal;
       // System.out.println("El valor del contador es: " + id);
    }

    
    /**
     * Devuelve el valor del contador que tiene el nodo
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el valor del estado Final
     * @return eFinal
     */
    public boolean iseFinal() {
        return eFinal;
    }

    
    /**
     * Devuelve el valor del estado Inicial
     * @return eInicial
     */
    public boolean iseInicial() {
        return eInicial;
    }

    /**
     * Permite asignar el valor del estado final del nodo
     * @param eFinal 
     */
    public void seteFinal(boolean eFinal) {
        this.eFinal = eFinal;
    }

    /**
     * Permite asignar el valor del estado incial del nodo
     * @param eInicial 
     */
    public void seteInicial(boolean eInicial) {
        this.eInicial = eInicial;
    }
    
    
    
    
    
}
