package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.view.View;

import java.awt.Component;

/**
 * The Class GuiView.
 */
public abstract class GuiView extends View {

    /**
     * Gets the component.
     *
     * @return the component
     */
    public abstract Component getComponent();

    /**
     * Creates the components.
     */
    protected abstract void createComponents();

    /**
     * Registers default button.
     */
    public void registerDefaultButton() {
        throw new UnsupportedOperationException();
    }
}
