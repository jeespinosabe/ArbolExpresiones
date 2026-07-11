/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import static javax.swing.JOptionPane.*;

/**
 *
 * @author jesus
 */
public class ArbolIA {

    //Atributos
    Stack<Nodo> arbolNodo;
    Stack<String> caracter;

    //Identidicar entre operador y operandos
    final String espacios = " \t";
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
        reglasEjecutadas = new ArrayList<String>();
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

        System.out.println("======= Tabla de Simbolos =======");
        System.out.print(reglaSemantica);

        return reglasE;
    }

    public void agregaValex(String lexema, String valor) {
        tablaSimbolos.put(lexema, valor);
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

        Nodo derecho = arbolNodo.pop();
        Nodo izquierdo = arbolNodo.pop();
        String operador = caracter.pop();

        //reconstruccion
        Nodo nuevoNodo = new Nodo(derecho, operador, izquierdo);

        //valor calculado del operando
        nuevoNodo.setValor(calcularValor(operador, izquierdo.getValor(), derecho.getValor()));

        arbolNodo.push(nuevoNodo);

        String reglaE = "E.nodo = new Nodo(" + operador + ",E1.nodo,T.nodo)";
        reglasEjecutadas.add("p" + paso + " " + reglaE);
    }//guardar

    //METODOS DEL ARBOL
    public Nodo crear(String expresion) {
        StringTokenizer tokenizer;
        String token;

        paso = 0;
        reglaSemantica = "";
        r = "";

        tokenizer = new StringTokenizer(expresion, espacios + aritmeticos + "/", true);

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            if (espacios.contains(token)) {
                continue;
            }

            if (!aritmeticos.contains(token)) {
                paso++;

                String regla = "T.nodo = new Hoja(id<" + token + ">,id.entrada_" + token + ")";
                reglasEjecutadas.add("p" + paso + " " + regla);

                //=======SOLICITAR EL VALOR DEL TOKEN e insertar en TablaSimbolos
                //1. Solicitar el valor para el token
                String valorToken = regresaValex(token);
                if (valorToken == null) {
                    //si el token ya es un número, se usa como su propio valor
                    if (token.matches("\\d+")) {
                        valorToken = token;
                    } else {
                        // Si no es un número, se solicita su valor
                        boolean valorValido = false;
                        while (!valorValido) {
                            try {
                                valorToken = showInputDialog(null,"Ingresa el valor entero para el token: " + token);

                                if (valorToken == null || valorToken.trim().isEmpty()) {
                                    valorToken = "0";
                                }
                                valorToken = valorToken.trim();

                                // Aquí se valida que el valor sea entero
                                Integer.parseInt(valorToken);
                                valorValido = true;
                            } catch (NumberFormatException e) {
                                showMessageDialog(null,"Ingresa solo números enteros","Incorrecto",ERROR_MESSAGE);
                            }
                        }
                    }
                }
                //2.Insertar en tablaSimbolo
                agregaValex(token, valorToken);
                //aquí se crea la hoja con su caracter y su valor.
                arbolNodo.push(new Nodo(token, valorToken));

                //3.mostrar en consola al finalizar en getReglasEjecutadas
                if (!reglaSemantica.contains("Token: " + token + " |")) {
                    reglaSemantica += "Token: " + token + " | Valor: " + regresaValex(token) + "\n";
                }

            } else if (token.equals("(")) {
                caracter.push(token);

            } else if (token.equals(")")) {
                while (!caracter.empty() && !caracter.peek().equals("(")) {
                    guardar();
                }

                if (!caracter.empty()) {
                    caracter.pop();
                }

            } else {
                while (!caracter.empty() && !caracter.peek().equals("(")) {
                    if (obtenerPrioridad(caracter.peek()) >= obtenerPrioridad(token)) {
                        guardar();
                    } else {
                        break;
                    }
                }

                caracter.push(token);
            }
        }//while tokenizer

        while (!caracter.empty()) {
            if (caracter.peek().equals("(")) {
                caracter.pop();
            } else {
                guardar();
                raiz = arbolNodo.peek();
            }
        }

        if (!arbolNodo.empty()) {
            raiz = arbolNodo.peek();
        }

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
                return -1;
        }
    }//obtenerPrioridad

    private String calcularValor(String operador, String valorIzquierdo, String valorDerecho) {
        try {
            double izquierdo = Double.parseDouble(valorIzquierdo);
            double derecho = Double.parseDouble(valorDerecho);
            double resultado;

            switch (operador) {
                case "+":
                    resultado = izquierdo + derecho;
                    break;

                case "-":
                    resultado = izquierdo - derecho;
                    break;

                case "*":
                    resultado = izquierdo * derecho;
                    break;

                case "/":
                    if (derecho == 0) {
                        return "indefinido";
                    }
                    resultado = izquierdo / derecho;
                    break;

                case "^":
                    resultado = Math.pow(izquierdo, derecho);
                    break;

                case "=":
                    resultado = derecho;
                    break;

                default:
                    return "";
            }

            if (resultado == Math.rint(resultado)) {
                return String.valueOf((int) resultado);
            }

            return String.valueOf(resultado);

        } catch (NumberFormatException e) {
            return "";
        }
    }//calcularValor
}
