package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.view.View;

import java.awt.Component;

public abstract class GuiView extends View {

    public abstract Component getComponent();

    protected abstract void createComponents();
}
