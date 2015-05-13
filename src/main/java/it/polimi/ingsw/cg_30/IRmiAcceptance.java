package it.polimi.ingsw.cg_30;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiAcceptance extends Remote {
	public String present() throws RemoteException;

}
