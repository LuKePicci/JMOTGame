package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.DeckViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliDeckView.
 */
public class CliDeckView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        @SuppressWarnings("unchecked")
        DeckViewModel<Card> viewModel = (DeckViewModel<Card>) model;
        if (viewModel.getPlayerCards() != null) {
            switch (viewModel.getPlayerCards().size()) {
                case 0:
                    CliEngine.printLineToCli("\r\nYou have no item cards.");
                    break;
                case 1:
                    CliEngine.printLineToCli("\r\nYou have one item card: ");
                    break;
                case 2:
                    CliEngine.printLineToCli("\r\nYou have two item cards: ");
                    break;
                case 3:
                    CliEngine.printLineToCli("\r\nYou have three item cards: ");
                    break;
                case 4:
                    CliEngine.printLineToCli("\r\nYou have four item cards: ");
                    break;
                default:
                    // should never get here, but just in case...
                    CliEngine.printLineToCli("\r\nYou have these item cards:");
                    break;
            }

            for (Card card : viewModel.getPlayerCards()) {
                this.printCardName(card);
            }

        } else
            CliEngine.printLineToCli("\r\nYou have no item cards.");
    }

    /**
     * Prints the item card name.
     *
     * @param card
     *            the item card
     */
    private void printCardName(Card card) {
        ItemCard icard = (ItemCard) card;
        switch (icard.getItem()) {
            case ATTACK:
                CliEngine.printLineToCli("ATTACK CARD");
                break;
            case TELEPORT:
                CliEngine.printLineToCli("TELEPORT CARD");
                break;
            case SEDATIVES:
                CliEngine.printLineToCli("SEDATIVES CARD");
                break;
            case SPOTLIGHT:
                CliEngine.printLineToCli("SPOTLIGHT CARD");
                break;
            case DEFENSE:
                CliEngine.printLineToCli("DEFENSE CARD");
                break;
            case ADRENALINE:
                CliEngine.printLineToCli("ADRENALINE CARD");
                break;
            default:
                CliEngine.printLineToCli("UNKNOWN ITEM CARD");
                break;
        }
    }

}
