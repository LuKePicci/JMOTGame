package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.messaging.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CardsImageLoader {

    public static final Map<SectorEvent, ImageIcon> SECTOR_CARDS = new HashMap<SectorEvent, ImageIcon>();
    public static final Map<HatchChance, ImageIcon> HATCH_CARDS = new HashMap<HatchChance, ImageIcon>();
    public static final Map<Item, ImageIcon> ITEM_CARDS = new HashMap<Item, ImageIcon>();

    static {
        for (SectorEvent i : SectorEvent.values())
            try {
                SECTOR_CARDS
                        .put(i,
                                new ImageIcon(ImageIO.read(GuiDeckView.class
                                        .getResourceAsStream("/sector_"
                                                + i.toString().toLowerCase()
                                                + ".jpg"))));
            } catch (IOException e) {
                LoggerMethods.iOException(e, "no image found for hatch");
            }

        for (HatchChance i : HatchChance.values())
            try {
                HATCH_CARDS
                        .put(i,
                                new ImageIcon(ImageIO.read(GuiDeckView.class
                                        .getResourceAsStream("/hatch_"
                                                + i.toString().toLowerCase()
                                                + ".jpg"))));
            } catch (IOException e) {
                LoggerMethods.iOException(e, "no image found for hatch");
            }

        for (Item i : Item.values())
            try {
                ITEM_CARDS
                        .put(i,
                                new ImageIcon(ImageIO.read(GuiDeckView.class
                                        .getResourceAsStream("/item_"
                                                + i.toString().toLowerCase()
                                                + ".jpg"))));
            } catch (IOException e) {
                LoggerMethods.iOException(e, "no image found for item");
            }
    }

}
