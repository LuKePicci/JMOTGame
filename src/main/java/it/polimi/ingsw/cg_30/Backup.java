package it.polimi.ingsw.cg_30;

import java.io.Serializable;

/**
 * The Class Backup.
 */
public class Backup implements Serializable {

    /** The active party. */
    private Party activeParty;

    /**
     * Instantiates a new backup.
     *
     * @param activeParty
     *            the active party
     */
    public Backup(Party activeParty) {
        this.activeParty = activeParty;
    }

    /**
     * Gets the active party.
     *
     * @return the active party
     */
    public Party getActiveParty() {
        return activeParty;
    }

}
