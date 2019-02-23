package chatroom.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import chatroom.message.EnterRoomMessage;
import chatroom.message.ExitRoomMessage;
import chatroom.message.RoomStautsMessage;
import chatroom.message.TalkMessage;
import chatroom.model.Room;
import chatroom.model.Talk;
import chatroom.model.User;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JList;

public class ChatRoomWin {

	private JFrame frmXx;
	private ClientMessageHandler clientMessageHandler;

	JPanel panel = new JPanel();
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	JTextArea textArea = new JTextArea();
	JTextArea textArea_1 = new JTextArea();
	JScrollPane scroll = new JScrollPane(textArea);
	JScrollPane scroll_1 = new JScrollPane(textArea_1);
	JButton btnNewButton = new JButton("发送");

	public ChatRoomWin(ClientMessageHandler clientMessageHandler) {
		initialize();
		this.clientMessageHandler = clientMessageHandler;
	}

	public void loadUsers(List<User> users) {

		JList<List> list = new JList<List>();
		list.setBounds(10, 10, 110, 410);
		DefaultListModel<List> listModel = new DefaultListModel<List>();
		listModel.addElement(users);
		list.setModel(listModel);
		panel.add(list);

	}

	public void loadHistroyTalks(List<Talk> talks) {
		if (!(talks == null)) {
			String[] toBeStored = talks.toArray(new String[talks.size()]);
			for (String s : toBeStored) {
				textArea.append("\n");
				textArea.append(s);
			}
		}
	}

	public void loadTalks(Talk talk) {
		String s1 = talk.getUserName();
		String s2 = timestampToDateStr(talk.getTimestamp());
		String s3 = talk.getContent();
		textArea.append("\n");
		textArea.append(s1 + " " + s2);
		textArea.append("\n");
		textArea.append(s3);

	}

	private void initialize() {
		frmXx = new JFrame();
		frmXx.setTitle("社团聊天室");
		frmXx.setBounds(100, 100, 592, 469);
		frmXx.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXx.getContentPane().setLayout(null);
		panel.setBounds(446, 0, 130, 430);
		frmXx.getContentPane().add(panel);
		panel.setLayout(null);
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		scroll.setSize(415, 240);
		scroll.setLocation(10, 10);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_1.add(scroll);
		panel_1.setBounds(10, 10, 423, 249);
		frmXx.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		frmXx.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				ExitRoomMessage exitRoomMessage = new ExitRoomMessage();
				exitRoomMessage.setUser(clientMessageHandler.getUser());
				//exitRoomMessage.setRoomId();
				try {
					clientMessageHandler.sendMessage(exitRoomMessage);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "退出房间失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		textArea_1.setEnabled(true);
		textArea_1.setLineWrap(true);
		scroll_1.setBounds(10, 10, 310, 141);
		scroll_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.setBounds(10, 269, 320, 151);
		frmXx.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		panel_2.add(scroll_1);

		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.setBounds(340, 269, 93, 151);
		frmXx.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Talk talk = new Talk();
				talk.setContent(textArea_1.getText());
				talk.setUserName(clientMessageHandler.getUser().getName());
				//talk.setRoomId(roomId);
				TalkMessage talkMessage = new TalkMessage();
				talkMessage.setTalk(talk);
				try {
					clientMessageHandler.sendMessage(talkMessage);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmXx, "发送信息失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
				textArea_1.setText("");
			}

		});
	}

	public void setVisible(boolean b) {
		frmXx.setVisible(b);
	}

	public static String timestampToDateStr(Long timestamp) {
		Date date = new Date(timestamp);
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
		String format = dateFormat.format(date);
		return format;
	}

}
