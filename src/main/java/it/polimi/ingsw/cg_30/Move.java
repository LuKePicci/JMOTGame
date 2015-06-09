package it.polimi.ingsw.cg_30;

import java.util.Set;

/**
 * The Class Move.
 */
public class Move extends ActionController {

    /** The target. */
    private Sector target;

    @Override
    public void initAction(MatchController matchController,
            ActionRequest request) {
        this.target = matchController.getZoneController().getCurrentZone()
                .getMap().get(request.getActionTarget());
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the movement is valid
     */
    @Override
    public boolean isValid() {
        // TODO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if (matchController.getTurnController().getTurn().getMustMove()) {
            int maxSteps = matchController.getTurnController().getTurn()
                    .getMaxSteps();
            Set<Sector> reachableSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(target, maxSteps);
            if (reachableSectors.contains(target)) {
                // anche se un settore è raggiungibile devo assicurarmi che non
                // sia (scialuppa/)settore umani/settore alieni
                // TODO è possibile che i seguenti test non siano necessari a
                // seconda che reachableTargets ritorni o meno i settori start e
                // sciluppa
                return ((!SectorType.ALIENS_START.equals(target.getType()))
                        && (!SectorType.HUMANS_START.equals(target.getType())) && (PlayerRace.HUMAN
                        .equals(player.getIdentity()) || !SectorType.ESCAPE_HATCH
                        .equals(target.getType())));
                // gli umani posso andare sulle scialuppe
            }
        }
        return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // sposto il giocatore
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player, target);
        // segno che il giocatore ha effettuato uno spostamento
        matchController.getTurnController().getTurn().setMustMove();
        if (SectorType.ESCAPE_HATCH.equals(target.getType())) {
            HatchCard drawnCard = (HatchCard) matchController.getMatch()
                    .getHatchesDeck().pickAndThrow();
            // TODO notifica quale carta è stata pescata
            if (HatchChance.FREE.equals(drawnCard.getChance())) {
                matchController.getMatch().getRescuedPlayer().add(player);
                // TODO notifica che il giocatore si è salvato
                // verifico se la partita è finita
                matchController.checkEndGame();
            }
            try {
                matchController.getZoneController()
                        .lockHatch(target.getPoint());
            } catch (NotAnHatchException e) {
                // in teoria ho già verificato il tipo di settore e quindi non
                // dovrebbe mai verificarsi un'eccezione
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (SectorType.DANGEROUS.equals(target.getType())) {
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(true);// l'alieno dovrà o pescare o
                                             // attaccare
            if ((PlayerRace.HUMAN.equals(player.getIdentity().getRace()))
                    && (matchController.getTurnController().getTurn()
                            .getSilenceForced() == false)) {
                // l'umano deve pescare (salvo uso di sedativi)
                DrawCard forcedDraw = new DrawCard(matchController);
                forcedDraw.processAction();
            }
        } else
            // TODO ritorna ActionMessage per settore non pericoloso
            ;
    }
}
