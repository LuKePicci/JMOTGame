package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CliEngine extends ViewEngine {

    private boolean validCommand;

    @Override
    public void runEngine() {

        this.printLineToCli("Welcome to EFTAIOS - Enjoy the CLI version of this game!");

        this.chooseProtocol();

        this.chooseGame();
    }

    @Override
    public void chooseProtocol() {
        try (Scanner s = new Scanner(System.in)) {
            while (!validCommand) {
                this.printLineToCli("");
                this.printLineToCli("Syntax: <rmi|socket> <host> <port>");
                this.printToCli("Connection> ");
                StringTokenizer stkn = new StringTokenizer(s.nextLine());
                URI serverURI;
                try {
                    if (stkn.countTokens() == 3) {

                        serverURI = new URI(stkn.nextToken(), null,
                                stkn.nextToken(), Integer.parseInt(stkn
                                        .nextToken()), null, null, null);

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

    }

    @Override
    public void chooseGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateViews() {
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
