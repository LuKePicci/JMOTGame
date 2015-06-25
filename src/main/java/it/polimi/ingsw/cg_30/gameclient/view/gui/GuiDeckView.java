package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.viewmodels.DeckViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JEftaiosCard;
import it.polimi.ingsw.cg_30.gameclient.view.gui.eventhandlers.MouseHoverMagnify;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GuiDeckView extends GuiView {

    JPanel deckPane;
    JLabel emptyLabel;

    private static final Map<Item, ImageIcon> CARD_FRONTS = new HashMap<Item, ImageIcon>();

    static {
        for (Item i : Item.values())
            try {
                CARD_FRONTS
                        .put(i,
                                new ImageIcon(
                                        ImageIO.read(GuiDeckView.class
                                                .getResourceAsStream("/gameclient/item_"
                                                        + i.toString()
                                                                .toLowerCase()
                                                        + ".jpg"))));
            } catch (IOException e) {
                LoggerMethods.iOException(e, "no image found for item");
            }
    }

    @Override
    public JPanel getComponent() {
        if (this.deckPane == null)
            this.createComponents();
        return this.deckPane;
    }

    @Override
    protected void createComponents() {
        deckPane = new JPanel();
        deckPane.setLayout(new BoxLayout(deckPane, BoxLayout.X_AXIS));

        deckPane.add(Box.createHorizontalGlue());

        emptyLabel = new JLabel("No items in your deck.");
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        emptyLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, emptyLabel.getFont().getSize()));
        deckPane.add(emptyLabel);

        deckPane.add(Box.createHorizontalGlue());

    }

    @Override
    public void applyUpdate(ViewModel model) {
        @SuppressWarnings("unchecked")
        DeckViewModel<ItemCard> deck = (DeckViewModel<ItemCard>) model;
        deckPane.removeAll();
        deckPane.revalidate();
        deckPane.repaint();

        deckPane.add(Box.createHorizontalGlue());

        if (deck.getPlayerCards() != null && deck.getPlayerCards().size() > 0)
            for (ItemCard card : deck.getPlayerCards())
                deckPane.add(this.newCard(card.getItem()));

        else
            deckPane.add(this.emptyLabel);

        deckPane.add(Box.createHorizontalGlue());

        deckPane.revalidate();
    }

    private JLabel newCard(Item itemType) {
        JLabel newCardLabel;
        if (CARD_FRONTS.containsKey(itemType)) {
            newCardLabel = new JEftaiosCard(itemType, CARD_FRONTS.get(itemType));
            newCardLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (GameClient.getActiveEngine() instanceof GuiEngine) {
                        final JEftaiosCard sender = (JEftaiosCard) e
                                .getSource();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                GuiEngine activeEngine = (GuiEngine) GameClient
                                        .getActiveEngine();
                                activeEngine.cardProcessor(sender.getItemType());
                            }
                        });

                    }
                }
            });
        } else
            newCardLabel = new JLabel();
        newCardLabel.setToolTipText(itemType.toString().toUpperCase());
        newCardLabel.addMouseListener(new MouseHoverMagnify(
                GameView.CARD_SIZE.width / 2, GameView.CARD_SIZE.height / 2,
                GameView.CARD_SIZE.width / 12));
        newCardLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newCardLabel.setBorder(BorderFactory.createEmptyBorder(
                GameView.CARD_SIZE.height / 2, GameView.CARD_SIZE.width / 2,
                GameView.CARD_SIZE.height / 2, GameView.CARD_SIZE.width / 2));
        newCardLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, newCardLabel.getFont().getSize()));
        return newCardLabel;
    }
}
