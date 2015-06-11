package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.network.IAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.exchange.network.IRmiClient;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

public class AcceptRmiPlayer extends AcceptPlayer implements IAcceptRmiPlayer {

    protected Message rcvMessage, sndMessage;
    private transient IRmiClient rmiClient;

    public AcceptRmiPlayer(IRmiClient client) throws RemoteException {
        this(client, UUID.randomUUID());
    }

    public AcceptRmiPlayer(IRmiClient client, UUID rmiSessionId)
            throws RemoteException {
        super(rmiSessionId);
        this.rmiClient = client;
    }

    @Override
    public void sendMessage(Message msg) {
        this.sndMessage = msg;
        try {
            this.rmiClient.toClient(this.sndMessage);
        } catch (RemoteException e) {
            System.out.println(String.format(
                    "%s : Message sending failure to %s", e.getMessage(),
                    this.sessionId));
            e.printStackTrace();
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
