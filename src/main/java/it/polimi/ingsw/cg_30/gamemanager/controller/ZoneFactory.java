package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

public abstract class ZoneFactory extends MapFactory {
    @SuppressWarnings("unchecked")
    @Override
    public abstract Zone newMap();
}
