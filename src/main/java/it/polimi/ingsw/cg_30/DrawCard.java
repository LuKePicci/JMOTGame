package it.polimi.ingsw.cg_30;

import java.util.EmptyStackException;

public class DrawCard extends ActionController {

    private Player player;
    private MatchController matchController;

    public DrawCard(MatchController matchController) {
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
        this.matchController = matchController;

    }

    @Override
    public boolean isValid() {// funzione utile solo per l'alieno
        if ((SectorType.DANGEROUS.equals(matchController.getZoneController()
                .getCurrentZone().getCell(player)))
                && (matchController.getTurnController().getTurn()
                        .getIsSecDangerous() == true)) {
            return true;
        } else
            return false;
    }

    @Override
    public void processAction() {
        {
            SectorCard drawnCard = new SectorCard();
            // TODO gestione eccezione
            drawnCard = (SectorCard) matchController.getMatch()
                    .getSectorsDeck().pickAndThrow();
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(false);
            matchController.getTurnController().getTurn().setCanAttack(false);
            if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
                // TODO notifica SILENZIO
            } else {
                if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
                    Noise noise = new Noise(matchController, player,
                            matchController.getZoneController()
                                    .getCurrentZone().getCell(player),
                            SectorEvent.NOISE_YOUR);
                    noise.processAction();
                } else if (SectorEvent.NOISE_ANY.equals(drawnCard.getEvent())) {
                    Noise noise = new Noise(matchController, player, null,
                            SectorEvent.NOISE_ANY);
                    noise.processAction();
                }
                // controllo la presenza del sibolo oggetto sulla carta
                if (drawnCard.hasObjectSymbol()) {
                    ItemCard icard = new ItemCard();
                    // il mazzo item è l'unico che potrebbe terminare le carte
                    try {
                        icard = (ItemCard) matchController.getMatch()
                                .getItemsDeck().pickCard();
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
    }

}
