package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

/**
 * The Class CliCardView.
 */
public class CliCardView extends View {

    /**
     * Updates the CLI using information from model.
     *
     * @param model
     *            the model
     */
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        Card viewModel = (Card) model;
        switch (viewModel.getType()) {
            case ITEM:
                this.itemPrinter(viewModel);
                break;
            case PLAYER:
                this.playerPrinter(viewModel);
                break;
            case SECTOR:
                this.sectorPrinter(viewModel);
                break;
            case HATCH:
                this.hatchPrinter(viewModel);
                break;
            default:
                CliEngine.printLineToCli("\r\nWrong card model received.");
                break;
        }
    }

    /**
     * Prints the item of the item card.
     *
     * @param viewModel
     *            the card view model
     */
    private void itemPrinter(Card viewModel) {
        CliEngine.printToCli("\r\nItem card: ");
        ItemCard icard = (ItemCard) viewModel;
        switch (icard.getItem()) {
            case ADRENALINE:
                CliEngine.printLineToCli("ADRENALINE");
                break;
            case ATTACK:
                CliEngine.printLineToCli("ATTACK");
                break;
            case DEFENSE:
                CliEngine.printLineToCli("DEFENSE");
                break;
            case SEDATIVES:
                CliEngine.printLineToCli("SEDATIVES");
                break;
            case SPOTLIGHT:
                CliEngine.printLineToCli("SPOTLIGHT");
                break;
            case TELEPORT:
                CliEngine.printLineToCli("TELEPORT");
                break;
            default:
                CliEngine.printLineToCli("WRONG ITEM CARD");
                break;
        }
    }

    /**
     * Prints the name of the player card.
     *
     * @param viewModel
     *            the card view model
     */
    private void playerPrinter(Card viewModel) {
        CliEngine.printLineToCli("\r\nPlayer card: ");
        PlayerCard pcard = (PlayerCard) viewModel;
        switch (pcard.getCharacter()) {
            case THE_FIRST_ALIEN:
                CliEngine.printLineToCli("Fist Alien");
                break;
            case THE_SECOND_ALIEN:
                CliEngine.printLineToCli("Second Alien");
                break;
            case THE_THIRD_ALIEN:
                CliEngine.printLineToCli("Third Alien");
                break;
            case THE_FOURTH_ALIEN:
                CliEngine.printLineToCli("Fourth Alien");
                break;
            case THE_CAPTAIN:
                CliEngine.printLineToCli("The Captain");
                break;
            case THE_PILOT:
                CliEngine.printLineToCli("The Pilot");
                break;
            case THE_PSYCHOLOGIST:
                CliEngine.printLineToCli("The Psychologist");
                break;
            case THE_SOLDIER:
                CliEngine.printLineToCli("The soldier");
                break;
            default:
                CliEngine.printLineToCli("Wrong player");
                break;
        }
    }

    /**
     * Prints the sector event of the sector card.
     *
     * @param viewModel
     *            the card view model
     */
    private void sectorPrinter(Card viewModel) {
        CliEngine.printToCli("\r\nSector card: ");
        SectorCard scard = (SectorCard) viewModel;
        switch (scard.getEvent()) {
            case SILENCE:
                CliEngine.printLineToCli("SILENCE");
                break;
            case NOISE_YOUR:
                this.noiseYourPrinter(scard);
                break;
            case NOISE_ANY:
                this.noiseAnyPrinter(scard);
                break;
            default:
                CliEngine.printLineToCli("WRONG SECTOR CARD");
                break;
        }
    }

    /**
     * Prints the hatch chance of the hatch card.
     *
     * @param viewModel
     *            the card view model
     */
    private void hatchPrinter(Card viewModel) {
        CliEngine.printToCli("\r\nHatch card: ");
        HatchCard hcard = (HatchCard) viewModel;
        if (hcard.getChance().equals(HatchChance.FREE)) {
            CliEngine.printLineToCli("GREEN ESCAPE HATCH CARD");
        } else {
            CliEngine.printLineToCli("RED ESCAPE HATCH CARD");
        }
    }

    /**
     * Prints the sector event noise your checking the presence of the item icon
     * on the sector card.
     *
     * @param scard
     *            the sector card
     */
    private void noiseYourPrinter(SectorCard scard) {
        if (scard.hasObjectSymbol()) {
            CliEngine.printLineToCli("NOISE IN YOUR SECTOR with ITEM ICON");
        } else
            CliEngine.printLineToCli("NOISE IN YOUR SECTOR without ITEM ICON");
    }

    /**
     * Prints the sector event noise any checking the presence of the item icon
     * on the sector card.
     *
     * @param scard
     *            the sector card
     */
    private void noiseAnyPrinter(SectorCard scard) {
        if (scard.hasObjectSymbol()) {
            CliEngine.printLineToCli("NOISE IN ANY SECTOR with ITEM ICON");
        } else
            CliEngine.printLineToCli("NOISE IN ANY SECTOR without ITEM ICON");
    }

}
