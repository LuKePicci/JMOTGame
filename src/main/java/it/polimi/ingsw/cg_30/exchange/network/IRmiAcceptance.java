package it.polimi.ingsw.cg_30.exchange.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiAcceptance extends Remote {
    public IAcceptRmiPlayer present(IRmiClient rmiClient)
            throws RemoteException;

}
