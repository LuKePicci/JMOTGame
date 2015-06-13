package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;

import java.net.URI;
import java.util.UUID;

public abstract class ClientMessenger {

    private static ClientMessenger currentMessenger;

    private UUID myID;

    public abstract void sendMessage(Message msg);

    public abstract void connect(String host, int port) throws Exception;

    public UUID getUUID() {
        return this.myID;
    }

    protected void setUUID(UUID id) {
        this.myID = id;
    }

    public static ClientMessenger getCurrentMessenger() {
        return currentMessenger;
    }

    public static void setCurrentMessenger(ClientMessenger m) {
        currentMessenger = m;
    }

    public static void connectToServer(URI serverURI) throws Exception {
        switch (serverURI.getScheme().toLowerCase()) {
            case "rmi":
                connectToRmi(serverURI);
                break;
            case "socket":
            default:
                connectToSocket(serverURI);
        }
    }

    private static void connectToRmi(URI serverURI) throws Exception {
        currentMessenger = new RmiMessenger();
        currentMessenger.connect(serverURI.getHost(), serverURI.getPort());
    }

    private static void connectToSocket(URI serverURI) throws Exception {
        currentMessenger = new SocketMessenger();
        currentMessenger.connect(serverURI.getHost(), serverURI.getPort());
    }
}
