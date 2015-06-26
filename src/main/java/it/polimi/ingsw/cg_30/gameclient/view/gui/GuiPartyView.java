package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PartyViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JAntiAliasedLabel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GuiPartyView extends GuiView {

    private JPanel partyPane;
    private JPanel playersPanel;
    private JLabel partyNameLabel;
    private JLabel cardsLabel;

    @Override
    public JPanel getComponent() {
        if (this.partyPane == null)
            this.createComponents();
        return this.partyPane;
    }

    @Override
    protected void createComponents() {
        this.partyPane = new JPanel();
        partyPane.setMaximumSize(GameView.PARTY_SIZE);
        partyPane.setPreferredSize(GameView.PARTY_SIZE);
        partyPane.setLayout(new BorderLayout());

        JPanel partyInfoPane = new JPanel();
        partyInfoPane.setLayout(new BorderLayout());
        partyPane.add(partyInfoPane, BorderLayout.NORTH);

        this.partyNameLabel = new JAntiAliasedLabel();
        partyNameLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Xb")
                .deriveFont(0, partyNameLabel.getFont().getSize()));
        partyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        partyInfoPane.add(partyNameLabel, BorderLayout.CENTER);

        this.cardsLabel = new JAntiAliasedLabel("Cards");
        cardsLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Xb")
                .deriveFont(0, cardsLabel.getFont().getSize()));
        cardsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardsLabel.setVisible(false);

        partyInfoPane.add(cardsLabel, BorderLayout.EAST);

        JPanel partyContainerPane = new JPanel();
        partyContainerPane.setLayout(new BorderLayout());
        partyPane.add(partyContainerPane, BorderLayout.CENTER);

        this.playersPanel = new JPanel();
        playersPanel.setLayout(new GridBagLayout());
        partyContainerPane.add(playersPanel, BorderLayout.NORTH);
    }

    @Override
    public void applyUpdate(ViewModel model) {
        PartyViewModel party = (PartyViewModel) model;

        this.partyNameLabel.setText(party.getPartyName());
        for (PlayerViewModel pvm : party.getPartyPlayers())
            this.newPlayer(pvm);

        this.cardsLabel.setVisible(true);
    }

    private JPanel newPlayer(PlayerViewModel pvm) {
        GridBagConstraints gbcPlayerPane = new GridBagConstraints();
        gbcPlayerPane.weighty = 0.2;
        gbcPlayerPane.weightx = 0.8;
        gbcPlayerPane.gridx = 0;
        gbcPlayerPane.gridy = pvm.getIndex();
        gbcPlayerPane.fill = GridBagConstraints.BOTH;

        JPanel playerPane = new JPanel();
        playerPane.setLayout(new BorderLayout());
        JLabel playerName = new JAntiAliasedLabel(pvm.getName());
        playerName.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, playerName.getFont().getSize()));
        playerPane.add(playerName, BorderLayout.WEST);
        JLabel playerCardsCount = new JAntiAliasedLabel(Integer.toString(pvm
                .getNumOfItemCards()));
        playerCardsCount.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, playerCardsCount.getFont().getSize()));
        playerPane.add(playerCardsCount, BorderLayout.EAST);

        this.playersPanel.add(playerPane, gbcPlayerPane);

        return playerPane;
    }
}
