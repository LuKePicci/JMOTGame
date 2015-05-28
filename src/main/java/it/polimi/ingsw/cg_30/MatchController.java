package it.polimi.ingsw.cg_30;

public class MatchController {

    public TurnController currentTurn;
    public PartyController currentParty;

    private ZoneController currentZoneController;
    private int turnCount;
    private StackedDeck itemsDeck;
    private StackedDeck hatchesDeck;
    private StackedDeck sectorsDeck;

    public int getTurnCount() {
        throw new UnsupportedOperationException();
    }

    private void initMatch() {
        throw new UnsupportedOperationException();
    }

    private TurnController newTurn() {
        currentTurn = new TurnController();
        return currentTurn;
    }

    public TurnController getCurrentTurn() {
        return currentTurn;
    }

    public PartyController getCurrentParty() {
        return currentParty;
    }

    public StackedDeck getItemsDeck() {
        return itemsDeck;
    }

    public ZoneController getZoneController() {
        return currentZoneController;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public void killed(Player deadPlayer) {
        // non posso rimuovere un player da party altrimenti incorro in problemi
        // successivamente in fase di notifiche/ripristino server

        // verifico eventuale presenza carta difesa
        if (deadPlayer.getIdentity().getRace().equals(PlayerRace.HUMAN)) {
            for (Card card : deadPlayer.getItemsDeck().getCards()) {
                if (card.equals(Item.DEFENSE)) {
                    itemsDeck.putIntoBucket(card);
                    // TO DO avviso dell'uso della carta DIFESA
                    // il giocatore è salvo
                    return;
                }
            }
        }
        // attivo il flag isDead di player
        deadPlayer.setIsDead();
        // TO DO avvisa quel giocatore che è morto, informa gli altri players
        // sulla sua identità e l'uccisore del fatto che ha ucciso

        // scarta le carte del giocatore
        for (Card card : deadPlayer.getItemsDeck().getCards()) {
            itemsDeck.putIntoBucket(card);
            deadPlayer.getItemsDeck().getCards().remove(card);
        }
        // faccio sparire il giocatore dalla mappa
        currentZoneController.getCurrentZone().movePlayer(deadPlayer, null);
        // l'incremento del contatore uccisione lo faccio in Attack
    }

    public void checkEndGame() {
        // TO DO funzione che verifica che ci sono le condizioni per la fine del
        // gioco. Se sì, lo termina notificando opportunamente.
    }

}
