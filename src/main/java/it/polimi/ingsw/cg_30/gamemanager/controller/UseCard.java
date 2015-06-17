package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.SpareDeck;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.Set;

/**
 * The Class UseCard.
 */
public class UseCard extends ActionController {

    /**
     * Instance of attack action needed if human uses an attack card
     */
    protected Attack forcedAttack = new Attack();

    /** The spare deck. */
    private SpareDeck<ItemCard> spareDeck;

    /** The item. */
    private Item item;

    @Override
    public void initAction(MatchController matchController,
            ActionRequest request) {
        super.initAction(matchController, request);
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
        this.item = request.getActionItem();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if player can use the card
     */
    @Override
    public boolean isValid() {
        // the player must be human and not alien
        if (PlayerRace.ALIEN.equals(this.player.getIdentity().getRace())) {
            return false;
        } else {
            // the human player must own the card he wants to use
            return findItemCardByItem(this.item) != null
                    && this.matchController.getTurnController().getTurn()
                            .getDrawnCard() == null
                    && !(Item.DEFENSE.equals(this.item) // defense card can't be
                                                        // use manually
                            || (Item.ADRENALINE.equals(this.item) && !this.matchController
                                    .getTurnController().getTurn()
                                    .getMustMove()) // must be used before
                                                    // moving
                            || (Item.SEDATIVES.equals(this.item) && !this.matchController
                                    .getTurnController().getTurn()
                                    .getMustMove())// must be used before moving
                    || (Item.SPOTLIGHT.equals(this.item) && !this.matchController
                            .getZoneController().getCurrentZone().getMap()
                            .containsKey(this.req.getActionTarget()))); // the
                                                                        // sector
            // must be on
            // the map
        }
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        ItemCard card = findItemCardByItem(item);
        this.matchController.showCardToParty(card);

        if (Item.ATTACK.equals(this.item)) {
            this.notifyInChatByCurrentPlayer("ATTACK CARD");
            ActionRequest forcedRequest = new ActionRequest(ActionType.ATTACK,
                    null, null);
            forcedAttack.initAction(matchController, forcedRequest);
            forcedAttack.processAction();
            this.matchController.getMatch().getItemsDeck().putIntoBucket(card);

        } else if (Item.TELEPORT.equals(item)) {
            this.notifyInChatByCurrentPlayer("TELEPORT CARD");
            this.teleportLogic();
            try {
                this.matchController.sendMapVariationToPlayer(player,
                        this.matchController.getZoneController()
                                .getHumansStart(),
                        SectorHighlight.PLAYER_LOCATION);
            } catch (DisconnectedException e) {
                // player's location will be updated as soon as the player comes
                // back
                // thanks to modelSender(Player returningPlayer) in
                // MatchController
            }

        } else if (Item.ADRENALINE.equals(item)) {
            this.notifyInChatByCurrentPlayer("ADRENALINE CARD");
            this.matchController.getTurnController().getTurn().setMaxSteps(2);

        } else if (Item.SEDATIVES.equals(item)) {
            this.notifyInChatByCurrentPlayer("SEDATIVES CARD");
            this.matchController.getTurnController().getTurn()
                    .setSilenceForced(true);

        } else if (Item.SPOTLIGHT.equals(item)) {
            this.notifyInChatByCurrentPlayer("SPOTLIGHT CARD");
            this.spotlightLogic();
        }

        // discard the used item card
        this.matchController.getMatch().getItemsDeck().putIntoBucket(card);
        this.spareDeck.getCards().remove(card);
        // remove the obliged to discard
        this.matchController.getTurnController().getTurn()
                .setMustDiscard(false);
        try {
            this.matchController.updateDeckView(player);
        } catch (DisconnectedException e) {
            // player's deck will be updated as soon as the player comes back
            // thanks to modelSender(Player returningPlayer) in MatchController
        }
    }

    /**
     * Logic of the ItemCard Teleport.
     */
    private void teleportLogic() {
        this.matchController
                .getZoneController()
                .getCurrentZone()
                .movePlayer(
                        player,
                        this.matchController.getZoneController()
                                .getHumansStart());
    }

    /**
     * Logic of the ItemCard Spotlight.
     */
    private void spotlightLogic() {
        // identify the sectors
        Sector start = this.matchController.getZoneController()
                .getCurrentZone().getMap().get(this.req.getActionTarget());
        Set<Sector> borderSectors = this.matchController.getZoneController()
                .getCurrentZone().reachableTargets(start, 1);
        borderSectors.add(start);
        for (Sector sec : borderSectors) {
            Set<Player> watchedPlayers = this.matchController
                    .getZoneController().getCurrentZone()
                    .getPlayersInSector(sec);
            for (Player playerFound : watchedPlayers) {
                this.notifyInChatByServer("The player " + playerFound.getName()
                        + " is in sector " + sec.getPoint().toString());
            }
        }
    }

}
