package it.polimi.ingsw.cg_30.gamemanager.network;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSocketAcceptance {

    static Thread serverThread;
    static SocketAcceptance server;
    static Semaphore sem = new Semaphore(0);

    public int randomPort;

    @BeforeClass
    public static void initAcceptance() {
        server = new SocketAcceptance();
        server.randomizePort();
        serverThread = new Thread(server, "TestServer");
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        sem.release();
    }

    @AfterClass
    public static void closeAcceptance() {
        server.stopServer();
        ;
        try {
            serverThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAcceptMoreSocketClients() throws IOException {

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            Socket soc = new Socket("127.0.0.1", server.getRandomPort());
            soc.close();
        }
    }

}
