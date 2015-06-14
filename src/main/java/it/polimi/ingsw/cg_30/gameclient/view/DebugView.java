package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

public class DebugView extends View {

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        // print some debug information to System.out
        System.out.println(String.format(
                "Model received { type: %s, contents: { %s } }",
                model.getViewType(), model.toString()));
    }

}
