package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliTurnView.
 */
public class CliTurnView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public void applyUpdate(ViewModel model) {
        TurnViewModel viewModel = (TurnViewModel) model;
        String situation = "%n";

        // must move & turn number
        situation += (viewModel.mustMove() && !viewModel.isSecDangerous()) ? " - Turn number: "
                + viewModel.getTurnCount()
                + "%n - You can cross "
                + viewModel.getMaxSteps() + " sector.%n"
                // can attack
                : (viewModel.canAttack() ? " - You can attack if you want, but remember:%n   if you are on a dangerous sector, you must attack or draw a card before turnover."
                        : "");

        // must discard
        situation += viewModel.mustDiscard() ? " - You must discard (or use) at least one Item Card before ending your turn.%n"
                : "";

        // silenced forced
        situation += viewModel.isSilenceForced() ? " - You won't draw a Sector Card if your movement ends on a dangerous sector.%n"
                : "";

        // drawn card
        situation += viewModel.getDrawnCard() != null ? "Choose where to make the noise."
                : "";

        CliEngine.printLineToCli(situation);
    }
}
