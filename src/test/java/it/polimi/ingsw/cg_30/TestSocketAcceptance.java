package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSocketAcceptance {

	static SocketAcceptance server;
	static Semaphore sem = new Semaphore(0);
	
	public int randomPort;
	
	@BeforeClass
	public static void initAcceptance(){
		SocketAcceptance.randomizePort = true;
		server = new SocketAcceptance();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {	}
		sem.release();
	}
	
	@AfterClass
	public static void closeAcceptance(){
		server.StopServer();;
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldAcceptMoreSocketClients() throws IOException {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Socket soc = new Socket("127.0.0.1", server.randomPort);
		
		boolean success = !(new DataInputStream(soc.getInputStream()).readBoolean());
		soc.close();
		assertTrue(success);
		
		success = false;
		
		soc = new Socket("127.0.0.1", server.randomPort);
		
		success = !(new DataInputStream(soc.getInputStream()).readBoolean());
		soc.close();
		assertTrue(success);
	}

}
