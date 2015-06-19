package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

public abstract class View {
    public abstract void applyUpdate(ViewModel model);

    protected String getCharFromNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }
}
