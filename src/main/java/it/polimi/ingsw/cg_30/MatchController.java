package it.polimi.ingsw.cg_30;

import java.util.Set;

public class MatchController {

    private TurnController currentTurn;
    private PartyController currentParty;
    private ZoneController currentZoneController;
    private int turnCount;
    private StackedDeck itemsDeck;
    private StackedDeck hatchesDeck;
    private StackedDeck sectorsDeck;
    private Set<Player> deadPlayer;
    private Set<Player> rescuedPlayer;

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
        this.turnCount++;
    }

    public Set<Player> getDeadPlayer() {
        return deadPlayer;
    }

    public ZoneController getCurrentZoneController() {
        return currentZoneController;
    }

    public StackedDeck getHatchesDeck() {
        return hatchesDeck;
    }

    public StackedDeck getSectorsDeck() {
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
            for (Card card : killedPlayer.getItemsDeck().getCards()) {
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
        for (Card card : killedPlayer.getItemsDeck().getCards()) {
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

}
