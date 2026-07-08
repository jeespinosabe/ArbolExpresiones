package arbolE;

import java.awt.BasicStroke;
import java.awt.Color;
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

    private final int RADIO = 20;
    private final int DIAMETRO = RADIO * 2;
    private final int ESPACIO_VERTICAL = 60;

    private int anchoLineas;
    private int anchoContornoNodos;

    private Color colorLineas;
    private Color colorContornoNodos;
    private Color colorRellenoNodos;

    public PanelArbol(Nodo raiz) {
        this.raiz = raiz;

        anchoLineas = 2;
        anchoContornoNodos = 2;

        colorLineas = Color.BLACK;
        colorContornoNodos = Color.BLUE;
        colorRellenoNodos = new Color(173, 216, 230);

        setBackground(Color.WHITE);
    }

    public void setAnchoLineas(int anchoLineas) {
        this.anchoLineas = anchoLineas;
        repaint();
    }

    public void setAnchoContornoNodos(int anchoContornoNodos) {
        this.anchoContornoNodos = anchoContornoNodos;
        repaint();
    }

    public void setColorLineas(Color colorLineas) {
        this.colorLineas = colorLineas;
        repaint();
    }

    public void setColorContornoNodos(Color colorContornoNodos) {
        this.colorContornoNodos = colorContornoNodos;
        repaint();
    }

    public int getAnchoLineas() {
        return anchoLineas;
    }

    public int getAnchoContornoNodos() {
        return anchoContornoNodos;
    }

    public Color getColorLineas() {
        return colorLineas;
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
        int yInicial = 40;
        int espacioHorizontal = getWidth() / 4;

        dibujarLineas(g2, raiz, xInicial, yInicial, espacioHorizontal);
        dibujarNodos(g2, raiz, xInicial, yInicial, espacioHorizontal);
    }

    private void dibujarLineas(Graphics2D g, Nodo nodo, int x, int y, int espacioHorizontal) {
        if (nodo == null) {
            return;
        }

        int siguienteEspacioHorizontal = Math.max(40, espacioHorizontal / 2);

        g.setColor(colorLineas);
        g.setStroke(new BasicStroke(anchoLineas));

        if (nodo.getIzquierdo() != null) {
            int xIzquierdo = x - espacioHorizontal;
            int yIzquierdo = y + ESPACIO_VERTICAL;

            g.drawLine(x, y, xIzquierdo, yIzquierdo);

            dibujarLineas(
                    g,
                    nodo.getIzquierdo(),
                    xIzquierdo,
                    yIzquierdo,
                    siguienteEspacioHorizontal
            );
        }

        g.setColor(colorLineas);
        g.setStroke(new BasicStroke(anchoLineas));

        if (nodo.getDerecho() != null) {
            int xDerecho = x + espacioHorizontal;
            int yDerecho = y + ESPACIO_VERTICAL;

            g.drawLine(x, y, xDerecho, yDerecho);

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

        int siguienteEspacioHorizontal = Math.max(40, espacioHorizontal / 2);

        if (nodo.getIzquierdo() != null) {
            dibujarNodos(
                    g,
                    nodo.getIzquierdo(),
                    x - espacioHorizontal,
                    y + ESPACIO_VERTICAL,
                    siguienteEspacioHorizontal
            );
        }

        if (nodo.getDerecho() != null) {
            dibujarNodos(
                    g,
                    nodo.getDerecho(),
                    x + espacioHorizontal,
                    y + ESPACIO_VERTICAL,
                    siguienteEspacioHorizontal
            );
        }

        g.setColor(colorRellenoNodos);
        g.fillOval(
                x - RADIO,
                y - RADIO,
                DIAMETRO,
                DIAMETRO
        );

        g.setColor(colorContornoNodos);
        g.setStroke(new BasicStroke(anchoContornoNodos));
        g.drawOval(
                x - RADIO,
                y - RADIO,
                DIAMETRO,
                DIAMETRO
        );

        g.setColor(Color.BLACK);

        FontMetrics fm = g.getFontMetrics();

        int anchoTexto = fm.stringWidth(nodo.getDato());
        int altoTexto = fm.getAscent();

        g.drawString(
                nodo.getDato(),
                x - (anchoTexto / 2),
                y + (altoTexto / 4)
        );
    }//dibujarNodos

}//FIN CLASE