package it.polimi.ingsw.cg_30;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Date;
import java.util.UUID;

public class AcceptRmiPlayer extends AcceptPlayer implements IAcceptRmiPlayer {
	private Message rcvMessage, sndMessage;
	private IRmiClient rmiClient;

	public AcceptRmiPlayer(UUID rmiSessionId) {
		try {
			this.rmiClient = (IRmiClient) Naming.lookup("rmi://localhost/"
					+ rmiSessionId);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(Message msg) {
		this.sndMessage = msg;
		this.rmiClient.toClient(this.sndMessage);
	}

	@Override
	protected Message receiveMessage() {
		this.lastMessage = new Date();
		return this.rcvMessage;
	}

	@Override
	public void toServer(Message msg) throws RemoteException {
		this.rcvMessage = msg;
		this.mc.deliver(this.receiveMessage());
	}
	
	@Override
	final public void run(){
		
	}
}
