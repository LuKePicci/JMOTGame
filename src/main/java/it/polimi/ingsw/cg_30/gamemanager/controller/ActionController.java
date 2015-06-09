package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.gamemanager.model.Card;
import it.polimi.ingsw.cg_30.gamemanager.model.ItemCard;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.SectorCard;

import java.util.EmptyStackException;

public abstract class ActionController {

    protected MatchController matchController;
    protected ActionRequest req;
    protected Player player;

    public void initAction(MatchController match, ActionRequest request) {
        this.req = request;
        this.matchController = match;
        this.player = match.getTurnController().getTurn().getCurrentPlayer();
    }

    public static ActionController getStrategy(ActionRequest request)
            throws InstantiationException, IllegalAccessException {

        return request.getActionType().getController();
    }

    /*
     * LEGGIMI NB: l'alieno che attacca non deve pescare la carta settore,
     * quindi in caso di attacco l'rodine delle operazioni da eseguire è:
     * isValid di move; settare l'attributo silencedForse di turn a true;
     * processAction di move; isValid di Attack(inutile in teoria);
     * processAction di Attack.
     */

    public abstract boolean isValid();

    public abstract void processAction();

    // cerco tra le carte in mano a player se c'è quella del tipo richiesto
    // la ritorno se c'è; ritorno null altrimenti
    protected ItemCard findItemCardByItem(Item item) {
        for (Card card : matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck().getCards()) {
            ItemCard icard = (ItemCard) card;
            if (item.equals(icard.getItem())) {
                return icard;
            }
        }
        return null;
    }

    // controllo la presenza del sibolo oggetto sulla carta settore pescata
    protected void hasObject(SectorCard drawnCard) {
        if (drawnCard.hasObjectSymbol()) {
            ItemCard icard;
            // il mazzo item è l'unico che potrebbe terminare le carte
            try {
                icard = (ItemCard) matchController.getMatch().getItemsDeck()
                        .pickCard();
            } catch (EmptyStackException e) {
                // TODO informa il giocatore che non ci son più carte
                // oggetto
                return;
            }
            player.getItemsDeck().getCards().add(icard);
            // TODO notifica il giocatore sulla carta pescata
            if (player.getItemsDeck().getCards().size() > 3) {
                matchController.getTurnController().getTurn()
                        .setMustDiscard(true);
                // TODO informa il giocatore che dovrà scartare o usare
                // una carta prima di finire il turno
            }
        }
    }

}
