package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.RequestTask;
import it.polimi.ingsw.cg_30.gameclient.UpdateTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ClientMessenger {

    private static ClientMessenger currentMessenger;

    protected static final ExecutorService threadPool = Executors
            .newFixedThreadPool(10);

    private File tokenFile;

    private UUID myID;

    public abstract void sendMessage(Message msg);

    protected void receiveMessage(Message receivedMessage) {
        this.setUUID(receivedMessage.getSessionId());
        this.executeUpdateProcessor(receivedMessage);
    }

    public abstract void connect(String host, int port) throws RemoteException,
            NotBoundException, IOException;

    public UUID getUUID() {
        return this.myID;
    }

    protected void setUUID(UUID id) {
        if (id != null && (this.myID == null || !this.myID.equals(id))) {
            this.myID = id;
            this.saveToken();
        }
    }

    private void saveToken() {
        if (tokenFile == null)
            return;

        try (PrintWriter out = new PrintWriter(tokenFile)) {
            out.println(this.myID.toString());
            out.close();
        } catch (FileNotFoundException e) {
            LoggerMethods.fileNotFoundException(e, "");
        }
    }

    public void loadToken(String username) {
        this.tokenFile = new File(username + ".eftaios");
        try (Scanner reader = new Scanner(tokenFile)) {
            this.myID = reader.hasNextLine() ? UUID.fromString(reader
                    .nextLine()) : null;
        } catch (FileNotFoundException e) {
            LoggerMethods.fileNotFoundException(e, "session file not existing");
        }
    }

    public static ClientMessenger getCurrentMessenger() {
        return currentMessenger;
    }

    public static void setCurrentMessenger(ClientMessenger m) {
        currentMessenger = m;
    }

    public static void connectToServer(URI serverURI) throws NotBoundException,
            IOException {
        switch (serverURI.getScheme().toLowerCase()) {
            case "rmi":
                connectToRmi(serverURI);
                break;
            case "socket":
            default:
                connectToSocket(serverURI);
        }
    }

    private static void connectToRmi(URI serverURI) throws NotBoundException,
            IOException {
        currentMessenger = new RmiMessenger();
        currentMessenger.connect(serverURI.getHost(), serverURI.getPort());
    }

    private static void connectToSocket(URI serverURI)
            throws NotBoundException, IOException {
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
