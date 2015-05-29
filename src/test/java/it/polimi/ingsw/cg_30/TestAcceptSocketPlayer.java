package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public static String testData = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8TWVzc2FnZSBNZXNzYWdlVHlwZT0iQ0hBVF9NRVNTQUdFIj4KICAgIDxDb250ZW50IHhzaTp0eXBlPSJjaGF0UmVxdWVzdCIgVGV4dD0iTWVzc2FnZSB0ZXN0aW5nIiBEYXRlPSIxOTcwLTAxLTAxVDAxOjAwOjAwKzAxOjAwIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIi8+CjwvTWVzc2FnZT4K";
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

            received = ap.receiveMessage().getRawContent();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(received != null);
        assertEquals("Message testing", ((ChatRequest) received).getText());
        System.out.println("Test succeeded: shouldReceiveMessage");
    }

    @Test
    public void shouldSendMessage() {
        try {
            Socket client = new Socket("127.0.0.1", sckTest.localPort);
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ChatRequest chat = new ChatRequest("Message testing");
            chat.setDate(new Date(0L));
            ap.sendMessage(new ChatMessage(chat));

            String utfData = ap.getLastSentData();
            client.close();
            assertEquals(testData, utfData);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Test succeeded: shouldSendMessage");
    }
}
