package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;
import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CliEngine extends ViewEngine {

    private boolean validCommand;
    private Scanner stdin;

    public CliEngine() {
        this.stdin = new Scanner(System.in);
    }

    @Override
    public void logonWizard() {

        CliEngine
                .printLineToCli("Welcome to EFTAIOS - Enjoy the CLI version of this game!");

        this.chooseProtocol();

        this.chooseGame();
    }

    @Override
    public void runEngine() {

        StringTokenizer stkn;
        CliEngine.printLineToCli("");
        while (!Thread.interrupted()) {
            CliEngine.printToCli("eftaios> ");

            stkn = new StringTokenizer(stdin.nextLine());
            if (stkn.hasMoreTokens())
                this.switchOnCommand(stkn);

        }
    }

    private void switchOnCommand(StringTokenizer stkn) {
        RequestModel request;
        String command = stkn.nextToken().toLowerCase();
        try {
            if (ActionCommand.validCommand(command)) {

                request = new ActionCommand(ActionCommand.typeOf(command))
                        .makeRequest(stkn);

                // this.printLineToCli("I'll be happy to do it in a few days...");
                // this.printLineToCli("");
                // return;
            } else {
                switch (command) {

                    case "chat":
                        request = new ChatCommand().makeRequest(stkn);
                        break;
                    default:
                        // this.printCommands();
                        return;
                }
            }
            ClientMessenger.getCurrentMessenger().executeRequestTask(request);
        } catch (InvalidParameterException pex) {
            return;
        }

    }

    @Override
    public void registerViews() {
        // TODO bind(ViewType.*, new *CliView());
        bind(ViewType.DECK, new CliDeckView());
        bind(ViewType.PARTY, new CliPartyView());
        bind(ViewType.CHAT, new CliChatView());
        bind(ViewType.ZONE, new CliZoneView());
        bind(ViewType.PLAYER, new CliPlayerView());
        bind(ViewType.CARD, new CliCardView());
        bind(ViewType.TURN, new CliTurnView());
        bind(ViewType.SECTOR, new CliSectorView());
    }

    @Override
    public void chooseProtocol() {
        StringTokenizer stkn;
        validCommand = false;
        while (!validCommand) {
            CliEngine.printLineToCli("");
            CliEngine.printLineToCli("Syntax: <rmi|socket> <host> <port>");
            CliEngine.printToCli("Connection> ");
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
            CliEngine.printLineToCli("");
            CliEngine
                    .printLineToCli("Syntax: <nickname> [<mapname> [partyname]]");
            CliEngine.printToCli("Join> ");

            stkn = new StringTokenizer(stdin.nextLine());
            if (stkn.hasMoreTokens()) {
                JoinRequest requestContent;
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

                ClientMessenger.getCurrentMessenger().loadToken(
                        requestContent.getNick());
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

    protected static void printToCli(String text) {
        System.out.print(text);
    }

    protected static void printLineToCli(String line) {
        System.out.println(line);
    }

    @Override
    public void showError(String errorMessage) {
        System.err.println("error: " + errorMessage);
    }

}
