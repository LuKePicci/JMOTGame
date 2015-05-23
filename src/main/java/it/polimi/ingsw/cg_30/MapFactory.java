package it.polimi.ingsw.cg_30;

public abstract class MapFactory {
    public abstract <T extends Cell> GameTable<T> newMap();
}
