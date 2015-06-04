package it.polimi.ingsw.cg_30;

public class MatchController {

    private TurnController turnController;
    private PartyController partyController;
    private ZoneController zoneController;
    private Match match;

    public MatchController() {// ???
        // TODO assign all sub-controllers instances
    }

    private void initMatch() {

        // TODO call init methods on every sub-controller

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
                if (Item.DEFENSE.equals(card)) {
                    match.getItemsDeck().putIntoBucket(card);
                    // TO DO avviso dell'uso della carta DIFESA
                    // il giocatore è salvo
                    return;
                }
            }
        }
        // inserisce il player tra i morti
        match.getDeadPlayer().add(killedPlayer);
        // TO DO avvisa quel giocatore che è morto, informa gli altri players
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
