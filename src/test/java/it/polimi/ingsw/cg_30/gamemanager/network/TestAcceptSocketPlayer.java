package it.polimi.ingsw.cg_30.gamemanager.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Semaphore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAcceptSocketPlayer extends Thread {

    public static Socket soc;
    public static AcceptSocketPlayer ap;
    public static TestAcceptSocketPlayer sckTest;
    public static String testData = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8TWVzc2FnZSBNZXNzYWdlVHlwZT0iQ0hBVF9NRVNTQUdFIj4KICAgIDxSZXF1ZXN0IHhzaTp0eXBlPSJjaGF0UmVxdWVzdCIgVmlzaWJpbGl0eT0iUFVCTElDIiBEYXRlPSIxOTcwLTAxLTAxVDAxOjAwOjAwKzAxOjAwIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj4KICAgICAgICA8VGV4dD5NZXNzYWdlIHRlc3Rpbmc8L1RleHQ+CiAgICA8L1JlcXVlc3Q+CjwvTWVzc2FnZT4K";
    public static Semaphore sem;

    @Before
    public void initServerSocket() {
        sem = new Semaphore(0);
        sckTest = new TestAcceptSocketPlayer();
        try {
            sckTest.server = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sckTest.start();

        try {
            sem.acquire();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @After
    public void closeServerSocket() {
        try {

            soc.close();

            sckTest.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sckTest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ServerSocket server;
    int localPort;

    @Override
    public void run() {
        try {
            sckTest.server.setReuseAddress(true);
            sckTest.server.bind(new InetSocketAddress(0));
            sckTest.localPort = sckTest.server.getLocalPort();
            System.out.println("Choosed port: " + sckTest.localPort);
            sem.release();
            soc = server.accept();
            ap = new AcceptSocketPlayer(soc);
            sem.release();
        } catch (IOException e) {
            e.printStackTrace();
            sem.release(2);
            this.interrupt();
        }
    }

    private static DataOutputStream cdout;

    @Test
    public void shouldReceiveMessage() {
        RequestModel received = null;
        try {
            Socket client = new Socket("127.0.0.1", sckTest.localPort);
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cdout = new DataOutputStream(client.getOutputStream());

            Thread sender = new Thread() {
                @Override
                public void run() {
                    try {
                        cdout.writeUTF(testData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            sender.start();

            try {
                sender.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                client.close();
            }

            received = ap.receiveMessage().getRawRequest();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(received != null);
        assertEquals("Message testing", ((ChatRequest) received).getText());
    }

    @Test
    public void shouldSendMessage() throws DisconnectedException {
        try {
            Socket client = new Socket("127.0.0.1", sckTest.localPort);
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String prevSentData = ap.getLastSentData();
            ap.sendMessage(new Message(new JoinRequest("Player",
                    new EftaiosGame())));
            assertNotEquals(prevSentData, ap.getLastSentData());

            ChatRequest chat = new ChatRequest("Message testing",
                    ChatVisibility.PUBLIC, null);
            chat.setDate(new Date(0L));
            prevSentData = ap.getLastSentData();
            ap.sendMessage(new ChatMessage(chat));
            assertNotEquals(prevSentData, ap.getLastSentData());

            client.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
