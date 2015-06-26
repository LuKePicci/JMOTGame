package it.polimi.ingsw.cg_30.gamemanager.network;

import java.util.Date;

/**
 * The Class DisconnectedException.
 */
public class DisconnectedException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -9024498887687827416L;

    /** The event date. */
    private final Date eventDate;

    /**
     * Instantiates a new disconnected exception.
     *
     * @param d
     *            the disconnected exception
     */
    public DisconnectedException(Date d) {
        super("Connection lost");
        this.eventDate = d;
    }

    /**
     * Gets the disconnection date.
     *
     * @return the disconnection date
     */
    public Date getDisconnectionDate() {
        return this.eventDate;
    }
}
