package it.polimi.ingsw.cg_30;

public class NotAnHatchException extends Exception {

    private static final long serialVersionUID = -7999516043014991991L;

    Sector actualSector;
    HexPoint requestedPoint;

    public NotAnHatchException(Sector found) {
        this.actualSector = found;
    }

    @Override
    public String getMessage() {
        return String
                .format("Cannot lock a non Hatch sector.\n\r\tType of sector on the map: %s\n\r\tPoint position:\tX=%d, Y=%d",
                        this.actualSector.getType().toString(),
                        this.actualSector.getPoint().getOffsetX(),
                        this.actualSector.getPoint().getOffsetY());
    }

}
