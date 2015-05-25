package it.polimi.ingsw.cg_30;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IAcceptRmiPlayer extends Remote {

    public void toServer(Message msg) throws RemoteException;

    public UUID getUUID() throws RemoteException;
}
