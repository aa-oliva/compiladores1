/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Moises Urias
 */
public class main {


     private static String p = ""; /*Direccion donde se crea la imagen del automata*/
     
     
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /***
         * Si esta variable es verdadera, entonces se ejecutan todas las instrucciones que se utilian para debugear
         * los AFN
         */


        /*Variables globales*/
        boolean debugAFN = false;
        boolean printAFN = true;
        boolean printTextAFN = false;
        boolean simularAFN = false;
        boolean exitAfterAFN = false;

        /*Almacena un arraylist con el alfabeto del automata*/
        ArrayList<String> alfabeto;
        int nodoinicialafn = 0; /*Almacena el nodo inicial del AFN*/
        int nodofinalafn = 0; /*Almacena el nodo final del AFN*/
       
        /*Permite realizar una simulacion del AFN*/ 
       Simulador simulador;
    
        
        /*Esta variable almacena el tiempo al principio de la ejecución del 
           programa*/
        long time1 = System.currentTimeMillis();
        
//        System.out.println("(b | b)*abb(     a  | b ) *");
//        System.out.println("Ingrese la expresion regular: ");

        /*Expresion regular que se va a analizar*/
        String cadena = "";
        if (debugAFN == true){
            System.out.println("Se esta en modo DEBUG, la expresion regular a analizar es: ");
            cadena = "(b|b)*abb(a|b)*";
            System.out.println(cadena);
            OpExtra.leerPantalla();
        }
        else{
            System.out.println("(b|b)*abb(a|b)*");
            System.out.println("Ingrese la expresion regular: ");
            Scanner scaner = new Scanner(System.in);
            cadena = scaner.nextLine();
        }
        


        cadena = cadena.trim();
        cadena = cadena.replace(" ", "");

        cadena = OpExtra.convertirCerraduraPositiva(cadena);
        System.out.println(cadena);
        OpExtra.leerPantalla();
        cadena = OpExtra.convertirSignoInterrogacion(cadena);
        System.out.println(cadena);
        OpExtra.leerPantalla();

        cadena = (RegexConverter.infixToPostfix(cadena));
        
        System.out.println("Cadena con posfix: \n" + cadena+"\n\n\n");


        

        
        /*Imprime el alfabeto del automata ingresado */
        System.out.println("El alfabeto del automata es:");
        alfabeto = OpExtra.alfabeto(cadena);
        System.out.println(alfabeto);
        
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
        
                
        System.out.println("\nLa cantidad de estados es: " + 
                ContadorNodo.getContador()+"\n");
 
        
        
        /*AUTOMATA CREADO*/
        aut = aut.automatas.pop(); 

        System.out.println("\nCantidad de transiciones: " + aut.transiciones.size()+"\n");
        
        ArrayList<Nodo> arnodo = aut.getEstados();
        int nodoinicial =0;
        for (int i=0; i<arnodo.size();i++)
        {
            
            if (arnodo.get(i).iseInicial() == true){
                System.out.println("Estado inicial: " + arnodo.get(i).getId());
                nodoinicial = arnodo.get(i).getId();
                nodoinicialafn = arnodo.get(i).getId();
            }
            
            if (arnodo.get(i).iseFinal() == true){
                System.out.println("Estado final: " + arnodo.get(i).getId());
                nodofinalafn = arnodo.get(i).getId();
            }
        }
        
        
          /*Se resta el tiempo actual del sistema con el tiempo de inicio y ese
           es tiempo total de la ejecucion*/
       long time = System.currentTimeMillis()-time1;
        System.out.println("\nEl tiempo total del programa es: " + time + " milisegundos\n");
        
        
        
        
        /*Crea un subset*/
        System.out.println("El nodo inicial a introducir en el subset es: " +nodoinicial);
       
        System.out.println("La cantidad de transiciones en el automata es: " + aut.getTransiciones().size());
        
        
        
        System.out.println(aut.getAlfabeto() + "ALFABETO");
        System.out.println(alfabeto);
        aut.setAlfabeto(alfabeto);
        System.out.println("El afabeto que posee el automata es: " + aut.getAlfabeto());
        
        
        
        /*Se genera el archivo con al AFN*/
        String texto = ""; /*Texto que en el que se almacen al informacino a guardar*/
        texto = texto + "El nodo inicial del AFN es: " + String.valueOf(nodoinicialafn) + "\r\n";
        texto = texto +"El nodo final del AFN es: " + String.valueOf(nodofinalafn) + "\n";
        texto = texto + "El alfabeto del AFN es: " + alfabeto + "\r\n";
        texto = texto + "El tiempo total de la simulacion del afn es: "+(System.currentTimeMillis()-time1)+"\r\n";
        texto = texto + "Las transiciones del afn son:\r\n ";
         for (int i=0; i<aut.transiciones.size();i++)
        {   
            
            texto = texto + i +": "+ aut.transiciones.get(i)+"\r\n";
        }
         
        texto = texto + "\r\n\r\nSiendo '!' lo que equivale al simbolo epsilon";
        
        /*Se guarda el automata*/
        if (printTextAFN){
            System.out.println(texto);
            guardar(texto);
        }

        
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
         
        texto = texto + "rankdir=LR;\n}";

        /*Guarda la imagen*/
        if (printAFN==true){
            guardarImagen(texto);
            llamarAlGraphViz();

        }

        
        
        
        /*Se simula el afn*/
        /*Se crea una nueva instancia del simulador y se hace la simulacion*/
        simulador  = new Simulador(aut);

        if(simularAFN==true) {
            simulador.hacerSimulacion();
        }

        /*Se termina el programa despues de simular el AFN*/
        if (exitAfterAFN == true){
            System.exit(0);
        }   
       /*Se crea un automata finito deterministico utilizando la construccion por subconjuntos*/
        AFD afd = new AFD(aut);


        /*Simulacion del AFD*/
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("De nuevo se esta en el main");
//        OpExtra.leerPantalla();
        SimuladorAFD simulador_de_AFD = new SimuladorAFD(afd);
        String cadena_para_simular = "";
        System.out.println("INGRESE LA CADENA QUE SE VA A SIMULAR: ");
        cadena_para_simular = scanner2.nextLine();


        boolean la_cadena_del_afd_es_aceptada;
        if (simulador_de_AFD.SimularAFD(cadena_para_simular)) la_cadena_del_afd_es_aceptada = true;
        else la_cadena_del_afd_es_aceptada = false;

        if (la_cadena_del_afd_es_aceptada){
            System.out.println("LA CADENA ES ACEPTADA POR EL AUTOMATA FINITO DETERMINISTA");
            OpExtra.imprirLinea();
        }
        else
            System.out.println("LA CADENA NO ES ACEPTADA POR EL AUTOMATA FINITO DETERMINISTA");

    }
        
        
        
         
        
       
        
        
    
    
    
    /**Este códifo fue tomado de: 
      http://jconnexion.blogspot.com/2011/12/jfilechooser-para-abrir-yo-guardar.html
      el 20 de agosto del 2015
    
    Se utiliza para guardar el afn generado*/
    private static void guardar(String text){
        System.out.println("Se ha abierto un cuadro de dialogo");
        if (text.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
            JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
        }else{

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.txt", "txt"));//filtro para ver solo archivos txt
            fileChooser.setSelectedFile(new File("AFN.txt"));
            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    p = PATH;
                    PrintWriter printwriter = new PrintWriter(JFC);
                    printwriter.print(text);//escribe en el archivo todo lo que se encuentre en el JTextArea
                    printwriter.close();//cierra el archivo
                    
                    //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                    if(!(PATH.endsWith(".txt"))){
                        File temp = new File(PATH+".txt");
                        JFC.renameTo(temp);//renombramos el archivo
                    }
                    
                    JOptionPane.showMessageDialog(null,"Guardado exitoso!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){//por alguna excepcion salta un mensaje de error
                JOptionPane.showMessageDialog(null,"Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
            }
        }           
    }


    /**
     * Se utiliza para guardar la imagen(.dot)
     * @param text 
     */
    private static void guardarImagen(String text){
        System.out.println("Dentro del metodo Guardar Imagen");

        
        if (text.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
            JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
        }else{
            System.out.println("Dentro del else");
            System.out.println("Se abrio el cuadro de dialogo");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.dot", "dot"));//filtro para ver solo archivos txt
            fileChooser.setSelectedFile(new File("AFNimagen.dot"));
            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    PrintWriter printwriter = new PrintWriter(JFC);
                    printwriter.print(text);//escribe en el archivo todo lo que se encuentre en el JTextArea
                    printwriter.close();//cierra el archivo
                    p = PATH;
                    //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                    if(!(PATH.endsWith(".dot"))){
                        File temp = new File(PATH+".dot");
                        
                        JFC.renameTo(temp);//renombramos el archivo
                    }
                    
                    JOptionPane.showMessageDialog(null,"Guardado exitoso!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){//por alguna excepcion salta un mensaje de error
                JOptionPane.showMessageDialog(null,"Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
            }
        }           
    }
    
    
    
    /**
     * Este metodo llama al graphviz con un process builder
     */
    private static void llamarAlGraphViz(){
//        System.out.println("Hola Mundo");
        try {
      
      String dotPath = "d:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
      
            System.out.println(p);
      String fileInputPath = p;
      String fileOutputPath = p+".jpg";
      
      String tParam = "-Tjpg";
      String tOParam = "-o";
        
      String[] cmd = new String[5];
      cmd[0] = dotPath;
      cmd[1] = tParam;
      cmd[2] = fileInputPath;
      cmd[3] = tOParam;
      cmd[4] = fileOutputPath;
                  
      Runtime rt = Runtime.getRuntime();
      
      rt.exec( cmd );
      
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }

  }
    
    
}
