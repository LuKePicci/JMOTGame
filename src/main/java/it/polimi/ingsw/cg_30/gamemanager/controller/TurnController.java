package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.Set;

/**
 * The Class TurnController.
 */
public class TurnController {

    /** The turn. */
    protected Turn turn;

    /** The max turn number. */
    private static final int MAX_TURN = 39;

    /**
     * Instance of discard card action needed if a player can't end his turn
     * properly.
     */
    protected DiscardCard forcedDiscard = new DiscardCard();

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Sets the turn.
     *
     * @param turn
     *            the new turn
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Prepares the first turn by assigning it to the player whose index is one,
     * or to the next online player in case player one is offline.
     *
     * @param matchController
     *            the match controller
     */
    public void firstTurn(MatchController matchController) {
        PlayerCard pcard = new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN);
        Player player0 = new Player("player0", 0, pcard);
        this.turn = new Turn(player0, matchController.getMatch().getTurnCount());
        this.nextTurn(matchController);
    }

    /**
     * Gets the players of this party.
     *
     * @param matchController
     *            the match controller
     * @return the players of the party
     */
    public Set<Player> getPartyPlayers(MatchController matchController) {
        // take all party players passing through matchController
        return matchController.getPartyController().getCurrentParty()
                .getMembers().keySet();// set containing all party players
    }

    /**
     * Assigns the turn to the next player.
     *
     * @param matchController
     *            the match controller
     */
    public void nextTurn(MatchController matchController) {
        // in case a player has gone offline and the new turn is called due to a
        // timer expiration, I must guarantee the player doesn't end his turn
        // with more than three item card
        this.checkLegality(matchController);
        Set<Player> playerList = getPartyPlayers(matchController);
        int playerNumber = playerList.size();
        int index = this.turn.getCurrentPlayer().getIndex();
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                matchController.getMatch().incrementTurnCount();
                if (matchController.getMatch().getTurnCount() == (MAX_TURN + 1)) {
                    matchController.endingByTurnController();
                    return;
                }
            } else {
                index++;
            }
            for (Player nextPlayer : playerList) {
                if (nextPlayer.getIndex() == index
                        && !this.checkIfPlayerIsOnline(nextPlayer,
                                matchController)
                        && !matchController.getMatch().getDeadPlayer()
                                .contains(nextPlayer)
                        && !matchController.getMatch().getRescuedPlayer()
                                .contains(nextPlayer)) {
                    // it's nextPlayer's turn
                    matchController.checkEndGame();
                    this.turn = new Turn(nextPlayer, matchController.getMatch()
                            .getTurnCount());
                    this.notify(nextPlayer, matchController);
                    return;
                }
            }
        }
    }

    /**
     * Checks if a player is ending his turn without having solved the
     * obligation of discarding a card. In case of positive answer, the method
     * discards a card before ending the player's turn.
     *
     * @param matchController
     *            the match controller
     */
    private void checkLegality(MatchController matchController) {
        if (matchController.getTurnController().getTurn().getMustDiscard()) {
            Object[] playerCards = matchController.getTurnController()
                    .getTurn().getCurrentPlayer().getItemsDeck().getCards()
                    .toArray();
            ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD,
                    null, ((ItemCard) playerCards[3]).getItem());
            forcedDiscard.initAction(matchController, action);
            forcedDiscard.processAction();
        }
    }

    /**
     * Checks if the player is online.
     *
     * @param player
     *            the player
     * @param matchController
     *            the match controller
     * @return true, if the player is offline
     */
    protected boolean checkIfPlayerIsOnline(Player player,
            MatchController matchController) {
        return MessageController
                .getPlayerHandler(
                        matchController.getPartyController().getCurrentParty()
                                .getPlayerUUID(player)).getAcceptPlayer()
                .connectionLost();
    }

    /**
     * Notify the new turn to all players.
     *
     * @param nextPlayer
     *            the next player
     * @param matchController
     *            the match controller
     */
    protected void notify(Player nextPlayer, MatchController matchController) {
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel("It's "
                        + nextPlayer.getName() + "'s turn.",
                        matchController.serverWordText, ChatVisibility.PARTY)));
        try {
            matchController.sendViewModelToAPlayer(nextPlayer, matchController
                    .getTurnController().getTurn().getViewModel());
        } catch (DisconnectedException e) {
            // the player will receive the view of his turn when he will
            // connect again thanks to modelSender(Player) (if it's
            // still his turn)
            // Otherwise, after a timeout the turn will be given to the
            // next player
        }
    }

}
