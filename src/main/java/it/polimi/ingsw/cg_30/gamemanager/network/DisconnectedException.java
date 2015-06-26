package it.polimi.ingsw.cg_30.gamemanager.network;

import java.util.Date;

public class DisconnectedException extends Exception {

    private static final long serialVersionUID = -9024498887687827416L;

    private final Date eventDate;

    public DisconnectedException(Date d) {
        super("Connection lost");
        this.eventDate = d;
    }

    public Date getDisconnectionDate() {
        return this.eventDate;
    }
}
