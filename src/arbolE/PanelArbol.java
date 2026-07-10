package arbolE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Jesus
 */
public class PanelArbol extends JPanel {

    private final Nodo raiz;

    private int radioNodo;
    private final int ESPACIO_VERTICAL_MINIMO = 80;

    private int anchoLineas;

    private Color colorLineas;
    private Color colorContornoNodos;
    private Color colorRellenoNodos;

    public PanelArbol(Nodo raiz) {
        this.raiz = raiz;

        radioNodo = 20;
        anchoLineas = 2;

        colorLineas = Color.BLACK;
        colorContornoNodos = Color.BLACK;
        colorRellenoNodos = new Color(173, 216, 230);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 600));
    }

    public void setAnchoLineas(int anchoLineas) {
        this.anchoLineas = anchoLineas;
        repaint();
    }

    public void setRadioNodo(int radioNodo) {
        this.radioNodo = radioNodo;
        repaint();
    }

    public void setColorLineas(Color colorLineas) {
        this.colorLineas = colorLineas;
        this.colorContornoNodos = colorLineas;
        repaint();
    }

    public void setColorRellenoNodos(Color colorRellenoNodos) {
        this.colorRellenoNodos = colorRellenoNodos;
        repaint();
    }

    public int getAnchoLineas() {
        return anchoLineas;
    }

    public int getRadioNodo() {
        return radioNodo;
    }

    public Color getColorLineas() {
        return colorLineas;
    }

    public Color getColorRellenoNodos() {
        return colorRellenoNodos;
    }

    // Métodos conservados por compatibilidad con código anterior.
    public void setAnchoContornoNodos(int anchoContornoNodos) {
        setAnchoLineas(anchoContornoNodos);
    }

    public void setColorContornoNodos(Color colorContornoNodos) {
        setColorLineas(colorContornoNodos);
    }

    public int getAnchoContornoNodos() {
        return anchoLineas;
    }

    public Color getColorContornoNodos() {
        return colorContornoNodos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (raiz == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int xInicial = getWidth() / 2;
        int yInicial = 60;
        int espacioHorizontal = getWidth() / 4;

        dibujarLineas(g2, raiz, xInicial, yInicial, espacioHorizontal);
        dibujarNodos(g2, raiz, xInicial, yInicial, espacioHorizontal);
    }

    private void dibujarLineas(Graphics2D g, Nodo nodo, int x, int y, int espacioHorizontal) {
        if (nodo == null) {
            return;
        }

        int siguienteEspacioHorizontal = Math.max(obtenerAnchoNodo() + 20, espacioHorizontal / 2);
        int espacioVertical = obtenerEspacioVertical();
        int medioAltoNodo = obtenerAltoNodo() / 2;

        g.setColor(colorLineas);
        g.setStroke(new BasicStroke(anchoLineas));

        if (nodo.getIzquierdo() != null) {
            int xIzquierdo = x - espacioHorizontal;
            int yIzquierdo = y + espacioVertical;

            g.drawLine(x, y + medioAltoNodo, xIzquierdo, yIzquierdo - medioAltoNodo);

            dibujarLineas(
                    g,
                    nodo.getIzquierdo(),
                    xIzquierdo,
                    yIzquierdo,
                    siguienteEspacioHorizontal
            );
        }

        if (nodo.getDerecho() != null) {
            int xDerecho = x + espacioHorizontal;
            int yDerecho = y + espacioVertical;

            g.drawLine(x, y + medioAltoNodo, xDerecho, yDerecho - medioAltoNodo);

            dibujarLineas(
                    g,
                    nodo.getDerecho(),
                    xDerecho,
                    yDerecho,
                    siguienteEspacioHorizontal
            );
        }
    }//dibujarLineas

    private void dibujarNodos(Graphics2D g, Nodo nodo, int x, int y, int espacioHorizontal) {
        if (nodo == null) {
            return;
        }

        int siguienteEspacioHorizontal = Math.max(obtenerAnchoNodo() + 20, espacioHorizontal / 2);
        int espacioVertical = obtenerEspacioVertical();

        if (nodo.getIzquierdo() != null) {
            dibujarNodos(
                    g,
                    nodo.getIzquierdo(),
                    x - espacioHorizontal,
                    y + espacioVertical,
                    siguienteEspacioHorizontal
            );
        }

        if (nodo.getDerecho() != null) {
            dibujarNodos(
                    g,
                    nodo.getDerecho(),
                    x + espacioHorizontal,
                    y + espacioVertical,
                    siguienteEspacioHorizontal
            );
        }

        dibujarNodoRectangular(g, nodo, x, y);
    }//dibujarNodos

    private void dibujarNodoRectangular(Graphics2D g, Nodo nodo, int x, int y) {
        int anchoCelda = obtenerAnchoCelda();
        int altoNodo = obtenerAltoNodo();
        int anchoNodo = obtenerAnchoNodo();

        int xInicial = x - (anchoNodo / 2);
        int yInicial = y - (altoNodo / 2);

        // Cuadro izquierdo: dato/caracter del nodo.
        g.setColor(colorRellenoNodos);
        g.fillRect(xInicial, yInicial, anchoCelda, altoNodo);

        // Cuadro derecho: valor del nodo.
        g.setColor(Color.WHITE);
        g.fillRect(xInicial + anchoCelda, yInicial, anchoCelda, altoNodo);

        // Contorno general del nodo.
        g.setColor(colorContornoNodos);
        g.setStroke(new BasicStroke(anchoLineas));
        g.drawRect(xInicial, yInicial, anchoNodo, altoNodo);

        // Línea que separa el caracter y el valor.
        g.drawLine(
                xInicial + anchoCelda,
                yInicial,
                xInicial + anchoCelda,
                yInicial + altoNodo
        );

        // Textos.
        g.setColor(Color.BLACK);
        dibujarTextoCentrado(g,nodo.getDato(),xInicial,yInicial,anchoCelda,altoNodo);

        dibujarTextoCentrado(g,nodo.getValor(),xInicial + anchoCelda,yInicial,anchoCelda,altoNodo);
    }//dibujarNodoRectangular

    private void dibujarTextoCentrado(Graphics2D g, String texto, int x, int y, int ancho, int alto) {
        if (texto == null) {
            texto = "";
        }
        FontMetrics fm = g.getFontMetrics();
        int anchoTexto = fm.stringWidth(texto);
        int xTexto = x + ((ancho - anchoTexto) / 2);
        int yTexto = y + ((alto - fm.getHeight()) / 2) + fm.getAscent();

        g.drawString(texto, xTexto, yTexto);
    }//dibujarTextoCentrado

    private int obtenerAnchoCelda() {
        return radioNodo*2;
    }//obtenerAnchoCelda

    private int obtenerAnchoNodo() {
        return obtenerAnchoCelda() * 2;
    }//obtenerAnchoNodo

    private int obtenerAltoNodo() {
        return radioNodo * 2;
    }//obtenerAltoNodo

    private int obtenerEspacioVertical() {
        return Math.max(ESPACIO_VERTICAL_MINIMO, obtenerAltoNodo()+40);
    }//obtenerEspacioVertical

}//FIN CLASE