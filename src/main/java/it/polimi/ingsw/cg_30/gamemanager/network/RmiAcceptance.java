package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.network.IAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.exchange.network.IRmiAcceptance;
import it.polimi.ingsw.cg_30.exchange.network.IRmiClient;

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
            LoggerMethods.remoteException(e, "");
            try {
                this.rmiRegistry = LocateRegistry.getRegistry(9090);
            } catch (RemoteException e1) {
                LoggerMethods.remoteException(e1, "");
                return;
            }
        }

        try {
            IRmiAcceptance stub = (IRmiAcceptance) UnicastRemoteObject
                    .exportObject(this, 0);
            this.rmiRegistry.rebind("RmiServer", stub);
        } catch (RemoteException e) {
            LoggerMethods.remoteException(e, "");
        }
    }

    @Override
    public IAcceptRmiPlayer present(IRmiClient rmiClient)
            throws RemoteException {
        AcceptRmiPlayer gameClient = null;
        try {
            gameClient = new AcceptRmiPlayer(rmiClient);
            this.connections.add(gameClient);
        } catch (RemoteException e) {
            LoggerMethods.remoteException(e, "");
        }
        return (IAcceptRmiPlayer) UnicastRemoteObject.exportObject(gameClient,
                0);
    }
}
