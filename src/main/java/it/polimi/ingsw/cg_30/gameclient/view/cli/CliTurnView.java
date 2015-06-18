package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliTurnView extends View {

    @Override
    public void applyUpdate(ViewModel model) {
        TurnViewModel viewModel = (TurnViewModel) model;
        // must move
        if (viewModel.mustMove()) {
            CliEngine.printLineToCli("You can cross " + viewModel.getMaxSteps()
                    + "sectors.");
        } else if (viewModel.canAttack()) { // can attack
            CliEngine.printLineToCli("You can attack.");
        }
        // must discard
        if (viewModel.mustDiscard()) {
            CliEngine
                    .printLineToCli("You must discard (or use if you can) at least one Item Card before ending your turn.");
        }
        // silenced forced
        if (viewModel.isSilenceForced()) {
            CliEngine
                    .printLineToCli("You will not draw a Sector Card if you end your movement on a dangerous sector.");
        }
        // drawn card
        if (viewModel.getDrawnCard() != null) {
            CliEngine.printLineToCli("Choose where to make the noise.");
        }
    }

}
