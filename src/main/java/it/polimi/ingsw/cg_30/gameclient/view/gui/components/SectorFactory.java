package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.gameclient.view.View;
import it.polimi.ingsw.cg_30.gameclient.view.gui.GameView;
import it.polimi.ingsw.cg_30.gameclient.view.gui.GuiEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class SectorFactory {

    private SectorFactory() {
    }

    public static JSector createGridSector(HexPoint hp) {
        return createGridSector(hp, GameView.SECTOR_WIDTH);
    }

    /**
     * Creates a new JSector with its location setted on the corresponding
     * position as if placed in a hexagonal grid with all other hex of the same
     * width.
     *
     * @param i
     *            the horizontal index
     * @param j
     *            the vertical index
     * @param width
     *            the hexagon width
     * @return the new JSector component
     */
    public static JSector createGridSector(HexPoint hp, int width) {
        int height = (int) Math.round(width * Math.sqrt(3) / 2);
        int horiz = Math.round((width * 3) / 4f) + 2;
        final JSector sector = new JSector(hp, width / 2, height / 2 + 1,
                width / 2);
        sector.setSize(width, height);
        sector.setLocation(horiz * hp.getOffsetX(), height * hp.getOffsetY()
                + ((hp.getOffsetX() % 2) * height / 2));

        @SuppressWarnings("serial")
        JLabel label = new JLabel(View.getCharFromNumber(hp.getOffsetX() + 1)
                + String.format("%02d", hp.getOffsetY() + 1)) {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };
        label.setFont(GuiEngine.loadCustomFont("TitilliumText22L").deriveFont(
                0, GameView.SECTOR_WIDTH / 3f));

        label.setSize(width, height);
        label.setHorizontalAlignment(JLabel.CENTER);

        sector.add(label);

        MouseAdapter ma = new MouseAdapter() {
            Color c;

            @Override
            public void mouseEntered(MouseEvent e) {
                JComponent sender = (JComponent) e.getSource();
                c = sender.getForeground();
                sender.setForeground(new Color(c.getRed(), c.getGreen(), c
                        .getBlue(), c.getAlpha() - 20));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JSector sender = (JSector) e.getSource();
                sender.setForeground(JSector.ColorMap.get(sender.getType()));
            }
        };
        sector.addMouseListener(ma);
        return sector;
    }
}
