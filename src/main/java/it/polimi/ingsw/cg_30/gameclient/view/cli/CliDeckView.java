package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.DeckViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliDeckView extends View {

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        DeckViewModel<Card> viewModel = (DeckViewModel<Card>) model;
        switch (viewModel.getPlayerCards().size()) {
            case 0:
                CliEngine.printLineToCli("You have no item cards.");
                break;
            case 1:
                CliEngine.printLineToCli("You have one item card: ");
                this.printCardName(viewModel.getPlayerCards().get(0));
                break;
            case 2:
                CliEngine.printLineToCli("You have two item cards: ");
                for (Card card : viewModel.getPlayerCards()) {
                    this.printCardName(card);
                }
                break;
            case 3:
                CliEngine.printLineToCli("You have three item cards: ");
                for (Card card : viewModel.getPlayerCards()) {
                    this.printCardName(card);
                }
                break;
            case 4:
                CliEngine.printLineToCli("You have four item cards: ");
                for (Card card : viewModel.getPlayerCards()) {
                    this.printCardName(card);
                }
                break;
            default:
                // should never get here, but just in case...
                CliEngine.printLineToCli("You have these item cards:");
                for (Card card : viewModel.getPlayerCards()) {
                    this.printCardName(card);
                }
                break;
        }
    }

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
