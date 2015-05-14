package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAcceptance extends PlayerAcceptance {

	public SocketAcceptance() {
		this.start();
	}

	@Override
	protected void acceptance() {
		ServerSocket soc;
		try {
			soc = new ServerSocket(17336);
			while (!this.isInterrupted()) {
				Socket CSoc;
				try {
					CSoc = soc.accept();
					AcceptPlayer gameClient = new AcceptSocketPlayer(CSoc);
					this.connections.add(gameClient);
				} catch (IOException e) {
					e.printStackTrace();
					this.interrupt();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
