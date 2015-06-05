package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class UseCard extends ActionController {

    private MatchController matchController;
    private SpareDeck spareDeck;
    private Player player;
    private ItemCard card;

    public UseCard(MatchController matchController, ItemCard card) {
        this.matchController = matchController;
        this.card = card;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
    }

    /*
     * LEGGIMI cosa presumo di sapere implementando questo metodo: il player ha
     * un elenco con tutti i tipi di itemCard e può chiedere l'utilizzo di
     * qualunque carta, sarà isValid che negherà l'uso di una carta se il
     * giocatore non la possiede o non è in condizione di usarla.
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
            if (spareDeck.getCards().contains(card)) {
                if (Item.DEFENSE.equals(card)) {// non posso attivare la carta
                                                // difesa
                    return false;
                } else if ((Item.ADRENALINE.equals(card))
                        && (matchController.getTurnController().getTurn()
                                .getMustMove() == false)) {
                    return false; // va usata prima di muoversi
                } else if (Item.SEDATIVES.equals(card)
                        && (matchController.getTurnController().getTurn()
                                .getMustMove() == false)) {
                    return false;// va usata prima di muoversi
                }
                return true;
            } else
                return false;
        }
    }

    @Override
    public void processAction() {
        if (Item.ATTACK.equals(card)) {
            Attack attack = new Attack(matchController);
            attack.processAction();
            matchController.getMatch().getItemsDeck().putIntoBucket(card);
        } else if (Item.TELEPORT.equals(card)) {
            Sector target = new Sector(null, null);
            // TODO target deve essere il settore di partenza degli umani;
            // target = SETTORE DI PARTENZA UMANI;
            matchController.getZoneController().getCurrentZone()
                    .movePlayer(player, target);
        } else if (Item.ADRENALINE.equals(card)) {
            matchController.getTurnController().getTurn().setMaxSteps(2);
        } else if (Item.SEDATIVES.equals(card)) {
            matchController.getTurnController().getTurn()
                    .setSilenceForced(true);
        } else if (Item.SPOTLIGHT.equals(card)) {
            // identifico i settori
            Sector start = new Sector(null, null);
            // TODO chiedere a player il settore centrale di partenza e
            // impostarne il valoce in start
            start = null; // settore scelto dal player
            Set<Sector> borderSectors = new HashSet<Sector>();
            borderSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(start, 1);// settori
                                                                 // confinanti
            borderSectors.add(start);
            for (Sector sec : borderSectors) {
                Set<Player> watchedPlayers = new HashSet<Player>();
                watchedPlayers = matchController.getZoneController()
                        .getCurrentZone().getPlayersInSector(sec);
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
