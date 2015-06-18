package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PartyViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliPartyView extends View {

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        PartyViewModel viewModel = (PartyViewModel) model;
        String players = "";
        for (ViewModel view : viewModel.getPartyPlayers()) {
            PlayerViewModel playerView = (PlayerViewModel) view;
            players += playerView.getName() + "\r\n";
        }
        CliEngine.printLineToCli("Name of the party: "
                + viewModel.getPartyName() + "\r\nPlayers of the party:");
    }

}
