package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JEftaiosGeneralCard;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class GuiCardView extends GuiView {

    private JDialog cardDialog;
    private JLabel cardPicture;
    private JButton closeButton;
    private final ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardDialog.dispatchEvent(new WindowEvent(cardDialog,
                    WindowEvent.WINDOW_CLOSING));
            closeTimer.stop();
        }
    };
    private final Timer closeTimer = new Timer(3000, this.closeListener);

    @Override
    public JDialog getComponent() {
        if (this.cardDialog == null)
            this.createComponents();
        return this.cardDialog;
    }

    @Override
    protected void createComponents() {
        cardDialog = new JDialog(null, Dialog.ModalityType.APPLICATION_MODAL);
        cardDialog.setLayout(new BorderLayout());
        cardDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.cardPicture = new JEftaiosGeneralCard();
        cardDialog.add(this.cardPicture, BorderLayout.CENTER);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this.closeListener);
        cardDialog.add(closeButton, BorderLayout.SOUTH);
    }

    @Override
    public void applyUpdate(ViewModel model) {
        Card card = (Card) model;
        this.cardPicture.setIcon(this.getCardIcon(card));

        this.getComponent().setVisible(true);

        this.closeTimer.start();
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
