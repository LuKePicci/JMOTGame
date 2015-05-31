package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAcceptance extends PlayerAcceptance {

    public static final int DEFAULT_SERVER_PORT = 22222;
    private boolean randomizePort = false;

    private ServerSocket soc;
    private int randomPort = 0;

    public SocketAcceptance() {
        try {
            this.soc = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randomizePort() {
        this.randomizePort = true;
    }

    public void stopServer() {
        try {
            if (!soc.isClosed())
                this.soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void socketAccept() throws IOException {
        Socket cSoc = soc.accept();
        AcceptSocketPlayer gameClient = new AcceptSocketPlayer(cSoc);
        GameServer.execute(gameClient);
        this.connections.add(gameClient);
    }

    @Override
    protected void acceptance() {
        Thread.currentThread().setName("SocketServerAcceptance");
        try {
            soc.setReuseAddress(true);
            soc.bind(new InetSocketAddress(randomizePort ? 0
                    : DEFAULT_SERVER_PORT));
            this.randomPort = soc.getLocalPort();
            while (!Thread.interrupted()) {
                try {
                    this.socketAccept();
                } catch (IOException e) {
                    System.out.println("Server " + soc.hashCode()
                            + " closed because of " + e.getMessage());
                    return;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (soc != null && !soc.isClosed())
                    soc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRandomPort() {
        return this.randomPort;
    }

}
