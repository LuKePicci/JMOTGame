package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAcceptance extends PlayerAcceptance {

	public static final int DEFAULT_SERVER_PORT = 22222;
	public static boolean randomizePort = false;

	private ServerSocket soc;
	public int randomPort = 0;

	public SocketAcceptance() {
		try {
			this.soc = new ServerSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}

	public void StopServer() {
		try {
			if (!soc.isClosed())
				this.soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void acceptance() {
		try {
			soc.setReuseAddress(true);
			soc.bind(new InetSocketAddress(randomizePort ? 0
					: DEFAULT_SERVER_PORT));
			this.randomPort = soc.getLocalPort();
			while (!this.isInterrupted()) {
				Socket CSoc;
				try {
					CSoc = soc.accept();
					AcceptPlayer gameClient = new AcceptSocketPlayer(CSoc);
					this.connections.add(gameClient);
				} catch (IOException e) {
					System.out.println("Server " + soc.hashCode()
							+ " closed because of " + e.getMessage());
					this.interrupt();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (soc != null && !soc.isClosed())
					soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
