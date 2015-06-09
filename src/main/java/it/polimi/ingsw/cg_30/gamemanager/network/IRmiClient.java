package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiClient extends Remote {
    public void toClient(Message msg) throws RemoteException;

}
