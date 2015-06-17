package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;

import java.util.StringTokenizer;

public interface ICliCommand {
    public RequestModel makeRequest(StringTokenizer stkn);
}
