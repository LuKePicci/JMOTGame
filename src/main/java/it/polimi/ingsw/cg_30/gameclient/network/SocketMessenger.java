package it.polimi.ingsw.cg_30.gameclient.network;

import it.polimi.ingsw.cg_30.exchange.messaging.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class SocketMessenger extends ClientMessenger {

    private Socket mySoc;
    private DataOutputStream socOut;
    private DataInputStream socIn;

    @Override
    public void sendMessage(Message msg) {

        String clearXml = Message.msgToXML(msg);
        String dataToSend = DatatypeConverter.printBase64Binary(clearXml
                .getBytes());
        try {
            this.socOut.writeUTF(dataToSend);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void connect(String host, int port) throws Exception {
        try {
            this.mySoc = new Socket(host, port);
            this.socOut = new DataOutputStream(this.mySoc.getOutputStream());
            this.socIn = new DataInputStream(this.mySoc.getInputStream());
            super.setUUID(UUID.fromString(this.socIn.readUTF()));
        } catch (IOException e) {
            throw e;
        }

    }

}
