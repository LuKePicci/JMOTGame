package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliPlayerView.
 */
public class CliPlayerView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        PlayerViewModel viewModel = (PlayerViewModel) model;
        CliEngine.printLineToCli("%nPlayer's details:");
        CliEngine.printLineToCli("%nname: " + viewModel.getName()
                + "%nkills number: " + viewModel.getKillsCount() + "%nindex: "
                + viewModel.getIndex() + "%nnumber of item cards owned: "
                + viewModel.getNumOfItemCards());
    }
}
