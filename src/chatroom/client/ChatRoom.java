package chatroom.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ChatRoom {

	private JFrame frmXx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom window = new ChatRoom();
					window.frmXx.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatRoom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmXx = new JFrame();
		frmXx.setTitle("XX\u793E\u56E2\u804A\u5929\u5BA4");
		frmXx.setBounds(100, 100, 592, 469);
		frmXx.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXx.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(446, 0, 130, 430);
		frmXx.getContentPane().add(panel);
		
		JScrollBar scrollBar = new JScrollBar();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(113, Short.MAX_VALUE)
					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 269, 320, 151);
		frmXx.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("\u53D1\u9001");
		btnNewButton.setFont(new Font("ËÎÌו", Font.PLAIN, 18));
		btnNewButton.setBounds(340, 269, 93, 151);
		frmXx.getContentPane().add(btnNewButton);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 0, 423, 257);
		frmXx.getContentPane().add(textArea_1);
	}
}
