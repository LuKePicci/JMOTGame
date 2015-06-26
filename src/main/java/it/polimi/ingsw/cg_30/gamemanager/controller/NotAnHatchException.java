package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;

/**
 * The Class NotAnHatchException.
 */
public class NotAnHatchException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7999516043014991991L;

    /** The actual sector. */
    final Sector actualSector;

    /**
     * Instantiates a new not an hatch exception.
     *
     * @param found
     *            the found
     */
    public NotAnHatchException(Sector found) {
        this.actualSector = found;
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return String
                .format("Cannot lock a non Hatch sector.%n\tType of sector on the map: %s%n\tPoint position:\tX=%d, Y=%d",
                        this.actualSector.getType().toString(),
                        this.actualSector.getPoint().getOffsetX(),
                        this.actualSector.getPoint().getOffsetY());
    }

}
