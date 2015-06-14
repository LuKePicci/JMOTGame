package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.RequestTask;
import it.polimi.ingsw.cg_30.gameclient.UpdateTask;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ClientMessenger {

    private static ClientMessenger currentMessenger;

    protected static final ExecutorService threadPool = Executors
            .newFixedThreadPool(10);

    private UUID myID;

    public abstract void sendMessage(Message msg);

    protected void receiveMessage(Message receivedMessage) {
        this.setUUID(receivedMessage.getRawView().getMyID());
        this.executeUpdateProcessor(receivedMessage);
    }

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

    protected void executeUpdateProcessor(Message viewMessage) {
        threadPool.execute(new UpdateTask(viewMessage.getRawView()));
    }

    public void executeRequestTask(RequestModel requestContent) {
        requestContent.setMyID(this.getUUID());
        threadPool.execute(new RequestTask(requestContent));
    }
}
