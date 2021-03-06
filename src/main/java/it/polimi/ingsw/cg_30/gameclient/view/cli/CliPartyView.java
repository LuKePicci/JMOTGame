package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PartyViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliPartyView.
 */
public class CliPartyView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        PartyViewModel viewModel = (PartyViewModel) model;
        String players = "";
        for (ViewModel view : viewModel.getPartyPlayers()) {
            PlayerViewModel playerView = (PlayerViewModel) view;
            players += playerView.getName() + "   (item cards: "
                    + playerView.getNumOfItemCards() + ")\r\n";
        }
        CliEngine.printLineToCli("\r\nName of the party:\r\n"
                + viewModel.getPartyName() + "\r\nPlayers of the party:\r\n"
                + players);
    }

}
