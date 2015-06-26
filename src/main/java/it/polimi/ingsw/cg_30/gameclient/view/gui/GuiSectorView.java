package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JSector;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * The Class GuiSectorView.
 */
public class GuiSectorView extends GuiView {

    /** The gui zone view. */
    GuiZoneView gzv;

    /** The highlight timer. */
    Timer highlightTimer;

    /**
     * Instantiates a new gui sector view.
     *
     * @param guiView
     *            the gui view
     */
    public GuiSectorView(GuiView guiView) {
        this.gzv = (GuiZoneView) guiView;
        this.highlightTimer = new Timer(500,
                PlayerLocationHighlighter.getPlayerLocationHighlighter());
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#getComponent()
     */
    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#createComponents()
     */
    @Override
    protected void createComponents() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.View#applyUpdate(it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel)
     */
    @Override
    public void applyUpdate(ViewModel model) {
        // jsec I want to modify.
        SectorViewModel viewModel = (SectorViewModel) model;
        JSector jsec = gzv.getJSector(viewModel.getSector().getPoint());
        if (jsec == null)
            return;
        if (SectorHighlight.HATCH_LOCKED.equals(viewModel.getHighlight())) {
            // hatch sector removed
            jsec.setType(SectorType.SECURE);
        } else { // new player's location
            highlightTimer.stop();
            PlayerLocationHighlighter.getPlayerLocationHighlighter()
                    .updateLocation(jsec);
            highlightTimer.start();
        }
    }

}
