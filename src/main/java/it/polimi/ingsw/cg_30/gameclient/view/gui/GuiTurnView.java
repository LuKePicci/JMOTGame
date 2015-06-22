package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class GuiTurnView extends GuiView {

    private JPanel turnPane;
    private JToggleButton discardButton;
    private JButton turnoverButton;
    private JButton drawButton;
    private JButton attackButton;
    private JLabel turnCountdown;
    private JLabel turnNick;
    private JLabel turnNumber;

    @Override
    public JComponent getComponent() {
        if (this.turnPane == null)
            this.createComponents();
        return this.turnPane;
    }

    @Override
    protected void createComponents() {
        turnPane = new JPanel();
        turnPane.setAlignmentY(Component.TOP_ALIGNMENT);
        turnPane.setLayout(new BorderLayout());

        JPanel turnInfo = new JPanel();
        turnInfo.setLayout(new GridBagLayout());
        turnPane.add(turnInfo, BorderLayout.NORTH);

        turnNumber = new JLabel();
        turnNumber.setFont(turnNumber.getFont().deriveFont(Font.PLAIN, 27));
        turnNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        turnNumber.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        GridBagConstraints gbc_turnNumber = new GridBagConstraints();
        gbc_turnNumber.weightx = 0.2;
        gbc_turnNumber.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc_turnNumber.gridy = 0;
        gbc_turnNumber.gridx = 0;
        gbc_turnNumber.fill = GridBagConstraints.HORIZONTAL;
        turnInfo.add(turnNumber, gbc_turnNumber);

        JLabel turnTotal = new JLabel("/39");
        turnTotal.setHorizontalAlignment(SwingConstants.LEFT);
        turnTotal.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc_turnTotal = new GridBagConstraints();
        gbc_turnTotal.weightx = 0.15;
        gbc_turnTotal.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc_turnTotal.gridy = 0;
        gbc_turnTotal.gridx = 1;
        gbc_turnTotal.fill = GridBagConstraints.HORIZONTAL;
        turnInfo.add(turnTotal, gbc_turnTotal);

        turnNick = new JLabel();
        turnNick.setHorizontalAlignment(SwingConstants.CENTER);
        turnNick.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc_turnNick = new GridBagConstraints();
        gbc_turnNick.weightx = 0.65;
        gbc_turnNick.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc_turnNick.fill = GridBagConstraints.HORIZONTAL;
        gbc_turnNick.gridy = 0;
        gbc_turnNick.gridx = 1;
        turnInfo.add(turnNick, gbc_turnNick);

        turnCountdown = new JLabel();
        turnCountdown.setHorizontalAlignment(SwingConstants.CENTER);
        turnCountdown.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc_turnCountdown = new GridBagConstraints();
        gbc_turnCountdown.gridwidth = 2;
        gbc_turnCountdown.fill = GridBagConstraints.HORIZONTAL;
        gbc_turnCountdown.gridy = 1;
        gbc_turnCountdown.gridx = 0;
        turnInfo.add(turnCountdown, gbc_turnCountdown);

        JPanel turnButtons = new JPanel();
        turnButtons.setAlignmentY(Component.TOP_ALIGNMENT);
        turnButtons.setLayout(new GridLayout(2, 2));
        turnPane.add(turnButtons, BorderLayout.SOUTH);

        attackButton = new JButton("Attack");
        attackButton.setEnabled(false);
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    GuiEngine activeEngine = (GuiEngine) GameClient
                            .getActiveEngine();
                    activeEngine.attackProcessor();
                }
            }
        });
        turnButtons.add(attackButton);

        drawButton = new JButton("Draw");
        drawButton.setEnabled(false);
        drawButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    GuiEngine activeEngine = (GuiEngine) GameClient
                            .getActiveEngine();
                    activeEngine.drawProcessor();
                }
            }
        });
        turnButtons.add(drawButton);

        turnoverButton = new JButton("Turnover");
        turnoverButton.setEnabled(false);
        turnoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    GuiEngine activeEngine = (GuiEngine) GameClient
                            .getActiveEngine();
                    activeEngine.turnoverProcessor();
                }
            }
        });
        turnButtons.add(turnoverButton);

        discardButton = new JToggleButton("Discard");
        discardButton.setEnabled(false);
        discardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JToggleButton sender = (JToggleButton) e.getSource();
                GuiEngine.setDiscardCard(sender.isSelected());
            }
        });
        turnButtons.add(discardButton);
    }

    @Override
    public void applyUpdate(ViewModel model) {
        TurnViewModel turn = (TurnViewModel) model;

        this.attackButton.setEnabled(turn.canAttack());
        this.discardButton.setSelected(false);
        this.discardButton.setEnabled(turn.mustDiscard());
        this.turnCountdown.setText(ViewEngine.SDF.format(turn.getTurnStart()));
        this.turnNumber.setText(Integer.toString(turn.getTurnCount()));
        GuiEngine.setNoise(turn.getDrawnCard() != null);
        GuiEngine.setMove(turn.mustMove());

        // turnover
        // draw
    }
}
