package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliPlayerView extends View {
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        PlayerViewModel viewModel = (PlayerViewModel) model;
        CliEngine.printLineToCli("Player's details:");
        CliEngine.printLineToCli("name: " + viewModel.getName()
                + "   kills number: " + viewModel.getKillsCount()
                + "   number: " + viewModel.getIndex());
    }
}
