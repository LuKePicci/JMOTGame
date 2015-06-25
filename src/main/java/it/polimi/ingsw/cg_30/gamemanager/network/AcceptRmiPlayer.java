package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.network.IAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.exchange.network.IRmiClient;
import it.polimi.ingsw.cg_30.gamemanager.controller.LoggerMethods;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

public class AcceptRmiPlayer extends AcceptPlayer implements IAcceptRmiPlayer {

    protected Message rcvMessage, sndMessage;
    private IRmiClient rmiClient;

    public AcceptRmiPlayer(IRmiClient client) throws RemoteException {
        this(client, UUID.randomUUID());
    }

    public AcceptRmiPlayer(IRmiClient client, UUID rmiSessionId)
            throws RemoteException {
        super(rmiSessionId);
        this.rmiClient = client;
    }

    @Override
    public void sendMessage(Message msg) throws DisconnectedException {
        if (this.hasLostConnection)
            throw new DisconnectedException(new Date());

        msg.setSessionId(this.sessionId);

        try {
            this.sndMessage = msg;
            this.rmiClient.toClient(this.sndMessage);
        } catch (RemoteException e) {
            LoggerMethods.remoteException(e, "Message sending failure");
            throw new DisconnectedException(this.loseConnection());
        }
    }

    @Override
    protected Message receiveMessage() {
        this.lastMessage = new Date();
        return this.rcvMessage;
    }

    @Override
    public synchronized void toServer(Message msg) throws RemoteException {
        this.rcvMessage = msg;
        this.mc.dispatchIncoming(this.receiveMessage());
    }

    @Override
    public void ping() {
        // TODO Not implemented yet
    }
}
