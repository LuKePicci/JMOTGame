package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class CardsImageLoader {

    public static final Map<SectorEvent, ImageIcon> SECTOR_CARDS = new HashMap<SectorEvent, ImageIcon>();
    public static final Map<HatchChance, ImageIcon> HATCH_CARDS = new HashMap<HatchChance, ImageIcon>();
    public static final Map<Item, ImageIcon> ITEM_CARDS = new HashMap<Item, ImageIcon>();

    static {
        for (SectorEvent i : SectorEvent.values())
            SECTOR_CARDS.put(
                    i,
                    new ImageIcon(GuiEngine.loadImage("sector_"
                            + i.toString().toLowerCase() + ".jpg")));

        for (HatchChance i : HatchChance.values())
            HATCH_CARDS.put(
                    i,
                    new ImageIcon(GuiEngine.loadImage("hatch_"
                            + i.toString().toLowerCase() + ".png")));

        for (Item i : Item.values())
            ITEM_CARDS.put(
                    i,
                    new ImageIcon(GuiEngine.loadImage("item_"
                            + i.toString().toLowerCase() + ".jpg")));
    }

    private CardsImageLoader() {
    }
}
