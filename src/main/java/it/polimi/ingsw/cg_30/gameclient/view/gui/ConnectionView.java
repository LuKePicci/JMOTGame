package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.PlaceholderTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class ConnectionView {

    private JFrame connFrame;
    private JLabel topImage, bottomImage;
    private PlaceholderTextField hostName, portNumber;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private BufferedImage floor, roof, imageBackBuffer;
    private Timer animation;
    private Map<BufferedImage, BufferedImage[]> animationCache = new HashMap<BufferedImage, BufferedImage[]>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ConnectionView cv = new ConnectionView();
        cv.initialize();
        cv.setVisible(true);
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void initialize() {
        connFrame = new JFrame();
        connFrame.setMaximumSize(new Dimension(470, 325));
        connFrame.setMinimumSize(new Dimension(470, 325));
        connFrame.setPreferredSize(new Dimension(470, 325));
        connFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                if (animation != null)
                    animation.stop();
            }
        });
        connFrame.setIconImage(GuiEngine.loadImage("custom_icon.png"));
        connFrame.setResizable(false);
        connFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connFrame.getContentPane().setLayout(new BorderLayout(0, 0));
        connFrame.setBounds(GameView.CENTER.x - connFrame.getWidth() / 2,
                GameView.CENTER.y - connFrame.getHeight() / 2,
                connFrame.getWidth(), connFrame.getHeight());

        JLayeredPane layeredPane = new JLayeredPane();
        connFrame.getContentPane().add(layeredPane, BorderLayout.CENTER);
        layeredPane.setLayout(new BorderLayout(0, 0));

        topImage = new JLabel();
        layeredPane.setLayer(topImage, 2);
        layeredPane.add(topImage, BorderLayout.NORTH);
        topImage.setPreferredSize(new Dimension(0, 75));
        topImage.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPane = new JPanel();
        layeredPane.setLayer(centerPane, 1);
        layeredPane.add(centerPane);
        GridBagLayout gbl_centerPane = new GridBagLayout();
        centerPane.setLayout(gbl_centerPane);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.weightx = 0.05;
        gbc_panel_1.gridheight = 3;
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        centerPane.add(panel_1, gbc_panel_1);

        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.weightx = 0.05;
        gbc_panel_2.gridheight = 3;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 2;
        gbc_panel_2.gridy = 0;
        centerPane.add(panel_2, gbc_panel_2);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.weightx = 0.05;
        gbc_panel.gridheight = 3;
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 4;
        gbc_panel.gridy = 0;
        centerPane.add(panel, gbc_panel);

        JLabel lblEscape = new JLabel("ESCAPE");
        lblEscape.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEscape.setHorizontalAlignment(SwingConstants.CENTER);
        lblEscape.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Xb")
                .deriveFont(0, 64));
        GridBagConstraints gbc_lblEscape = new GridBagConstraints();
        gbc_lblEscape.fill = GridBagConstraints.BOTH;
        gbc_lblEscape.weightx = 0.3;
        gbc_lblEscape.insets = new Insets(10, 0, 5, 5);
        gbc_lblEscape.gridx = 1;
        gbc_lblEscape.gridy = 0;
        centerPane.add(lblEscape, gbc_lblEscape);

        JLabel lblFromTheAliens = new JLabel("FROM THE ALIENS");
        lblFromTheAliens.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFromTheAliens.setHorizontalAlignment(SwingConstants.CENTER);
        lblFromTheAliens.setFont(GuiEngine
                .loadCustomFont("TitilliumText22L-Xb").deriveFont(0, 26));
        GridBagConstraints gbc_lblFromTheAliens = new GridBagConstraints();
        gbc_lblFromTheAliens.fill = GridBagConstraints.BOTH;
        gbc_lblFromTheAliens.insets = new Insets(0, 0, 5, 5);
        gbc_lblFromTheAliens.gridx = 1;
        gbc_lblFromTheAliens.gridy = 1;
        centerPane.add(lblFromTheAliens, gbc_lblFromTheAliens);

        JLabel lblInOuterSpace = new JLabel("IN OUTER SPACE");
        lblInOuterSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInOuterSpace.setHorizontalAlignment(SwingConstants.CENTER);
        lblInOuterSpace.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Xb")
                .deriveFont(0, 30));
        GridBagConstraints gbc_lblInOuterSpace = new GridBagConstraints();
        gbc_lblInOuterSpace.fill = GridBagConstraints.BOTH;
        gbc_lblInOuterSpace.insets = new Insets(0, 0, 8, 5);
        gbc_lblInOuterSpace.gridx = 1;
        gbc_lblInOuterSpace.gridy = 2;
        centerPane.add(lblInOuterSpace, gbc_lblInOuterSpace);

        JPanel controlsPane = new JPanel();
        GridBagConstraints gbc_controlsPane = new GridBagConstraints();
        gbc_controlsPane.insets = new Insets(0, 0, 0, 5);
        gbc_controlsPane.weightx = 0.65;
        gbc_controlsPane.gridheight = 3;
        gbc_controlsPane.fill = GridBagConstraints.BOTH;
        gbc_controlsPane.gridx = 3;
        gbc_controlsPane.gridy = 0;
        centerPane.add(controlsPane, gbc_controlsPane);
        GridBagLayout gbl_controlsPane = new GridBagLayout();
        controlsPane.setLayout(gbl_controlsPane);

        hostName = new PlaceholderTextField();
        hostName.setMinimumSize(new Dimension(4, 25));
        hostName.setPlaceholder("Hostname");
        hostName.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Lt")
                .deriveFont(0, 16));
        GridBagConstraints gbc_hostName = new GridBagConstraints();
        gbc_hostName.weighty = 0.33;
        gbc_hostName.weightx = 0.6;
        gbc_hostName.fill = GridBagConstraints.HORIZONTAL;
        gbc_hostName.gridx = 0;
        gbc_hostName.gridy = 0;
        controlsPane.add(hostName, gbc_hostName);
        hostName.setColumns(15);

        JLabel lblSeparator = new JLabel(":");
        lblSeparator.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSeparator.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSeparator = new GridBagConstraints();
        gbc_lblSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblSeparator.gridx = 1;
        gbc_lblSeparator.gridy = 0;
        controlsPane.add(lblSeparator, gbc_lblSeparator);

        portNumber = new PlaceholderTextField();
        portNumber.setMinimumSize(new Dimension(4, 25));
        portNumber.setPlaceholder("Port");
        portNumber.setFont(GuiEngine.loadCustomFont("TitilliumText22L-Lt")
                .deriveFont(0, 16));
        GridBagConstraints gbc_portNumber = new GridBagConstraints();
        gbc_portNumber.weightx = 0.4;
        gbc_portNumber.fill = GridBagConstraints.HORIZONTAL;
        gbc_portNumber.gridx = 2;
        gbc_portNumber.gridy = 0;
        controlsPane.add(portNumber, gbc_portNumber);
        portNumber.setColumns(5);

        JRadioButton rdbtnSocket = new JRadioButton("Socket");
        rdbtnSocket.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnSocket.setHorizontalTextPosition(SwingConstants.LEADING);
        rdbtnSocket.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 13));
        buttonGroup.add(rdbtnSocket);
        rdbtnSocket.setSelected(true);
        GridBagConstraints gbc_rdbtnSocket = new GridBagConstraints();
        gbc_rdbtnSocket.weighty = 0.33;
        gbc_rdbtnSocket.gridheight = 2;
        gbc_rdbtnSocket.weightx = 0.5;
        gbc_rdbtnSocket.fill = GridBagConstraints.BOTH;
        gbc_rdbtnSocket.gridx = 0;
        gbc_rdbtnSocket.gridy = 1;
        controlsPane.add(rdbtnSocket, gbc_rdbtnSocket);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (GameClient.getActiveEngine() instanceof GuiEngine) {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            for (Enumeration<AbstractButton> buttons = buttonGroup
                                    .getElements(); buttons.hasMoreElements();) {
                                AbstractButton button = buttons.nextElement();

                                if (!button.isSelected())
                                    continue;

                                if (activeEngine.connect(button.getText(),
                                        hostName.getText(),
                                        portNumber.getText())) {
                                    animation.stop();
                                    connFrame.dispose();
                                }

                                return;
                            }
                        }
                    }
                });
            }
        });

        JLabel label = new JLabel("-");
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.gridheight = 2;
        gbc_label.fill = GridBagConstraints.HORIZONTAL;
        gbc_label.gridx = 1;
        gbc_label.gridy = 1;
        controlsPane.add(label, gbc_label);

        JRadioButton rdbtnRmi = new JRadioButton("RMI");
        rdbtnRmi.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnRmi.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 13));
        buttonGroup.add(rdbtnRmi);
        GridBagConstraints gbc_rdbtnRmi = new GridBagConstraints();
        gbc_rdbtnRmi.gridheight = 2;
        gbc_rdbtnRmi.fill = GridBagConstraints.BOTH;
        gbc_rdbtnRmi.gridx = 2;
        gbc_rdbtnRmi.gridy = 1;
        controlsPane.add(rdbtnRmi, gbc_rdbtnRmi);
        btnConnect.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, 17));
        GridBagConstraints gbc_btnConnect = new GridBagConstraints();
        gbc_btnConnect.weighty = 0.33;
        gbc_btnConnect.gridheight = 2;
        gbc_btnConnect.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnConnect.gridwidth = 3;
        gbc_btnConnect.gridx = 0;
        gbc_btnConnect.gridy = 3;
        controlsPane.add(btnConnect, gbc_btnConnect);
        JRootPane rootPane = SwingUtilities.getRootPane(btnConnect);
        rootPane.setDefaultButton(btnConnect);

        bottomImage = new JLabel();
        layeredPane.add(bottomImage, BorderLayout.SOUTH);
        bottomImage.setPreferredSize(new Dimension(0, 75));
        bottomImage.setHorizontalAlignment(SwingConstants.CENTER);

        // connFrame.pack();
    }

    public void setVisible(boolean visible) {
        if (visible)
            this.animateEffect();
        this.connFrame.setVisible(visible);
    }

    private BufferedImage toRGB(BufferedImage source) {
        BufferedImage rgb = new BufferedImage(source.getWidth(),
                source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        rgb.getGraphics().drawImage(source, 0, 0, null);
        return rgb;
    }

    private BufferedImage rescaleEffect(BufferedImage image,
            BufferedImage backBuffer, float factor) {
        BufferedImageOp op = new RescaleOp(factor, 0, null);
        op.filter(image, backBuffer);
        return backBuffer;
    }

    private void animateEffect() {
        floor = toRGB(GuiEngine.loadImage("rsz_floor.png"));
        roof = toRGB(GuiEngine.loadImage("rsz_roof.png"));
        final BufferedImage[] fMap = new BufferedImage[100];
        final BufferedImage[] rMap = new BufferedImage[100];
        this.animationCache.put(floor, fMap);
        this.animationCache.put(roof, rMap);
        float factor;

        final float a = 0.5F;
        final float w = 1F;
        float c = 0;
        for (int i = 0; i < 100; i++) {
            factor = 1 + (float) (a * Math.sin(w * c * Math.PI));
            imageBackBuffer = toRGB(floor);
            fMap[i] = rescaleEffect(floor, imageBackBuffer, factor);
            imageBackBuffer = toRGB(roof);
            rMap[i] = rescaleEffect(roof, imageBackBuffer, factor);
            c += 0.02;
        }

        this.animation = new Timer(40, new ActionListener() {
            float counter = 0;
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        index = (int) (counter / 0.02);
                        bottomImage.setIcon(new ImageIcon(fMap[index]));
                        topImage.setIcon(new ImageIcon(rMap[index]));
                        counter += 0.02;
                        counter %= 2;
                    }
                });
            }
        });
        this.animation.start();
    }
}
