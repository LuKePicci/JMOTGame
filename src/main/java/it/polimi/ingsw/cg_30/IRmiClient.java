package it.polimi.ingsw.cg_30;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiClient extends Remote {
	public void toClient(Message msg) throws RemoteException;

}
