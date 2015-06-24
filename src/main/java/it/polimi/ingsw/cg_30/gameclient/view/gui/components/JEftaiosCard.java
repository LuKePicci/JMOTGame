package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JEftaiosCard extends JLabel {

    private static final long serialVersionUID = -6490352724948950375L;

    Item itemType;
    ImageIcon imageIcon;

    public JEftaiosCard(Item type, ImageIcon icon) {
        super();
        this.imageIcon = icon;
        this.itemType = type;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public Item getItemType() {
        return this.itemType;
    }
}
