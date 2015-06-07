package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class MatchController {

    private TurnController turnController;
    private PartyController partyController;
    private ZoneController zoneController;
    private Match match;
    private final int MAX_TURN = 39;

    public MatchController() {
    }

    // TODO questa implementazione è una bozza, necessita di revisione
    public MatchController(PartyController partyController) {
        this.partyController = partyController;
        this.match = new Match();
        this.turnController = new TurnController();
        Zone zone = new Zone();
        this.zoneController = new ZoneController(zone);
        // TODO implementare correttamente
    }

    // TODO questa implementazione è una bozza, necessita di completamento
    private void initMatch() {
        // TODO call init methods on every sub-controller
        turnController.firstTurn(this);

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
            for (Card card : killedPlayer.getItemsDeck().getCards()) {
                ItemCard icard = (ItemCard) card;
                if (Item.DEFENSE.equals(icard.getItem())) {
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
        for (Card card : killedPlayer.getItemsDeck().getCards()) {
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

        // NON CI SONO PIÙ SCIALUPPE DISPONIBILI
        else if (zoneController.noMoreHatches()) {
            partialVictory();
        }

        // TURNO 39 FINITO (conto partendo da 1)
        else if (match.getTurnCount() == (MAX_TURN + 1)) {
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
