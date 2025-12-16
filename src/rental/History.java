package rental;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class History extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	public JPanel historyPanel;

	/**
	 * Launch the application.
	 * @return 
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					History frame = new History();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	/**
	 * Create the frame.
	 */
	public History() {
		
		connection = SQLiteConnection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 859, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		historyPanel = new JPanel();
		historyPanel.setBounds(0, 0, 809, 558);
		contentPane.add(historyPanel);
		historyPanel.setLayout(null);
		
				
				JLabel issueLBL = new JLabel("Issued Items");
				issueLBL.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 20));
				issueLBL.setBounds(342, 56, 124, 34);
				historyPanel.add(issueLBL);
				
				
				table = new JTable();
				JScrollPane issueItemScroll = new JScrollPane(table);
				issueItemScroll.setBounds(55, 101, 707, 159);
				historyPanel.add(issueItemScroll);
				
				JLabel returnLBL = new JLabel("Returned Items");
				returnLBL.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 18));
				returnLBL.setBounds(344, 291, 122, 43);
				historyPanel.add(returnLBL);
				
				table_1 = new JTable();
				JScrollPane returnScrollPane = new JScrollPane(table_1);
				returnScrollPane.setBounds(55, 345, 707, 180);
				historyPanel.add(returnScrollPane);
								
				refresh();

	}
	
	public void refresh() {
		

		try {
			
			String query = "SELECT * FROM issueItem WHERE ReturnStatus = 'No'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
	        rs.close();
			
			String query1 = "SELECT * FROM issueItem WHERE ReturnStatus = 'Yes'";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			ResultSet rs1 = pst1.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rs1));
			pst1.close();
	        rs1.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	
}
