package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

/**
 * The Class DrawCard.
 */
public class DrawCard extends ActionController {

    /**
     * Checks the legality of this action.
     * 
     * @return true if the player can draw a card
     */
    @Override
    public boolean isValid() {
        // method to be used only for the alien
        return SectorType.DANGEROUS.equals(this.matchController
                .getZoneController().getCurrentZone().getCell(this.player)
                .getType())
                && this.matchController.getTurnController().getTurn()
                        .getIsSecDangerous();
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        SectorCard drawnCard = this.matchController.getMatch().getSectorsDeck()
                .pickAndThrow();
        this.matchController.getTurnController().getTurn()
                .setIsSecDangerous(false);
        this.matchController.getTurnController().getTurn().setCanAttack(false);
        try {
            this.showCardToCurrentPlayer(drawnCard);
        } catch (DisconnectedException e) {
            // when the player is back he will be informed if he has to
            // make a noise thanks to drawnCard not being null in turn view
            // model.
        }
        if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
            this.notifyInChatByCurrentPlayer("SILENCE");
        } else if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
            this.notifyInChatByCurrentPlayer("NOISE in sector "
                    + this.matchController.getZoneController().getCurrentZone()
                            .getCell(this.player).toString());
            this.hasObject(drawnCard);
        } else {// NoiseAny case
            // I'm going to salve in turn the drawnCard in order to be able to
            // access it in noiseAny action too.
            this.matchController.getTurnController().getTurn()
                    .setDrawnCard(drawnCard);
            try {
                this.notifyCurrentPlayerByServer("CHOOSE WHERE TO MAKE THE NOISE");
            } catch (DisconnectedException e) {
                // when the player is back he will be informed that he has to
                // make a noise thanks to drawnCard not being null in turn view
                // model.
            }
        }
    }

}
