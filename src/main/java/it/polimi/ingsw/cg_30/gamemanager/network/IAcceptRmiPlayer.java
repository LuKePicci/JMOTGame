package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IAcceptRmiPlayer extends Remote {

    public void toServer(Message msg) throws RemoteException;

    public UUID getUUID() throws RemoteException;
}
