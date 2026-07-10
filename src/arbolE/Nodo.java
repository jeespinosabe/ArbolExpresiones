/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

/**
 *
 * @author jesus
 * Parte 1 
 * Parte 2.
 * Parte 3. Código intermedio
 * Parte 4. Código objeto
 */
public class Nodo {
    //Atributos
    private String dato;
    private String valor;
    private Nodo padre;
    private Nodo izquierdo;
    private Nodo derecho;
    private String codigoIntermedio;
    private String lugar; //para los temporales
    
    public Nodo(String dato) {//Información
        this.dato = dato;
        this.valor = "";
        this.padre = null;
        this.izquierdo = null;
        this.derecho = null;
        this.codigoIntermedio = "";
        this.lugar = "";
    }
    
    public Nodo(String dato, String valor) {//Información con valor semántico
        this.dato=dato;
        this.valor=valor;
        this.padre=null;
        this.izquierdo=null;
        this.derecho=null;
        this.codigoIntermedio="";
        this.lugar="";
    }
    
    public Nodo(Nodo derecho, String dato, Nodo izquierdo) {
        this.derecho = derecho;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.padre = null;
        this.valor = "";
        this.codigoIntermedio = "";
        this.lugar = "";
        
        if (this.izquierdo != null) {
            this.izquierdo.setPadre(this);
        }
        
        if (this.derecho != null) {
            this.derecho.setPadre(this);
        }
    }//Constructor

    public String getDato() {
        return dato;
    }

    public String getValor() {
        return valor;
    }

    public Nodo getPadre() {
        return padre;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public String getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public String getLugar() {
        return lugar;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public void setCodigoIntermedio(String codigoIntermedio) {
        this.codigoIntermedio = codigoIntermedio;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}