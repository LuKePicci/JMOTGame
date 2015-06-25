package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class GameView {

    public static final Point CENTER = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getCenterPoint();

    public static final Dimension FRAME_SIZE = GuiEngine.getResponsive(1.4049,
            1.1676);

    public static final Dimension PARTY_SIZE = GuiEngine.getResponsive(0.6204,
            0.1406);
    public static final Dimension MAP_SIZE = GuiEngine.getResponsive(1.9, 1.62);

    public static final Dimension CARD_SIZE = GuiEngine.getResponsive(16, 6);

    public static final int SECTOR_WIDTH = GuiEngine.getResponsive(36.2264);

    private JFrame mainFrame;

    private JScrollPane mapScrollPane, deckScrollPane;

    private final Map<ViewType, GuiView> subViews = new HashMap<ViewType, GuiView>();

    /**
     * Create the game form.
     */
    public GameView() {
        this.subViews.put(ViewType.ZONE, new GuiZoneView());
        this.subViews.put(ViewType.CHAT, new GuiChatView());
        this.subViews.put(ViewType.DECK, new GuiDeckView());
        this.subViews.put(ViewType.TURN, new GuiTurnView());
    }

    /**
     * @throws UnsupportedLookAndFeelException
     * @wbp.parser.entryPoint
     */
    public static void windowBuilder() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new HiFiLookAndFeel());
        new GameView().initialize();
    }

    public Set<Entry<ViewType, GuiView>> getSubViews() {
        return this.subViews.entrySet();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        mainFrame = new JFrame();
        mainFrame.setIconImage(GuiEngine.loadImage("custom_icon.png"));

        // set position and size
        mainFrame.setBounds((int) (CENTER.x - FRAME_SIZE.getWidth() / 2),
                (int) (CENTER.y - FRAME_SIZE.getHeight() / 2),
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
        subViews.get(ViewType.CHAT).registerDefaultButton();

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
