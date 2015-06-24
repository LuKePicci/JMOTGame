package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.EftaiosDecks;
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

    /** The server word text. */
    protected String serverWordText = "Server";

    /**
     * The sector where a player is moved after he had escaped or had been
     * killed.
     */
    protected final Sector endingSector = new Sector(SectorType.SECURE,
            HexPoint.fromOffset(26, 1));

    /**
     * Obtains party players.
     *
     * @return the list of players of the current party
     */
    protected List<Player> obtainPartyPlayers() {
        return new ArrayList<Player>(this.partyController.getCurrentParty()
                .getMembers().keySet());
    }

    /**
     * Sends all the model needed in order to star a new match.
     *
     * @throws DisconnectedException
     *             the disconnected exception
     */
    private void modelSender() {
        // clean map
        this.sendMapViewToParty();

        // party
        this.updatePartyToAllPlayers();

        // players' cards and position
        for (Player player : obtainPartyPlayers()) {
            try {
                this.updateDeckView(player);
                this.sendMapVariationToPlayer(player, zoneController
                        .getCurrentZone().getCell(player),
                        SectorHighlight.PLAYER_LOCATION);
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
     * Sends all the model needed in order to let the ReturningPlayer come back
     * in the match.
     *
     * @param returningPlayer
     *            the returning player
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public void modelSender(Player returningPlayer)
            throws DisconnectedException {

        // cleqn map
        this.sendMapViewToPlayer(returningPlayer);
        // player's location
        this.sendMapVariationToPlayer(returningPlayer, zoneController
                .getCurrentZone().getCell(returningPlayer),
                SectorHighlight.PLAYER_LOCATION);

        // party
        this.updatePartyModel(returningPlayer);
        // player's cards
        this.updateDeckView(returningPlayer);

        // is player still alive
        if (match.getDeadPlayer().contains(returningPlayer)) {
            this.notifyAPlayerAbout(returningPlayer, "You are dead.");
        } else if (match.getRescuedPlayer().contains(returningPlayer)) {
            this.notifyAPlayerAbout(returningPlayer, "You are escaped.");
        } else {
            this.notifyAPlayerAbout(returningPlayer, "You are still alive.");
        }

        // turn (in case returningPlayer is the currentPlayer)
        if (turnController.getTurn().getCurrentPlayer().equals(returningPlayer)) {
            this.sendTurnViewModel();
        }
    }

    /**
     * Establishes the role of each player.
     */
    private void establishRoles() {
        List<Player> players = this.obtainPartyPlayers();
        Collections.shuffle(players);
        StackedDeck<PlayerCard> playerCardDeck = EftaiosDecks
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
                this.notifyAPlayerAbout(player, "You're alien, start hunting!");
            } else {
                this.notifyAPlayerAbout(player, "You're human, start running!");
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
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public void initMatch(PartyController partyController)
            throws FileNotFoundException, URISyntaxException {
        this.partyController = partyController;
        this.match = new Match();
        this.turnController = new TurnController(this);

        // map preparation
        EftaiosGame game = (EftaiosGame) partyController.getCurrentParty()
                .getGame();
        ZoneFactory zf = new TemplateZoneFactory(game.getMapName());
        this.zoneController = new ZoneController(zf);

        this.establishRoles(); // roles assignment
        this.zoneController.placePlayers(obtainPartyPlayers()); // put players
                                                                // on starts
        this.turnController.firstTurn(); // first turn preparation
        this.modelSender(); // send the models
        this.sayRoles(); // inform every player about his role
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
    protected void killed(Player killedPlayer) {

        // checks defense card
        if (PlayerRace.HUMAN.equals(killedPlayer.getIdentity().getRace())) {
            for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
                if (Item.DEFENSE.equals(card.getItem())) {
                    this.match.getItemsDeck().putIntoBucket(card);
                    killedPlayer.getItemsDeck().getCards().remove(card);
                    notifyPartyByPlayer(killedPlayer, "DEFENSE CARD");
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
        this.notifyAPlayerAbout(killedPlayer, "You are dead.");
        // informs the other players about killedPlayer's identity
        List<Player> others = obtainPartyPlayers();
        others.remove(killedPlayer);
        for (Player otherPlayer : others) {
            this.notifyAPlayerAbout(otherPlayer, "The "
                    + killedPlayer.getIdentity().getRace().toString() + " "
                    + killedPlayer.getName() + " is dead.");
        }
        // discards killedPlayer's cards
        this.match.getItemsDeck().putAllIntoBucket(
                killedPlayer.getItemsDeck().getCards());
        killedPlayer.getItemsDeck().getCards().clear();
        try {
            this.updateDeckView(killedPlayer);
        } catch (DisconnectedException e) {
            // do not push this model, will be retrieved manually on reconnect
        }
        // killedPlayer has to disappear from the map
        this.zoneController.getCurrentZone().movePlayer(killedPlayer,
                endingSector);
        try {
            this.sendMapVariationToPlayer(killedPlayer, zoneController
                    .getCurrentZone().getCell(killedPlayer),
                    SectorHighlight.PLAYER_LOCATION);
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
        Set<Player> playerList = this.turnController.getPartyPlayers();
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
        Set<Player> playerList = this.turnController.getPartyPlayers();
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
        Set<Player> playerList = this.turnController.getPartyPlayers();
        // aliens won
        this.sayYouWin(getAlienPlayers());
        // escaped humans won
        this.sayYouWin(match.getRescuedPlayer());
        // remaining humans lost
        playerList.removeAll(getAlienPlayers());
        playerList.removeAll(match.getRescuedPlayer());
        this.sayYouLose(playerList);
    }

    /**
     * Utility method to be used by turnController: all aliens win, rescued
     * humans win, remaining humans lose.
     */
    protected void endingByTurnController() {
        this.partialVictory();
        this.partyController.endMatch();
    }

    /**
     * Says the players losers lost this match.
     *
     * @param losers
     *            the losers
     */
    protected void sayYouLose(Set<Player> losers) {
        for (Player loser : losers)
            this.notifyAPlayerAbout(loser, "GAME OVER\r\nYOU LOSE");
    }

    /**
     * Says the players winners won this match.
     *
     * @param winners
     *            the winners
     */
    protected void sayYouWin(Set<Player> winners) {
        for (Player winner : winners)
            this.notifyAPlayerAbout(winner, "GAME OVER\r\nYOU WIN");
    }

    /**
     * Checks if the game has come to its end.
     */
    protected void checkEndGame() {
        Set<Player> playerList = this.turnController.getPartyPlayers();
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
            this.sayYouLose(getHumanPlayers());
            // aliens won
            this.sayYouWin(getAlienPlayers());
            this.turnController.stopTimeoutTimer();
            this.partyController.endMatch();
        }

        // ALL HUMANS ARE ESCAPED
        else if (this.match.getRescuedPlayer().size() == humanNumber) {
            // humans won
            this.sayYouWin(getHumanPlayers());
            // aliens lost
            this.sayYouLose(getAlienPlayers());
            this.turnController.stopTimeoutTimer();
            this.partyController.endMatch();
        }

        // ALIENS KILLED THE LAST HUMAN
        else if (((deadHumans + this.match.getRescuedPlayer().size()) == humanNumber)
                && (this.turnController.getTurn().getHumanKilled() > 0)) {
            // aliens won
            this.sayYouWin(getAlienPlayers());
            // escaped humans won
            this.sayYouWin(this.match.getRescuedPlayer());
            // dead humans lost
            this.match.getDeadPlayer().removeAll(getAlienPlayers());
            this.sayYouLose(this.match.getDeadPlayer());
            this.turnController.stopTimeoutTimer();
            this.partyController.endMatch();
        }

        // THE LAST HUMAN ESCAPED
        else if (((deadHumans + this.match.getRescuedPlayer().size()) == humanNumber)
                && (this.turnController.getTurn().getHumanKilled() == 0)) {
            // aliens lost
            this.sayYouLose(getAlienPlayers());
            // escaped humans won
            this.sayYouWin(this.match.getRescuedPlayer());
            // dead humans lost
            this.match.getDeadPlayer().removeAll(getAlienPlayers());
            this.sayYouLose(this.match.getDeadPlayer());
            this.turnController.stopTimeoutTimer();
            this.partyController.endMatch();
        }

        // NO MORE HATCHES AVAILABLE
        else if (this.zoneController.noMoreHatches()) {
            this.partialVictory();
            this.turnController.stopTimeoutTimer();
            this.partyController.endMatch();
        }
    }

    /**
     * Processes the action request.
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
                this.notifyAPlayerAbout(turnController.getTurn()
                        .getCurrentPlayer(), "Sorry, you can't do this.");

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
            MessageController.getPlayerHandler(
                    this.partyController.getCurrentParty()
                            .getPlayerUUID(player)).dispatchOutgoing(
                    new ChatMessage(new ChatViewModel(about,
                            this.serverWordText, ChatVisibility.PLAYER)));
        } catch (DisconnectedException e) {
            // Should enqueue this notification for later dispatch
        }
    }

    /**
     * Notifies the string received to the party adding player's name before the
     * string and using server as sender.
     *
     * @param player
     *            the sender
     * @param what
     *            the string to notify
     */
    protected void notifyPartyByPlayer(Player player, String what) {
        this.partyController.sendMessageToParty(new ChatMessage(
                new ChatViewModel(player.getName() + ": " + what,
                        this.serverWordText, ChatVisibility.PARTY)));
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
        this.sendViewModelToAPlayer(player, player.getItemsDeck()
                .getViewModel());
    }

    /**
     * Sends map view to party. Note: this map does not contain neither players'
     * locations nor possible used hatches.
     */
    protected void sendMapViewToParty() {
        this.partyController.sendMessageToParty(new Message(this.zoneController
                .getCurrentZone().getViewModel()));
    }

    /**
     * Sends map view to player. Note: this map does not contain neither
     * players' locations nor possible used hatches.
     *
     * @param player
     *            the player
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void sendMapViewToPlayer(Player player)
            throws DisconnectedException {
        this.sendViewModelToAPlayer(player, this.zoneController
                .getCurrentZone().getViewModel());
    }

    /**
     * Sends map variation to the player "player"; a variation could be about
     * player's location, used hatch,... (see SectorHighlight enum) .
     *
     * @param player
     *            the player
     * @param sec
     *            the sector
     * @param highlight
     *            the kind of highlight
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void sendMapVariationToPlayer(Player player, Sector sec,
            SectorHighlight highlight) throws DisconnectedException {
        SectorViewModel viewModel = new SectorViewModel(sec, highlight);
        this.sendViewModelToAPlayer(player, viewModel);
    }

    /**
     * Updates party view for the player received.
     *
     * @param player
     *            the player
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void updatePartyModel(Player player) throws DisconnectedException {
        this.sendViewModelToAPlayer(player, this.partyController
                .getCurrentParty().getViewModel());
    }

    /**
     * Updates the party view to all party players (this view is used in the GUI
     * to show the number of item cards of each players).
     */
    protected void updatePartyToAllPlayers() {
        this.partyController.sendMessageToParty(new Message(
                this.partyController.getCurrentParty().getViewModel()));
    }

    /**
     * Sends the turn view model to the current player.
     */
    protected void sendTurnViewModel() {
        try {
            this.sendViewModelToAPlayer(turnController.getTurn()
                    .getCurrentPlayer(), turnController.getTurn()
                    .getViewModel());
        } catch (DisconnectedException e) {
            // the player will receive the updated turn view when he comes back
            // online
        }
    }

    /**
     * Sends the view model received to the player received.
     *
     * @param p
     *            the player
     * @param content
     *            the view model
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void sendViewModelToAPlayer(Player p, ViewModel content)
            throws DisconnectedException {
        MessageController.getPlayerHandler(
                this.partyController.getCurrentParty().getPlayerUUID(p))
                .dispatchOutgoing(new Message(content));
    }

}
