package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

public abstract class View {
    public abstract void applyUpdate(ViewModel model);

    protected char getCharFromNumber(int i) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        return alphabet[i];
    }
}
