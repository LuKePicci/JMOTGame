package it.polimi.ingsw.cg_30.gameclient;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

public class UpdateTask implements Runnable {

    private ViewModel modelToUpdate;

    public UpdateTask(ViewModel update) {
        this.modelToUpdate = update;
    }

    @Override
    public void run() {
        GameClient.getActiveEngine()
                .getViewByType(this.modelToUpdate.getViewType())
                .applyUpdate(this.modelToUpdate);
    }

}
