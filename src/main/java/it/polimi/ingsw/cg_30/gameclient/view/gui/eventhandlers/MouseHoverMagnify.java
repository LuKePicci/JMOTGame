package it.polimi.ingsw.cg_30.gameclient.view.gui.eventhandlers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class MouseHoverMagnify extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        JComponent sender = (JComponent) e.getSource();
        sender.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JComponent sender = (JComponent) e.getSource();
        sender.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

}
