package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EftaiosDecks {

    private static final Set<HatchCard> HATCH_CARDS;
    private static final Set<SectorCard> SECTOR_CARDS;
    private static final Set<ItemCard> ITEM_CARDS;
    private static final List<PlayerCard> PLAYER_CARDS;

    static {
        HATCH_CARDS = new HashSet<HatchCard>();
        for (int i = 0; i < 3; i++) {
            HATCH_CARDS.add(new HatchCard(HatchChance.FREE));
            HATCH_CARDS.add(new HatchCard(HatchChance.LOCKED));
        }

        SECTOR_CARDS = new HashSet<SectorCard>();
        for (int i = 0; i < 5; i++) {
            SECTOR_CARDS.add(new SectorCard(SectorEvent.SILENCE, false));
        }
        for (int i = 0; i < 4; i++) {
            SECTOR_CARDS.add(new SectorCard(SectorEvent.NOISE_ANY, true));
            SECTOR_CARDS.add(new SectorCard(SectorEvent.NOISE_YOUR, true));
        }
        for (int i = 0; i < 6; i++) {
            SECTOR_CARDS.add(new SectorCard(SectorEvent.NOISE_ANY, false));
            SECTOR_CARDS.add(new SectorCard(SectorEvent.NOISE_YOUR, false));
        }

        ITEM_CARDS = new HashSet<ItemCard>();
        ITEM_CARDS.add(new ItemCard(Item.DEFENSE));
        for (int i = 0; i < 2; i++) {
            ITEM_CARDS.add(new ItemCard(Item.ADRENALINE));
            ITEM_CARDS.add(new ItemCard(Item.ATTACK));
            ITEM_CARDS.add(new ItemCard(Item.TELEPORT));
            ITEM_CARDS.add(new ItemCard(Item.SPOTLIGHT));
        }
        for (int i = 0; i < 3; i++) {
            ITEM_CARDS.add(new ItemCard(Item.SEDATIVES));
        }

        PLAYER_CARDS = new ArrayList<PlayerCard>();
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_SOLDIER));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FOURTH_ALIEN));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_PSYCHOLOGIST));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_THIRD_ALIEN));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_PILOT));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_SECOND_ALIEN));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN));
        PLAYER_CARDS.add(new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN));
    }

    /**
     * Stacked deck hatch.
     *
     * @return the stacked deck of HatchCard
     */
    public static StackedDeck<HatchCard> newStackedDeckHatch() {
        StackedDeck<HatchCard> ex = new StackedDeck<HatchCard>(HATCH_CARDS);
        ex.shuffle();
        return ex;
    }

    /**
     * Stacked deck sector.
     *
     * @return the stacked deck of SectorCard
     */
    public static StackedDeck<SectorCard> newStackedDeckSector() {
        StackedDeck<SectorCard> ex = new StackedDeck<SectorCard>(SECTOR_CARDS);
        ex.shuffle();
        return ex;
    }

    /**
     * Stacked deck item.
     *
     * @return the stacked deck of ItemCard
     */
    public static StackedDeck<ItemCard> newStackedDeckItem() {
        StackedDeck<ItemCard> ex = new StackedDeck<ItemCard>(ITEM_CARDS);
        ex.shuffle();
        return ex;
    }

    /**
     * Stacked deck player.
     *
     * @return the stacked deck of PlayerCard
     */
    public static StackedDeck<PlayerCard> newStackedDeckPlayer() {
        return new StackedDeck<PlayerCard>(PLAYER_CARDS);
    }

}
