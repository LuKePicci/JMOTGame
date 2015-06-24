package it.polimi.ingsw.cg_30.gameclient.view.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class HomeScreen {

    private JFrame frmE;
    private JTextField txtHost;
    private JTextField txtPort;
    private final JRadioButton rdbtnSocketRadioButton = new JRadioButton(
            "Socket");
    private final JRadioButton rdbtnRmiRadioButton = new JRadioButton("RMI");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HomeScreen window = new HomeScreen();
                    window.frmE.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public HomeScreen() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmE = new JFrame();
        frmE.setResizable(false);
        frmE.setTitle("EFTAIOS - Escape from the aliens in outer space");
        frmE.setBounds(100, 100, 782, 581);
        frmE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.WEST, rdbtnRmiRadioButton, 50,
                SpringLayout.EAST, rdbtnSocketRadioButton);
        frmE.getContentPane().setLayout(springLayout);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, btnConnect, 417,
                SpringLayout.NORTH, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnConnect, 174,
                SpringLayout.WEST, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnConnect, -31,
                SpringLayout.SOUTH, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnConnect, -201,
                SpringLayout.EAST, frmE.getContentPane());
        btnConnect.setFont(new Font("TitilliumText22L Xb", Font.PLAIN, 30));
        frmE.getContentPane().add(btnConnect);

        txtHost = new JTextField();
        txtHost.setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 30));
        txtHost.setText("127.0.0.1");
        frmE.getContentPane().add(txtHost);
        txtHost.setColumns(14);

        txtPort = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, txtPort, 0,
                SpringLayout.NORTH, txtHost);
        txtPort.setText("22222");
        txtPort.setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 30));
        frmE.getContentPane().add(txtPort);
        txtPort.setColumns(6);

        JLabel lblHost = new JLabel("Host:");
        lblHost.setLabelFor(txtHost);
        springLayout.putConstraint(SpringLayout.NORTH, lblHost, 240,
                SpringLayout.NORTH, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, txtHost, 40,
                SpringLayout.NORTH, lblHost);
        springLayout.putConstraint(SpringLayout.WEST, txtHost, 0,
                SpringLayout.WEST, lblHost);
        springLayout.putConstraint(SpringLayout.WEST, lblHost, 108,
                SpringLayout.WEST, frmE.getContentPane());
        lblHost.setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 25));
        frmE.getContentPane().add(lblHost);

        JLabel lblPort = new JLabel("Port:");
        lblPort.setLabelFor(txtPort);
        springLayout.putConstraint(SpringLayout.NORTH, lblPort, 0,
                SpringLayout.NORTH, lblHost);
        springLayout.putConstraint(SpringLayout.WEST, lblPort, 318,
                SpringLayout.EAST, lblHost);
        lblPort.setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 25));
        frmE.getContentPane().add(lblPort);
        rdbtnSocketRadioButton.setSelected(true);
        rdbtnSocketRadioButton.setFont(new Font("TitilliumText22L Rg",
                Font.PLAIN, 25));
        frmE.getContentPane().add(rdbtnSocketRadioButton);
        rdbtnRmiRadioButton.setFont(new Font("TitilliumText22L Rg", Font.PLAIN,
                25));
        frmE.getContentPane().add(rdbtnRmiRadioButton);

        JLabel lblSeparator = new JLabel(":");
        springLayout.putConstraint(SpringLayout.WEST, txtPort, 15,
                SpringLayout.WEST, lblSeparator);
        springLayout.putConstraint(SpringLayout.NORTH, lblSeparator, 0,
                SpringLayout.NORTH, txtHost);
        springLayout.putConstraint(SpringLayout.WEST, lblSeparator, 365,
                SpringLayout.WEST, txtHost);
        lblSeparator.setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 30));
        frmE.getContentPane().add(lblSeparator);

        JLabel lblHowtoconnect = new JLabel("Choose a connection protocol:");
        springLayout.putConstraint(SpringLayout.NORTH, lblHowtoconnect, 70,
                SpringLayout.NORTH, txtHost);
        springLayout.putConstraint(SpringLayout.WEST, lblHowtoconnect, 90,
                SpringLayout.WEST, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, rdbtnSocketRadioButton,
                350, SpringLayout.WEST, lblHowtoconnect);
        springLayout.putConstraint(SpringLayout.NORTH, rdbtnRmiRadioButton, 0,
                SpringLayout.NORTH, lblHowtoconnect);
        springLayout.putConstraint(SpringLayout.NORTH, rdbtnSocketRadioButton,
                0, SpringLayout.NORTH, lblHowtoconnect);
        lblHowtoconnect
                .setFont(new Font("TitilliumText22L Rg", Font.PLAIN, 25));
        frmE.getContentPane().add(lblHowtoconnect);

        JLabel lblLogo = new JLabel("Logo");
        springLayout.putConstraint(SpringLayout.NORTH, lblLogo, 0,
                SpringLayout.NORTH, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblLogo, 0,
                SpringLayout.WEST, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lblLogo, 124,
                SpringLayout.NORTH, frmE.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblLogo, 776,
                SpringLayout.WEST, frmE.getContentPane());
        frmE.getContentPane().add(lblLogo);
    }
}
