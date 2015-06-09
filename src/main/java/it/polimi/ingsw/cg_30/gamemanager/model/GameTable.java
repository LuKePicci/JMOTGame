package it.polimi.ingsw.cg_30.gamemanager.model;

import java.util.Collection;

public abstract class GameTable<T extends Cell> {

    public abstract void movePlayer(Player who, T where);

    public abstract T getCell(Player player);

    public abstract Collection<T> reachableTargets(T from, Integer maxSteps);
}
