package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public class Backup implements Serializable {
    private Party activeParty;

    public Party getActiveParty() {
        return activeParty;
    }

    // solo public???
    public Backup(Party activeParty) {
        this.activeParty = activeParty;
    }

}
