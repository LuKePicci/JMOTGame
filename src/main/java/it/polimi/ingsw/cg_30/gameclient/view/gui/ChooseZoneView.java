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

    private String titilliumTextString = "TitilliumText22L";

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
        nickLabel.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_nickLabel = new GridBagConstraints();
        gbc_nickLabel.insets = new Insets(0, 0, 5, 0);
        gbc_nickLabel.gridx = 0;
        gbc_nickLabel.gridy = 0;
        chooseFrame.getContentPane().add(nickLabel, gbc_nickLabel);

        final PlaceholderTextField nickTextField = new PlaceholderTextField();
        nickTextField.setPlaceholder("Nickname");
        nickTextField.setFont(GuiEngine.loadCustomFont(titilliumTextString)
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
        zoneLabel.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        zoneLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_zoneLabel = new GridBagConstraints();
        gbc_zoneLabel.insets = new Insets(0, 0, 5, 0);
        gbc_zoneLabel.fill = GridBagConstraints.BOTH;
        gbc_zoneLabel.gridx = 0;
        gbc_zoneLabel.gridy = 3;
        chooseFrame.getContentPane().add(zoneLabel, gbc_zoneLabel);

        final JComboBox<String> zoneCombo = new JComboBox<String>();
        zoneCombo.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        zoneLabel.setLabelFor(zoneCombo);
        zoneCombo.setMinimumSize(new Dimension(25, 22));
        zoneCombo.setEditable(true);
        zoneCombo.setModel(new DefaultComboBoxModel<String>(new String[] {
                "Galilei", "Galvani", "Fermi" }));
        GridBagConstraints gbc_zoneCombo = new GridBagConstraints();
        gbc_zoneCombo.insets = new Insets(0, 0, 5, 0);
        gbc_zoneCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_zoneCombo.gridx = 0;
        gbc_zoneCombo.gridy = 4;
        chooseFrame.getContentPane().add(zoneCombo, gbc_zoneCombo);

        JLabel partyLabel1 = new JAntiAliasedLabel(
                "Choose the name of your party if you would like to play with your friends,");
        partyLabel1.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        GridBagConstraints gbc_partyLabel1 = new GridBagConstraints();
        gbc_partyLabel1.insets = new Insets(15, 0, 5, 0);
        gbc_partyLabel1.fill = GridBagConstraints.BOTH;
        gbc_partyLabel1.gridx = 0;
        gbc_partyLabel1.gridy = 5;

        chooseFrame.getContentPane().add(partyLabel1, gbc_partyLabel1);
        partyLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel partyLabel2 = new JAntiAliasedLabel(
                "otherwise leave blank and you will be joined to a free party with other people.");
        partyLabel2.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        partyLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_partyLabel2 = new GridBagConstraints();
        gbc_partyLabel2.insets = new Insets(0, 0, 5, 0);
        gbc_partyLabel2.fill = GridBagConstraints.BOTH;
        gbc_partyLabel2.gridx = 0;
        gbc_partyLabel2.gridy = 6;
        chooseFrame.getContentPane().add(partyLabel2, gbc_partyLabel2);

        final PlaceholderTextField partyTextField = new PlaceholderTextField();
        partyTextField.setFont(GuiEngine.loadCustomFont(titilliumTextString)
                .deriveFont(0, 12));
        partyTextField.setMinimumSize(new Dimension(4, 25));
        partyTextField.setPlaceholder("Party name");

        GridBagConstraints gbc_partyTextField = new GridBagConstraints();
        gbc_partyTextField.insets = new Insets(0, 0, 5, 0);
        gbc_partyTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_partyTextField.gridx = 0;
        gbc_partyTextField.gridy = 7;
        chooseFrame.getContentPane().add(partyTextField, gbc_partyTextField);
        partyTextField.setColumns(10);

        JButton joinButton = new JButton("Enter the party");
        joinButton.setFont(GuiEngine.loadCustomFont(titilliumTextString)
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
        GridBagConstraints gbc_joinButton = new GridBagConstraints();
        gbc_joinButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_joinButton.gridx = 0;
        gbc_joinButton.gridy = 9;
        chooseFrame.getContentPane().add(joinButton, gbc_joinButton);

        JRootPane rootPane = SwingUtilities.getRootPane(joinButton);
        rootPane.setDefaultButton(joinButton);
    }

    public void setVisible(boolean visible) {
        this.chooseFrame.setVisible(visible);
    }
}
