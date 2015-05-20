package it.polimi.ingsw.cg_30;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IRmiAcceptance extends Remote {
    public UUID present() throws RemoteException;

}
