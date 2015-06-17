package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliChatView extends View {

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ChatViewModel viewModel = (ChatViewModel) model;
        CliEngine.printLineToCli(viewModel.getSenderNick().toUpperCase() + ": "
                + viewModel.getText());
    }

}
