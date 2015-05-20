package it.polimi.ingsw.cg_30;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.UUID;

public class AcceptRmiPlayer extends AcceptPlayer implements IAcceptRmiPlayer {
    protected Message rcvMessage, sndMessage;
    private IRmiClient rmiClient;
    private UUID rmiSessionId;
    private Registry rmiRegistry;

    public AcceptRmiPlayer() throws RemoteException {
        this(UUID.randomUUID(), LocateRegistry.getRegistry());
    }

    public AcceptRmiPlayer(Registry reg) throws RemoteException {
        this(UUID.randomUUID(), reg);
    }

    public AcceptRmiPlayer(UUID rmiSessionId, Registry rmiReg)
            throws RemoteException {
        super(rmiSessionId);
        this.rmiSessionId = rmiSessionId;
        this.rmiRegistry = rmiReg;
        IAcceptRmiPlayer stub = (IAcceptRmiPlayer) UnicastRemoteObject
                .exportObject(this, 0);
        this.rmiRegistry.rebind("server-" + rmiSessionId, stub);
    }

    public Registry getRegistry() {
        return this.rmiRegistry;
    }

    private boolean lookupClient() {
        if (rmiClient == null)
            try {
                this.rmiClient = (IRmiClient) this.rmiRegistry.lookup("client-"
                        + rmiSessionId);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        return false;
    }

    @Override
    public void sendMessage(Message msg) {
        this.sndMessage = msg;
        if (lookupClient())
            try {
                this.rmiClient.toClient(this.sndMessage);
            } catch (RemoteException e) {
                System.out.println(String.format(
                        "%s : Message sending failure to %s", e.getMessage(),
                        this.rmiSessionId));
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
        this.mc.deliver(this.receiveMessage());
    }

    @Override
    public void ping() {
        // TODO Not implemented yet
    }
}
