package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;

public class InvalidActionException extends Exception {
    public ActionRequest action;

}
