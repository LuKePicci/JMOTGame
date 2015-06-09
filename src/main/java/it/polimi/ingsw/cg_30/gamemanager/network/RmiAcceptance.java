package it.polimi.ingsw.cg_30.gamemanager.network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiAcceptance extends PlayerAcceptance implements IRmiAcceptance {

    private Registry rmiRegistry;

    public RmiAcceptance() {

    }

    @Override
    protected void acceptance() {
        Thread.currentThread().setName("RmiServerAcceptance");
        try {

            this.rmiRegistry = LocateRegistry.createRegistry(9090);

        } catch (RemoteException e) {
            try {
                this.rmiRegistry = LocateRegistry.getRegistry(9090);
            } catch (RemoteException e1) {
                return;
            }
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
    public IAcceptRmiPlayer present(IRmiClient rmiClient) {
        AcceptRmiPlayer gameClient = null;
        try {
            gameClient = new AcceptRmiPlayer(rmiClient);
            this.connections.add(gameClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return gameClient;
    }
}
