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
        this.player = matchController.getCurrentTurn().getTurn()
                .getCurrentPlayer();
        this.spareDeck = matchController.getCurrentTurn().getTurn()
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
        // TO DO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        // verifico che player sia umano e non alieno
        if (player.getIdentity().getRace().equals(PlayerRace.ALIEN)) {
            return false;
        } else {
            // verifico che player possieda la carta
            if (spareDeck.getCards().contains(card)) {
                if (card.equals(Item.DEFENSE)) {// non posso attivare la carta
                                                // difesa
                    return false;
                }
                if (card.equals(Item.ADRENALINE)
                        && (matchController.getCurrentTurn().getTurn()
                                .getMustMove() == false)) {
                    return false; // va usata prima di muoversi
                }
                if (card.equals(Item.SEDATIVES)
                        && (matchController.getCurrentTurn().getTurn()
                                .getMustMove() == false)) {
                    return false;// va usata prima di muoversi
                }
                return true;
            } else
                return false;
        }
    }

    @Override
    public ActionMessage processAction() {
        if (card.equals(Item.ATTACK)) {
            Attack attack = new Attack(matchController);
            attack.processAction();
            // TO DO devo gestire il messaggio ritornato da attack
            matchController.getItemsDeck().putIntoBucket(card);
        }
        if (card.equals(Item.TELEPORT)) {
            Sector target = new Sector(null, null);
            // TO DO target deve essere il settore di partenza degli umani;
            // target = SETTORE DI PARTENZA UMANI;
            matchController.getZoneController().getCurrentZone()
                    .movePlayer(player, target);
        }
        if (card.equals(Item.ADRENALINE)) {
            matchController.getCurrentTurn().getTurn().setMaxSteps(2);
        }
        if (card.equals(Item.SEDATIVES)) {
            matchController.getCurrentTurn().getTurn().setSilenceForced(true);
        }
        if (card.equals(Item.SPOTLIGHT)) {
            // identifico i settori
            Sector start = new Sector(null, null);
            // TO DO chiedere a player il settore centrale di partenza e
            // impostarne il valoce in start
            start = null; // settore scelto dal player
            Set<Sector> borderSectors = new HashSet<Sector>();
            borderSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(start, 1);// settori
                                                                 // confinanti
            borderSectors.add(start);
            for (Sector sec : borderSectors) {
                Set<Player> watchedPlayers = new HashSet<Player>();
                watchedPlayers = (Set<Player>) matchController
                        .getZoneController().getCurrentZone()
                        .getPlayersInSector(sec);
                // TO DO avvisa tutti che nel settore sec si trovano i players
                // watchedPlayers (ma non terminare il metodo)
            }
        }
        // scarto la carta oggetto utilizzata
        matchController.getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // TO DO rimuovere la seguente riga
        return null;
    }

}
