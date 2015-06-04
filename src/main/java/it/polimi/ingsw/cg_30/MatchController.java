package it.polimi.ingsw.cg_30;

import java.util.Set;

public class MatchController {

    private TurnController currentTurn;
    private PartyController currentParty;
    private ZoneController currentZoneController;
    private int turnCount;
    private StackedDeck<ItemCard> itemsDeck;
    private StackedDeck<HatchCard> hatchesDeck;
    private StackedDeck<SectorCard> sectorsDeck;
    private Set<Player> deadPlayer;
    private Set<Player> rescuedPlayer;

    public MatchController() {
        // TODO assign all sub-controllers instances
    }

    public int getTurnCount() {
        throw new UnsupportedOperationException();
    }

    private void initMatch() {

        // TODO call init methods on every sub-controller

        this.currentTurn = this.newTurn();
    }

    public TurnController newTurn() {
        // prendo i membri dal party passando da partyController
        Set<Player> playerList;
        playerList = this.getCurrentParty().getCurrentParty().getMembers()
                .keySet();// set con tutti i player del party
        int playerNumber = playerList.size();
        int index = this.getCurrentTurn().getTurn().getCurrentPlayer()
                .getIndex();// indice del giocatore
                            // successivo a quello di cui
                            // voglio terminare il turno
        playerList.removeAll(this.getDeadPlayer());
        playerList.removeAll(this.getRescuedPlayer());
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                this.incrementTurnCount();
            } else {
                index++;
            }
            for (Player nextPlayer : playerList) {// faccio ciò in quanto non ho
                                                  // la
                                                  // cartezza che nel set
                                                  // restituito
                                                  // i player siano nell'ordine
                                                  // index (vedi attributi
                                                  // Player)
                if (nextPlayer.getIndex() == index) {
                    // qui passo il turno a nextPlayer
                    this.currentTurn = new TurnController(nextPlayer);
                    // TO DO comunico il passaggio del turno
                    this.checkEndGame();
                    // TO DO rimuovere la seguente riga
                    return null;
                }
            }
        }
        // qui non ci dovrei mai arrivare
        // TO DO rimuovere la seguente riga
        return currentTurn;
    }

    public TurnController getCurrentTurn() {
        return currentTurn;
    }

    public PartyController getCurrentParty() {
        return currentParty;
    }

    public StackedDeck<ItemCard> getItemsDeck() {
        return itemsDeck;
    }

    public ZoneController getZoneController() {
        return currentZoneController;
    }

    public void incrementTurnCount() {
        this.turnCount++;
    }

    public Set<Player> getDeadPlayer() {
        return deadPlayer;
    }

    public ZoneController getCurrentZoneController() {
        return currentZoneController;
    }

    public StackedDeck<HatchCard> getHatchesDeck() {
        return hatchesDeck;
    }

    public StackedDeck<SectorCard> getSectorsDeck() {
        return sectorsDeck;
    }

    public Set<Player> getRescuedPlayer() {
        return this.rescuedPlayer;
    }

    public void killed(Player killedPlayer) {
        // non posso rimuovere un player da party altrimenti incorro in problemi
        // successivamente in fase di notifiche/ripristino server

        // verifico eventuale presenza carta difesa
        if (killedPlayer.getIdentity().getRace().equals(PlayerRace.HUMAN)) {
            for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
                if (card.equals(Item.DEFENSE)) {
                    itemsDeck.putIntoBucket(card);
                    // TO DO avviso dell'uso della carta DIFESA
                    // il giocatore è salvo
                    return;
                }
            }
        }
        // inserisce il player tra i morti
        deadPlayer.add(killedPlayer);
        // TO DO avvisa quel giocatore che è morto, informa gli altri players
        // sulla sua identità e l'uccisore del fatto che ha ucciso

        // scarta le carte del giocatore
        for (ItemCard card : killedPlayer.getItemsDeck().getCards()) {
            itemsDeck.putIntoBucket(card);
            killedPlayer.getItemsDeck().getCards().remove(card);
        }
        // faccio sparire il giocatore dalla mappa
        currentZoneController.getCurrentZone().movePlayer(killedPlayer, null);
        // l'incremento del contatore uccisione lo faccio in Attack
    }

    public void checkEndGame() {
        // TO DO funzione che verifica che ci sono le condizioni per la fine del
        // gioco. Se sì, lo termina notificando opportunamente.

        // ricorda che se c'è ancora una sciluppa ma tutte le carte
        // HatchChance.Free sono già state usate l'umano rimasto è morto.
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
