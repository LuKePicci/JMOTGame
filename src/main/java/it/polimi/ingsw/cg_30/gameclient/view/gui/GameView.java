package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class GameView {

    public static final Dimension FRAME_SIZE = GuiEngine.getResponsive(1.5610,
            1.1676);

    public static final Dimension PARTY_SIZE = GuiEngine.getResponsive(0.6204,
            0.1406);
    public static final Dimension MAP_SIZE = GuiEngine.getResponsive(2, 1.62);

    public static final Dimension CARD_SIZE = GuiEngine.getResponsive(16, 6);

    public static final int SECTOR_WIDTH = GuiEngine.getResponsive(36.2264);

    private JFrame mainFrame;

    private JScrollPane mapScrollPane, deckScrollPane;

    private final Map<ViewType, GuiView> subViews = new HashMap<ViewType, GuiView>();

    /**
     * @throws UnsupportedLookAndFeelException
     * @wbp.parser.entryPoint
     */
    public static void windowBuilder() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new HiFiLookAndFeel());
        new GameView().initialize();
    }

    /**
     * Create the game form.
     */
    public GameView() {
        this.subViews.put(ViewType.ZONE, new GuiZoneView());
        this.subViews.put(ViewType.CHAT, new GuiChatView());
        this.subViews.put(ViewType.DECK, new GuiDeckView());
    }

    public Set<Entry<ViewType, GuiView>> getSubViews() {
        return this.subViews.entrySet();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        mainFrame = new JFrame();
        // mainFrame.setTitle("Escape From The Aliens Into Outer Space - EFTAIOS");
        mainFrame.setIconImage(GuiEngine.loadImage("custom_icon.png"));
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getCenterPoint();

        // set position and size
        mainFrame.setBounds((int) (center.x - FRAME_SIZE.getWidth() / 2),
                (int) (center.y - FRAME_SIZE.getHeight() / 2),
                (int) FRAME_SIZE.getWidth(), (int) FRAME_SIZE.getHeight());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout());

        JSplitPane topDownSplit = new JSplitPane();
        topDownSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainFrame.getContentPane().add(topDownSplit);

        JSplitPane bottomSplit = new JSplitPane();
        topDownSplit.setBottomComponent(bottomSplit);

        deckScrollPane = new JScrollPane();
        deckScrollPane.setViewportView(subViews.get(ViewType.DECK)
                .getComponent());
        bottomSplit.setRightComponent(deckScrollPane);

        bottomSplit
                .setLeftComponent(subViews.get(ViewType.CHAT).getComponent());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topDownSplit.setTopComponent(topPanel);

        mapScrollPane = new JScrollPane();
        mapScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapScrollPane.setPreferredSize(new Dimension(
                (int) MAP_SIZE.getWidth() + 2, (int) MAP_SIZE.getHeight() + 2));
        mapScrollPane.setViewportView(subViews.get(ViewType.ZONE)
                .getComponent());
        topPanel.add(mapScrollPane, BorderLayout.CENTER);

        JPanel topRightPane = new JPanel();
        topRightPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topRightPane.setLayout(new BorderLayout(0, 0));
        topPanel.add(topRightPane, BorderLayout.EAST);

        JPanel turnPane = new JPanel();
        turnPane.setAlignmentY(Component.TOP_ALIGNMENT);
        turnPane.setLayout(new BorderLayout());

        JPanel turnInfo = new JPanel();
        turnInfo.setLayout(new GridBagLayout());
        turnPane.add(turnInfo, BorderLayout.NORTH);

        JLabel turnNumber = new JLabel("19/39");
        turnNumber.setHorizontalAlignment(SwingConstants.CENTER);
        turnNumber.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc_turnNumber = new GridBagConstraints();
        gbc_turnNumber.weightx = 0.35;
        gbc_turnNumber.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc_turnNumber.gridy = 0;
        gbc_turnNumber.gridx = 0;
        gbc_turnNumber.fill = GridBagConstraints.HORIZONTAL;
        turnInfo.add(turnNumber, gbc_turnNumber);

        JLabel turnNick = new JLabel("Player1");
        turnNick.setHorizontalAlignment(SwingConstants.CENTER);
        turnNick.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc_turnNick = new GridBagConstraints();
        gbc_turnNick.weightx = 0.65;
        gbc_turnNick.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc_turnNick.fill = GridBagConstraints.HORIZONTAL;
        gbc_turnNick.gridy = 0;
        gbc_turnNick.gridx = 1;
        turnInfo.add(turnNick, gbc_turnNick);

        JLabel turnCountdown = new JLabel("00:30");
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

        JButton attackButton = new JButton("Attack");
        turnButtons.add(attackButton);

        JButton drawButton = new JButton("Draw");
        turnButtons.add(drawButton);

        JButton turnoverButton = new JButton("Turnover");
        turnButtons.add(turnoverButton);

        JButton discardButton = new JButton("Discard");
        turnButtons.add(discardButton);

        topRightPane.add(subViews.get(ViewType.TURN).getComponent(),
                BorderLayout.NORTH);

        JScrollPane partyScrollPane = new JScrollPane();
        partyScrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        partyScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        topRightPane.add(partyScrollPane, BorderLayout.CENTER);

        JList partyList = new JList();
        partyList.setMaximumSize(PARTY_SIZE);
        partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partyScrollPane.setViewportView(partyList);
    }

    /**
     * Sets the visibility state of game window.
     *
     * @param visible
     *            the new visibility state
     */
    public void setVisible(boolean visible) {
        this.mainFrame.setVisible(visible);
    }

}
