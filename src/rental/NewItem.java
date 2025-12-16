package rental;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import java.sql.*;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class NewItem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField penaltyField;
	JLabel imageLBL;
	TableRowSorter<TableModel> sort;
	public JPanel newItemPanel;
	JComboBox comboSearch;
	LocalDate now;
	String[] searchCombo = {"Item Id", "Name", "PenaltyCost", "DateAdded"};

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewItem frame = new NewItem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTable table;
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public NewItem(){
		
		connection = SQLiteConnection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1026, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		newItemPanel = new JPanel();
		newItemPanel.setBounds(10, 22, 967, 506);
		contentPane.add(newItemPanel);
		newItemPanel.setLayout(null);
		
		JLabel namelbl = new JLabel("Name");
		namelbl.setFont(new Font("Dialog", Font.PLAIN, 17));
		namelbl.setBounds(32, 337, 107, 14);
		newItemPanel.add(namelbl);
		
		nameField = new JTextField();
		nameField.setBounds(149, 334, 163, 17);
		newItemPanel.add(nameField);
		nameField.setColumns(10);
		
		penaltyField = new JTextField();
		penaltyField.setBounds(149, 373, 163, 17);
		newItemPanel.add(penaltyField);
		penaltyField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		penaltyField.setColumns(10);
		
		JButton saveBTN = new JButton("SAVE");
		saveBTN.setFont(new Font("MS PGothic", Font.BOLD, 14));
		saveBTN.setBounds(30, 422, 89, 23);
		newItemPanel.add(saveBTN);
		
		JLabel penaltyLBL = new JLabel("Penalty Cost");
		penaltyLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		penaltyLBL.setBounds(32, 376, 107, 14);
		newItemPanel.add(penaltyLBL);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(403, 82, 517, 394);
		newItemPanel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(row);

	            TableModel model = table.getModel();

	            idField.setText(model.getValueAt(modelRow, 0).toString());
	            nameField.setText(model.getValueAt(modelRow, 1).toString());
	            penaltyField.setText(model.getValueAt(modelRow, 2).toString());  
	            
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
	                            imageLBL.getWidth(), imageLBL.getHeight(), img.SCALE_SMOOTH));
	                        imageLBL.setIcon(icon);
	                    } else {
	                        imageLBL.setIcon(null); // no image
	                    }

	                }

	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
			}
		});
		scrollPane.setViewportView(table);
			ItemReferesher();
	
		imageLBL = new JLabel();
		imageLBL.setBounds(88, 45, 173, 172);
		newItemPanel.add(imageLBL);
		
		JButton findImageBTN = new JButton("Insert Image");
		findImageBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImage();
				
			}
		});
		findImageBTN.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 9));
		findImageBTN.setBounds(113, 240, 89, 23);
		newItemPanel.add(findImageBTN);
		
		JButton deleteBTN = new JButton("DELETE");
		deleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            Delete();
				ItemReferesher();
				clear();
				
			}
		});
		deleteBTN.setFont(new Font("MS PGothic", Font.BOLD, 14));
		deleteBTN.setBounds(253, 422, 89, 23);
		newItemPanel.add(deleteBTN);
		
		searchField = new JTextField();
		searchField.setBounds(403, 64, 206, 20);
		newItemPanel.add(searchField);
		searchField.setColumns(10);
		
		comboSearch = new JComboBox(searchCombo);
		comboSearch.setBounds(825, 63, 95, 22);
		newItemPanel.add(comboSearch);
		
		JButton updateBTN = new JButton("Update");
		updateBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		updateBTN.setFont(new Font("MS PGothic", Font.BOLD, 14));
		updateBTN.setBounds(142, 422, 89, 23);
		newItemPanel.add(updateBTN);
		
		JLabel idLBL = new JLabel("ID");
		idLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		idLBL.setBounds(32, 296, 107, 14);
		newItemPanel.add(idLBL);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setColumns(10);
		idField.setBounds(149, 296, 163, 17);
		newItemPanel.add(idField);
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
		
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				  if (itemNameExists(nameField.getText())) {
			            JOptionPane.showMessageDialog(null, 
			                "This item name already exists. Please enter a different name.");
			            return;
			        }
				
				try {
					String query = "insert into newItem (Name, PenaltyCost, DateAdded, Image) values (?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, nameField.getText());
					pst.setString(2, penaltyField.getText());
					pst.setString(3, LocalDate.now().toString());
					
					try {
						String path = selectedFile.toString();
					// Load the file as InputStream
			        File file = new File(path);
			        FileInputStream fis = new FileInputStream(file);
			        pst.setBinaryStream(4, fis, (int) file.length());
					}catch(NullPointerException ea) {
			        	
			        }

					
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Saved!");
					
					pst.close();
					ItemReferesher();
					clear();

					
				} catch (Exception e1) {
				    e1.printStackTrace();   
				}
			}
		});
		
		
		

	}
	private void Delete() {
		
		connection = SQLiteConnection.getConnection();
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow == -1) {
		    JOptionPane.showMessageDialog(null, "Please select a row first.");
		    return;
		}

		try {
		int Ed = (int) table.getValueAt(selectedRow	, 0);
		String delete = "DELETE FROM newItem WHERE itemID = ?";
			PreparedStatement pst = connection.prepareStatement(delete);
			pst.setInt(1, Ed);
			pst.executeUpdate();
			pst.close();
			ItemReferesher();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void ItemReferesher() {
		
		connection = SQLiteConnection.getConnection();
		
		String query1 = "SELECT * FROM newItem";
		PreparedStatement pst1;
		try {
			pst1 = connection.prepareStatement(query1);
			ResultSet rs1 = pst1.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs1));
			sort = new TableRowSorter<>(table.getModel());
			table.setRowSorter(sort);
			
			pst1.close();
	        rs1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void inputImage() throws SQLException, IOException {
		
		connection = SQLiteConnection.getConnection();

        String sql = "INSERT INTO Image VALUES ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst = connection.prepareStatement(sql);

        // Load the file as InputStream
        File file = new File("laptop.png");
        FileInputStream fis = new FileInputStream(file);
        pst.setBinaryStream(3, fis, (int) file.length());

        pst.executeUpdate();
		
        fis.close();
        pst.close();
        connection.close();
	}
	
	File selectedFile;
	private JTextField searchField;
	private JTextField idField;
	
	private void selectImage(){
		
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Select an Image");
	    
	    int result = fileChooser.showOpenDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	       selectedFile = fileChooser.getSelectedFile();
	       
	        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
	        
	        ImageIcon logomabiniIcon = new ImageIcon(selectedFile.toString());
			Image TaxiImage = logomabiniIcon.getImage().getScaledInstance(110, 116, Image.SCALE_DEFAULT);
	        imageLBL.setIcon(new ImageIcon(TaxiImage));
		
	}
	}
	private boolean itemNameExists(String name) {
	    String sql = "SELECT COUNT(*) FROM newItem WHERE Name = ?";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, name);
	        ResultSet rs = pst.executeQuery();
	        return rs.getInt(1) > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
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
		}
	}
	
	public void clear() {
		idField.setText("");
		nameField.setText("");
		penaltyField.setText("");
		
	}
private void update() {
		connection = SQLiteConnection.getConnection();
		
		String id = idField.getText();
		String name = nameField.getText();
		String penalty = penaltyField.getText();
		
		if (id.isEmpty() || name.isEmpty() || penalty.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please fill up all form fields.");
		}
		try{
       String updated = "UPDATE newItem SET Name = ?, PenaltyCost = ?, Image = ? WHERE itemID = ?";
       
       PreparedStatement ppst = connection.prepareStatement(updated); 

       ppst.setString(1, name);
       ppst.setString(2, penalty);
        ppst.setString(4, id);
        try {

         String path = selectedFile.toString();
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        ppst.setBinaryStream(3, fis, (int) file.length());
		}catch(NullPointerException | FileNotFoundException ea) {
        	System.out.println("dsfdsfsddsfdsf");
        }
        
       ppst.executeUpdate();
       
       ppst.close();
       ItemReferesher();
       clear();
    		return;
    		
		}catch(SQLException ea) {
			JOptionPane.showMessageDialog(null, "Error ");;
			return;
		}

		 }
}
