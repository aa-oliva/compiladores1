/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringJoiner;

/**
 * Esta clase modela un automata finito no determinista
 * @author Moises Urias
 */
public class Automata {

    /**
     * 0=Character
     * 1=Keyword
     * 2=Token
     * Esta variable se utiliza para saber que tipo de elemento se esta simulando en el programa generado del lexer
     */
    private int lexer_kind;

    private boolean exceptedKeywords=false;

    public ArrayList<Nodo> estados = new ArrayList<Nodo>();
   
    private String simbolo;
    public ArrayList<Transicion> transiciones = new ArrayList<Transicion>();

    public Stack<Automata> automatas = new Stack<Automata>();
    
    /*Este sera el nombre del automata, este se utiliza en el Lexer*/
    private String nombre;
    
    /**
     * Contiene el alfabeto que es utilizado en el automata
     */
    public ArrayList<String> alfabeto = new ArrayList<String>();
    
    
    /**
     * Constructor de la clase automata sin parametros
     */
    public Automata(){}


    /**
     * Constructor de la clase automata con el alfabeto.
     * Este constructor inicializa el automata con un alfabeto.
     */
    public Automata(ArrayList<String> alfabeto){
        this.setAlfabeto(alfabeto);
    }


    /**
     * Constructor de la clase automata.<p>
     * Crea un automata sencillo basado en el simbolo del automata.<p>
     * Los DOS nodos del automata se definen como NO inciales y NO finales.
     * @param simbolo 
     */
    public Automata(String simbolo) {
       
        /*Se crean los nodos que tendra el automata*/
        Nodo nodoA = new Nodo(true, false); /*Se crea un nuevo nodo inicial*/
        Nodo nodoB = new Nodo(false, true); /*Se crea un nuevo nodo final*/
        
        
       
        this.simbolo = simbolo; /*Simbolo del automata y de la transicion*/
        this.transiciones.add(new Transicion(nodoA.getId(),simbolo,nodoB.getId()));
 
      
        
        this.estados.add(nodoA);
        this.estados.add(nodoB);
        
    }
    
    /**
     * Crea un automata en base a dos automatas proporcionados.<p>
     * Se usa para la expresion OR entre dos automatas
     * @param automata1
     * @param automata2 
     */
    private Automata OR(Automata automata1, Automata automata2)
    {
        int cont =0; /*Contador*/
        Automata automata = new Automata(); /*Automata que contenra */
        
        Nodo nodoI = new Nodo(); /*Se crea un nodo inicial*/
        Nodo nodoF = new Nodo(); /*Se crea un nodo final*/
        
        
        /*Se recorre el primer automata*/
        while (cont<automata1.estados.size())
            {              
                
                /*Nodo provisional para nodos de los automatas*/
                Nodo estado = automata1.estados.get(cont);
                
                if (estado.iseInicial() == true)
                {
                    
                    
                    automata.transiciones.add(new Transicion(nodoI.getId(),"!",estado.getId()));
                   
                    
                    
                    
                    estado.seteInicial(false);
                    automata1.estados.set(cont, estado);
                }
                
                if (estado.iseFinal()==true)
                {
                    
                    automata.transiciones.add(new Transicion(estado.getId(),"!",nodoF.getId()));
                     
                    
                  
                    
                    estado.seteFinal(false);
                    automata1.estados.set(cont, estado);
                }
                
                cont++;
                
            }
        
        /*Se reinicia el contador a cero*/
        cont=0;
       
        
        /*Se recorren los estados del segundo automata*/
        while (cont<automata2.estados.size())
            {
                                
                /*Nodo provisional para nodos de los automatas*/
                Nodo estado = automata2.estados.get(cont);
                
                if (estado.iseInicial() == true)
                {
                    automata.transiciones.add(new Transicion(nodoI.getId(),"!",estado.getId()));
                  
                    estado.seteInicial(false);
                    automata2.estados.set(cont, estado);
                }
                
                if (estado.iseFinal()==true)
                {
                    automata.transiciones.add(new Transicion(estado.getId(),"!",nodoF.getId()));
                    
                    
                    
                    estado.seteFinal(false);
                    automata2.estados.set(cont, estado);
                }
                
                cont++;
                
            }
        
        
        cont =0; /*Reinicia el contrador a cero*/
        
        /*Asigna todos los nodos del automata1 al nuevo automata*/
        while(cont<automata1.estados.size())
        {
            automata.estados.add(automata1.estados.get(cont));
            cont++;
        }
        
        
        cont=0; /*Reinicia el contador a cero*/
        
        /*Asignna todos los nodos del automata2 al nuevo automata*/
        while(cont<automata2.estados.size())
        {
            automata.estados.add(automata2.estados.get(cont));
            cont++;
            
        }
        
        cont=0; /*Reinicia el contrador a cero*/
        
        /*Asinga las transiciones del atuomata1 al nuevo automata*/
        while(cont<automata1.transiciones.size())
        {
            automata.transiciones.add(automata1.transiciones.get(cont));
            cont++; //Se suma uno al contador
        }
        
        /*Se reinicia a cero el contrador*/
        cont = 0;
        
        /*Asina las transiciones del automata2 al nuevo automata*/
        while(cont<automata2.transiciones.size()){
            automata.transiciones.add(automata2.transiciones.get(cont));
            cont++;
        }
        
        /*se asignna un nuevo nodo inicial al automata*/
        nodoI.seteInicial(true);
        automata.estados.add(nodoI);
        
        /*Se asigna un nuevo nodo final al automata*/
        nodoF.seteFinal(true);
        automata.estados.add(nodoF);
    
        /*Devuelve el automata*/
        return automata;
    }
    
    /**
     * Este constructor crea un automata basandose en el simbolo cuando se <p>
     * quiere especificar si los nodos del automata son iniciales o finales.
     * 
     * @param simbolo
     * @param einicial1
     * @param efinal1
     * @param einicial2
     * @param efinal2 
     */
    public Automata(String simbolo, boolean einicial1, boolean efinal1,
            boolean einicial2, boolean efinal2)
    {
        this.simbolo = simbolo;
        this.estados.add(new Nodo(einicial1,efinal1));
        this.estados.add(new Nodo(einicial2, efinal2));
    }
    
    
    
    
    /**
     * Este metodo se utiliza para crear un nuevo estado del automata
     * @param simbolo
     * @param einicial
     * @param efinal 
     */
    public void crearEstado(String simbolo, boolean einicial, boolean efinal)
    {
      this.estados.add(new Nodo(einicial, efinal));
    }
        
    /**
     * Crea un automata basado en el simbolo que se ingrese
     * @param simbolo 
     */
    public void crearAutomata(String simbolo){      
        
        /*Compara el simbolo para saber si es parte del alfabeto*/
       boolean comparar = esAlfabeto(simbolo);
       
       if (comparar == true){
           automatas.push(new Automata(simbolo));
        }
       else
       {
           
           /*Si se encuentra un OR*/
           if (simbolo.equals("|"))
           {
               /*Se sacan dos automatas de la pila*/
               Automata automata1 = automatas.pop();
               Automata automata2 = automatas.pop();
               
               automatas.push(this.OR(automata1, automata2));
               
           }
           /*Si se encuentra un clean*/
           else if(simbolo.equals("*"))
           {
               Automata automata1 = automatas.pop();
               automatas.push(this.kleene(automata1));
               
           }
           /*Si se encuentra una concatenacion*/
           else if(simbolo.equals("."))
           {
               Automata automata1 = automatas.pop();
               Automata automata2 = automatas.pop();
               automatas.push(this.concatenar(automata1, automata2));
           }
           else
           {
               /*Programacion defensiva*/
               System.out.println("La expesion regular esta mal ingresada, el"
                       + "simbolo: ' " +simbolo+"'"+
                       "no es reconocible");
               
           }
       }
        
    }
    
    
    
    /**
     * Esta funcion sirve para saber si un simbolo de la cadena de texto leida<p>
     * es parte del alfabeto o es un operador
     * 
     * @param simbolo
     * @return simbolo
     */
    private boolean esAlfabeto(String simbolo)
    {
        String[] operadores = {"(","|","?",".","*","+","^"};
        boolean operador = true; /*Si se ha reconocido como operador o no*/
        
        
        int i = 0; /*Contador de operadores*/
        
        while (i<operadores.length)
        {
           if (simbolo.equals(operadores[i]))
           {
               operador = false;
           }
           
           i++; /*Aumenta uno el contador 'operador'*/
            
        }
        
        /*Devuelve el resultado de evaluar el simbolo para saber si es un 
            operador o no*/
        return operador;
    }
    
    
    /**
     * Devuevelve una automata despues de aplicarle la operacion kleene al 
     *  automata ingresado
     * @param automata1
     * @return 
     */
    private Automata kleene(Automata automata1)
    {
        
        Automata automata = automata1;
        
        Nodo nodoI = new Nodo(); /*Nuevo nodo inicial*/
        Nodo nodoF = new Nodo(); /*Nuevo nodo final*/
        
        int cont=0; /*Contador*/
        
        /*Se crean temporalmente estados iniciales y finales*/
        Nodo tEstadoInicial = null;
        Nodo tEstadoFinal = null;
        
        /*Se recorre el automata para encontrar el estado inicial*/
        while (cont<automata1.estados.size())
        {
            Nodo estado=automata1.estados.get(cont);
            if (estado.iseInicial()==true)
            {
                /*Se crea la transicion entre el nodo inicial del kleene y el 
                  nodo inicial del automata que se esta analizando*/
                automata.transiciones.add(new Transicion(nodoI.getId(),"!",estado.getId()));
                
                tEstadoInicial = estado;
                estado.seteInicial(false);
                
                /*Se le quita la propiedad de estado inicial*/
                automata.estados.set(cont, estado);
            }

             /*Se crea la transicion entre el nodo final del kleene y el
                  nodo final del automata que se esta analizando*/
            if (estado.iseFinal() == true)
            {
                automata.transiciones.add(new Transicion(estado.getId(),"!",nodoF.getId()));
                

                tEstadoFinal = estado;
                estado.seteFinal(false);
                
                /*Se le quita la propiedad de estado final*/
                automata.estados.set(cont, estado);
            }
            
            cont++;
        }
        
        /*Se crea una transicion entre el estado final y el estado inicial 
            del automata original*/
            automata.transiciones.add(new Transicion(tEstadoFinal.getId(),"!",tEstadoInicial.getId()));
           
            
            /*Se agrga la transicion epsilon entre el primer nuevo estado 
            inicial y el nuevo estado final*/
            automata.transiciones.add(new Transicion(nodoI.getId(),"!",nodoF.getId()));
           
            /*Se agregan los nuevos nodos al automata*/
            
            nodoF.seteFinal(true);
            nodoI.seteInicial(true);
            
            automata.estados.add(nodoF);
            automata.estados.add(nodoI);
        
            /*Deuvelve el automata generado*/
        return automata;
    }

    
    /**
     * Concatena dos automatas
     * @param automata2
     * @param automata1
     * @return automata1 concatenado con automata2
     */
    private Automata concatenar(Automata automata2, Automata automata1) {
        
        int cont=0; /*Contador*/
       
        while(cont<automata1.estados.size())
        {
          Nodo nodo = automata1.estados.get(cont);
          
          if (nodo.iseFinal()==true){
            
              /*Se recorre el otro automata para buscar su estado inicial
                 y concatenarlos*/
              for(int i =0; i<automata2.estados.size();i++){
                  Nodo nodo2 = automata2.estados.get(i);
                  
                  if (nodo2.iseInicial()==true)
                  {
                     
                      /*Se crea una transicion entre ambos automatas */
                      nodo2.seteInicial(false);
                      automata1.transiciones.add(new Transicion(nodo.getId(),"!",nodo2.getId()));
                      
                      nodo.seteFinal(false);
                      
                      break;
                  }
              }
                  
          }  
          
          cont++;
          
           
          
        }
        
        /*Se agrega al automata1 todos los nodos y transiciones del automata2*/
        for(int i=0;i<automata2.estados.size();i++)
          {
            automata1.estados.add(automata2.estados.get(i));
          }
        
        /*Se agregan todas las transiciones del automata2 al automata1*/
        for(int i=0;i<automata2.transiciones.size();i++)
        {
            automata1.transiciones.add(automata2.transiciones.get(i));
        }
        
        return automata1;
    }

    /**
     * Devuelve un ArrayList con las transiciones del automata
     * @return 
     */
    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    /**
     * Devuelve un ArrayList con los estados del automata
     * @return 
     */
    public ArrayList<Nodo> getEstados() {
        return estados;
    }

    /*Este metodo devuelve el alfabeto que es utilizado ene l automata*/
    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    /*Este metodo asigna el alfabeto que es utilizado en el automata*/
    public void setAlfabeto(ArrayList<String> alfabeto) {
        this.alfabeto = alfabeto;
    }
    
    
    /**
     * Este metodo asigna un valor al atributo nombre del automata
     * @param nombre 
     */
    public void setNombre(String nombre)
    {
       this.nombre = nombre; 
    }
    
    /**
     * Este metodo devuelve el nombre del automata
     * @return nombre
     */
    public String getNombre()
    {
        return this.nombre;
    }
    
    
    

    
    
    public int getEstadoInicial (){
        
        /*Se encuetra el estado inicial y el estado final, el cual será el estado
            de acptación. Esto es necesario al realizar la simulación, saber si
            se ha hecho una simulación efectiva o no*/
        int nodoinicialafn = 0;
        
        ArrayList<Nodo> arnodo = this.getEstados();
        int nodoinicial =0;
        for (int i=0; i<arnodo.size();i++)
        {
            
            if (arnodo.get(i).iseInicial() == true){
                //System.out.println("Estado inicial: " + arnodo.get(i).getId());
                nodoinicial = arnodo.get(i).getId();
                nodoinicialafn = arnodo.get(i).getId();
            }
            
        }
        
        return nodoinicialafn;
    }
            
    
    public int getEstadoFinal(){
         ArrayList<Nodo> arnodo = this.getEstados();
        
         int nodofinalafn =0;
        
        for (int i=0; i<arnodo.size();i++)
        {
            if (arnodo.get(i).iseFinal() == true){
                //System.out.println("Estado final: " + arnodo.get(i).getId());
                nodofinalafn = arnodo.get(i).getId();
            }
        }
        
        return nodofinalafn;
    }
    
    
    public boolean simular(String cadena)
    {
        /*Devuelce si el automata cumple con una cadena de aceptacion*/
        boolean estadoAceptacion = false;
        
        return estadoAceptacion;
    }
    
    public void setEstados(ArrayList<Nodo> estados) {
     this.estados = estados;
    }
    
    /**
     * Debido a que el ArrayList de estado es de tipo privado, este método 
     * permite agregar estados a este arreglo
     * @param estado 
     */
    public void agregarEstado(Nodo estado){
        this.estados.add(estado);
    }

    public int getLexer_kind() {
        return lexer_kind;
    }

    public void setLexer_kind(int lexer_kind) {
        this.lexer_kind = lexer_kind;
    }

    public boolean isExceptedKeywords() {
        return exceptedKeywords;
    }

    public void setExceptedKeywords(boolean exceptedKeywords) {
        this.exceptedKeywords = exceptedKeywords;
    }
}
            
    

