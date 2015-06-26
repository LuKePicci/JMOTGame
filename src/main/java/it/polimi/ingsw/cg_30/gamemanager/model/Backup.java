package it.polimi.ingsw.cg_30.gamemanager.model;

import java.io.Serializable;

/**
 * The Class Backup.
 */
public class Backup implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1507047141267767493L;

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
