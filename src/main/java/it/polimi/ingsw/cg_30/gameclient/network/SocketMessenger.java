package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.gameclient.GameClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class SocketMessenger extends ClientMessenger implements Runnable {

    private Socket mySoc;
    private DataOutputStream socOut;
    private DataInputStream socIn;

    @Override
    public synchronized void sendMessage(Message msg) {

        String clearXml = Message.msgToXML(msg);
        String dataToSend = DatatypeConverter.printBase64Binary(clearXml
                .getBytes());
        try {
            this.socOut.writeUTF(dataToSend);
        } catch (IOException e) {
            GameClient.getActiveEngine().showError("connection lost");
        }
    }

    @Override
    public void connect(String host, int port) throws Exception {
        try {
            this.mySoc = new Socket(host, port);
            this.socOut = new DataOutputStream(this.mySoc.getOutputStream());
            this.socIn = new DataInputStream(this.mySoc.getInputStream());
            threadPool.execute(this);
        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    public void run() {
        String receivedData, clearXml;
        Message receivedMessage;
        while (!Thread.interrupted() && !this.mySoc.isClosed()) {
            try {
                receivedData = this.socIn.readUTF();
                clearXml = new String(
                        DatatypeConverter.parseBase64Binary(receivedData));
                receivedMessage = Message.msgFromXML(clearXml);
                this.receiveMessage(receivedMessage);
            } catch (IOException e) {
                try {
                    this.mySoc.close();
                } catch (IOException e1) {
                    // BTW...
                }
            }
        }
    }
}
