package it.polimi.ingsw.cg_30;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class AcceptSocketPlayer extends AcceptPlayer implements Runnable {

    private static final long serialVersionUID = -5365149375010707737L;

    private final transient Socket mySoc;
    private final transient DataInputStream din;
    private final transient DataOutputStream dout;
    private final transient Marshaller messageMarshaller;
    private final transient Unmarshaller messageUnmarshaller;

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

        Marshaller msl = null;
        Unmarshaller unmsl = null;
        try {
            JAXBContext ctx;
            ctx = JAXBContext.newInstance(Message.class);
            msl = ctx.createMarshaller();
            msl.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            unmsl = ctx.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            this.messageMarshaller = msl;
            this.messageUnmarshaller = unmsl;
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
                try {
                    this.mySoc.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Socket " + this.mySoc.hashCode()
                        + " closed because of " + e.toString());
            } catch (Exception e) {
                System.out
                        .println("Failed to decode user message, see log for details.");
            }
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            // Marshall, encode and send Message objects to output stream
            String clearXml = this.msgToXML(message);
            String dataToSend = DatatypeConverter.printBase64Binary(clearXml
                    .getBytes());

            this.dout.writeUTF(dataToSend);
            this.dout.flush();
            this.lastSentData = dataToSend;
        } catch (IOException e) {
            try {
                this.mySoc.close();
            } catch (IOException e1) {
            }
            System.out.println("Socket " + this.mySoc.hashCode()
                    + " closed because of " + e.toString());
        }
    }

    @Override
    protected Message receiveMessage() throws IOException {
        // Receive, decode and unmarshall Message objects from input stream
        String encoded = this.din.readUTF();
        this.lastMessage = new Date();
        String decodedXml = new String(
                DatatypeConverter.parseBase64Binary(encoded));

        return this.msgFromXML(decodedXml);
    }

    private String msgToXML(Message msg) {
        StringWriter sw = new StringWriter();
        try {
            messageMarshaller.marshal(msg, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    private Message msgFromXML(String xml) {
        Message msg = null;
        try {
            msg = (Message) this.messageUnmarshaller
                    .unmarshal(new StringReader(xml));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
