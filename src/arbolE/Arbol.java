/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author jesus
 */
public class Arbol {
    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;
    //Identidicar entre operador y operandos
    final String espacios="\t";
    final String aritmeticos="+-*()^=/";
    final String variables="abcdefghijklmnopqrstuvwxyz";
    final String opMultiplica="*";
    private Nodo raiz;
    //30 de junio
    String[] temporales={"T1","T2","T3","T4","T5"};
    
    HashMap<String,String> tablaSimbolos;
    HashMap<String,String> erroresSemanticos;
    HashMap<String,String> producciones;
    
    int paso;
    
    //1ro de Julio
    ArrayList <String> reglasEjecutadas;
    
    //Constructor
    public Arbol(){
        reglasEjecutadas =new ArrayList<String>(); //1ro de Julio
        tablaSimbolos=new HashMap();
        erroresSemanticos=new HashMap();
        producciones=new HashMap();
        
        arbolNodo=new Stack<Nodo>();
        caracter=new Stack<String>();
        
        paso=0;
    }//Fin del constructor
    //*********REGLAS EJECUTADAS 1ro de Julio
    public String getReglasEjecutadas(){
        String reglasE="";
        for(int i=0;i<reglasEjecutadas.size();i++){
            System.out.println("Reglas ejecutadas "+reglasEjecutadas.get(i));
            reglasE+=reglasEjecutadas.get(i)+"\n";
        }//for
        return reglasE;
    }
    
    public void agregaValex(String lexema,String valor){
        
    }//agregaValex - Análisis semántico
    
    public String regresaValex(String lexema){
        return this.tablaSimbolos.get(lexema);
    }//regresaValex
    
    public void guardar(){//Permite construir el árbol
        paso++;
        Nodo izquierdo=(Nodo) arbolNodo.pop();
        Nodo derecho=(Nodo) arbolNodo.pop();
        
        String operador= caracter.peek();
        //El método peek va a la pila y toma su elemento sin sacarlo de la pila
        arbolNodo.push(new Nodo(derecho,caracter.pop(),izquierdo));
        //
        if (operador.equals("+")){
            String reglaE ="E.nodo=new Nodo(+,E1.nodo,T.nodo)";
            reglasEjecutadas.add("p"+paso+" "+reglaE);  
        }
        if (operador.equals("-")){
            String reglaE ="E.nodo=new Nodo(-,E1.nodo,T.nodo)";
            reglasEjecutadas.add("p"+paso+" "+reglaE);  
        }
        if (operador.equals("*")){
            String reglaE ="E.nodo=new Nodo(*,E1.nodo,T.nodo)";
            reglasEjecutadas.add("p"+paso+" "+reglaE);  
        }
        if (operador.equals("/")){
            String reglaE ="E.nodo=new Nodo(/,E1.nodo,T.nodo)";
            reglasEjecutadas.add("p"+paso+" "+reglaE);  
        }
    }
    
    //METODOS DEL ARBOL
    public Nodo crear(String expresion){
        //1. Considerar la expresión como un conjunto de tokens
        //StringTokenizer es una clase que sirve para dividir una cadena de texto
        //en partes, usando separadores llamados delimitadores
        StringTokenizer tokenizer;
        String token;
        //2. Separación de tokens de la expresión
        tokenizer=new StringTokenizer(expresion,espacios+aritmeticos,true);
        //3. Mientras existaan tokens
        while(tokenizer.hasMoreTokens()){
            //4. Omitir espacios en blanco
            token=tokenizer.nextToken();
            System.out.println("Token "+token);
            if(espacios.indexOf(token)>=0){
                //5.Se trata de un identificador
                System.out.println("Se trata de un identificador");
                //
            }else if(aritmeticos.indexOf(token)<0){
                //6.Extraer de la pila los terminos que estaban
                arbolNodo.push(new Nodo(token));
                paso++;
                String regla=" T.nodo=new Hoja(id<"+token+">,id.entrada_"+token+")";
                reglasEjecutadas.add("p"+paso+""+regla);
            }else if(token.equals(")")){
                //7. Tratar tokens que no son paréntesis
                    while(!caracter.empty() && !caracter.peek().equals("(")){
                        guardar();
                    }//while
                    caracter.pop();
            }else{
                if(!token.equals("(")&&!caracter.empty()){
                    String exa=(String) caracter.peek();
                    while(!exa.equals("(")&&caracter.empty() && aritmeticos.indexOf(exa)>=aritmeticos.indexOf(token)){
                        guardar();
                        if(!caracter.empty()){
                            exa=(String)caracter.peek();
                        }//if !caracter.empty
                    }//while !exa
                }//if-token
                caracter.push(token);//guardar el token
            }//if else
        }//while-tokenizer-hasMoreTokens
        while(!caracter.empty()){
            if(caracter.peek().equals("(")){//El caracter tiene simbolo de apertura
                caracter.pop();
            }else{
                guardar();//Aqui se insertan los operadores
                raiz=(Nodo)arbolNodo.peek();
            }//if
        }//while caracter.isempty
        return raiz;
    }
}
