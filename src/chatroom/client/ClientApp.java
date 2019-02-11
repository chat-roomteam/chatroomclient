package chatroom.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import chatroom.model.Room;
import chatroom.model.User;

public class ClientApp {
	
	private LoginWin loginWin;
	private ChatRoomListWin chatRoomListWin;
    private ChatRoomWin chatRoomWin;
    public ClientMessageHandler clientMessageHandler;
    private User currentUser;
    private boolean isLogin;
    private Room currentRoom;
    private static Logger logger = Logger.getLogger(ClientApp.class);
    public Socket socket = null;
    
    public void start() throws UnknownHostException, IOException {
    	socket = new Socket("127.0.0.1", 55533);
    	
    	clientMessageHandler=new ClientMessageHandler(socket);
    	
    	loginWin = new LoginWin(clientMessageHandler);
    	clientMessageHandler.setLoginWin(loginWin);
    	loginWin.setVisible(true);
    	/* 	
    	chatRoomListWin = new ChatRoomListWin(clientMessageHandler);
    	clientMessageHandler.setChatRoomListWin(chatRoomListWin);
    	chatRoomListWin.setVisible(true);
/* 	
    	chatRoomWin = new ChatRoomWin(clientMessageHandler);
    	clientMessageHandler.setChatRoomWin(chatRoomWin);
    	chatRoomWin.setVisible(true);
 */    	
    	logger.info("ClientApp start......");
    }
    
    public static void main(String args[]) {
    	ClientApp clientApp = new ClientApp();
    	try {
			clientApp.start();
		} catch (UnknownHostException e) {
			logger.error("ClientApp启动出错 ",e);
		} catch (IOException e) {
			logger.error("ClientApp启动出错 ",e);
		}
    	
    }
    
    
}
