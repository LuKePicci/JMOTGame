package it.polimi.ingsw.cg_30;

public class GameServer {
    private static Iterable<AcceptPlayer> clients;

    public static void main(String[] args) {
        GameServer gm = new GameServer();
        gm.startServers();
        System.out.println("Hello Client!");
    }

    private void startServers() {
        PlayerAcceptance socketServer = new SocketAcceptance();
        PlayerAcceptance rmiServer = new RmiAcceptance();
        Thread socketThread = new Thread(socketServer, "socket-GameServer");
        Thread rmiThread = new Thread(rmiServer, "rmi-GameServer");

        socketThread.start();
        rmiThread.start();
    }
}
