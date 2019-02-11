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
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.apache.log4j.Logger;

import java.awt.TextField;
import java.awt.TextArea;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import chatroom.message.LoginMessage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginWin {
	public static Logger logger = Logger.getLogger(LoginWin.class);
    private ClientMessageHandler clientMessageHandler;
	private JFrame frame;
	private final Label label = new Label("学号：");
	private JPasswordField passwordField;
	private JTextField studentIDField;


	public LoginWin(ClientMessageHandler clientMessageHandler) {
		this.clientMessageHandler=clientMessageHandler;
		initialize();
	}
	
	public void failLogin() {
		JOptionPane.showMessageDialog(frame, "学号或密码错误！", "错误提示", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录到聊天室");
		frame.setBounds(100, 100, 385, 249);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		label.setBounds(41, 61, 50, 33);
		frame.getContentPane().add(label);

		Label label_1 = new Label("密码：");
		label_1.setBounds(41, 100, 50, 23);
		frame.getContentPane().add(label_1);

		Button button = new Button("登录");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strStudentID = studentIDField.getText();
				String strPwd = passwordField.getText();
				
			
		
				if (strStudentID == null || strPwd == null || strStudentID.trim().length() == 0
						|| strPwd.trim().length() == 0) {
					
					JOptionPane.showMessageDialog(frame, "学号和密码不能为空！", "错误提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				LoginMessage loginMessage = new LoginMessage();
				loginMessage.setStudentID(strStudentID);
				loginMessage.setPassword(strPwd);
			    try {
					clientMessageHandler.sendMessage(loginMessage);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "登录出错！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
				
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

		studentIDField = new JTextField();
		studentIDField.setBounds(113, 65, 177, 21);
		frame.getContentPane().add(studentIDField);
		studentIDField.setColumns(10);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);

	}
}
