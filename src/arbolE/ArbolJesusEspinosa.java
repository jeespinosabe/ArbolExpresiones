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
public class ArbolJesusEspinosa {
    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;

    //Identificar
    final String espacios = "\t";
    final String aritmeticos = "+-*()^=/";

    private Nodo raiz;

    HashMap<String, String> tablaSimbolos;
    HashMap<String, String> erroresSemanticos;
    HashMap<String, String> producciones;

    int paso;

    ArrayList<String> reglasEjecutadas;

    //constructor
    public ArbolJesusEspinosa() {
        reglasEjecutadas = new ArrayList<String>();
        tablaSimbolos = new HashMap<String, String>();
        erroresSemanticos = new HashMap<String, String>();
        producciones = new HashMap<String, String>();
        arbolNodo = new Stack<Nodo>();
        caracter = new Stack<String>();
        paso = 0;
    }//fin constructor

    public String getReglasEjecutadas() {
        String reglasE = "";
        for (int i = 0; i < reglasEjecutadas.size(); i++) {
            System.out.println("Reglas ejecutadas " + reglasEjecutadas.get(i));
            reglasE += reglasEjecutadas.get(i) + "\n";
        }
        return reglasE;
    }

    public void agregaValex(String lexema, String valor) {
        tablaSimbolos.put(lexema, valor);
    }

    public String regresaValex(String lexema) {
        return tablaSimbolos.get(lexema);
    }

    public void guardar() {
        paso++;
        Nodo derecho = arbolNodo.pop();
        Nodo izquierdo = arbolNodo.pop();
        String operador = caracter.pop();

        arbolNodo.push(new Nodo(derecho, operador, izquierdo));
        if (operador.equals("+")) {
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(+,E1.nodo,T.nodo)");
        }
        if (operador.equals("-")) {
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(-,E1.nodo,T.nodo)");
        }
        if (operador.equals("*")) {
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(*,E1.nodo,T.nodo)");
        }
        if (operador.equals("/")) {
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(/,E1.nodo,T.nodo)");
        }
        if (operador.equals("^")) {
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(^,E1.nodo,T.nodo)");
        }
        if (operador.equals("=")){
            reglasEjecutadas.add("p" + paso + " E.nodo=new Nodo(=,E1.nodo,T.nodo)");
        }
    }//fin guardar

    //Metodos del árbol
    public Nodo crear(String expresion) {
        StringTokenizer tokenizer = new StringTokenizer(expresion, espacios + aritmeticos, true);
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            System.out.println("Token " + token);
            if (espacios.contains(token)) {
                System.out.println("Se trata de un espacio");
            } else if (!aritmeticos.contains(token)){
                arbolNodo.push(new Nodo(token));
                paso++;
                reglasEjecutadas.add("p" + paso + " T.nodo=new Hoja(id<" + token + ">,id.entrada_" + token + ")");
            } else if (token.equals(")")) {
                while (!caracter.empty() && !caracter.peek().equals("(")) {
                    guardar();
                }
                if (!caracter.empty()) {
                    caracter.pop();
                }
            } else {
                if (!token.equals("(") && !caracter.empty()) {
                    String exa = caracter.peek();
                    while (!caracter.empty() && !exa.equals("(") && prioridad(exa) >= prioridad(token)) {
                        guardar();
                        if (!caracter.empty()) {
                            exa = caracter.peek();
                        }//gin if !caracter.empty
                    }
                }//fin if
                caracter.push(token);
            }//fin if else
        }//fin while tokenizer
        while (!caracter.empty()) {
            if (caracter.peek().equals("(")) {
                caracter.pop();
            } else {
                guardar();
                raiz = arbolNodo.peek();
            }
        }
        return raiz;
    }//fin crear

    private int prioridad(String operador) {
        if (operador.equals("=")) {
            return 0;
        }
        if (operador.equals("+") || operador.equals("-")) {
            return 1;
        }
        if (operador.equals("*") || operador.equals("/")) {
            return 2;
        }
        if (operador.equals("^")) {
            return 3;
        }
        return -1;
    } 
}