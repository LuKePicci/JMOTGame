package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;

public class NotAnHatchException extends Exception {

    private static final long serialVersionUID = -7999516043014991991L;

    final Sector actualSector;

    public NotAnHatchException(Sector found) {
        this.actualSector = found;
    }

    @Override
    public String getMessage() {
        return String
                .format("Cannot lock a non Hatch sector.%n\tType of sector on the map: %s%n\tPoint position:\tX=%d, Y=%d",
                        this.actualSector.getType().toString(),
                        this.actualSector.getPoint().getOffsetX(),
                        this.actualSector.getPoint().getOffsetY());
    }

}
