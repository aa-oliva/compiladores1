
package Automata;

import java.util.ArrayList;


/**
 * Esta clase genera automatas no deteministicos
 * @author Moises Urias
 */
public class Generador_de_Automatas {

    boolean remover_espacio_en_blanco = false;
    /* Constructor del Generador de Automtas    */
    public Generador_de_Automatas(){    
        
    }
    
    
    /* Este metodo genera un Automata finito no determinsta*/
    public Automata createAutomata(String cadena) {
        /*Almacena un arraylist con el alfabeto del automata*/
        ArrayList<String> alfabeto;
        int nodoinicialafn = 0;
        int nodofinalafn = 0;

        // TODO code application logic here

        /*Esta variable almacena el tiempo al principio de la ejecución del
           programa*/
        long time1 = System.currentTimeMillis();

        if(remover_espacio_en_blanco){
            cadena = cadena.trim();
            cadena = cadena.replace(" ", "");
        }


        
        /*Se agrega la funcion de cerradura positiva*/
        cadena = OpExtra.convertirCerraduraPositiva(cadena);
//        System.out.println(cadena);
        
        /*Se convirte de infix a postfix*/
        cadena = (RegexConverter.infixToPostfix(cadena));

//        System.out.println("Cadena con posfix: \n" + cadena+"\n\n\n");




        /*Imprime el alfabeto del automata ingresado */
//        System.out.println("El alfabeto del automata es:");
        alfabeto = OpExtra.alfabeto(cadena);
//        System.out.println(alfabeto);

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


//        System.out.println("\nLa cantidad de estados es: " +
//                ContadorNodo.getContador()+"\n");

        aut = aut.automatas.pop();




//        System.out.println("\nCantidad de transiciones: " + aut.transiciones.size()+"\n");

        ArrayList<Nodo> arnodo = aut.getEstados();
        int nodoinicial =0;
        for (int i=0; i<arnodo.size();i++)
        {

            if (arnodo.get(i).iseInicial() == true){
//                System.out.println("Estado inicial: " + arnodo.get(i).getId());
                nodoinicial = arnodo.get(i).getId();
                nodoinicialafn = arnodo.get(i).getId();
            }

            if (arnodo.get(i).iseFinal() == true){
//                System.out.println("Estado final: " + arnodo.get(i).getId());
                nodofinalafn = arnodo.get(i).getId();
            }
        }


          /*Se resta el tiempo actual del sistema con el tiempo de inicio y ese
           es tiempo total de la ejecucion*/
       long time = System.currentTimeMillis()-time1;
//        System.out.println("\nEl tiempo total del programa es: " + time + " milisegundos\n");



        /*Crea un subset*/
//        System.out.println("El nodo inicial a introducir en el subset es: " +nodoinicial);

//        System.out.println("La cantidad de transiciones en el automata es: " + aut.getTransiciones().size());



//        System.out.println(aut.getAlfabeto() + "ALFABETO");
//        System.out.println(alfabeto);
        aut.setAlfabeto(alfabeto);
//        System.out.println("El afabeto que posee el automata es: " + aut.getAlfabeto());



        /*Se genera el archivo con al AFN*/
        String texto = ""; /*Texto que en el que se almacen al informacino a guardar*/
        texto = texto + "El nodo inicial del AFN es: " + String.valueOf(nodoinicialafn) + "\n";
        texto = texto +"El nodo final del AFN es: " + String.valueOf(nodofinalafn) + "\n";
        texto = texto + "El alfabeto del AFN es: " + alfabeto + "\n\n";
        texto = texto + "El tiempo total de la simulacion del afn es: "+(System.currentTimeMillis()-time1)+"\n\n";
        texto = texto + "Las transiciones del afn son:\n ";
         for (int i=0; i<aut.transiciones.size();i++)
        {

            texto = texto + i +": "+ aut.transiciones.get(i)+"\n";
        }

        texto = texto + "\n\nSiendo '!' lo que equivale al simbolo epsilon";


        return aut; /*Se devuelve el automata generado*/
    }



}
