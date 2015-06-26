package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JAntiAliasedLabel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.PlaceholderTextField;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ChooseZoneView {

    JFrame chooseFrame;

    /**
     * @wbp.parser.entryPoint
     */
    public void initialize() {
        chooseFrame = new JFrame();
        chooseFrame.setMaximumSize(new Dimension(470, 325));
        chooseFrame.setMinimumSize(new Dimension(470, 325));
        chooseFrame.setPreferredSize(new Dimension(470, 325));
        chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseFrame.setIconImage(GuiEngine.loadImage("custom_icon.png"));
        chooseFrame.setAlwaysOnTop(true);
        chooseFrame
                .setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        chooseFrame.setResizable(false);
        chooseFrame.setBounds(GameView.CENTER.x - chooseFrame.getWidth() / 2,
                GameView.CENTER.y - chooseFrame.getHeight() / 2,
                chooseFrame.getWidth(), chooseFrame.getHeight());
        GridBagLayout gridBagLayout = new GridBagLayout();

        chooseFrame.getContentPane().setLayout(gridBagLayout);

        JLabel nickLabel = new JAntiAliasedLabel("Enter your nickname:");
        nickLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcNickLabel = new GridBagConstraints();
        gbcNickLabel.insets = new Insets(0, 0, 5, 0);
        gbcNickLabel.gridx = 0;
        gbcNickLabel.gridy = 0;
        chooseFrame.getContentPane().add(nickLabel, gbcNickLabel);

        final PlaceholderTextField nickTextField = new PlaceholderTextField();
        nickTextField.setPlaceholder("Nickname");
        nickTextField.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        nickTextField.setMinimumSize(new Dimension(4, 25));
        nickTextField.setColumns(10);
        GridBagConstraints gbc_nickTextField = new GridBagConstraints();
        gbc_nickTextField.insets = new Insets(0, 0, 5, 0);
        gbc_nickTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nickTextField.gridx = 0;
        gbc_nickTextField.gridy = 1;
        chooseFrame.getContentPane().add(nickTextField, gbc_nickTextField);
        nickLabel.setLabelFor(nickTextField);

        JLabel zoneLabel = new JAntiAliasedLabel(
                "Choose the zone where you would like to play:");
        zoneLabel.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        zoneLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbcZoneLabel = new GridBagConstraints();
        gbcZoneLabel.insets = new Insets(0, 0, 5, 0);
        gbcZoneLabel.fill = GridBagConstraints.BOTH;
        gbcZoneLabel.gridx = 0;
        gbcZoneLabel.gridy = 3;
        chooseFrame.getContentPane().add(zoneLabel, gbcZoneLabel);

        final JComboBox<String> zoneCombo = new JComboBox<String>();
        zoneCombo.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        zoneLabel.setLabelFor(zoneCombo);
        zoneCombo.setMinimumSize(new Dimension(25, 22));
        zoneCombo.setEditable(true);
        zoneCombo.setModel(new DefaultComboBoxModel<String>(new String[] {
                "Galilei", "Galvani", "Fermi" }));
        GridBagConstraints gbcZoneCombo = new GridBagConstraints();
        gbcZoneCombo.insets = new Insets(0, 0, 5, 0);
        gbcZoneCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcZoneCombo.gridx = 0;
        gbcZoneCombo.gridy = 4;
        chooseFrame.getContentPane().add(zoneCombo, gbcZoneCombo);

        JLabel partyLabel1 = new JAntiAliasedLabel(
                "Choose the name of your party if you would like to play with your friends,");
        partyLabel1.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        GridBagConstraints gbcPartyLabel1 = new GridBagConstraints();
        gbcPartyLabel1.insets = new Insets(15, 0, 5, 0);
        gbcPartyLabel1.fill = GridBagConstraints.BOTH;
        gbcPartyLabel1.gridx = 0;
        gbcPartyLabel1.gridy = 5;

        chooseFrame.getContentPane().add(partyLabel1, gbcPartyLabel1);
        partyLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel partyLabel2 = new JAntiAliasedLabel(
                "otherwise leave blank and you will be joined to a free party with other people.");
        partyLabel2.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        partyLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcPartyLabel2 = new GridBagConstraints();
        gbcPartyLabel2.insets = new Insets(0, 0, 5, 0);
        gbcPartyLabel2.fill = GridBagConstraints.BOTH;
        gbcPartyLabel2.gridx = 0;
        gbcPartyLabel2.gridy = 6;
        chooseFrame.getContentPane().add(partyLabel2, gbcPartyLabel2);

        final PlaceholderTextField partyTextField = new PlaceholderTextField();
        partyTextField.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        partyTextField.setMinimumSize(new Dimension(4, 25));
        partyTextField.setPlaceholder("Party name");

        GridBagConstraints gbcPartyTextField = new GridBagConstraints();
        gbcPartyTextField.insets = new Insets(0, 0, 5, 0);
        gbcPartyTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcPartyTextField.gridx = 0;
        gbcPartyTextField.gridy = 7;
        chooseFrame.getContentPane().add(partyTextField, gbcPartyTextField);
        partyTextField.setColumns(10);

        JButton joinButton = new JButton("Enter the party");
        joinButton.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 12));
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (GameClient.getActiveEngine() instanceof GuiEngine) {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            if (activeEngine.join(nickTextField.getText(),
                                    (String) zoneCombo.getSelectedItem(),
                                    partyTextField.getText())) {
                                chooseFrame.dispose();
                            }
                        }

                    }
                });
            }
        });
        GridBagConstraints gbcJoinButton = new GridBagConstraints();
        gbcJoinButton.fill = GridBagConstraints.HORIZONTAL;
        gbcJoinButton.gridx = 0;
        gbcJoinButton.gridy = 9;
        chooseFrame.getContentPane().add(joinButton, gbcJoinButton);

        JRootPane rootPane = SwingUtilities.getRootPane(joinButton);
        rootPane.setDefaultButton(joinButton);
    }

    public void setVisible(boolean visible) {
        this.chooseFrame.setVisible(visible);
    }
}
