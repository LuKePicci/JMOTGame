package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliSectorView extends View {

    @Override
    public void applyUpdate(ViewModel model) {
        SectorViewModel viewModel = (SectorViewModel) model;
        switch (viewModel.getHighlight()) {
            case PLAYER_LOCATION:
                CliEngine.printLineToCli("You are in "
                        + this.getCharFromNumber(viewModel.getSector()
                                .getPoint().getOffsetX())
                        + viewModel.getSector().getPoint().getOffsetY() + 1
                        + ".");
                break;
            case HATCH_LOCKED:
                CliEngine.printLineToCli("Hatch in "
                        + this.getCharFromNumber(viewModel.getSector()
                                .getPoint().getOffsetX())
                        + viewModel.getSector().getPoint().getOffsetY() + 1
                        + " is now locked.");
                break;
        }
    }

}
