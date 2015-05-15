package it.polimi.ingsw.cg_30;

import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class AcceptSocketPlayer extends AcceptPlayer {
	private final Socket mySoc;
	private final DataInputStream din;
	private final DataOutputStream dout;
	private final Map<MessageType, Marshaller> marshallers;
	private final Unmarshaller unmarshaller;

	private String lastSentData = null;

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

		this.marshallers = new HashMap<MessageType, Marshaller>();
		Unmarshaller unmsl = null;
		try {
			JAXBContext ctx;
			for (MessageType t : MessageType.values()) {
				ctx = JAXBContext.newInstance(t.linkedClass());

				this.marshallers.put(t, ctx.createMarshaller());
				// for pretty printing
				this.marshallers.get(t).setProperty(
						Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			}

			ctx = JAXBContext.newInstance(Message.class);
			unmsl = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			this.unmarshaller = unmsl;
		}

		this.start();
	}

	private void ping() {
		try {
			dout.writeBoolean(false);
		} catch (IOException e) {
			e.printStackTrace();
			this.interrupt();
		}
	}

	public String getLastSentData() {
		return this.lastSentData;
	}

	@Override
	final public void run() {
		this.ping();
		while (this.mySoc.isConnected() && !this.mySoc.isClosed()
				&& !this.isInterrupted()) {
			try {
				this.mc.deliver(receiveMessage());
			} catch (IOException e) {
				try {
					this.mySoc.close();
				} catch (IOException e1) { }
				
				System.out.println("Socket " + this.mySoc.hashCode()
						+ " closed because of " + e.toString());

				this.interrupt();
			}
		}
	}

	@Override
	public void sendMessage(Message message) {
		try {
			// Marshall, encode and send Message objects to output stream
			String clearXml = this.msgToXML(message);
			byte[] encodedBytes = Base64.getEncoder().encode(
					clearXml.getBytes());

			String dataToSend = new String(encodedBytes);
			this.dout.writeUTF(dataToSend);
			this.lastSentData = dataToSend;
		} catch (IOException e) {
			try {
				this.mySoc.close();
			} catch (IOException e1) {
			}
			System.out.println("Socket " + this.mySoc.hashCode()
					+ " closed because of " + e.toString());

			this.interrupt();
		}
	}

	@Override
	protected Message receiveMessage() throws IOException {
		// Receive, decode and unmarshall Message objects from input stream
		String encoded = this.din.readUTF();

		this.lastMessage = new Date();

		byte[] decodedBytes = Base64.getDecoder().decode(encoded.getBytes());
		String decodedXml = new String(decodedBytes);
		return this.msgFromXML(decodedXml);
	}

	private String msgToXML(Message msg) {
		StringWriter sw = new StringWriter();
		try {
			this.marshallers.get(msg.getType()).marshal(msg, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	private Message msgFromXML(String xml) {
		Message msg = null;
		try {
			msg = (Message) this.unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return msg;
	}
}
