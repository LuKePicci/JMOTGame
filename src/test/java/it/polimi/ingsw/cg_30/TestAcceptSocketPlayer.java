package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.Semaphore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAcceptSocketPlayer extends Thread {

	public static Socket soc;
	public static AcceptSocketPlayer ap;
	public static TestAcceptSocketPlayer sckTest;
	public static String testData = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8TWVzc2FnZSBUeXBlPSJDaGF0TWVzc2FnZSI+CiAgICA8Q29udGVudCBUZXh0PSJNZXNzYWdlIHRlc3RpbmciIERhdGU9IjE5NzAtMDEtMDFUMDE6MDA6MDArMDE6MDAiLz4KPC9NZXNzYWdlPgo=";
	public static Semaphore sem = new Semaphore(0);

	@BeforeClass
	public static void initServerSocket() {
		sckTest = new TestAcceptSocketPlayer();
		try {
			sckTest.server = new ServerSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sckTest.start();
	}

	@AfterClass
	public static void closeServerSocket() {
		try {

			soc.close();

			sckTest.server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			sckTest.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	ServerSocket server;
	int localPort;

	@Override
	public void run() {
		try {
			sckTest.server.setReuseAddress(true);
			sckTest.server.bind(new InetSocketAddress(0));
			sckTest.localPort = sckTest.server.getLocalPort();
			System.out.println("Choosed port: " + sckTest.localPort);
			sem.release();
			soc = server.accept();
			ap = new AcceptSocketPlayer(soc);
			sem.release();
		} catch (IOException e) {
			e.printStackTrace();
			sem.release(2);
			this.interrupt();
		}
	}

	// @Test
	public void shouldReceiveMessage() {
		try {
			Socket client = new Socket("127.0.0.1", sckTest.localPort);
			DataOutputStream cdout = new DataOutputStream(
					client.getOutputStream());
			cdout.writeUTF(new String(testData));
			if (soc.isConnected() && !soc.isClosed()) {
				ap.receiveMessage();
			}
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldSendMessage() {
		try {
			try {
				sem.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Socket client = new Socket("127.0.0.1", sckTest.localPort);
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			DataInputStream cdin = new DataInputStream(client.getInputStream());

			// request server interruption preventing message from being
			// delivered
			ap.interrupt();
			ChatRequest chat = new ChatRequest("Message testing");
			chat.setDate(new Date(0L));
			ap.sendMessage(new ChatMessage(chat));

			String utfData = ap.getLastSentData();
			client.close();
			assertEquals(testData, utfData);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
