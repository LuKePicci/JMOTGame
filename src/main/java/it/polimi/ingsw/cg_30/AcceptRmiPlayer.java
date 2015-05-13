package it.polimi.ingsw.cg_30;

import java.net.MalformedURLException;
import java.rmi.*;

public class AcceptRmiPlayer extends AcceptPlayer implements IAcceptRmiPlayer {
	private IMessage rcvMessage, sndMessage;
	private IRmiClient rmiClient;

	public AcceptRmiPlayer(String sessionId) {
		try {
			this.rmiClient = (IRmiClient) Naming.lookup("rmi://localhost/"
					+ sessionId);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(IMessage msg) {
		this.sndMessage = msg;
		this.rmiClient.toClient(this.sndMessage);
	}

	@Override
	protected void receiveMessage() {
		this.mc.deliver(this.rcvMessage);
	}

	@Override
	public void toServer(IMessage msg) throws RemoteException {
		this.rcvMessage = msg;
		this.receiveMessage();
	}
	
	@Override
	final public void run(){
		
	}
}
