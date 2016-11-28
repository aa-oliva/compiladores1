/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Moises Urias
 */
public class AFD {
   /** **************************************** DEBUG ************************************************/
    /**
    * Si esta variable es true, entonces se ejecutan toas las lineas que se utilizan para hacer debug.
    * */
    private boolean debugAFD = false;

    /**
     * Si esta variable es true, entonces de va a dibujar el AFD
     */
    private boolean printAFD = true;

    private boolean printTextAFD = false;
    /**
     * Si esta variable es true, entonces se simula el automata
     */
    private boolean simularAFD = false;
    /** **************************************** DEBUG (END)************************************************/


    private static String p; /*Direccion donde se crea la imagen del automata*/

    /**
     * Estos son los estados del AFD
     */
    private ArrayList<Subset> estados = new ArrayList<Subset>();


    /**
     * Estas son las transiciones entre los estados del AFD
     * */
    private ArrayList<Transicion> tranciciones = new ArrayList<Transicion>();
    
    /**
     * Este es el contador de nodos del AFD.
     * Se utiliza el mismo contador de nodos que se utilizo para el AFN.
     * */
    private ContadorNodo contador; /*Contador del numero de nodos*/


    /**
     * Este es el estado de aceptacion del AFN
     */
    private int estado_de_aceptacion_AFN = 0;


    /**
     * Este atributo almacena el estado inicial del AFN.
     */
    private int nodoInicial = 0;


    /**
     * Estados de aceptacion del AFD
     * */
    private ArrayList<Integer>estados_de_aceptacion = new ArrayList<>();
    /**
     * Constructor de un AFD con base en un automata
     * @param afn
     */
    public AFD(Automata afn)
    {
        Scanner scaner = new Scanner(System.in);
        System.out.println("\n\nDentro de constructor del AFD");
        
        /*Verifica que aun existan estados no marcados en la pila.
        Se empieza con true, por el primer estado de la pila*/
        boolean hayEstadosNoMarcados = true;

        
        /*Se busca el estado inicial del afn*/
        this.nodoInicial =0;


        /*Se busca el estado de aceptacion del AFN*/
        this.nodoInicial = afn.getEstadoInicial();
        this.estado_de_aceptacion_AFN = afn.getEstadoFinal();
        System.out.println("...Estado inicial del AFN: " + this.nodoInicial);
        System.out.println("...Estado final del AFN: " + this.estado_de_aceptacion_AFN);

        if(debugAFD) {
            OpExtra.leerPantalla();
        }

        
        /*Una ves ya se ha encontrado el estado inicial del afn, se crea un 
          subset con ese estado. Este va a ser el estado inicial del AFD*/
        Subset subset1 = new Subset(this.nodoInicial, afn.getTransiciones());




        //DEBUG
        if (debugAFD == true){
            System.out.println("Antes del e-closure");
           OpExtra.leerPantalla();
        }


        /*Se hace un eClosure de ese estado*/
        subset1 = OpExtra.eClosure(subset1);



        System.out.println("Primer e-closure del AFD: " + subset1);
        subset1.ordenar();
        System.out.println("Subset 1 ordenado: " + subset1);
        scaner.nextLine();
        /*Se le pone nombre al primer estado del AFD*/
        //El nombre del primer estado del AFD por defecto es 1.
        //Como ya se creo el AFN, se renicia el contador de nodos
        ContadorNodo.reset();
        int primer_id_del_subset = contador.getInstance().getContador();
        System.out.println("Contador de nodos: " + primer_id_del_subset);
        subset1.setNombre_subset(primer_id_del_subset);
        System.out.println("El nombre asignado al subset1 es: " + subset1.getNombre_subset());

        //Se asgina el subset1 como etado inicial del AFD
        subset1.setEstado_inicial(true);
        System.out.printf("Se ha asignado este estado como estado inicil");

        if (debugAFD) {
            OpExtra.leerPantalla();
        }

        //DEBUG
        if (debugAFD == true){
            OpExtra.leerPantalla();
        }
        /*Se agrega el subconjunto inicial a una lista de estados iniciales*/
        estados.add(subset1);
        
        
        /*Se obtiene el alfabeto del automata*/
        ArrayList<String> alfabeto = afn.getAlfabeto();






        /*********** Esto es lo que hay que arreglar ************/
        /*Un vez se ha creado el estado inicial del AFD, el resto de estados se construye de una forma recursiva*/
        /*Mientras haya estados no marcados, se hace e-closure y mover*/
        while(hayEstadosNoMarcados == true)
        {
            System.out.println("Dentro del ciclo while del AFD");
            
            /*Se recorren todos los estados que hay actualmente en el AFD*/
            for (int h=0; h<estados.size();h++)
            {
                /*Se toma el estado actual*/
                Subset estadoActualAnterior = estados.get(h);
                Subset estadoActual = estados.get(h);


                /*Como se esta revisando este esatdo, el esatado se marca como
                  marcado*/
                estadoActualAnterior.setMarcado(true);
                
                
                /*Se crea el nuevo estado del AFD*/
                for (int i=0; i<alfabeto.size(); i++) {

                    /*Se aplica la operacion 'mover' al estado actual*/
                    estadoActual= OpExtra.mover(estadoActualAnterior, alfabeto.get(i));
                    System.out.println("Este es el move de estado " + estadoActualAnterior.getNombre_subset() + " con el simbolo " + alfabeto.get(i)  + ": "
                            + estadoActual);

                    if (debugAFD == true){
                        OpExtra.leerPantalla();
                    }
                    Subset estadoMove = estadoActual;


                    /*Se hace eClosure al estado actual*/
                    estadoActual = OpExtra.eClosure(estadoActual);

                    /*Se ordena de menor a mayor el estado actual*/
                    estadoActual.ordenar();
                    System.out.println("E-closure del estado actual ordenado: " + estadoActual);  //Nuevo estado del AFD


                    //Si el e-closure devuelve vacio, entonces se salta un bucle for
                    if (estadoActual.getNodos().size() == 0){
                        System.out.println("---> El estado actual no tiene nodos!!!");
                        System.out.println("Se saltara este ciclo de creacion de estados");
                        if (debugAFD){
                            OpExtra.leerPantalla();
                        }
                        continue;
                    }


                    /*Se revista si el estado obtenido despues de realizar el 
                      mover y el eclosure es un nuevo estado de la lista de
                      estados del AFD o ya existe.*/
                    boolean estadoExistente = false;
                    int nombre_del_estado_existente = 0;

                    //DEBUG
                    if (debugAFD){
                        OpExtra.imprirLinea();
                    }

                    System.out.println("Se compara el estado actual con todos los estados que hay actualmente en la " +
                            "lista de estados");

                    //DEBUG
                    if (debugAFD){
                        OpExtra.leerPantalla();
                    }


                    for(int g = 0; g<this.estados.size();g++){ //Todos los estados del AFD
                        ;
                            /*Subset que posteriormente se añadira a los estados del
                               AFD*/
                        Subset sub = new Subset(); //Subset vacio

                            /*Se toma el arraylist de nodos del estado actual del ciclo que se esta analizando*/
                            /*La forma en que se comparan los subsets es con base a sus nodos*/
                        Subset estado1 = this.estados.get(g);

                            /*Se ordena de menor a mayor los nodos del estado actual del ciclo*/
                        estado1.ordenar();
                        System.out.println("Estado " + String.valueOf(estado1.getNombre_subset()) +": " + estado1);
                        System.out.println("---------------------");

                            /*Compara si son iguales*/
                        if (estadoActual.equals(estado1))
                        {
                            System.out.println("Estado 1: " + estado1);
                            System.out.println("Estado Actual: " + estadoActual);
                            System.out.println("Lo estados son iguales");
                            System.out.println("El estado actual es igual al estado: " + estado1.getNombre_subset());
                            estadoExistente = true;
                            nombre_del_estado_existente = estado1.getNombre_subset();
                            //
                        }
                        else{
                            System.out.println("Estado 1: " + estado1);
                            System.out.println("Estado Actual: " + estadoActual);
                            System.out.println("Los estados no son iguales");
                            //System.out.println("Como no son iguales, se agrega el nuevo estado al conjunto de estados");

                        }

                    }

                        /*Si despues de recorrer todos los estados que actualmente tiene el AFD se encontro que el
                        * estado acual no existe, entonces se agrega el estado a la lista de estado del AFD*/
                    if (estadoExistente == false){
                        System.out.println("----------  DEBUG: CREACION DE ESTADO -----------------");
                        System.out.println("La letra que se esta utilizando es: " + alfabeto.get(i) );
                        estadoActual.setNombre_subset(contador.getInstance().getContador());
                        System.out.println("Estado Actual.nombre = " + estadoActual.getNombre_subset());
                        System.out.println("Se crea la nueva transicion");

                        /*Se crea una instancia de uns transicion*/
                        Transicion nuevaTransicion = new Transicion(estadoActualAnterior.getNombre_subset(),alfabeto.get(i),
                                estadoActual.getNombre_subset());


                        tranciciones.add(nuevaTransicion);
                        System.out.println(nuevaTransicion.toString());

                        estados.add(estadoActual);
                        System.out.println(estados);
                        System.out.println("tranciciones: " + tranciciones.toString());
                        System.out.println("----------  DEBUG (END): CREACION DE ESTADO -----------------");
                    }
                    else{
                        OpExtra.imprirLineaCorta();
                        System.out.println("\n");
                        System.out.println("El resultado del analisis detemrino que el esatdo ya existia,\n" +
                                "por lo tanto no se agregara a la lista de esados");
                        if(debugAFD == true){
                            OpExtra.leerPantalla();
                        }


                        //Si el estado existe, es necesario crear una transicion, pero no se agrega el estado.
                        System.out.println("----------  DEBUG: CREACION DE TRANSICION -----------------");
                        tranciciones.add(new Transicion(estadoActualAnterior.getNombre_subset(),alfabeto.get(i),
                                nombre_del_estado_existente));
                        System.out.println(new Transicion(estadoActualAnterior.getNombre_subset(),alfabeto.get(i),
                                nombre_del_estado_existente).toString());
                        System.out.println("----------  DEBUG (END): CREACION DE TRANSICION -----------------");

                        if (debugAFD == true){
                            OpExtra.leerPantalla();
                        }

                    }

                    //DEBUG
                    if (debugAFD == true){
                        OpExtra.leerPantalla();
                    }

                }
            }




            /*Se imprimeen todas las transiciones que se generaron en el proceso*/
            System.out.println("Estas son todas las transiciones del automata");
            for (int k=0; k<tranciciones.size(); k++){
                String t = "Transicion { nodo_Inicial: ";
                t = t + OpExtra.reemplazarNumerosPorLetras(tranciciones.get(k).getNodoInicial());
                t = t + ", simbolo = ";
                t = t + tranciciones.get(k).getSimbolo();
                t = t + ", nodo_Final: " + OpExtra.reemplazarNumerosPorLetras(tranciciones.get(k).getNodoFinal());
                System.out.print(t);
                System.out.println("\t\t\t" + tranciciones.get(k).toString());
            }


            /*Se revisa si hay o no estados no marcados. Esto se hace para salir del ciclo infinito del while */
            boolean faltantes = false;
            for (int m=0; m<estados.size();m++){
                if (estados.get(m).isMarcado() == false){
                    faltantes = true;
                    break;
                }
            }

            if (faltantes == true){
                hayEstadosNoMarcados = true;
            }
            else {
                hayEstadosNoMarcados = false;
            }

        }

        System.out.println(this.estados);
        System.out.println("Los estados de aceptacion son:");

        /*Se revisan todos los estados del AFN.
        ** Se les asigna su propiedad de estado de acepatacion o no (estado normal)
        **/
        for (int o = 0; o<estados.size();o++){
            if (estados.get(o).getNodos().contains(estado_de_aceptacion_AFN)){
                estados.get(o).setEstado_de_aceptacion(true);
                this.estados_de_aceptacion.add(estados.get(o).getNombre_subset());
            }
            else{
                estados.get(o).setEstado_de_aceptacion(false);
            }
        }


        String texto = ""; /*Texto que en el que se almacen al informacino a guardar*/
        texto = texto + "El estado inicial del AFD es: " + String.valueOf(this.getEstadoInicial().getNombre_subset()) + "\r\n";
        texto = texto +"Los estados de aceptacion del AFD es: " + String.valueOf(this.getEstados_de_aceptacion()) + "\r\n";
        texto = texto + "El alfabeto del AFD es: " + alfabeto + "\r\n\r\n";
//      texto = texto + "El tiempo total de la simulacion del afn es: "+(System.currentTimeMillis()-time1)+"\n\n";
        texto = texto + "Las transiciones del afd son:\r\n";
        for (int textoTransiciones = 0; textoTransiciones<this.getTranciciones().size();textoTransiciones++){
            texto = texto + this.getTranciciones().get(textoTransiciones) + "\r\n";
        }
//        texto = texto + this.getTranciciones();

        /*Se guarda el automata*/
        if (printTextAFD){
            System.out.println(texto);
           guardar(texto);
        }


        System.out.println(estados_de_aceptacion);
        if (debugAFD){
            OpExtra.leerPantalla();
        }

        //Se dibuja el AFD
        if (printAFD){
            this.dibujarAFD();
        }


    }



    /**
     * Este metodo devuelve los estados de aceptacion del AFD
     * */
    public ArrayList<Integer> getEstados_de_aceptacion() {
        return estados_de_aceptacion;
    }


    /**
     * Este metodo permite recibir estados de aceptacion del AFD.
     * @param estados_de_aceptacion
     */
    public void setEstados_de_aceptacion(ArrayList<Integer> estados_de_aceptacion) {
        this.estados_de_aceptacion = estados_de_aceptacion;
    }



    /**
     * Este metodo devuelve el estado inicial del del automata
     * */
    public Subset getEstadoInicial(){
        //Estado inicial del Automata Finito Determinista
        Subset estadoInicial = null;

        for (int r =0;r<estados.size(); r++){
            if (estados.get(r).isEstado_inicial() == true){
                estadoInicial = estados.get(r);
            }
        }

        return estadoInicial;
    }


    /**
     * Este metodo devuelve los estados del automata
     * */
    public ArrayList<Subset> getEstados() {
        return estados;
    }


    /**
     * ESte metodo asigna los estados del automata
     * @param estados
     */
    public void setEstados(ArrayList<Subset> estados) {
        this.estados = estados;
    }

    /**
     * Este metodo devuelve el nodo inicial del AFD.
     * */
    public int getNodoInicial() {
        return nodoInicial;
    }

    /**
     * Este metodo permite asignar el nodo incial del AFD.
     */
    public void setNodoInicial(int nodoInicial) {
        this.nodoInicial = nodoInicial;
    }

    /**
     * Este metodo devuelve el estado inicial del AFN.
     */
    private void dibujarAFD(){
       /*Texto*/
       String texto = "";

       texto = texto + "digraph G\n";
       texto = texto +"{\n";
       texto = texto + "node [shape=circle];";
       texto = texto + "node [style=filled];";
       texto = texto + "node [fillcolor=\"#EEEEEE\"];";
       texto = texto + "edge [color=\"#31CEF0\"];";

       //Los estados de aceptacion se vizualizan de una manera diferente
       for (int p =0;p<estados_de_aceptacion.size();p++){
           texto = texto + estados_de_aceptacion.get(p) + " [fillcolor=\"yellow\" shape=doublecircle];";
       }

       for (int l =0; l<tranciciones.size();l++){

           texto = (
                   texto + tranciciones.get(l).getNodoInicial() + " -> " +
                   tranciciones.get(l).getNodoFinal() + "[label=\""+
                   tranciciones.get(l).getSimbolo()+"\"];"
           );

       }

       texto = texto + "rankdir=LR;\n}";

       System.out.println(texto);
        if(debugAFD) {
            OpExtra.leerPantalla();
        }

       this.guardarImagen(texto);
       llamarAlGraphViz();
   }

    /**Este códifo fue tomado de:
     http://jconnexion.blogspot.com/2011/12/jfilechooser-para-abrir-yo-guardar.html
     el 20 de agosto del 2015

     Se utiliza para guardar el afn generado*/
    private static void guardar(String text){


        if (text.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
            JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
        }else{

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.txt", "txt"));//filtro para ver solo archivos txt
            fileChooser.setSelectedFile(new File("AFD.txt"));
            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    p = PATH;
                    PrintWriter printwriter = new PrintWriter(JFC);
                    printwriter.print(text);//escribe en el archivo  lo que se encuentre en el JTextArea
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
            fileChooser.setSelectedFile(new File("AFDimagen.dot"));
            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    PrintWriter printwriter = new PrintWriter(JFC);
                    printwriter.print(text);//escribe en el archivo  lo que se encuentre en el JTextArea
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


    /**
     * Este metodo devuelve las transiciones del automata
     * @return
     */
    public ArrayList<Transicion> getTranciciones() {
        return tranciciones;
    }

    /**
     * Este metodo permite asignar las transiciones al automata
     * @param tranciciones
     */
    public void setTranciciones(ArrayList<Transicion> tranciciones) {
        this.tranciciones = tranciciones;
    }
}
