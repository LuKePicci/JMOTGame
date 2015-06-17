package eftaios.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class GameView {

	private JFrame frame;

	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameView window = new GameView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1480, 1059);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		splitPane_1.setRightComponent(scrollPane_2);

		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_3);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		splitPane_3.setLeftComponent(tabbedPane);

		JTextArea textArea = new JTextArea();
		tabbedPane.addTab("New tab", null, textArea, null);

		JTextArea textArea_1 = new JTextArea();
		tabbedPane.addTab("New tab", null, textArea_1, null);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_3.setRightComponent(splitPane_4);

		JButton btnSendchat = new JButton("SendChat");
		splitPane_4.setRightComponent(btnSendchat);

		JTextArea txtrMessage = new JTextArea();
		txtrMessage.setText("Type here your message");
		splitPane_4.setLeftComponent(txtrMessage);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane.setLeftComponent(splitPane_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane_2.setRightComponent(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);

		scrollPane_1 = new JScrollPane();
		splitPane_2.setLeftComponent(scrollPane_1);
	}

	public void fillMapPanel(JPanel map) {
		this.scrollPane_1.setViewportView(map);
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
}
