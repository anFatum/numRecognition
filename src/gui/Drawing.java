package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Drawing extends JPanel implements MouseListener, MouseMotionListener{

    Color color = Color.black;
    private boolean[][] grid;

    public Drawing() {
        this.addMouseMotionListener(this);
        clearGrid();
        setSize(new Dimension(396, 396));
        setMinimumSize(new Dimension(396, 396));
        setMaximumSize(new Dimension(396, 396));
        setPreferredSize(new Dimension(396, 396));

    }

    public boolean[][] getGrid() {
        return this.grid;
    }

    private void clearGrid() {
        grid = new boolean[32][];
        for (int i=0; i<32; i++) {
            grid[i] = new boolean[32];
            for (int j=0; j<32; j++) {
                grid[i][j] = false;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        grawGrid(g);
    }

    private void grawGrid(Graphics g) {
        g.setColor(color);
        for (int row=0; row<32; row++) {
            for (int col=0; col<32; col++) {
                if (grid[row][col] == true) {
                    for (int i=0; i<12; i++) {
                        g.drawLine(2 + row * 12, 2 + col * 12 + i, 13 + row * 12, 2 + col * 12 + i);
                    }
                }
            }
        }
    }

    public void clear() {
        clearGrid();
        this.repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        int dx = (x - 2) / 12;
        int dy = (y - 2) / 12;
        if (dx<0 || dx>=32 || dy<0 || dy>=32) {
            return;
        }

        safeSet(dx, dy);
        safeSet(dx - 1 , dy - 1);
        safeSet(dx , dy - 1);
        safeSet(dx - 1 , dy);

        this.repaint();
    }

    private void safeSet(int dx, int dy) {
        if (dx<0 || dx>=32 || dy<0 || dy>=32) {
            return;
        }
        grid[dx][dy] = true;
    }

    public void mouseMoved(MouseEvent arg0) {

    }

}
