package chatroom.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Panel;
import java.awt.Label;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.TextField;
import java.awt.TextArea;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private final Label label = new Label("\u5B66\u53F7\uFF1A");
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u767B\u5F55\u754C\u9762");
		frame.setBounds(100, 100, 385, 249);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		label.setBounds(41, 61, 50, 33);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("\u5BC6\u7801\uFF1A");
		label_1.setBounds(41, 100, 50, 23);
		frame.getContentPane().add(label_1);
		
		Button button = new Button("\u767B\u5F55");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		button.setBounds(149, 159, 76, 23);
		frame.getContentPane().add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(113, 102, 177, 21);
		frame.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(113, 65, 177, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
