package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CliEngine extends ViewEngine {

    private boolean validCommand;
    private Scanner stdin;

    public CliEngine() {
        this.stdin = new Scanner(System.in);
    }

    @Override
    public void logoonWizard() {

        this.printLineToCli("Welcome to EFTAIOS - Enjoy the CLI version of this game!");

        this.chooseProtocol();

        this.chooseGame();
    }

    @Override
    public void runEngine() {

        StringTokenizer stkn;
        this.printLineToCli("");
        while (!Thread.interrupted()) {
            this.printToCli("eftaios> ");

            stkn = new StringTokenizer(stdin.nextLine());
            if (stkn.hasMoreTokens()) {
                this.printLineToCli("I'll be happy to do it in a few days...");
                this.printLineToCli("");
            }
        }
    }

    @Override
    public void registerViews() {
        // TODO bind(ViewType.*, new *CliView());
    }

    @Override
    public void chooseProtocol() {
        StringTokenizer stkn;
        validCommand = false;
        while (!validCommand) {
            this.printLineToCli("");
            this.printLineToCli("Syntax: <rmi|socket> <host> <port>");
            this.printToCli("Connection> ");
            stkn = new StringTokenizer(stdin.nextLine());
            if (!stkn.hasMoreTokens())
                continue;
            URI serverURI;
            try {
                if (stkn.countTokens() == 3) {

                    serverURI = new URI(stkn.nextToken(), null,
                            stkn.nextToken(),
                            Integer.parseInt(stkn.nextToken()), null, null,
                            null);

                } else if (stkn.countTokens() == 2) {
                    serverURI = new URI("default", null, stkn.nextToken(),
                            Integer.parseInt(stkn.nextToken()), null, null,
                            null);
                } else {
                    this.showError("bad syntax, not enough arguments");
                    continue;
                }
            } catch (NumberFormatException | URISyntaxException e) {
                this.showError("malformed server address");
                continue;
            }

            try {
                ClientMessenger.connectToServer(serverURI);
                validCommand = true;
            } catch (Exception ex) {
                this.showError("cannot connect");
            }
        }

    }

    @Override
    public void chooseGame() {
        StringTokenizer stkn;
        validCommand = false;
        while (!validCommand) {
            this.printLineToCli("");
            this.printLineToCli("Syntax: <nickname> [<mapname> [partyname]]");
            this.printToCli("Join> ");

            stkn = new StringTokenizer(stdin.nextLine());
            if (stkn.hasMoreTokens()) {
                RequestModel requestContent;
                switch (stkn.countTokens()) {
                    case 3:
                        requestContent = new RequestComposer()
                                .createJoinRequest(stkn.nextToken(),
                                        stkn.nextToken(), stkn.nextToken());
                        break;
                    case 2:
                        requestContent = new RequestComposer()
                                .createJoinRequest(stkn.nextToken(),
                                        stkn.nextToken());
                        break;
                    case 1:
                        requestContent = new RequestComposer()
                                .createJoinRequest(stkn.nextToken());
                        break;
                    default:
                        this.showError("bad syntax, too many arguments");
                        continue;
                }

                ClientMessenger.getCurrentMessenger().executeRequestTask(
                        requestContent);
                break;
            }
        }

    }

    @Override
    public void showGames() {
        // TODO Auto-generated method stub

    }

    private void printToCli(String text) {
        System.out.print(text);
    }

    private void printLineToCli(String line) {
        System.out.println(line);
    }

    @Override
    public void showError(String errorMessage) {
        System.err.println("error: " + errorMessage);
    }

}
