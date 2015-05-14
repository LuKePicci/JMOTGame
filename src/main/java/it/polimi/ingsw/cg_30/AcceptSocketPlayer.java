package it.polimi.ingsw.cg_30;

import java.io.*;
import java.net.*;
import java.util.Date;

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
	
	private void pingTrue(){
		try {
			dout.writeBoolean(true);
		} catch (IOException e) {
			e.printStackTrace();
			this.interrupt();
		}
	}

	@Override
	final public void run(){
		this.pingTrue();
		while(this.mySoc.isConnected() && !this.mySoc.isClosed() && !this.isInterrupted()){
			receiveMessage();
		}
	}
	
	@Override
	public void sendMessage(IMessage message) {
		// TODO Marshall, encode and send to output stream
		
	}

	@Override
	protected void receiveMessage() {
		try {
			din.readUTF();
			
			this.lastMessage = new Date();
			// TODO Receive, decode and unmarshall IMessage from input channel
			IMessage msg = null;
			
			this.mc.deliver(msg);
		} catch (IOException e) {
			try {
				this.mySoc.close();
			} catch (IOException e1) { }
			System.out.println("Socket " + this.mySoc.hashCode() + " closed because of " + e.toString());
			
			this.interrupt();
		}
	}
}
