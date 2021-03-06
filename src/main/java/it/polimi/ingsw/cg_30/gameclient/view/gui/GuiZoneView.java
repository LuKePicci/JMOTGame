package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JSector;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.SectorFactory;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The Class GuiZoneView.
 */
public class GuiZoneView extends GuiView {

    /** The zone panel. */
    private JPanel zonePane;

    /** The JSectors. */
    private final Map<HexPoint, JSector> jSectors = new HashMap<HexPoint, JSector>();

    /**
     * Gets the JSector from the hexPoint hex.
     *
     * @param hex
     *            the hex point
     * @return the JSector
     */
    public JSector getJSector(HexPoint hex) {
        return this.jSectors.get(hex);
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#getComponent()
     */
    @Override
    public JPanel getComponent() {
        if (this.zonePane == null)
            this.createComponents();
        return this.zonePane;
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#createComponents()
     */
    @Override
    protected void createComponents() {
        this.zonePane = new JPanel();
        this.zonePane.setLayout(null);
        this.zonePane.setPreferredSize(GameView.MAP_SIZE);
        this.zonePane.setCursor(Cursor
                .getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.View#applyUpdate(it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel)
     */
    @Override
    public void applyUpdate(ViewModel model) {
        Map<HexPoint, Sector> sectors = ((ZoneViewModel) model).getSectorsMap();

        for (Map.Entry<HexPoint, Sector> e : sectors.entrySet()) {
            JSector js = SectorFactory.createGridSector(e.getKey());
            this.jSectors.put(e.getKey(), js);
            js.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    super.mouseClicked(event);
                    final JSector sender = (JSector) event.getSource();
                    if (sender.contains(event.getPoint())
                            && GameClient.getActiveEngine() instanceof GuiEngine) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                GuiEngine activeEngine = (GuiEngine) GameClient
                                        .getActiveEngine();
                                activeEngine.sectorProcessor(sender
                                        .getHexPoint());
                            }
                        });

                    }
                }
            });
            js.setType(e.getValue().getType());
            this.zonePane.add(js);
        }

        this.zonePane.repaint();
        this.zonePane.revalidate();
    }
}
