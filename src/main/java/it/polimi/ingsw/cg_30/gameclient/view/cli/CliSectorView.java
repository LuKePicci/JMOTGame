package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliSectorView.
 */
public class CliSectorView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public void applyUpdate(ViewModel model) {
        SectorViewModel viewModel = (SectorViewModel) model;
        if (viewModel.getSector().getPoint().getOffsetX() != 26) {
            String sectorRep = getCharFromNumber(viewModel.getSector()
                    .getPoint().getOffsetX() + 1)
                    + String.format("%02d", viewModel.getSector().getPoint()
                            .getOffsetY() + 1);

            switch (viewModel.getHighlight()) {
                case PLAYER_LOCATION:
                    CliEngine.printLineToCli("\r\nYou are in " + sectorRep
                            + ".");
                    break;
                case HATCH_LOCKED:
                    CliEngine.printLineToCli("\r\nHatch in " + sectorRep
                            + " is locked now.");
                    break;
                case SPOTTED:
                    CliEngine.printLineToCli("\r\nPlayer spotted in "
                            + sectorRep + "!");
                    break;
                default:
                    CliEngine
                            .printLineToCli("\r\nWrong sector notification received ("
                                    + sectorRep + ").");
                    break;
            }
        }
    }

}
