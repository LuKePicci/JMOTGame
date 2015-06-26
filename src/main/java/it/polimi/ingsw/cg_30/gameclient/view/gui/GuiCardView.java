package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JEftaiosGeneralCard;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GuiCardView extends GuiView {

    public static final Dimension SIZE_CARD_SHOWN = GuiEngine.getResponsive(
            9.1429, 3.6);
    private JDialog cardDialog;
    private JLabel cardPicture;
    private JButton closeButton;
    private final ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardDialog.setVisible(false);
            closeTimer.stop();
        }
    };
    private Timer closeTimer;

    @Override
    public JDialog getComponent() {
        if (this.cardDialog == null)
            this.createComponents();
        return this.cardDialog;
    }

    @Override
    protected void createComponents() {
        closeTimer = new Timer(3000, closeListener);

        cardDialog = new JDialog(null, Dialog.ModalityType.APPLICATION_MODAL);
        cardDialog.setLayout(new BorderLayout());
        cardDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        cardDialog.setMaximumSize(SIZE_CARD_SHOWN);
        cardDialog.setMinimumSize(SIZE_CARD_SHOWN);
        cardDialog.setPreferredSize(SIZE_CARD_SHOWN);
        cardDialog.setBounds(GameView.CENTER.x - cardDialog.getWidth() / 2,
                GameView.CENTER.y - cardDialog.getHeight() / 2,
                cardDialog.getWidth(), cardDialog.getHeight());

        cardPicture = new JEftaiosGeneralCard();
        cardPicture.setHorizontalAlignment(SwingConstants.CENTER);
        cardDialog.add(this.cardPicture, BorderLayout.CENTER);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this.closeListener);
        cardDialog.add(closeButton, BorderLayout.SOUTH);

        cardDialog.setVisible(false);
    }

    @Override
    public void applyUpdate(ViewModel model) {
        Card card = (Card) model;
        this.cardPicture.setIcon(this.getCardIcon(card));

        if (this.closeTimer != null)
            this.closeTimer.start();
        this.getComponent().setVisible(true);

    }

    private ImageIcon getCardIcon(Card card) {
        switch (card.getType()) {
            case HATCH:
                HatchCard hcard = (HatchCard) card;
                return CardsImageLoader.HATCH_CARDS.get(hcard.getChance());
            case ITEM:
                ItemCard icard = (ItemCard) card;
                return CardsImageLoader.ITEM_CARDS.get(icard.getItem());
            case SECTOR:
                SectorCard scard = (SectorCard) card;
                return CardsImageLoader.SECTOR_CARDS.get(scard.getEvent());
            case PLAYER:
            default:
                break;
        }
        return null;
    }

}
