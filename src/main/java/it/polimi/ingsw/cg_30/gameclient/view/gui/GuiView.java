package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.view.View;

import javax.swing.JComponent;

public abstract class GuiView extends View {

    public abstract JComponent getComponent();

    protected abstract void createComponents();
}
