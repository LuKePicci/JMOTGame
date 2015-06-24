package it.polimi.ingsw.cg_30.gameclient.view.gui.eventhandlers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class MouseHoverMagnify extends MouseAdapter {

    private int xSize, ySize, addSize;

    public MouseHoverMagnify(int x, int y, int addition) {
        this.xSize = x;
        this.ySize = y;
        this.addSize = addition;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JComponent sender = (JComponent) e.getSource();
        sender.setBorder(BorderFactory.createEmptyBorder(this.ySize
                + this.addSize, this.xSize + this.addSize, this.ySize
                + this.addSize, this.xSize + this.addSize));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JComponent sender = (JComponent) e.getSource();
        sender.setBorder(BorderFactory.createEmptyBorder(this.ySize,
                this.xSize, this.ySize, this.xSize));
    }

}
