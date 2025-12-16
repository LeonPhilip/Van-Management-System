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

public class IssueItem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField itemIdField;
	private JTextField userIdField;
	String searchList[] = {"Item Id", "Name", "Penalty", "Date"};
	JComboBox searchCombo;
	JPanel issueItemPanel;
	JTextField issueDateField;
	TableRowSorter<TableModel> sort;
	LocalDate now;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IssueItem frame = new IssueItem();
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
	public IssueItem() {
		
		connection = SQLiteConnection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1132, 732);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		issueItemPanel = new JPanel();
		issueItemPanel.setBounds(31, 28, 1034, 621);
		contentPane.add(issueItemPanel);
		issueItemPanel.setLayout(null);
		
		itemIdField = new JTextField();
		itemIdField.setBounds(163, 345, 220, 20);
		issueItemPanel.add(itemIdField);
		itemIdField.setColumns(10);
		
		userIdField = new JTextField();
		userIdField.setBounds(163, 376, 220, 20);
		issueItemPanel.add(userIdField);
		userIdField.setColumns(10);
		
//		JDateChooser issueDateChooser = new JDateChooser();
//		issueDateChooser.setBounds(163, 407, 220, 20);
//		issueItemPanel.add(issue	DateChooser);
		
		
		issueDateField = new JTextField(LocalDate.now().toString());
		issueDateField.setBounds(163, 407, 220, 20);
		 issueItemPanel.add(issueDateField);
		issueDateField.setColumns(10);
		 
		
		
		JDateChooser returnDateChooser = new JDateChooser();
		returnDateChooser.setBounds(163, 439, 220, 20);
		issueItemPanel.add(returnDateChooser);
		
		JLabel returnDateLBL = new JLabel("Return Date");
		returnDateLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		returnDateLBL.setBounds(23, 439, 108, 20);
		issueItemPanel.add(returnDateLBL);
		
		JLabel issueDateLBL = new JLabel("Issue Date");
		issueDateLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		issueDateLBL.setBounds(23, 408, 89, 20);
		issueItemPanel.add(issueDateLBL);
		
		JLabel userIdLBL = new JLabel("User ID");
		userIdLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		userIdLBL.setBounds(23, 377, 89, 20);
		issueItemPanel.add(userIdLBL);
		
		JLabel itemIdLBL = new JLabel("Item ID");
		itemIdLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		itemIdLBL.setBounds(23, 346, 89, 20);
		issueItemPanel.add(itemIdLBL);
		
		JButton borrowBTN = new JButton("Borrow");
		borrowBTN.setFont(new Font("Sitka Subheading", Font.BOLD, 16));
		borrowBTN.setBounds(163, 492, 123, 34);
		issueItemPanel.add(borrowBTN);
		
		JLabel PictureLBL = new JLabel("");
		PictureLBL.setHorizontalAlignment(SwingConstants.CENTER);
		PictureLBL.setBackground(SystemColor.activeCaptionBorder);
		PictureLBL.setBounds(133, 74, 168, 175);
		issueItemPanel.add(PictureLBL);
		
		ItemTable = new JTable();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(480, 118, 512, 456);
		issueItemPanel.add(scrollPane);
		scrollPane.setViewportView(ItemTable);
		try {
			ItemReferesher();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		searchField = new JTextField();
		searchField.setBounds(480, 90, 294, 28);
		issueItemPanel.add(searchField);
		
		JLabel imageLBL = new JLabel("IMAGE");
		imageLBL.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		imageLBL.setHorizontalAlignment(SwingConstants.CENTER);
		imageLBL.setBounds(163, 270, 115, 20);
		issueItemPanel.add(imageLBL);
		
		searchCombo = new JComboBox();
		searchCombo.setBounds(896, 93, 96, 25);
		issueItemPanel.add(searchCombo);
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
		
		borrowBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				        try {
				            String itemID = itemIdField.getText();
				            String userID = userIdField.getText();
				            
				            if (itemID.isEmpty() || userID.isEmpty() || returnDateChooser.getDate() == null) {
				                JOptionPane.showMessageDialog(null, "Please fill in all fields and select dates.");
				                return;
				            }

//				            java.sql.Date issueDate = new java.sql.Date(issueDateChooser.getDate().getTime());
				            java.sql.Date dueDate   = new java.sql.Date(returnDateChooser.getDate().getTime());

				            
				            String checkItem = "SELECT * FROM newItem WHERE ItemID = ?";
				            PreparedStatement pst1 = connection.prepareStatement(checkItem);
				            pst1.setString(1, itemID);
				            ResultSet rs1 = pst1.executeQuery();
				            if (!rs1.next()) {
				                JOptionPane.showMessageDialog(null, "Incorrect Item ID");
				                rs1.close();
				                pst1.close();
				                return;
				            }
				            rs1.close();
				            pst1.close();

				            
				            String checkUser = "SELECT * FROM newUser WHERE UserID = ?";
				            PreparedStatement pst2 = connection.prepareStatement(checkUser);
				            pst2.setString(1, userID);
				            ResultSet rs2 = pst2.executeQuery();
				            if (!rs2.next()) {
				                JOptionPane.showMessageDialog(null, "Incorrect User ID");
				                rs2.close();
				                pst2.close();
				                return;
				            }
				            rs2.close();
				            pst2.close();

				            
				            String checkIssued = "SELECT 1 FROM issueItem WHERE ItemID = ? AND ReturnStatus = 'No'";
				            PreparedStatement pstCheck = connection.prepareStatement(checkIssued);
				            pstCheck.setString(1, itemID);
				            ResultSet rsCheck = pstCheck.executeQuery();
				            if (rsCheck.next()) {
				                JOptionPane.showMessageDialog(null, "This item is already issued and not returned.");
				                rsCheck.close();
				                pstCheck.close();
				                return;
				            }
				            rsCheck.close();
				            pstCheck.close();

				            
				            String insertQuery = "INSERT INTO issueItem (ItemID, UserID, IssueDate, DueDate, ReturnStatus) VALUES (?, ?, ?, ?, ?)";
				            PreparedStatement pst3 = connection.prepareStatement(insertQuery);
				            pst3.setString(1, itemID);
				            pst3.setString(2, userID);
//				            pst3.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(issueDate));
				            pst3.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
				            pst3.setString(5, "No");
				            pst3.executeUpdate();
				            pst3.close();

				            JOptionPane.showMessageDialog(null, "Item Successfully Issued");

				            setVisible(false);
				            new IssueItem().setVisible(true);

				        } catch (Exception e1) {
				            e1.printStackTrace();
				        }
				    
				
			}
		});

	}
	ResultSet rs11;
	
	public void ItemReferesher() throws SQLException  {
		
		connection = SQLiteConnection.getConnection();
		
		String query1 = "SELECT * FROM newItem";
		PreparedStatement pst1 = connection.prepareStatement(query1);
		rs11 = pst1.executeQuery();
		ItemTable.setModel(DbUtils.resultSetToTableModel(rs11));
		sort = new TableRowSorter<>(ItemTable.getModel());
		ItemTable.setRowSorter(sort);
		pst1.close();
        rs11.close();
	}
	
	
	public void search() {
		String nameF = searchField.getText();
		if(nameF.trim().isEmpty()) {
			System.out.println("dfgfdg");
			 sort.setRowFilter(null);
		}else {
			sort.setRowFilter(RowFilter.regexFilter("(?i)" + nameF));
			//System.out.println("2");
			
		}
	}
	public void clear() {
		
		itemIdField.setText("");
		userIdField.setText(" ");
		
	}
	
}
