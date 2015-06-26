package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JEftaiosGeneralCard extends JLabel {

    private static final long serialVersionUID = -8145903647621819483L;

    ImageIcon imageIcon;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void setIcon(Icon icon) {
        this.imageIcon = (ImageIcon) icon;
        super.setIcon(null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.imageIcon.getIconWidth(),
                this.imageIcon.getIconHeight());
    }

}
