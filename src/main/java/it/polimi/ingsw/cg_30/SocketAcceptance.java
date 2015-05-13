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
			soc = new ServerSocket(5217);
			while (true) {
				Socket CSoc;
				try {
					CSoc = soc.accept();
					AcceptPlayer gameClient = new AcceptSocketPlayer(CSoc);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
