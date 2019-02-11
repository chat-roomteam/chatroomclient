package chatroom.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import org.apache.log4j.Logger;

import chatroom.message.EnterRoomMessage;
import chatroom.message.LoginMessage;
import chatroom.message.RoomListMessage;
import chatroom.model.Room;

public class ChatRoomListWin {

	public static Logger logger = Logger.getLogger(LoginWin.class);
	private ClientMessageHandler clientMessageHandler;
	private JFrame f;
	JTable table = null;
	JButton button = new JButton("进入");
	DefaultTableModel model = null;

	public ChatRoomListWin(ClientMessageHandler clientMessageHandler) {
		this.clientMessageHandler = clientMessageHandler;
		initialize();
	}
	
	public void loadRooms(List<Room> rooms) {
		
		String[] columnNames = { "房间号", "聊天室名称", "在线人数", "操作" };
		int size = rooms.size();
		
		Object[][] data = new Object[size][3];
		
	
		for(int i=0;i<size;i++) {
			
			data[i][0]=rooms.get(i).getId();
			data[i][1]=rooms.get(i).getName();
			data[i][2]=rooms.get(i).getOnlineNumber();
		}

		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		table.setRowHeight(40);
		ActionPanelEditorRenderer er = new ActionPanelEditorRenderer();
		TableColumn column = table.getColumnModel().getColumn(3);
		column.setCellRenderer(er);
		column.setCellEditor(er);

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JScrollPane(table));
		p.setPreferredSize(new Dimension(320, 200));
		f.getContentPane().add(p);
		f.pack();
	}

	public void initialize() {
		f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}


	class ActionPanelEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

		JPanel panel = new JPanel();

		public ActionPanelEditorRenderer() {
			super();
			
			panel.add(button);

			button.addMouseListener(new MouseAdapter() {
				private int count = 1;

				public void mouseEntered(MouseEvent e) {
					int i = table.getSelectedRow();
					String s = (String) model.getValueAt(i, 0);
					EnterRoomMessage enterRoomMessage = new EnterRoomMessage();
					enterRoomMessage.setRoomId(s);
					enterRoomMessage.setUser(clientMessageHandler.getUser());
					try {
						clientMessageHandler.sendMessage(enterRoomMessage);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(f, "进入房间出错！", "错误提示", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			return panel;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			panel.setBackground(table.getSelectionBackground());
			return panel;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}
	}
	
	public void setVisible(boolean b) {
		f.setVisible(b);
	}
}
