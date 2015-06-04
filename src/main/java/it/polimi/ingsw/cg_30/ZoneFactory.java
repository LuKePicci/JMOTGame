package it.polimi.ingsw.cg_30;

public abstract class ZoneFactory extends MapFactory {
    @SuppressWarnings("unchecked")
    @Override
    public abstract Zone newMap();
}
