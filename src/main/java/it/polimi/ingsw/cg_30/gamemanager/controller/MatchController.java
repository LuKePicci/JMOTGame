package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
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

public class MatchController {

    protected TurnController turnController;
    protected PartyController partyController;
    protected ZoneController zoneController;
    protected Match match;
    private final int MAX_TURN = 39;

    // lista dei giocatori del party
    public List<Player> obtainPartyPlayers() {
        List<Player> playerList = new ArrayList<Player>(partyController
                .getCurrentParty().getMembers().keySet());
        return playerList;
    }

    // invio i ViewModels della partita
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

    // assegno i ruoli (alieno/umano)
    private void establishRoles() {
        List<Player> players = obtainPartyPlayers();
        Collections.shuffle(players);
        StackedDeck<PlayerCard> playerCardDeck = StackedDeck
                .newStackedDeckPlayer();
        for (Player player : players) {
            player.setIdentity(playerCardDeck.pickCard());
        }
    }

    // avvio la partita preparando il necessario
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

    public TurnController getTurnController() {
        return turnController;
    }

    public PartyController getPartyController() {
        return partyController;
    }

    public ZoneController getZoneController() {
        return zoneController;
    }

    public Match getMatch() {
        return match;
    }

    public void killed(Player killedPlayer) {
        // non posso rimuovere un player da party altrimenti incorro in problemi
        // successivamente in fase di notifiche/ripristino server

        // verifico eventuale presenza carta difesa
        if (PlayerRace.HUMAN.equals(killedPlayer.getIdentity().getRace())) {
            for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
                if (Item.DEFENSE.equals(card.getItem())) {
                    match.getItemsDeck().putIntoBucket(card);
                    // TODO avviso dell'uso della carta DIFESA
                    // il giocatore è salvo
                    turnController.getTurn().changeHumanKilled(-1);
                    turnController.getTurn().getCurrentPlayer()
                            .decrementKillsCount();
                    return;
                }
            }
        }
        // inserisce il player tra i morti
        match.getDeadPlayer().add(killedPlayer);
        // TODO avvisa quel giocatore che è morto, informa gli altri players
        // sulla sua identità e l'uccisore del fatto che ha ucciso

        // scarta le carte del giocatore
        for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
            match.getItemsDeck().putIntoBucket(card);
            killedPlayer.getItemsDeck().getCards().remove(card);
        }
        // faccio sparire il giocatore dalla mappa
        zoneController.getCurrentZone().movePlayer(killedPlayer, null);
        // l'incremento del contatore uccisione lo faccio in Attack
    }

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

    private void sayYouLose(Set<Player> losers) {
        // TODO informa loser che la partita è finita e hanno perso
    }

    private void sayYouWin(Set<Player> winner) {
        // TODO informa winners che la partita è finita e hanno vinto
    }

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

        // NON CI SONO PIÙ SCIALUPPE DISPONIBILI o TURNO 39 FINITO (conto
        // partendo da 1)
        else if (zoneController.noMoreHatches()
                || match.getTurnCount() == (MAX_TURN + 1)) {
            partialVictory();
        }

    }

    public synchronized void processActionRequest(ActionRequest req) {
        ActionController act;
        try {
            act = ActionController.getStrategy(req);
            act.initAction(this, req);
            if (act.isValid())
                act.processAction();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Log this exception
            System.out
                    .println("Failed to instanciate a controller for requested action.");
        }
    }

}
