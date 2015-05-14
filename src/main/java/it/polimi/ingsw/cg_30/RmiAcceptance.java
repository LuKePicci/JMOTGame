package it.polimi.ingsw.cg_30;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.rmi.PortableRemoteObject;

public class RmiAcceptance extends PlayerAcceptance implements IRmiAcceptance {

	public RmiAcceptance() {

		this.start();
	}

	@Override
	protected void acceptance() {
		try {
			PortableRemoteObject.exportObject(this);
			Naming.rebind("RmiServer", this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String present() {
		// Generate random session id
		UUID rmiSessionId = UUID.randomUUID();
		AcceptRmiPlayer rmiClient = new AcceptRmiPlayer(rmiSessionId);
		try {
			PortableRemoteObject.exportObject(rmiClient);
			Naming.rebind(rmiSessionId.toString(), rmiClient);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return rmiSessionId.toString();
	}
}
