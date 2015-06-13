package it.polimi.ingsw.cg_30.gamemanager.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class AcceptSocketPlayer extends AcceptPlayer implements Runnable {

    private final transient Socket mySoc;
    private final transient DataInputStream din;
    private final transient DataOutputStream dout;

    private String lastSentData = null;

    public AcceptSocketPlayer(Socket soc) {
        this(UUID.randomUUID(), soc);
    }

    public AcceptSocketPlayer(UUID sid, Socket soc) {
        super(sid);
        this.mySoc = soc;

        DataInputStream tempDin = null;
        try {
            tempDin = new DataInputStream(soc.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.din = tempDin;
        }

        DataOutputStream tempDout = null;
        try {
            tempDout = new DataOutputStream(soc.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.dout = tempDout;
        }

    }

    @Override
    public void ping() {
        try {
            dout.writeUTF(this.sessionId.toString());
        } catch (IOException e) {
            try {
                this.mySoc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String getLastSentData() {
        return this.lastSentData;
    }

    @Override
    public final void run() {
        this.ping();
        while (this.mySoc.isConnected() && !this.mySoc.isClosed()
                && !Thread.interrupted()) {
            try {
                this.mc.dispatchIncoming(receiveMessage());
            } catch (IOException e) {

                this.disconnect(e);

            } catch (Exception e) {
                System.out
                        .println("Failed to decode user message, see log for details.");
            }
        }
    }

    @Override
    public void sendMessage(Message message) throws DisconnectedException {
        if (this.connectionLost())
            throw new DisconnectedException(new Date());

        try {
            // Marshall, encode and send Message objects to output stream
            String clearXml = Message.msgToXML(message);
            String dataToSend = DatatypeConverter.printBase64Binary(clearXml
                    .getBytes());

            this.dout.writeUTF(dataToSend);
            this.dout.flush();
            this.lastSentData = dataToSend;
        } catch (IOException e) {
            this.disconnect(e);
            throw new DisconnectedException(new Date());
        }
    }

    @Override
    protected Message receiveMessage() throws IOException {
        // Receive, decode and unmarshall Message objects from input stream
        String encoded = this.din.readUTF();
        this.lastMessage = new Date();
        String decodedXml = new String(
                DatatypeConverter.parseBase64Binary(encoded));

        return Message.msgFromXML(decodedXml);
    }

    private void disconnect(Exception reason) {
        try {
            this.mySoc.close();
            System.out.println("Player socket " + this.mySoc.hashCode()
                    + " closed because of " + reason.toString());
        } catch (IOException e1) {
            System.out.println("Socket " + this.mySoc.hashCode()
                    + " already closed");
        }

        this.hasLostConnection = true;
    }
}
