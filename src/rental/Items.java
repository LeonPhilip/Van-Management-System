package rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;

public class Items extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JPanel panel;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Items frame = new Items();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTable ItemTable;
	private JTable userTable;
	/**
	 * Create the frame.
	 */
	public Items() {
		
		connection = SQLiteConnection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 972, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 21, 901, 510);
		contentPane.add(panel);
		panel.setLayout(null);
		
		userTable = new JTable();
		JScrollPane scrollPane2 = new JScrollPane(userTable);
		scrollPane2.setBounds(60, 309, 726, 154);
		panel.add(scrollPane2);
		
		JLabel userLBL = new JLabel("Users");
		userLBL.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 20));
		userLBL.setBounds(379, 258, 87, 28);
		panel.add(userLBL);
		
		JLabel itemLBL = new JLabel("Items");
		itemLBL.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 20));
		itemLBL.setBounds(381, 73, 85, 29);
		panel.add(itemLBL);
		
		ItemTable = new JTable();
		JScrollPane ItemScrollPane = new JScrollPane(ItemTable);
		ItemScrollPane.setBounds(60, 113, 726, 127);
		panel.add(ItemScrollPane);
		ItemReferesher();

	}
	
	public void itemModeClass() {
		
		
	}
	
public void ItemReferesher() {
		
		connection = SQLiteConnection.getConnection();
		
		String query1 = "SELECT * FROM newUser";
		String query2 = "SELECT * FROM newItem";
		PreparedStatement pst1;
		PreparedStatement pst2;
		try {
			pst1 = connection.prepareStatement(query1);
			pst2 = connection.prepareStatement(query2);
			ResultSet rs11 = pst1.executeQuery();
			ResultSet rs12 = pst2.executeQuery();
			ItemTable.setModel(DbUtils.resultSetToTableModel(rs12));
			userTable.setModel(DbUtils.resultSetToTableModel(rs11));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
