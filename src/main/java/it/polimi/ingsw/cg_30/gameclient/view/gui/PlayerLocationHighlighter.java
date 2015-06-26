package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JSector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerLocationHighlighter implements ActionListener {

    private static PlayerLocationHighlighter plh;
    private JSector newLocation;
    private JSector oldLocation;

    private PlayerLocationHighlighter() {
    }

    /**
     * Gets singleton instance of this class.
     * 
     * @return PlayerLocationHighlighter instance
     */
    public static PlayerLocationHighlighter getPlayerLocationHighlighter() {
        return plh == null ? new PlayerLocationHighlighter() : plh;
    }

    public void updateLocation(JSector jsec) {
        this.newLocation = jsec;
    }

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
