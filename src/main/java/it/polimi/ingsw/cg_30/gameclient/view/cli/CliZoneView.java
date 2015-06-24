package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliZoneView.
 */
public class CliZoneView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ZoneViewModel viewModel = (ZoneViewModel) model;
        String textZone = "%nThis map contains the following sectors:%n";
        for (Sector sec : viewModel.getSectorsMap().values()) {
            textZone += this.getSectorRep(sec);
        }
        CliEngine.printLineToCli(textZone);
    }

    /**
     * Gets the sector coordinates and type.
     *
     * @param sec
     *            the sector
     * @return the sector coordinates and type
     */
    private String getSectorRep(Sector sec) {
        String rep = getCharFromNumber(sec.getPoint().getOffsetX() + 1)
                + String.format("%02d", sec.getPoint().getOffsetY() + 1);
        switch (sec.getType()) {
            case DANGEROUS:
                rep += " dangerous";
                break;
            case SECURE:
                rep += " secure";
                break;
            case ALIENS_START:
                rep += " aliens' start";
                break;
            case HUMANS_START:
                rep += " humans' start";
                break;
            case ESCAPE_HATCH:
                rep += " escape hatch";
                break;
            case EMPTY:
                rep += " empty";
                break;
            default:
                rep += " error";
        }
        return rep + "%n";
    }

}
