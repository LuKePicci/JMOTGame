package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private AcceptRmiPlayer rmiServer;
    private TestAcceptRmiPlayer thisTest;
    private Thread serverThread;
    private boolean noError;
    private static IAcceptRmiPlayer rmiClient;
    private static Registry testRegistry;

    @BeforeClass
    public static void fakeRegistry() {
        try {
            testRegistry = LocateRegistry.createRegistry(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void fakeServer() {
        this.noError = false;
        thisTest = this;
        try {
            rmiServer = new AcceptRmiPlayer("rmiTesting", testRegistry) {
                @Override
                public synchronized void toServer(Message msg)
                        throws RemoteException {
                    super.rcvMessage = msg;
                    ChatMessage cm = (ChatMessage) this.receiveMessage();
                    if (cm.getContent().getText().equals(TEST_MESSAGE_TEXT))
                        thisTest.noError = true;
                }
            };
            serverThread = new Thread(rmiServer, "rmiTesting");
            serverThread.start();
            IRmiClient stub = (IRmiClient) UnicastRemoteObject.exportObject(
                    this, 0);
            testRegistry.rebind("client-rmiTesting", stub);
            System.out.println("Dumb client bound");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void shouldReceiveMessage() {
        try {
            rmiClient = (IAcceptRmiPlayer) testRegistry
                    .lookup("server-rmiTesting");
            rmiClient.toServer(new ChatMessage(new ChatRequest(
                    TEST_MESSAGE_TEXT)));
            assertTrue(this.noError);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSendMessage() {
        this.rmiServer.sendMessage(new ChatMessage(new ChatRequest(
                TEST_MESSAGE_TEXT)));
        assertTrue(noError);
    }

    @Override
    public void toClient(Message msg) {
        ChatMessage unboxedMessage = (ChatMessage) msg;

        assertEquals(TEST_MESSAGE_TEXT, unboxedMessage.getContent().getText());
        this.noError = true;
    }
}
