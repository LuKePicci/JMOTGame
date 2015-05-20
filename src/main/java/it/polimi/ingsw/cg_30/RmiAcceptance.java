package it.polimi.ingsw.cg_30;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import javax.rmi.PortableRemoteObject;

public class RmiAcceptance extends PlayerAcceptance implements IRmiAcceptance {

    private Registry rmiRegistry;

    public RmiAcceptance() {

    }

    @Override
    protected void acceptance() {
        Thread.currentThread().setName("RmiServerAcceptance");
        try {
            this.rmiRegistry = LocateRegistry.createRegistry(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            IRmiAcceptance stub = (IRmiAcceptance) UnicastRemoteObject
                    .exportObject(this, 0);
            this.rmiRegistry.rebind("RmiServer", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UUID present() {
        UUID ret = null;
        try {
            AcceptRmiPlayer gameClient = new AcceptRmiPlayer(this.rmiRegistry);
            PortableRemoteObject.exportObject(gameClient);
            Naming.rebind("server-" + gameClient.getUUID(), gameClient);
            this.connections.add(gameClient);
            ret = gameClient.getUUID();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
