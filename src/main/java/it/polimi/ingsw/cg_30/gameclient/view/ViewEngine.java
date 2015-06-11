package it.polimi.ingsw.cg_30.gameclient.view;

public abstract class ViewEngine {
    public abstract void runEngine();

    public abstract void chooseProtocol();

    public abstract void chooseGame();

    public abstract void showGame();

    public abstract void updateViews();

    public abstract void showError(String errorMessage);

}
