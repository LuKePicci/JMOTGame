package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.StackedDeck;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class MatchController.
 */
public class MatchController {

    /** The turn controller. */
    protected TurnController turnController;

    /** The party controller. */
    protected PartyController partyController;

    /** The zone controller. */
    protected ZoneController zoneController;

    /** The match model. */
    protected Match match;

    /** The max turn number. */
    private static final int MAX_TURN = 39;

    /** The server word text. */
    private String serverWordText = "Server";

    /**
     * Obtains party players.
     *
     * @return the list of players of the current party
     */
    public List<Player> obtainPartyPlayers() {
        return new ArrayList<Player>(this.partyController.getCurrentParty()
                .getMembers().keySet());
    }

    /**
     * Sends all the model needed in order to star a new match.
     */
    private void modelSender() {
        // map
        this.partyController.sendMessageToParty(new Message(this.zoneController
                .getCurrentZone().getViewModel()));
        // players
        this.partyController.sendMessageToParty(new Message(
                this.partyController.getCurrentParty().getViewModel()));
        // players' cards
        for (Player player : obtainPartyPlayers()) {
            ViewModel model = player.getItemsDeck().getViewModel();
            try {
                MessageController
                        .getPlayerHandler(
                                this.partyController.getCurrentParty()
                                        .getPlayerUUID(player))
                        .getAcceptPlayer().sendMessage(new Message(model));
            } catch (DisconnectedException e) {
                // This player will not be able to play until he reconnects.
            }
        }
        // chat
        this.partyController.sendMessageToParty(new ChatMessage(
                new ChatViewModel("Game started", serverWordText,
                        ChatVisibility.PARTY)));
    }

    /**
     * Establishes the role of each player.
     */
    private void establishRoles() {
        List<Player> players = this.obtainPartyPlayers();
        Collections.shuffle(players);
        StackedDeck<PlayerCard> playerCardDeck = StackedDeck
                .newStackedDeckPlayer();
        for (Player player : players) {
            player.setIdentity(playerCardDeck.pickCard());
        }
    }

    /**
     * Notifies each player about his role.
     */
    private void sayRoles() {
        List<Player> players = obtainPartyPlayers();
        for (Player player : players) {
            if (PlayerRace.ALIEN.equals(player.getIdentity().getRace())) {
                notifyAPlayerAbout(player, "You are an alien, start hunting!");
            } else {
                notifyAPlayerAbout(player, "You are a human, start running!");
            }
        }
    }

    /**
     * Initializes the match.
     *
     * @param partyController
     *            the party controller
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    public void initMatch(PartyController partyController)
            throws FileNotFoundException, URISyntaxException {
        this.partyController = partyController;
        this.match = new Match();
        this.turnController = new TurnController();

        // map preparation
        EftaiosGame game = (EftaiosGame) partyController.getCurrentParty()
                .getGame();
        ZoneFactory zf = new TemplateZoneFactory(game.getMapName());
        this.zoneController = new ZoneController(zf);

        establishRoles(); // roles assignement
        List<Player> playerList = obtainPartyPlayers();
        this.zoneController.placePlayers(playerList); // put players on starts
        this.turnController.firstTurn(playerList); // first turn preparation
        this.modelSender(); // send the models
        this.sayRoles(); // inform every player abotu his role
        // inform the party about the first turn
        this.partyController.sendMessageToParty(new ChatMessage(
                new ChatViewModel("It's "
                        + this.turnController.getTurn().getCurrentPlayer()
                                .getName() + "'s turn", serverWordText,
                        ChatVisibility.PARTY)));
    }

    /**
     * Gets the turn controller.
     *
     * @return the turn controller
     */
    public TurnController getTurnController() {
        return this.turnController;
    }

    /**
     * Gets the party controller.
     *
     * @return the party controller
     */
    public PartyController getPartyController() {
        return this.partyController;
    }

    /**
     * Gets the zone controller.
     *
     * @return the zone controller
     */
    public ZoneController getZoneController() {
        return this.zoneController;
    }

    /**
     * Gets the match.
     *
     * @return the match
     */
    public Match getMatch() {
        return this.match;
    }

    /**
     * Kills the player "killedPlayer".
     *
     * @param killedPlayer
     *            the player to be killed
     */
    public void killed(Player killedPlayer) {

        // checks defense card
        if (PlayerRace.HUMAN.equals(killedPlayer.getIdentity().getRace())) {
            for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
                if (Item.DEFENSE.equals(card.getItem())) {
                    this.match.getItemsDeck().putIntoBucket(card);
                    killedPlayer.getItemsDeck().getCards().remove(card);
                    notifyPartyFromPlayer(killedPlayer, "DEFENSE CARD");
                    showCardToParty(card);
                    try {
                        updateDeckView(killedPlayer);
                    } catch (DisconnectedException e) {
                        // do not push this model, will be retrieved manually on
                        // reconnect
                    }
                    this.turnController.getTurn().changeHumanKilled(-1);
                    this.turnController.getTurn().getCurrentPlayer()
                            .decrementKillsCount();
                    return;
                }
            }
        }

        // adds killedPlayer among dead players
        this.match.getDeadPlayer().add(killedPlayer);
        // informs killedPlayer that he is dead
        notifyAPlayerAbout(killedPlayer, "You are dead");
        // informs the other players about killedPlayer's identity
        List<Player> others = obtainPartyPlayers();
        others.remove(killedPlayer);
        for (Player otherPlayer : others) {
            notifyAPlayerAbout(otherPlayer, "The "
                    + killedPlayer.getIdentity().getRace().toString() + " "
                    + killedPlayer.getName() + " is dead");
        }
        // discards killedPlayer's cards
        for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
            this.match.getItemsDeck().putIntoBucket(card);
            killedPlayer.getItemsDeck().getCards().remove(card);
        }
        try {
            updateDeckView(killedPlayer);
        } catch (DisconnectedException e) {
            // do not push this model, will be retrieved manually on reconnect
        }
        // killedPlayer have to disappear from the map
        this.zoneController.getCurrentZone().movePlayer(killedPlayer, null);
        try {
            updateMapView(killedPlayer);
        } catch (DisconnectedException e) {
            // do not push this model, will be retrieved manually on reconnect
        }
        // note: incrementKillCount has already been done in Attack
    }

    /**
     * Gets the human players of the party.
     *
     * @return the human players
     */
    private Set<Player> getHumanPlayers() {
        Set<Player> humanPlayers = new HashSet<Player>();
        Set<Player> playerList = this.turnController.getPartyPlayers(this);
        for (Player thisPlayer : playerList) {
            if (PlayerRace.HUMAN.equals(thisPlayer.getIdentity().getRace())) {
                humanPlayers.add(thisPlayer);
            }
        }
        return humanPlayers;
    }

    /**
     * Gets the alien players of the party.
     *
     * @return the alien players
     */
    private Set<Player> getAlienPlayers() {
        Set<Player> alienPlayers = new HashSet<Player>();
        Set<Player> playerList = this.turnController.getPartyPlayers(this);
        for (Player thisPlayer : playerList) {
            if (PlayerRace.ALIEN.equals(thisPlayer.getIdentity().getRace())) {
                alienPlayers.add(thisPlayer);
            }
        }
        return alienPlayers;
    }

    /**
     * Utility method: all aliens win, rescued humans win, remaining humans
     * lose.
     */
    private void partialVictory() {
        Set<Player> playerList = this.turnController.getPartyPlayers(this);
        // aliens won
        sayYouWin(getAlienPlayers());
        // escaped humans won
        sayYouWin(match.getRescuedPlayer());
        // remaining humans lost
        playerList.removeAll(getAlienPlayers());
        playerList.removeAll(match.getRescuedPlayer());
        sayYouLose(playerList);
    }

    /**
     * Say the players losers lost this match.
     *
     * @param losers
     *            the losers
     */
    protected void sayYouLose(Set<Player> losers) {
        for (Player loser : losers) {
            try {
                MessageController
                        .getPlayerHandler(
                                this.partyController.getCurrentParty()
                                        .getPlayerUUID(loser))
                        .getAcceptPlayer()
                        .sendMessage(
                                new Message(new ChatViewModel(
                                        "GAME OVER\nYOU LOSE", serverWordText,
                                        ChatVisibility.PLAYER)));
            } catch (DisconnectedException e) {
                // this player won't know that he lost
            }
        }
    }

    /**
     * Say the players winners won this match.
     *
     * @param winners
     *            the winners
     */
    protected void sayYouWin(Set<Player> winners) {
        for (Player winner : winners) {
            try {
                MessageController
                        .getPlayerHandler(
                                this.partyController.getCurrentParty()
                                        .getPlayerUUID(winner))
                        .getAcceptPlayer()
                        .sendMessage(
                                new Message(new ChatViewModel(
                                        "GAME OVER\nYOU WIN", serverWordText,
                                        ChatVisibility.PLAYER)));
            } catch (DisconnectedException e) {
                // this player won't know that he won
            }
        }
    }

    /**
     * Checks if the game has come to its end.
     */
    public void checkEndGame() {
        Set<Player> playerList = this.turnController.getPartyPlayers(this);
        int playerNumber = playerList.size();
        int humanNumber = playerNumber / 2;
        int deadHumans = 0;
        for (Player thisPlayer : this.match.getDeadPlayer()) {
            if (PlayerRace.HUMAN.equals(thisPlayer.getIdentity().getRace())) {
                deadHumans++;
            }
        }

        // ALL HUMANS ARE DEAD
        if (deadHumans == humanNumber) {
            // humans lost
            sayYouLose(getHumanPlayers());
            // aliens won
            sayYouWin(getAlienPlayers());
        }

        // ALL HUMANS ARE ESCAPED
        else if (this.match.getRescuedPlayer().size() == humanNumber) {
            // humans won
            sayYouWin(getHumanPlayers());
            // aliens lost
            sayYouLose(getAlienPlayers());
        }

        // ALIENS KILLED THE LAST HUMAN
        else if (((deadHumans + this.match.getRescuedPlayer().size()) == humanNumber)
                && (this.turnController.getTurn().getHumanKilled() > 0)) {
            // aliens won
            sayYouWin(getAlienPlayers());
            // escaped humans won
            sayYouWin(this.match.getRescuedPlayer());
            // dead humans lost
            this.match.getDeadPlayer().removeAll(getAlienPlayers());
            sayYouLose(this.match.getDeadPlayer());
        }

        // THE LAST HUMAN ESCAPED
        else if (((deadHumans + this.match.getRescuedPlayer().size()) == humanNumber)
                && (this.turnController.getTurn().getHumanKilled() == 0)) {
            // aliens lost
            sayYouLose(getAlienPlayers());
            // escaped humans won
            sayYouWin(this.match.getRescuedPlayer());
            // dead humans lost
            this.match.getDeadPlayer().removeAll(getAlienPlayers());
            sayYouLose(this.match.getDeadPlayer());
        }

        // END OF 39th TURN (count from 1) or NO MORE HATCHES AVAILABLE
        else if (this.match.getTurnCount() == (MAX_TURN + 1)
                || this.zoneController.noMoreHatches()) {
            partialVictory();
        }

    }

    /**
     * Process the action request.
     *
     * @param req
     *            the action request
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public synchronized void processActionRequest(ActionRequest req)
            throws DisconnectedException {
        ActionController act;
        try {
            act = ActionController.getStrategy(req);
            act.initAction(this, req);
            if (act.isValid())
                act.processAction();
            else
                MessageController
                        .getPlayerHandler(
                                this.partyController.getCurrentParty()
                                        .getPlayerUUID(
                                                turnController.getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(new ChatViewModel(
                                        "Sorry, you can't do this",
                                        serverWordText, ChatVisibility.PLAYER)));
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Log this exception
            System.out
                    .println("Failed to instanciate a controller for requested action.");
        }
    }

    /**
     * Notifies the string received to the player received using server as
     * sender.
     *
     * @param player
     *            the player
     * @param about
     *            the string to notify
     */
    protected void notifyAPlayerAbout(Player player, String about) {
        try {
            MessageController
                    .getPlayerHandler(
                            this.partyController.getCurrentParty()
                                    .getPlayerUUID(player))
                    .getAcceptPlayer()
                    .sendMessage(
                            new ChatMessage(new ChatViewModel(about,
                                    serverWordText, ChatVisibility.PLAYER)));
        } catch (DisconnectedException e) {
            // TODO Enqueue this notification for later dispatch
        }
    }

    /**
     * Notifies the string received to the party using the player received as
     * sender.
     *
     * @param player
     *            the sender
     * @param what
     *            the string to notify
     */
    protected void notifyPartyFromPlayer(Player player, String what) {
        this.partyController
                .sendMessageToParty(new ChatMessage(new ChatViewModel(what,
                        player.getName(), ChatVisibility.PARTY)));
    }

    /**
     * Shows the card received to the party.
     *
     * @param card
     *            the card to notify
     */
    protected void showCardToParty(Card card) {
        this.partyController.sendMessageToParty(new Message(card));
    }

    /**
     * Updates cards view for the player.
     *
     * @param player
     *            the player
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void updateDeckView(Player player) throws DisconnectedException {
        MessageController
                .getPlayerHandler(
                        this.partyController.getCurrentParty().getPlayerUUID(
                                player)).getAcceptPlayer()
                .sendMessage(new Message(player.getItemsDeck().getViewModel()));
    }

    /**
     * Updates map view for the player.
     *
     * @param player
     *            the player
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void updateMapView(Player player) throws DisconnectedException {
        ZoneViewModel viewModel = (ZoneViewModel) this.zoneController
                .getCurrentZone().getViewModel();
        viewModel.setPlayerLocation(this.zoneController.getCurrentZone()
                .getCell(player));
        MessageController
                .getPlayerHandler(
                        this.partyController.getCurrentParty().getPlayerUUID(
                                player)).getAcceptPlayer()
                .sendMessage(new Message(viewModel));
    }

}
