package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;

public class InvalidActionException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -4872099243955571417L;
    public ActionRequest action;

}
