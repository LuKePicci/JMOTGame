package it.polimi.ingsw.cg_30.gamemanager.network;

import java.util.HashSet;
import java.util.Set;

public abstract class PlayerAcceptance implements Runnable {

    protected Set<AcceptPlayer> connections = new HashSet<AcceptPlayer>();

    public Set<AcceptPlayer> getConnections() {
        return this.connections;
    }

    protected abstract void acceptance();

    @Override
    public void run() {
        this.acceptance();
    }

}
