package chatroom.client;

import java.awt.EventQueue;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

public class ChatRoomWin {

	private JFrame frmXx;
	private ClientMessageHandler clientMessageHandler;

	public ChatRoomWin(ClientMessageHandler clientMessageHandler) {
		initialize();
		this.clientMessageHandler=clientMessageHandler;
	}

	public void loadUsers(List<User> users) {
		JPanel panel = new JPanel();
		panel.setBounds(446, 0, 130, 430);
		frmXx.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JList<List> list = new JList<List>();
		list.setBounds(10, 10, 110, 410);
		DefaultListModel<List> listModel=new DefaultListModel<List>();
		listModel.addElement(users);
		list.setModel(listModel);
		panel.add(list);
		
	}
	
	public void loadTalks(List<Talk> talks) {
		String[] toBeStored = talks.toArray(new String[talks.size()]); 
		for(String s : toBeStored) { 
			frmXx.textArea.setText(s);
		} 
	}

	private void initialize() {
		frmXx = new JFrame();
		frmXx.setTitle("社团聊天室");
		frmXx.setBounds(100, 100, 592, 469);
		frmXx.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXx.getContentPane().setLayout(null);
		JPanel panel_1 = new JPanel();
		JTextArea textArea = new JTextArea(); 
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea); 
		scroll.setSize(415, 240);
		scroll.setLocation(10, 10);
		scroll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel_1.add(scroll);
		panel_1.setBounds(10, 10, 423, 249);
		frmXx.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		frmXx.addWindowListener(new WindowAdapter() { 
            public void windowClosing(WindowEvent we) { 
            	ExitRoomMessage exitRoomMessage = new ExitRoomMessage();
    			try {
    				clientMessageHandler.sendMessage(exitRoomMessage);
    			} catch (IOException e) {
    				JOptionPane.showMessageDialog( null, "退出房间失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
    			}
            } 
        });
		

		JPanel panel_2 = new JPanel();
		JTextArea textArea_1 = new JTextArea(); 
		textArea_1.setEnabled(true);
		textArea_1.setLineWrap(true);
		JScrollPane scroll_1 = new JScrollPane(textArea_1);
		scroll_1.setBounds(10, 10, 310, 141);
		scroll_1.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.setBounds(10, 269, 320, 151);
		frmXx.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		panel_2.add(scroll_1);
		
		JButton btnNewButton = new JButton("发送");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.setBounds(340, 269, 93, 151);
		frmXx.getContentPane().add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				
				Talk talk = new Talk();
				talk.setContent(textArea_1.getText());
				talk.setUserName(clientMessageHandler.getUser().getName());
				TalkMessage talkMessage = new TalkMessage();
				talkMessage.setTalk(talk);
				try {
					clientMessageHandler.sendMessage(talkMessage);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frmXx, "发送信息失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
	}
	public void setVisible(boolean b) {
		frmXx.setVisible(b);
	}
}
