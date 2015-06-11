package it.polimi.ingsw.cg_30.gamemanager.network;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.network.IAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.exchange.network.IRmiAcceptance;
import it.polimi.ingsw.cg_30.exchange.network.IRmiClient;
import it.polimi.ingsw.cg_30.gamemanager.network.AcceptRmiPlayer;
import it.polimi.ingsw.cg_30.gamemanager.network.RmiAcceptance;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAcceptRmiPlayer implements IRmiClient {

    private static final String TEST_MESSAGE_TEXT = "Rmi Message Testing";
    private static RmiAcceptance rmiServer = new RmiAcceptance();

    private Registry rmiReg;

    private IRmiClient clientSkel;
    private IRmiAcceptance serverStub;
    private IAcceptRmiPlayer acceptStub;

    private AcceptRmiPlayer rmiAcceptServer;

    @BeforeClass
    public static void initServer() {
        rmiServer.acceptance();
    }

    @Before
    public void initClient() {
        try {
            this.rmiReg = LocateRegistry.getRegistry("localhost", 9090);

            this.serverStub = (IRmiAcceptance) rmiReg.lookup("RmiServer");

            this.clientSkel = (IRmiClient) UnicastRemoteObject.exportObject(
                    this, 0);

            this.acceptStub = this.serverStub.present(clientSkel);

            System.out.println("Player connected by RMI {id:"
                    + this.acceptStub.getUUID() + "}");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReceiveMessage() {
        Message toSend = new ChatMessage(new ChatRequest(TEST_MESSAGE_TEXT,
                ChatVisibility.PUBLIC, null));
        try {
            acceptStub.toServer(toSend);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSendMessage() throws RemoteException {
        this.rmiAcceptServer = new AcceptRmiPlayer(clientSkel);
        this.rmiAcceptServer.sendMessage(new ChatMessage(new ChatRequest(
                TEST_MESSAGE_TEXT, ChatVisibility.PUBLIC, null)));
    }

    @Override
    public void toClient(Message msg) throws RemoteException {
        ChatMessage unboxedMessage = (ChatMessage) msg;

        assertEquals(TEST_MESSAGE_TEXT, unboxedMessage.getRequest().getText());
    }
}
