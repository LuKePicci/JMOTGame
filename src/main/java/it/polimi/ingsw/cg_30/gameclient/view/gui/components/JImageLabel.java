package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JImageLabel extends JLabel {

    private static final long serialVersionUID = 7652584895090307271L;

    ImageIcon imageIcon;

    public JImageLabel(ImageIcon icon) {
        super();
        this.imageIcon = icon;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
