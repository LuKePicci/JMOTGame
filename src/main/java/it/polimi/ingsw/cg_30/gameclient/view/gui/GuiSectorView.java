package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JSector;

import javax.swing.JComponent;
import javax.swing.Timer;

public class GuiSectorView extends GuiView {

    GuiZoneView gzv;
    Timer highlightTimer;

    public GuiSectorView(GuiView guiView) {
        this.gzv = (GuiZoneView) guiView;
        this.highlightTimer = new Timer(500,
                PlayerLocationHighlighter.getPlayerLocationHighlighter());
    }

    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void createComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void applyUpdate(ViewModel model) {

        SectorViewModel viewModel = (SectorViewModel) model; // jsec I want to
                                                             // modify.
        JSector jsec = gzv.getJSector(viewModel.getSector().getPoint());
        if (jsec == null)
            return;
        // SectorType.ESCAPE_HATCH.equals(jsec.getType() condizione superflua
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
