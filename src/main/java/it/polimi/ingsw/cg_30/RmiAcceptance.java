package it.polimi.ingsw.cg_30;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import javax.rmi.PortableRemoteObject;

public class RmiAcceptance extends PlayerAcceptance implements IRmiAcceptance {

	private Registry rmiRegistry;

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
		String rmiSessionId = UUID.randomUUID().toString();
		try {
			AcceptRmiPlayer gmClient = new AcceptRmiPlayer(rmiSessionId,
					this.rmiRegistry);
			PortableRemoteObject.exportObject(gmClient);
			Naming.rebind(rmiSessionId, gmClient);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return rmiSessionId;
	}

	@Override
	public void run() {
		try {
			this.rmiRegistry = LocateRegistry.createRegistry(0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
