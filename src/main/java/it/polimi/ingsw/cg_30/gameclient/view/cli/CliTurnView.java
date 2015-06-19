package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliTurnView extends View {

    @Override
    public void applyUpdate(ViewModel model) {
        TurnViewModel viewModel = (TurnViewModel) model;
        String situation = "\r\n";

        // must move
        situation += viewModel.mustMove() ? " - You can cross "
                + viewModel.getMaxSteps() + " sector.\r\n"
        // can attack
                : (viewModel.canAttack() ? " - You can attack.\r\n" : "");

        // must discard
        situation += viewModel.mustDiscard() ? " - You must discard (or use) at least one Item Card before ending your turn.\r\n"
                : "";

        // silenced forced
        situation += viewModel.isSilenceForced() ? " - You won't draw a Sector Card if your movement ends on a dangerous sector.\r\n"
                : "";

        // drawn card
        situation += viewModel.getDrawnCard() != null ? "\r\nChoose where to make the noise."
                : "";

        CliEngine.printLineToCli(situation);
    }
}
