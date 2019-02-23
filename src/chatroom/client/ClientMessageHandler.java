package chatroom.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import chatroom.message.EnterRoomMessage;
import chatroom.message.ExitRoomMessage;
import chatroom.message.IMessageProtocol;
import chatroom.message.LoginMessage;
import chatroom.message.MessageType;
import chatroom.message.RoomListMessage;
import chatroom.message.RoomStautsMessage;
import chatroom.message.TalkMessage;
import chatroom.model.Room;
import chatroom.model.Talk;
import chatroom.model.User;

public class ClientMessageHandler {
	private LoginWin loginWin;
	private ChatRoomWin chatRoomWin;
	private ChatRoomListWin chatRoomListWin;
	private Socket socket;
	private static Logger logger = Logger.getLogger(ClientMessageHandler.class);
	private User user;
	
	public LoginWin getLoginWin() {
		return loginWin;
	}

	public void setLoginWin(LoginWin loginWin) {
		this.loginWin = loginWin;
	}

	public ChatRoomWin getChatRoomWin() {
		return chatRoomWin;
	}

	public void setChatRoomWin(ChatRoomWin chatRoomWin) {
		this.chatRoomWin = chatRoomWin;
	}

	public ChatRoomListWin getChatRoomListWin() {
		return chatRoomListWin;
	}

	public void setChatRoomListWin(ChatRoomListWin chatRoomListWin) {
		this.chatRoomListWin = chatRoomListWin;
	}
	
	public ClientMessageHandler(Socket socket){
		this.socket=socket;
		new ClientReceiveThread();
	}
	
	public void sendMessage(IMessageProtocol msg) throws IOException {

		OutputStream outputStream = this.socket.getOutputStream();
		outputStream.write(msg.jsonByteLen() >> 8);
		outputStream.write(msg.jsonByteLen());
		outputStream.write(msg.jsonBytes());
		outputStream.flush();

		logger.info("发送消息------>>>> type=" + msg.getType() + "  msg=" + msg.toJsonString());

	}
	
	public boolean receiveMessage() throws IOException {
		InputStream inputStream = this.socket.getInputStream();
		int first = inputStream.read();
		// 如果读取的值为-1 说明到了流的末尾，Socket已经被关闭了，此时将不能再去读取
		if (first == -1) {
			// return false;
		}
		int second = inputStream.read();
		int length = (first << 8) + second;
		// 然后构造一个指定长的byte数组
		byte[] bytes = new byte[length];
		// 然后读取指定长度的消息即可
		inputStream.read(bytes);
		String strJson = new String(bytes, "UTF-8");

		JSONObject jo = JSONObject.parseObject(strJson);

		String type = jo.getString("type");

		logger.info("接收消息<<<------ type=" + type + " msg=" + strJson);

		switch (type) {

		case MessageType.MESSAGE_TYPE_LOGIN:
			LoginMessage loginMsg = JSON.toJavaObject(jo, LoginMessage.class);
			if(loginMsg.getLoginResult()) {
				user = new User();
				user.setName(loginMsg.getName());
				user.setStudentID(loginMsg.getStudentID());
				loginWin.setVisible(false);
				
				RoomListMessage roomListMessage = new RoomListMessage();
				try {
					sendMessage(roomListMessage);
				} catch (IOException e) {
					JOptionPane.showMessageDialog( null, "房间列表读取失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
			else {
				loginWin.failLogin();
			}
			break;
			// logger.debug("receiveMessage json=" + strJson);
			
		case MessageType.MESSAGE_TYPE_ROOMSLIST:
			RoomListMessage roomListMsg= JSON.toJavaObject(jo, RoomListMessage.class);
			List<Room> rooms = roomListMsg.getRooms();
			
			
			chatRoomListWin = new ChatRoomListWin(this);
			chatRoomListWin.loadRooms(rooms);
			chatRoomListWin.setVisible(true);
			break;
		
		case MessageType.MESSAGE_TYPE_EXITROOM:
			ExitRoomMessage exitRoomMsg= JSON.toJavaObject(jo, ExitRoomMessage.class);
			
			chatRoomListWin.setVisible(true);
			break;
			
		case MessageType.MESSAGE_TYPE_ENTERROOM:
			EnterRoomMessage enterRoomMsg=  JSON.toJavaObject(jo, EnterRoomMessage.class);
			enterRoomMsg.getRoomId();
			chatRoomListWin.setVisible(false);
			RoomStautsMessage roomStautsMessage = new RoomStautsMessage();
			try {
				sendMessage(roomStautsMessage);
			} catch (IOException e) {
				JOptionPane.showMessageDialog( null, "房间状态读取失败！", "错误提示", JOptionPane.WARNING_MESSAGE);
			}
	    	break;

		case MessageType.MESSAGE_TYPE_ROOMSTAUTS:
			RoomStautsMessage roomStautsMsg= JSON.toJavaObject(jo, RoomStautsMessage.class);
			List<User> users=roomStautsMsg.getUsers();
			List<Talk> talks=roomStautsMsg.getTalks();
			chatRoomWin = new ChatRoomWin(this);
	    	setChatRoomWin(chatRoomWin);
	    	chatRoomWin.loadUsers(users);
	     	chatRoomWin.loadHistroyTalks(talks);
	    	chatRoomWin.setVisible(true);
			
			break;
			
		case MessageType.MESSAGE_TYPE_TALK:
			TalkMessage talkMsg = JSON.toJavaObject(jo, TalkMessage.class);
			chatRoomWin.loadTalks(talkMsg.getTalk());

		}
		return true;
	}
	private class ClientReceiveThread implements Runnable {
		public ClientReceiveThread() {
			new Thread(this).start();
		}

		@Override
		public void run() {
			try {
				while (true) {
					receiveMessage();
				}
			} catch (IOException e) {
				logger.error("receiveMessage出错：", e);
			}

		}
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
