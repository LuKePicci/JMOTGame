package it.polimi.ingsw.cg_30;

import java.rmi.Remote;

public interface IRmiClient extends Remote {
	public void toClient(Message msg);
}
