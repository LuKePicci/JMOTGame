package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.Timer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSocketConnection {

	static SocketAcceptance server;
	
	@BeforeClass
	public static void initAcceptance(){
		server = new SocketAcceptance();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {	}
	}
	
	@Test
	public void shouldAcceptSocketClient() throws IOException {
		Socket soc = new Socket("127.0.0.1", 17336);
		
		boolean success = new DataInputStream(soc.getInputStream()).readBoolean();
		assertTrue(success);
		soc.close();
	}
}
