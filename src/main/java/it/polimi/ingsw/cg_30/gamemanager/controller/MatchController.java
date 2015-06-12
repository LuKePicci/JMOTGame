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
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.StackedDeck;

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

    /** The match. */
    protected Match match;

    /** The max turn. */
    private final int MAX_TURN = 39;

    /**
     * Obtain party players.
     *
     * @return the list of players of the current party
     */
    public List<Player> obtainPartyPlayers() {
        List<Player> playerList = new ArrayList<Player>(partyController
                .getCurrentParty().getMembers().keySet());
        return playerList;
    }

    /**
     * Sends all the model needed in order to star a new match.
     */
    private void modelSender() {
        // mappa
        partyController.sendMessageToParty(new Message(zoneController
                .getCurrentZone().getViewModel()));
        // players
        partyController.sendMessageToParty(new Message(partyController
                .getCurrentParty().getViewModel()));
        // carte dei players
        for (Player player : obtainPartyPlayers()) {
            ViewModel model = player.getItemsDeck().getViewModel();
            MessageController
                    .getPlayerHandler(
                            partyController.getCurrentParty().getPlayerUUID(
                                    player)).getAcceptPlayer()
                    .sendMessage(new Message(model));
        }
        // chat
        partyController.sendMessageToParty(new ChatMessage(new ChatViewModel(
                "Game started", "Server", ChatVisibility.PARTY)));
    }

    /**
     * Establishes the role of each player.
     */
    private void establishRoles() {
        List<Player> players = obtainPartyPlayers();
        Collections.shuffle(players);
        StackedDeck<PlayerCard> playerCardDeck = StackedDeck
                .newStackedDeckPlayer();
        for (Player player : players) {
            player.setIdentity(playerCardDeck.pickCard());
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

        // preparo mappa
        EftaiosGame game = (EftaiosGame) partyController.getCurrentParty()
                .getGame();
        ZoneFactory zf = new TemplateZoneFactory(game.getMapName());
        this.zoneController = new ZoneController(zf);

        establishRoles(); // assegno i ruoli
        List<Player> playerList = obtainPartyPlayers();
        zoneController.placePlayers(playerList); // posiziono i players
        turnController.firstTurn(playerList); // preparo primo turno
        // inivio model
        modelSender();
        // informo il primo player
        partyController.sendMessageToParty(new ChatMessage(new ChatViewModel(
                turnController.getTurn().getCurrentPlayer().getName()
                        + "'s turn", "Server", ChatVisibility.PARTY)));
    }

    /**
     * Gets the turn controller.
     *
     * @return the turn controller
     */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * Gets the party controller.
     *
     * @return the party controller
     */
    public PartyController getPartyController() {
        return partyController;
    }

    /**
     * Gets the zone controller.
     *
     * @return the zone controller
     */
    public ZoneController getZoneController() {
        return zoneController;
    }

    /**
     * Gets the match.
     *
     * @return the match
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Kills the player "killedPlayer".
     *
     * @param killedPlayer
     *            the player to be killed
     */
    public void killed(Player killedPlayer) {

        // verifico eventuale presenza carta difesa
        if (PlayerRace.HUMAN.equals(killedPlayer.getIdentity().getRace())) {
            for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
                if (Item.DEFENSE.equals(card.getItem())) {
                    match.getItemsDeck().putIntoBucket(card);
                    killedPlayer.getItemsDeck().getCards().remove(card);
                    notifyPartyFromPlayer(killedPlayer, "DEFENSE CARD");
                    showCardToParty(card);
                    updateDeckView(killedPlayer);
                    turnController.getTurn().changeHumanKilled(-1);
                    turnController.getTurn().getCurrentPlayer()
                            .decrementKillsCount();
                    return;
                }
            }
        }

        // inserisce il player tra i morti
        match.getDeadPlayer().add(killedPlayer);
        // avvisa che quel giocatore che è morto
        notifyAPlayerAbout(killedPlayer, "You are dead");
        // informa gli altri players sulla sua identità

        List<Player> others = obtainPartyPlayers();
        others.remove(killedPlayer);
        for (Player otherPlayer : others) {
            notifyAPlayerAbout(otherPlayer, "The "
                    + killedPlayer.getIdentity().getRace().toString() + " "
                    + killedPlayer.getName() + " is dead");
        }
        // scarta le carte del giocatore
        for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
            match.getItemsDeck().putIntoBucket(card);
            killedPlayer.getItemsDeck().getCards().remove(card);
        }
        updateDeckView(killedPlayer);
        // faccio sparire il giocatore dalla mappa
        zoneController.getCurrentZone().movePlayer(killedPlayer, null);
        updateMapView(killedPlayer);
        // l'incremento del contatore uccisione lo faccio in Attack
    }

    /**
     * Gets the human players.
     *
     * @return the human players
     */
    private Set<Player> getHumanPlayers() {
        Set<Player> humanPlayers = new HashSet<Player>();
        Set<Player> playerList = turnController.getPartyPlayers(this);
        for (Player thisPlayer : playerList) {
            if (PlayerRace.HUMAN.equals(thisPlayer.getIdentity().getRace())) {
                humanPlayers.add(thisPlayer);
            }
        }
        return humanPlayers;
    }

    /**
     * Gets the alien players.
     *
     * @return the alien players
     */
    private Set<Player> getAlienPlayers() {
        Set<Player> alienPlayers = new HashSet<Player>();
        Set<Player> playerList = turnController.getPartyPlayers(this);
        for (Player thisPlayer : playerList) {
            if (PlayerRace.ALIEN.equals(thisPlayer.getIdentity().getRace())) {
                alienPlayers.add(thisPlayer);
            }
        }
        return alienPlayers;
    }

    private void partialVictory() {
        Set<Player> playerList = turnController.getPartyPlayers(this);
        // gli alieni vincono
        sayYouWin(getAlienPlayers());
        // gli umani fuggiti vincono
        sayYouWin(match.getRescuedPlayer());
        // gli altri umani perdono
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
            MessageController
                    .getPlayerHandler(
                            partyController.getCurrentParty().getPlayerUUID(
                                    loser))
                    .getAcceptPlayer()
                    .sendMessage(
                            new Message(new ChatViewModel(
                                    "GAME OVER\nYOU LOSE", "Server",
                                    ChatVisibility.PLAYER)));
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
            MessageController
                    .getPlayerHandler(
                            partyController.getCurrentParty().getPlayerUUID(
                                    winner))
                    .getAcceptPlayer()
                    .sendMessage(
                            new Message(new ChatViewModel("GAME OVER\nYOU WIN",
                                    "Server", ChatVisibility.PLAYER)));
        }
    }

    /**
     * Checks if the game has come to its end.
     */
    public void checkEndGame() {
        Set<Player> playerList = turnController.getPartyPlayers(this);
        int playerNumber = playerList.size();
        int humanNumber = playerNumber / 2;
        int deadHumans = 0;
        for (Player thisPlayer : match.getDeadPlayer()) {
            if (PlayerRace.HUMAN.equals(thisPlayer.getIdentity().getRace())) {
                deadHumans++;
            }
        }

        // TUTTI GLI UMANI SONO MORTI
        if (deadHumans == humanNumber) {
            // gli umani hanno perso
            sayYouLose(getHumanPlayers());
            // gli alieni hanno vinto
            sayYouWin(getAlienPlayers());
        }

        // TUTTI GLI UMANI SONO FUGGITI
        else if (match.getRescuedPlayer().size() == humanNumber) {
            // gli umani sono salvi
            sayYouWin(getHumanPlayers());
            // gli alieni hanno perso
            sayYouLose(getAlienPlayers());
        }

        // GLI ALIENI UCCIDONO L'ULTIMO UMANO
        else if (((deadHumans + match.getRescuedPlayer().size()) == humanNumber)
                && (turnController.getTurn().getHumanKilled() > 0)) {
            // gli alieni vincono
            sayYouWin(getAlienPlayers());
            // gli umani fuggiti vincono
            sayYouWin(match.getRescuedPlayer());
            // gli umani morti perdono
            match.getDeadPlayer().removeAll(getAlienPlayers());
            sayYouLose(match.getDeadPlayer());
        }

        // L'ULTIMO UMANO RIESCE A FUGGIRE
        else if (((deadHumans + match.getRescuedPlayer().size()) == humanNumber)
                && (turnController.getTurn().getHumanKilled() == 0)) {
            // gli alieni perdono
            sayYouLose(getAlienPlayers());
            // gli umani fuggiti vincono
            sayYouWin(match.getRescuedPlayer());
            // gli umani morti perdono
            match.getDeadPlayer().removeAll(getAlienPlayers());
            sayYouLose(match.getDeadPlayer());
        }

        // TURNO 39 FINITO (conto partendo da 1) o NON CI SONO PIÙ SCIALUPPE
        // DISPONIBILI
        else if (match.getTurnCount() == (MAX_TURN + 1)
                || zoneController.noMoreHatches()) {
            partialVictory();
        }

    }

    /**
     * Process thw action request.
     *
     * @param req
     *            the action request
     */
    public synchronized void processActionRequest(ActionRequest req) {
        ActionController act;
        try {
            act = ActionController.getStrategy(req);
            act.initAction(this, req);
            if (act.isValid())
                act.processAction();
            else
                MessageController
                        .getPlayerHandler(
                                partyController.getCurrentParty()
                                        .getPlayerUUID(
                                                turnController.getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(new ChatViewModel(
                                        "Sorry, you can't do this", "Server",
                                        ChatVisibility.PLAYER)));
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
     * @param about
     *            the string to notify
     */
    protected void notifyAPlayerAbout(Player player, String about) {
        MessageController
                .getPlayerHandler(
                        partyController.getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new ChatMessage(new ChatViewModel(about, "Server",
                                ChatVisibility.PLAYER)));
    }

    protected void notifyPartyFromPlayer(Player player, String what) {
        partyController.sendMessageToParty(new ChatMessage(new ChatViewModel(
                what, player.getName(), ChatVisibility.PARTY)));
    }

    /**
     * Shows the card received to the party.
     *
     * @param card
     *            the card to notify
     */
    protected void showCardToParty(Card card) {
        partyController.sendMessageToParty(new Message(card));
    }

    /**
     * Updates cards view for the player.
     */
    protected void updateDeckView(Player player) {
        MessageController
                .getPlayerHandler(
                        partyController.getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(new Message(player.getItemsDeck().getViewModel()));
    }

    /**
     * Updates map view for the player.
     */
    protected void updateMapView(Player player) {
        MessageController
                .getPlayerHandler(
                        partyController.getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new Message(zoneController.getCurrentZone()
                                .getViewModel()));
    }

    // Aggiunti solo per il testing
    protected Set<Player> vincitori = new HashSet<Player>();
    protected Set<Player> perdenti = new HashSet<Player>();

}
