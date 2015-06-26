package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class JAntiAliasedLabel extends JLabel {

    private static final long serialVersionUID = 5680327383311952573L;

    public JAntiAliasedLabel() {
    }

    public JAntiAliasedLabel(final String pText) {
        super(pText);
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        Graphics2D graphics2d = (Graphics2D) pG;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(pG);
    }
}
