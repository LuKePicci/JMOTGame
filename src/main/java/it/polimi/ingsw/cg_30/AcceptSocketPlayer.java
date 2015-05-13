package it.polimi.ingsw.cg_30;

import java.io.*;
import java.net.*;

public class AcceptSocketPlayer extends AcceptPlayer {
	private final Socket mySoc;
	private final DataInputStream din;
	private final DataOutputStream dout;

	public AcceptSocketPlayer(Socket soc) {
		this.mySoc = soc;
		
		DataInputStream din = null;
		try {
			din = new DataInputStream(soc.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.din = din;
		}
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.dout = dout;
		}
		
		this.start();
	}

	@Override
	final public void run(){
		while(true){
			receiveMessage();
		}
	}
	
	@Override
	public void sendMessage(IMessage message) {
		// TODO Marshall, encode and send to output stream
		
	}

	@Override
	protected void receiveMessage() {
		// TODO Receive, decode and unmarshall IMessage from input channel
		IMessage msg = null;
		
		this.mc.deliver(msg);
	}
}
