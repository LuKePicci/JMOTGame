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
    private transient final Socket mySoc;
    private transient final DataInputStream din;
    private transient final DataOutputStream dout;
    // private final Map<MessageType, Marshaller> marshallers;
    private transient final Marshaller messageMarshaller;
    private transient final Unmarshaller messageUnmarshaller;

    private String lastSentData = null;

    public AcceptSocketPlayer(Socket soc) {
        this(UUID.randomUUID(), soc);
    }

    public AcceptSocketPlayer(UUID sid, Socket soc) {
        super(sid);
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

        // this.marshallers = new HashMap<MessageType, Marshaller>();
        Marshaller msl = null;
        Unmarshaller unmsl = null;
        try {
            JAXBContext ctx;
            /*
             * for (MessageType t : MessageType.values()) { ctx =
             * JAXBContext.newInstance(t.linkedClass());
             * 
             * this.marshallers.put(t, ctx.createMarshaller()); // for pretty
             * printing this.marshallers.get(t).setProperty(
             * Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); }
             */
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
    final public void run() {
        while (this.mySoc.isConnected() && !this.mySoc.isClosed()
                && !Thread.interrupted()) {
            try {
                this.mc.deliver(receiveMessage());
            } catch (IOException e) {
                try {
                    this.mySoc.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Socket " + this.mySoc.hashCode()
                        + " closed because of " + e.toString());
            }
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            // Marshall, encode and send Message objects to output stream
            String clearXml = this.msgToXML(message);

            // byte[] encodedBytes = Base64.getEncoder().encode(
            // clearXml.getBytes());
            // String dataToSend = new String(encodedBytes);

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

        // byte[] decodedBytes = Base64.getDecoder().decode(encoded.getBytes());
        // String decodedXml = new String(decodedBytes);

        String decodedXml = new String(
                DatatypeConverter.parseBase64Binary(encoded));

        return this.msgFromXML(decodedXml);
    }

    private String msgToXML(Message msg) {
        StringWriter sw = new StringWriter();
        try {
            // this.marshallers.get(msg.getType()).marshal(msg, sw);
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

            // Class<Message> msgClass = msg.getType().linkedClass();
            // msgClass.cast(msg);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
