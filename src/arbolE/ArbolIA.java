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
public class ArbolIA {

    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;
    //Identidicar entre operador y operandos
    final String espacios = "\t";
    final String aritmeticos = "+-*()^=/";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    final String opMultiplica = "*";
    private Nodo raiz;
    String r;
    String reglaSemantica;
    //30 de junio
    String[] temporales = {"T1", "T2", "T3", "T4", "T5"};

    HashMap<String, String> tablaSimbolos;
    HashMap<String, String> erroresSemanticos;
    HashMap<String, String> producciones;

    int paso;

    //1ro de Julio
    ArrayList<String> reglasEjecutadas;

    //Constructor
    public ArbolIA() {
        reglasEjecutadas = new ArrayList<String>(); //1ro de Julio
        tablaSimbolos = new HashMap();
        erroresSemanticos = new HashMap();
        producciones = new HashMap();

        arbolNodo = new Stack<Nodo>();
        caracter = new Stack<String>();
        r = "";
        reglaSemantica = "";
        paso = 0;
    }//Fin del constructor
    //*********REGLAS EJECUTADAS 1ro de Julio

    public String getReglasEjecutadas() {
        String reglasE = "";
        for (int i = 0; i < reglasEjecutadas.size(); i++) {
            System.out.println("Reglas ejecutadas " + reglasEjecutadas.get(i));
            reglasE += reglasEjecutadas.get(i) + "\n";
        }//for
        return reglasE;
    }

    public void agregaValex(String lexema, String valor) {

    }//agregaValex - Análisis semántico

    public String regresaValex(String lexema) {
        return this.tablaSimbolos.get(lexema);
    }//regresaValex

    private void guardar() {
        if (arbolNodo.size() < 2 || caracter.empty()) {
            return;
        }

        paso++;
        r = "r" + paso;

        Nodo izquierdo = arbolNodo.pop();
        Nodo derecho = arbolNodo.pop();
        String operador = caracter.pop();

        // Reconstrucción del subárbol
        arbolNodo.push(new Nodo(derecho, operador, izquierdo));

        // Optimización: En una sola línea dinámica
        String reglaE = "E.nodo = new Nodo(" + operador + ",E1.nodo,T.nodo)";
        reglasEjecutadas.add("p" + paso + " " + reglaE);
    }// guardar

    //METODOS DEL ARBOL
    public Nodo crear(String expresion) {
        //1. Considerar la expresión como un conjunto de tokens
        //StringTokenizer es una clase que sirve para dividir una cadena de texto
        //en partes, usando separadores llamados delimitadores
        StringTokenizer tokenizer;
        String token;
        paso = 0;
        reglaSemantica = "";
        r = "";
        //2. Separación de tokens de la expresión
        tokenizer = new StringTokenizer(expresion, espacios + aritmeticos + "/", true);
        //3. Mientras existaan tokens
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            if (espacios.contains(token)) {
                continue;	//Es un espacio
            }
            if (aritmeticos.indexOf(token) < 0) {
                // Si no es un operador (es un ID o Número)
                arbolNodo.push(new Nodo(token));
                paso++;

                String regla = "T.nodo = new Hoja(id<" + token + ">,id.entrada_" + token + ")";
                reglasEjecutadas.add("p" + paso + " " + regla);

                //insertaSimbolos(token);
            } else if (token.equals("(")) {
                caracter.push(token);
            } else if (token.equals(")")) {
                while (!caracter.empty() && !caracter.peek().equals("(")) {
                    guardar();
                }

                if (!caracter.empty()) {
                    caracter.pop(); // Quita el "("
                }
            } else { // Es un operador (+, -, *, /, ^, =)
                while (!caracter.empty() && !caracter.peek().equals("(")) {
                    // Si el operador en el tope tiene mayor o igual prioridad, se procesa primero
                    if (obtenerPrioridad(caracter.peek()) >= obtenerPrioridad(token)) {
                        guardar();
                    } else {
                        break;
                    }
                }//while

                caracter.push(token); // Guardar el operador actual
            }
        }//while tokenizer
        while (!caracter.empty()) {
            if (caracter.peek().equals("(")) {//El caracter tiene simbolo de apertura
                caracter.pop();
            } else {
                guardar();//Aqui se insertan los operadores
                raiz = (Nodo) arbolNodo.peek();
            }//if
        }//while caracter.isempty
        return raiz;
    }

    private int obtenerPrioridad(String operador) {
        switch (operador) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            case "=":
                return 0;
            default:
                return -1; // Para paréntesis u otros caracteres
        }//switch
    }//obtenerPrioridad
}
