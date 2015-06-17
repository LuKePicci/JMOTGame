package it.polimi.ingsw.cg_30.gameclient;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;

public class RequestTask implements Runnable {

    private RequestModel requestToSend;

    public RequestTask(RequestModel request) {
        this.requestToSend = request;
    }

    @Override
    public void run() {
        ClientMessenger.getCurrentMessenger().sendMessage(
                new Message(requestToSend));
    }

}
