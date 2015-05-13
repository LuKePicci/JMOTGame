package it.polimi.ingsw.cg_30;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAcceptRmiPlayer extends Remote {

	public void toServer(IMessage msg) throws RemoteException;
}
