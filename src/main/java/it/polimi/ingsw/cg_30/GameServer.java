package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.naming.OperationNotSupportedException;

public class GameServer {
	private static Iterable<AcceptPlayer> clients;

	public static void main(String[] args) {
		GameServer gm = new GameServer();
		gm.startServers();
		System.out.println("Hello Client!");
	}

	private void startServers(){
		PlayerAcceptance socketServer = new SocketAcceptance();
		// PlayerAcceptance rmiServer = new RmiAcceptance();
	}
}
