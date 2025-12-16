package rental;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class NewBorrower extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField ageField;
	public JPanel panel;
	String combolist[] = {"User Id", "Name", "Age", "sex", "Contact"}; 
	JComboBox comboSearch;
	JLabel imageLBL;
	File selectedFile;
	
	/**
	 * Launch the application.
	 */
	public void run()  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBorrower frame = new NewBorrower();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTable table;
	private JTextField contactField;
	private JTextField searchField;
	/**
	 * Create the frame.
	 */
	public NewBorrower() {
		
		connection = SQLiteConnection.getConnection();

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1175, 778);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(33, 35, 1070, 586);
		contentPane.add(panel);
		panel.setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(153, 317, 159, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		ageField = new JTextField();
		ageField.setBounds(153, 348, 159, 20);
		panel.add(ageField);
		ageField.setColumns(10);
		
		JComboBox sexCombo = new JComboBox();
		sexCombo.setBounds(153, 378, 159, 21);
		panel.add(sexCombo);
		sexCombo.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		
		JLabel contactLBL = new JLabel("Contact");
		contactLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		contactLBL.setBounds(57, 410, 85, 20);
		panel.add(contactLBL);
		
		JLabel sexLBL = new JLabel("Sex");
		sexLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		sexLBL.setBounds(57, 379, 85, 20);
		panel.add(sexLBL);
		
		JLabel ageLBL = new JLabel("Age");
		ageLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		ageLBL.setBounds(57, 348, 85, 20);
		panel.add(ageLBL);
		
		JLabel nameLBL = new JLabel("Name");
		nameLBL.setFont(new Font("Dialog", Font.PLAIN, 17));
		nameLBL.setBounds(57, 317, 85, 20);
		panel.add(nameLBL);
		
		JButton saveBTN = new JButton("Save");
		saveBTN.setBounds(32, 454, 89, 41);
		panel.add(saveBTN);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(386, 92, 580, 403);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) throws NullPointerException {
					int row = table.getSelectedRow();
					int modelRow = table.convertRowIndexToModel(row);

		            TableModel model = table.getModel();

		            Object idVal = model.getValueAt(modelRow, 0);
		            Object nameVal = model.getValueAt(modelRow, 1);
		            Object ageVal = model.getValueAt(modelRow, 2);
		            Object contactVal = model.getValueAt(modelRow, 4);

		            idField.setText(idVal == null ? "" : idVal.toString());
		            nameField.setText(nameVal == null ? "" : nameVal.toString());
		            ageField.setText(ageVal == null ? "" : ageVal.toString());
		            contactField.setText(contactVal == null ? "" : contactVal.toString());
		            
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
		
		imageLBL = new JLabel("");
		imageLBL.setBounds(95, 45, 185, 175);
		panel.add(imageLBL);
		
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(153, 410, 159, 20);
		panel.add(contactField);
		
		searchField = new JTextField();
		searchField.setBounds(386, 72, 235, 20);
		panel.add(searchField);
		searchField.setColumns(10);
		
		comboSearch = new JComboBox(combolist);
		comboSearch.setBounds(888, 71, 78, 22);
		panel.add(comboSearch);
		
		JButton Update = new JButton("Update");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				ItemReferesher();
				clear();
				
			}
		});
		Update.setBounds(159, 454, 89, 41);
		panel.add(Update);
		
		JButton deleteBTN = new JButton("Delete");
		deleteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete();
				ItemReferesher();
				clear();
			}
		});
		deleteBTN.setBounds(276, 454, 89, 41);
		panel.add(deleteBTN);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setColumns(10);
		idField.setBounds(153, 287, 159, 20);
		panel.add(idField);
		
		JLabel IDlabel = new JLabel("ID");
		IDlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
		IDlabel.setBounds(57, 290, 85, 20);
		panel.add(IDlabel);
		
		JButton insertImageLBL = new JButton("Insert Image");
		insertImageLBL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}
		});
		insertImageLBL.setBounds(118, 231, 118, 23);
		panel.add(insertImageLBL);
		saveBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query = "insert into newUser (Name, Age, Sex, Contact) values (?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, nameField.getText());
					pst.setString(2, ageField.getText());
					pst.setString(3, sexCombo.getSelectedItem().toString());
					pst.setString(4, contactField.getText());
					
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Saved!");
					
					pst.close();
					
				} catch (Exception e1) {
				    e1.printStackTrace();   
				}
		ItemReferesher();
				
			}
		});

	}
	TableRowSorter sort;
	private JTextField idField;
public void ItemReferesher() {
		
		connection = SQLiteConnection.getConnection();
		
		String query1 = "SELECT * FROM newUser";
		PreparedStatement pst1;
		try {
			pst1 = connection.prepareStatement(query1);
			ResultSet rs11 = pst1.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs11));
			sort = new TableRowSorter<>(table.getModel());
			table.setRowSorter(sort);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 
	 idField.setText("");
	 nameField.setText("");
	 ageField.setText("");
	 contactField.setText("");
 }
 
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

private void update() {
	connection = SQLiteConnection.getConnection();
	
	String id = idField.getText();
	String name = nameField.getText();
	String age = ageField.getText();
	String contact = contactField.getText();
	
	if (id.isEmpty() || name.isEmpty() || age.isEmpty() || contact.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill up all form fields.");
	}
	try{
   String updated = "UPDATE newItem SET Name = ?, Age = ?, Contact = ?, Image = ? WHERE UserID = ?";
   
   PreparedStatement ppst = connection.prepareStatement(updated); 

   ppst.setString(1, name);
   ppst.setString(2, age);
   ppst.setString(3, contact);
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
	}
