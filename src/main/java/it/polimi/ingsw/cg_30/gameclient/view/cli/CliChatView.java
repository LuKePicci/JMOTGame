package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliChatView.
 */
public class CliChatView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ChatViewModel viewModel = (ChatViewModel) model;
        CliEngine.printLineToCli("");
        CliEngine.printLineToCli(viewModel.getSenderNick().toUpperCase() + ": "
                + viewModel.getText());
    }

}
