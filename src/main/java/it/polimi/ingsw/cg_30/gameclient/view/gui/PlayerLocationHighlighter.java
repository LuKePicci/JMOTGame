package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JSector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class PlayerLocationHighlighter.
 */
public class PlayerLocationHighlighter implements ActionListener {

    /** The player location highlighter. */
    private static PlayerLocationHighlighter plh;

    /** The new location. */
    private JSector newLocation;

    /** The old location. */
    private JSector oldLocation;

    /**
     * Instantiates a new player location highlighter.
     */
    private PlayerLocationHighlighter() {
    }

    /**
     * Gets singleton instance of this class.
     * 
     * @return PlayerLocationHighlighter instance
     */
    public static PlayerLocationHighlighter getPlayerLocationHighlighter() {
        return plh == null ? plh = new PlayerLocationHighlighter() : plh;
    }

    /**
     * Updates the new location.
     *
     * @param jsec
     *            the new JSector
     */
    public void updateLocation(JSector jsec) {
        this.newLocation = jsec;
    }

    /**
     * Action performed.
     *
     * @param e
     *            the e
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (oldLocation != null && !oldLocation.isVisible()) {
            oldLocation.setVisible(true);
        } else {
            newLocation.setVisible(false);
            this.oldLocation = this.newLocation;
        }
    }

}
