package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliCardView extends View {
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        Card viewModel = (Card) model;
        switch (viewModel.getType()) {
            case ITEM:
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
                }

                break;
            case HATCH:
                CliEngine.printToCli("Hatch card: ");
                HatchCard hcard = (HatchCard) viewModel;
                if (hcard.getChance().equals(HatchChance.FREE)) {
                    CliEngine.printLineToCli("GREEN ESCAPE HATCH CARD");
                } else {
                    CliEngine.printLineToCli("RED ESCAPE HATCH CARD");
                }

                break;
            case PLAYER:
                CliEngine.printLineToCli("Player card: ");
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
                }
                break;
            case SECTOR:
                CliEngine.printToCli("Sector card: ");
                SectorCard scard = (SectorCard) viewModel;
                switch (scard.getEvent()) {
                    case SILENCE:
                        CliEngine.printLineToCli("SILENCE");
                        break;
                    case NOISE_YOUR:
                        if (scard.hasObjectSymbol()) {
                            CliEngine
                                    .printLineToCli("NOISE IN YOUR SECTOR with ITEM ICON");
                        } else
                            CliEngine
                                    .printLineToCli("NOISE IN YOUR SECTOR without ITEM ICON");
                        break;
                    case NOISE_ANY:
                        if (scard.hasObjectSymbol()) {
                            CliEngine
                                    .printLineToCli("NOISE IN ANY SECTOR with ITEM ICON");
                        } else
                            CliEngine
                                    .printLineToCli("NOISE IN ANY SECTOR without ITEM ICON");
                        break;
                }
                break;
        }
    }
}
