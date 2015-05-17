package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Before;
import org.junit.Test;

public class AcceptRmiPlayerTest implements IRmiClient {

	private static final String TEST_MESSAGE_TEXT = "Rmi Message Testing";
	private AcceptRmiPlayer rmiServer;
	private boolean noError = false;
	private static IAcceptRmiPlayer rmiClient;
	private static Registry testRegistry;

	@Before
	public void rmiConnect() {
		try {
			testRegistry = LocateRegistry.createRegistry(0);

			rmiServer = new AcceptRmiPlayer("rmiTesting", testRegistry);
			IRmiClient stub = (IRmiClient) UnicastRemoteObject.exportObject(
					this, 0);
			testRegistry.rebind("client-rmiTesting", stub);
			System.out.println("Dumb client bound");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	// @Test
	public void shouldReceiveMessage() {

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
