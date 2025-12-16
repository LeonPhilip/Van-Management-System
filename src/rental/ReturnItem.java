package rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ReturnItem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField itemIdField;
	private JTextField userIdField;
	JPanel returnPanel;
	JComboBox comboSearch;
	LocalDate now;
	TableRowSorter<TableModel> sort;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnItem frame = new ReturnItem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField searchField;
	private JTable ItemTable;
	/**
	 * Create the frame.
	 */
	public ReturnItem() {
		
		connection = SQLiteConnection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1132, 732);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		returnPanel = new JPanel();
		returnPanel.setBounds(31, 28, 1034, 621);
		contentPane.add(returnPanel);
		returnPanel.setLayout(null);
		
		itemIdField = new JTextField();
		itemIdField.setBounds(163, 345, 220, 20);
		returnPanel.add(itemIdField);
		itemIdField.setColumns(10);
		
		userIdField = new JTextField();
		userIdField.setBounds(163, 376, 220, 20);
		returnPanel.add(userIdField);
		userIdField.setColumns(10);
		
//		JDateChooser issueDateChooser = new JDateChooser();
//		issueDateChooser.setBounds(163, 407, 220, 20);
//		returnPanel.add(issueDateChooser);
		
//		JDateChooser returnDateChooser = new JDateChooser();
//		returnDateChooser.setBounds(163, 439, 220, 20);
//		returnPanel.add(returnDateChooser);
		
//		JLabel returnDateLBL = new JLabel("Return Date");
//		returnDateLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
//		returnDateLBL.setBounds(23, 439, 108, 20);
//		returnPanel.add(returnDateLBL);
		
//		JLabel issueDateLBL = new JLabel("Issue Date");
//		issueDateLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
//		issueDateLBL.setBounds(23, 408, 89, 20);
//		returnPanel.add(issueDateLBL);
		
		JLabel userIdLBL = new JLabel("User ID");
		userIdLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		userIdLBL.setBounds(46, 376, 89, 20);
		returnPanel.add(userIdLBL);
		
		JLabel itemIdLBL = new JLabel("Item ID");
		itemIdLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		itemIdLBL.setBounds(46, 345, 89, 20);
		returnPanel.add(itemIdLBL);
		
		JButton returnBTN = new JButton("RETURN");
		returnBTN.setFont(new Font("Sitka Subheading", Font.BOLD, 16));
		returnBTN.setBounds(134, 436, 123, 34);
		returnPanel.add(returnBTN);
		
		JLabel PictureLBL = new JLabel("");
		PictureLBL.setHorizontalAlignment(SwingConstants.CENTER);
		PictureLBL.setBackground(SystemColor.activeCaptionBorder);
		PictureLBL.setBounds(134, 89, 168, 175);
		returnPanel.add(PictureLBL);
		
		ItemTable = new JTable();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(480, 118, 512, 437);
		returnPanel.add(scrollPane);
		scrollPane.setViewportView(ItemTable);
		try {
			ItemReferesher();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		searchField = new JTextField();
		searchField.setBounds(480, 90, 276, 28);
		returnPanel.add(searchField);
		
		JLabel imageLBL = new JLabel("IMAGE");
		imageLBL.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		imageLBL.setHorizontalAlignment(SwingConstants.CENTER);
		imageLBL.setBounds(163, 286, 115, 20);
		returnPanel.add(imageLBL);
		
		comboSearch = new JComboBox();
		comboSearch.setBounds(903, 96, 89, 22);
		returnPanel.add(comboSearch);
		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
				
			}
			
		});
		
		
		ItemTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = ItemTable.getSelectedRow();
				int modelRow = ItemTable.convertRowIndexToModel(row);

	            TableModel model = ItemTable.getModel();

	            itemIdField.setText(model.getValueAt(modelRow, 1).toString());  // Column 0 = ItemID
	            
	            String itemID = model.getValueAt(modelRow, 0).toString();
	            
	            try {
	                Connection conn = SQLiteConnection.getConnection();
	                String sql = "SELECT Image FROM newItem WHERE ItemID = ?";
	                PreparedStatement pst = conn.prepareStatement(sql);
	                pst.setString(1, itemID);
	                ResultSet rs = pst.executeQuery();

	                if (rs.next()) {
	                    InputStream is = rs.getBinaryStream("Image"); // BLOB column
	                    if (is != null) {
	                        BufferedImage img = ImageIO.read(is);
	                        ImageIcon icon = new ImageIcon(img.getScaledInstance(
	                            PictureLBL.getWidth(), PictureLBL.getHeight(), img.SCALE_SMOOTH));
	                        PictureLBL.setIcon(icon);
	                    } else {
	                        PictureLBL.setIcon(null); // no image
	                    }
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	            
			}
		});
		
		returnBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				
			}
		});

	}
	ResultSet rs11;
	
	public void ItemReferesher() throws SQLException  {
		
		connection = SQLiteConnection.getConnection();
		
		String query1 = "SELECT * FROM issueItem";
		PreparedStatement pst1 = connection.prepareStatement(query1);
		rs11 = pst1.executeQuery();
		ItemTable.setModel(DbUtils.resultSetToTableModel(rs11));
		sort = new TableRowSorter<>(ItemTable.getModel());
		ItemTable.setRowSorter(sort);
		pst1.close();
        rs11.close();
	}
	

	String idNum = "";
	private void update() {

	    String itemId = itemIdField.getText().trim();
	    String userId = userIdField.getText().trim();
	    String Status = "Yes";
	    now = LocalDate.now();

	    if (itemId.isEmpty() || userId.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please fill up all form fields.");
	        return;
	    }

	    try {
	        String query = "SELECT UserID FROM issueItem WHERE ItemID = ?";
	        String update = "UPDATE issueItem SET ReturnStatus = ?, ReturnDate = ? WHERE ItemID = ?";
	        String delete = "DELETE FROM issueItem WHERE ItemID = ?";

	        // 1. Find the item
	        PreparedStatement pst = connection.prepareStatement(query);
	        pst.setString(1, itemId);

	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            JOptionPane.showMessageDialog(null, "Item not found.");
	            return;
	        }

	        String dbUserId = rs.getString("UserID");

	        // 2. Check if this user matches
	        if (!dbUserId.equals(userId)) {
	            JOptionPane.showMessageDialog(null, "User ID does not match.");
	            return;
	        }

	        // 3. Update return status
	        PreparedStatement updatePst = connection.prepareStatement(update);
	        updatePst.setString(1, Status);
	        updatePst.setString(2, now.toString());
	        updatePst.setString(3, itemId);

	        updatePst.executeUpdate();
	        updatePst.close();

	        // OPTIONAL: delete after update
	        PreparedStatement deletePst = connection.prepareStatement(delete);
	        deletePst.setString(1, itemId);
	        deletePst.executeUpdate();
	        deletePst.close();

	        pst.close();
	        rs.close();

	        JOptionPane.showMessageDialog(null, "Update successful!");
	        return;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error occurred.");
	    }
	}


public void search() {
	
	String nameF = searchField.getText();
	int comboValue = comboSearch.getSelectedIndex();
	switch(comboValue) {
	case 0:
	if(nameF.trim().isEmpty()) {
		System.out.println("dfgfdg");
		 sort.setRowFilter(null);
		 break;
	}else {
		sort.setRowFilter(RowFilter.regexFilter("(?i)" + nameF, 0));
		//System.out.println("2");
		break;
	}
	case 1:
		if(nameF.trim().isEmpty()) {
			System.out.println("dfgfdg");
			 sort.setRowFilter(null);
			 break;
		}else {
			sort.setRowFilter(RowFilter.regexFilter("(?i)" + nameF, 1));
			//System.out.println("2");
			break;
		}
	case 2:
		if(nameF.trim().isEmpty()) {
			System.out.println("dfgfdg");
			 sort.setRowFilter(null);
			 break;
		}else {
			sort.setRowFilter(RowFilter.regexFilter("(?i)" + nameF, 3));
			//System.out.println("2");
			break;
		}
	case 4:
		if(nameF.trim().isEmpty()) {
			System.out.println("dfgfdg");
			 sort.setRowFilter(null);
			 break;
		}else {
			sort.setRowFilter(RowFilter.regexFilter("(?i)" + nameF, 4));
			//System.out.println("2");
			break;
		}
	}
}
public void clear() {
	itemIdField.setText("");
	userIdField.setText("");
}
}
