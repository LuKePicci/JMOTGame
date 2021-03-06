package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.network.IAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.exchange.network.IRmiAcceptance;
import it.polimi.ingsw.cg_30.exchange.network.IRmiClient;
import it.polimi.ingsw.cg_30.gameclient.GameClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiMessenger extends ClientMessenger implements IRmiClient {

    IAcceptRmiPlayer remoteStub;

    @Override
    public void sendMessage(Message msg) {
        try {
            this.remoteStub.toServer(msg);
        } catch (RemoteException e) {
            LoggerMethods.remoteException(e, "connection lost");
            GameClient.getActiveEngine().showError("connection lost");
        }
    }

    @Override
    public void connect(String host, int port) throws RemoteException,
            NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(host, port);
            IRmiAcceptance rmiServer = (IRmiAcceptance) reg.lookup("RmiServer");
            IRmiClient myStub = (IRmiClient) UnicastRemoteObject.exportObject(
                    this, 0);
            this.remoteStub = rmiServer.present(myStub);
        } catch (RemoteException | NotBoundException e) {
            throw e;
        }

    }

    @Override
    public void toClient(Message receivedMessage) throws RemoteException {
        this.receiveMessage(receivedMessage);
    }
}
