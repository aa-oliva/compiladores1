/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Esta clase se cea con base del main de el paquete automata.
 * 
 *  La diferencia general entre esta clase y el main, es que esta clase permite
 *  la creacion de automatas AFN en base a una expresion ingresada por el 
 *  usuario u otro metodo. Es decir que esta clase esta hecha para se instanciada
 *  desde cualquier otra parte del prgroma, a diferencia del main que fue 
 *  utilizado para la primera entrega del proyecto.
 * 
 * @author Moises Urias
 */
public class AFN {
    
    /**/
    private static String p;
    private Automata automata;
    private String nombre = ""; /*Nombre del automata*/
    
    /**
     * Constructor del AFN
     * @param expresionRegular 
     */
    public AFN()
    {
        /*Constructor del automata*/
        System.out.println("Se ha construido un nuevo automata");
    }
    
    public AFN(String regex)
    {
        this.automata = crearAutomata(regex);
    }
    
    
    /**
     * Devuelve un automata AFN en base a una expresion regular ingresada
     * @param regex
     * @return 
     */
    public Automata crearAutomata(String regex)
    {
         /*Almacena un arraylist con el alfabeto del automata*/
        ArrayList<String> alfabeto;
        int nodoinicialafn = 0; /*Almacena el nodo inicial del AFN*/
        int nodofinalafn = 0; /*Almacena el nodo final del AFN*/
       
        /*Permite realizar una simulacion del AFN*/ 
       Simulador simulador;

        String cadena = regex;

        cadena = cadena.trim();
        cadena = cadena.replace(" ", "");
        
        cadena = (RegexConverter.infixToPostfix(cadena));
        

        /*Almacena el alfabeto del automata ingresado */
        
        alfabeto = OpExtra.alfabeto(cadena);
        
        
        /*Se crea el automata*/
        Automata aut = new Automata();
        
        /*Se le asigna al nuevo automata el alfabeto que este utilizara*/
        aut.setAlfabeto(alfabeto);
        
        /*Aqui crean los nodos del automata*/
        int j=0; /*Contador*/
        while (j<cadena.length())
        {
            aut.crearAutomata(cadena.substring(j, j+1));
            j++;
        }

        /*AUTOMATA CREADO*/
        aut = aut.automatas.pop(); 

        
        ArrayList<Nodo> arnodo = aut.getEstados();
        int nodoinicial =0;
        
        /*Se obtiene el nodo incicial y final del AFN*/
        for (int i=0; i<arnodo.size();i++)
        {
            
            if (arnodo.get(i).iseInicial() == true){
             
                nodoinicial = arnodo.get(i).getId();
                nodoinicialafn = arnodo.get(i).getId();
            }
            
            if (arnodo.get(i).iseFinal() == true){
                
                nodofinalafn = arnodo.get(i).getId();
            }
        }

        
        
        aut.setAlfabeto(alfabeto);
        
 
        /*Se genera el archivo con al AFN*/
        String texto = ""; /*Texto que en el que se almacen al informacino a guardar*/
        texto = texto + "El nodo inicial del AFN es: " + String.valueOf(nodoinicialafn) + "\n";
        texto = texto +"El nodo final del AFN es: " + String.valueOf(nodofinalafn) + "\n";
        texto = texto + "El alfabeto del AFN es: " + alfabeto + "\n\n";
        texto = texto + "Las transiciones del afn son:\n ";
         for (int i=0; i<aut.transiciones.size();i++)
        {   
            
            texto = texto + i +": "+ aut.transiciones.get(i)+"\n";
        }
         
        texto = texto + "\n\nSiendo '!' lo que equivale al simbolo epsilon";
        
        /*Se guarda el automata*/
        // guardar(texto);
        
        
        /*__________________________________________________________________*/
        /*Se grafica el grafo*/
        texto = "";
        
        texto = texto + "digraph G\n"; 
        texto = texto +"{\n";
        texto = texto + "node [shape=circle];";
        texto = texto + "node [style=filled];";
        texto = texto + "node [fillcolor=\"#EEEEEE\"];";
        texto = texto + "edge [color=\"#31CEF0\"];";
        
        for (int i=0; i<aut.transiciones.size();i++)
        {   
            
           texto = texto + aut.transiciones.get(i).getNodoInicial() + " -> " +
                   aut.transiciones.get(i).getNodoFinal() + "[label=\""+
                   aut.getTransiciones().get(i).getSimbolo()+"\"];";
        }
         
        //texto = texto + "rankdir=LR;\n}";

        /*Guarda la imagen*/
        //guardarImagen(texto);
        //llamarAlGraphViz();
        
        //AFD afd = new AFD(aut);
        
        
        
                                    /*Se simula el afn*/
        /*Se crea una nueva instancia del simulador*/
        //simulador  = new Simulador(aut);
        //simulador.simular(); /*Se hace la simulacion*/
     
        /*Asigna el nombre del automata*/
        aut.setNombre(nombre);
        
        
        /*Devuelve el automata AFN generado por la expresion regular*/
        return aut;
    }

    public Automata getAutomata() {
        return automata;
    }
    
    
    /**
     * Este metodo asigna un nombre al automata que sera creado
     * @param nombre 
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    
    /**
     * Devuelve el nombre del automata
     * @return 
     */
    public String getNombre()
    {
        return this.nombre;
    }
    
}
