package it.polimi.ingsw.cg_30.gamemanager;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.gamemanager.network.PlayerAcceptance;
import it.polimi.ingsw.cg_30.gamemanager.network.RmiAcceptance;
import it.polimi.ingsw.cg_30.gamemanager.network.SocketAcceptance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {
    private static final int SHUTDOWN_TIME = 10;
    private final List<PlayerAcceptance> servers;
    private static final ExecutorService es = Executors.newCachedThreadPool();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                System.out.println("Shutting down...");
                es.shutdown();
                try {
                    if (!es.awaitTermination(SHUTDOWN_TIME, TimeUnit.SECONDS)) {
                        LoggerMethods
                                .warning("Executor did not terminate in the specified time.");
                        List<Runnable> droppedTasks = es.shutdownNow();
                        LoggerMethods
                                .warning("Executor was abruptly shut down. "
                                        + droppedTasks.size()
                                        + " tasks will not be executed.");
                    }
                } catch (InterruptedException e) {
                    LoggerMethods.interruptedException(e, "");
                }
            }
        });
    }

    private GameServer() {
        this.servers = new ArrayList<PlayerAcceptance>();
    }

    public static void main(String[] args) {
        GameServer gm = new GameServer();
        gm.startServers();
    }

    public static void execute(Runnable task) {
        es.execute(task);
    }

    private void startServers() {
        PlayerAcceptance socketServer = new SocketAcceptance();
        this.servers.add(socketServer);
        PlayerAcceptance rmiServer = new RmiAcceptance();
        this.servers.add(rmiServer);
        execute(socketServer);
        execute(rmiServer);
    }
}
