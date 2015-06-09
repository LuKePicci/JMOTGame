package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.model.ItemCard;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.SpareDeck;

import java.util.Set;

/**
 * The Class UseCard.
 */
public class UseCard extends ActionController {

    private SpareDeck<ItemCard> spareDeck;
    private Item item;

    @Override
    public void initAction(MatchController match, ActionRequest request) {
        super.initAction(matchController, request);
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
        this.item = request.getActionItem();
    }

    /*
     * LEGGIMI cosa presumo di sapere implementando questo metodo: il player ha
     * un elenco con tutti i tipi di itemCard e può chiedere l'utilizzo di
     * qualunque carta, sarà isValid che negherà l'uso di una carta se il
     * giocatore non la possiede o non è in condizione di usarla.
     */
    /**
     * Checks the legality of this action.
     * 
     * @return true if player can use the card
     */
    @Override
    public boolean isValid() {
        // TODO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        // verifico che player sia umano e non alieno
        if (PlayerRace.ALIEN.equals(player.getIdentity().getRace())) {
            return false;
        } else {
            // verifico che player umano possieda la carta
            return findItemCardByItem(item) != null
                    && matchController.getTurnController().getTurn()
                            .getDrawnCard() == null
                    && !(Item.DEFENSE.equals(item)// non posso attivare la carta
                                                  // difesa
                            || (Item.ADRENALINE.equals(item))
                            && !(matchController.getTurnController().getTurn()
                                    .getMustMove()) // va usata prima di
                                                    // muoversi
                    || Item.SEDATIVES.equals(item)
                            && !(matchController.getTurnController().getTurn()
                                    .getMustMove())); // va usata prima di
                                                      // muoversi
        }
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        ItemCard card = findItemCardByItem(item);
        if (Item.ATTACK.equals(item)) {
            Attack attack = new Attack(matchController);
            attack.processAction();
            matchController.getMatch().getItemsDeck().putIntoBucket(card);
        } else if (Item.TELEPORT.equals(item)) {
            // Sector target = funzioneCheRitornaLeCoordinateDelSettoreUmani
            // TODO target deve essere il settore di partenza degli umani;
            matchController
                    .getZoneController()
                    .getCurrentZone()
                    .movePlayer(
                            player,
                            matchController.getZoneController()
                                    .getCurrentZone().getMap()
                                    .get(this.req.getActionTarget()));
        } else if (Item.ADRENALINE.equals(item)) {
            matchController.getTurnController().getTurn().setMaxSteps(2);
        } else if (Item.SEDATIVES.equals(item)) {
            matchController.getTurnController().getTurn()
                    .setSilenceForced(true);
        } else if (Item.SPOTLIGHT.equals(item)) {
            // identifico i settori
            Sector start = matchController.getZoneController().getCurrentZone()
                    .getMap().get(this.req.getActionTarget());
            Set<Sector> borderSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(start, 1);// settori
                                                                 // confinanti
            borderSectors.add(start);
            for (Sector sec : borderSectors) {
                Set<Player> watchedPlayers = matchController
                        .getZoneController().getCurrentZone()
                        .getPlayersInSector(sec);
                // TODO avvisa tutti che nel settore sec si trovano i players
                // watchedPlayers (ma non terminare il metodo)
            }
        }
        // scarto la carta oggetto utilizzata
        matchController.getMatch().getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // elimino l'eventuale obbligo di scartare
        matchController.getTurnController().getTurn().setMustDiscard(false);
    }
}
