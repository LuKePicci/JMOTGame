package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.GameClient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * The Class GuiTurnView.
 */
public class GuiTurnView extends GuiView {

    /** The turn panel. */
    private JPanel turnPane;

    /** The discard button. */
    private JToggleButton discardButton;

    /** The turnover button. */
    private JButton turnoverButton;

    /** The draw button. */
    private JButton drawButton;

    /** The attack button. */
    private JButton attackButton;

    /** The turn countdown. */
    private JLabel turnCountdown;

    /** The turn nick. */
    private JLabel turnNick;

    /** The turn number. */
    private JLabel turnNumber;

    /** The count down timer. */
    private Timer countDown;

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#getComponent()
     */
    @Override
    public JComponent getComponent() {
        if (this.turnPane == null)
            this.createComponents();
        return this.turnPane;
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.gui.GuiView#createComponents()
     */
    @Override
    protected void createComponents() {
        turnPane = new JPanel();
        turnPane.setAlignmentY(Component.TOP_ALIGNMENT);
        turnPane.setLayout(new BorderLayout());

        JPanel turnInfo = new JPanel();
        turnInfo.setLayout(new GridBagLayout());
        turnPane.add(turnInfo, BorderLayout.NORTH);

        turnNumber = new JLabel("0");
        turnNumber.setFont(turnNumber.getFont().deriveFont(Font.PLAIN, 27));
        turnNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        turnNumber.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        GridBagConstraints gbcTurnNumber = new GridBagConstraints();
        gbcTurnNumber.weightx = 0.2;
        gbcTurnNumber.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcTurnNumber.gridy = 0;
        gbcTurnNumber.gridx = 0;
        gbcTurnNumber.fill = GridBagConstraints.HORIZONTAL;
        turnInfo.add(turnNumber, gbcTurnNumber);

        JLabel turnTotal = new JLabel("/39");
        turnTotal.setHorizontalAlignment(SwingConstants.LEFT);
        turnTotal.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbcTurnTotal = new GridBagConstraints();
        gbcTurnTotal.weightx = 0.15;
        gbcTurnTotal.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcTurnTotal.gridy = 0;
        gbcTurnTotal.gridx = 1;
        gbcTurnTotal.fill = GridBagConstraints.HORIZONTAL;
        turnInfo.add(turnTotal, gbcTurnTotal);

        turnNick = new JLabel();
        turnNick.setHorizontalAlignment(SwingConstants.CENTER);
        turnNick.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbcTurnNick = new GridBagConstraints();
        gbcTurnNick.weightx = 0.65;
        gbcTurnNick.anchor = GridBagConstraints.FIRST_LINE_END;
        gbcTurnNick.fill = GridBagConstraints.HORIZONTAL;
        gbcTurnNick.gridy = 0;
        gbcTurnNick.gridx = 1;
        turnInfo.add(turnNick, gbcTurnNick);

        turnCountdown = new JLabel("--:--");
        turnCountdown.setFont(turnCountdown.getFont()
                .deriveFont(Font.PLAIN, 27));
        turnCountdown.setHorizontalAlignment(SwingConstants.CENTER);
        turnCountdown.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbcTurnCountdown = new GridBagConstraints();
        gbcTurnCountdown.gridwidth = 2;
        gbcTurnCountdown.fill = GridBagConstraints.HORIZONTAL;
        gbcTurnCountdown.gridy = 1;
        gbcTurnCountdown.gridx = 0;
        turnInfo.add(turnCountdown, gbcTurnCountdown);

        JPanel turnButtons = new JPanel();
        turnButtons.setAlignmentY(Component.TOP_ALIGNMENT);
        turnButtons.setLayout(new GridLayout(2, 2));
        turnPane.add(turnButtons, BorderLayout.SOUTH);

        attackButton = new JButton("Attack");
        attackButton.setEnabled(false);
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            activeEngine.attackProcessor();
                        }
                    });
                }
            }
        });
        turnButtons.add(attackButton);

        drawButton = new JButton("Draw");
        drawButton.setEnabled(false);
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            activeEngine.drawProcessor();
                        }
                    });
                }
            }
        });
        turnButtons.add(drawButton);

        turnoverButton = new JButton("Turnover");
        turnoverButton.setEnabled(false);
        turnoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (GameClient.getActiveEngine() instanceof GuiEngine) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            activeEngine.turnoverProcessor();
                        }
                    });
                }
            }
        });
        turnButtons.add(turnoverButton);

        discardButton = new JToggleButton("Discard");
        discardButton.setEnabled(false);
        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JToggleButton sender = (JToggleButton) e.getSource();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GuiEngine.setDiscardCard(sender.isSelected());
                    }
                });
            }
        });
        turnButtons.add(discardButton);
    }

    /**
     * @see it.polimi.ingsw.cg_30.gameclient.view.View#applyUpdate(it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel)
     */
    @Override
    public void applyUpdate(ViewModel model) {
        TurnViewModel turn = (TurnViewModel) model;
        this.stopCountDown();
        this.attackButton.setEnabled(turn.canAttack());
        this.discardButton.setSelected(false);
        this.discardButton.setEnabled(turn.mustDiscard());
        this.turnNumber.setText(Integer.toString(turn.getTurnCount()));
        GuiEngine.setNoise(turn.getDrawnCard() != null);
        GuiEngine.setMove(turn.mustMove());
        GuiEngine.setMyNickName(turn.getCurrentPlayerName());
        turnNick.setText(turn.getCurrentPlayerName());
        this.drawButton.setEnabled(turn.isSecDangerous()
                && !turn.mustMove()
                && PlayerRace.ALIEN.equals(turn.getCurrentPlayerIdentity()
                        .getRace()));
        this.turnoverButton.setEnabled(turn.canTurnOver());
        if (!(turn.mustMove() && turn.isSecDangerous()))
            this.startCountDown(turn.getTurnStart());
    }

    /**
     * Starts the count down timer.
     *
     * @param d
     *            the date
     */
    private void startCountDown(Date d) {
        DateTime startDate = new DateTime(d);
        final DateTime endDate = startDate.plus(new Duration(75L * 1000L));

        this.countDown = new Timer(500, new ActionListener() {
            boolean showSeparator = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Period timeToGo = new Duration(DateTime.now(), endDate)
                                .toPeriod();
                        PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                                .printZeroIfSupported().minimumPrintedDigits(1)
                                .appendMinutes()
                                .appendSeparator(showSeparator ? ":" : " ")
                                .minimumPrintedDigits(2).appendSeconds()
                                .toFormatter();
                        turnCountdown.setText(minutesAndSeconds.print(timeToGo));
                        showSeparator = !showSeparator;
                    }
                });
            }
        });
        this.countDown.start();

    }

    /**
     * Stops the count down timer.
     */
    private void stopCountDown() {
        if (countDown != null)
            countDown.stop();
        turnCountdown.setText("--:--");
    }
}
