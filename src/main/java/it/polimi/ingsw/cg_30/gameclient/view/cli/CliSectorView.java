package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliSectorView extends View {

    @Override
    public void applyUpdate(ViewModel model) {
        SectorViewModel viewModel = (SectorViewModel) model;

        String sectorRep = this.getCharFromNumber(viewModel.getSector()
                .getPoint().getOffsetX() + 1)
                + String.format("%02d", (viewModel.getSector().getPoint()
                        .getOffsetY() + 1));

        switch (viewModel.getHighlight()) {
            case PLAYER_LOCATION:
                CliEngine.printLineToCli("You are in " + sectorRep + ".");
                break;
            case HATCH_LOCKED:
                CliEngine.printLineToCli("Hatch in " + sectorRep
                        + " is now locked.");
                break;
            case SPOTTED:
                CliEngine
                        .printLineToCli("Player spotted in " + sectorRep + "!");
                break;
        }
    }

}
