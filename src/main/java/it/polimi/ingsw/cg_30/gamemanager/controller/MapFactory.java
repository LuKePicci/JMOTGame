package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Cell;
import it.polimi.ingsw.cg_30.gamemanager.model.GameTable;

public abstract class MapFactory {
    public abstract <T extends Cell> GameTable<T> newMap();
}
