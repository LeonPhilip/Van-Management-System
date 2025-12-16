package rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.Dialog.ModalExclusionType;

public class signIn {

	public JFrame frame;
	private JTextField userNameField;
	private JPasswordField passwordField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signIn window = new signIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	Connection connection = null;
	private JPanel logInPanel;
	private JLabel sidePiclbl;
	private JTextField ageField;
	private JTextField contactField;
	private JTextField sexField;
	private JTextField nameField;
	private JLabel nameLBL;
	private JLabel sexLBL;
	private JLabel contactLBL;
	private JLabel ageLBL;
	/**
	 * Create the application.
	 */
	public signIn() {
		initialize();
		
		connection = SQLiteConnection.getConnection();
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(signIn.class.getResource("/Image/Untitled-2.jpg")));
		frame.setBounds(0, 0, 1147, 806);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		logInPanel = new JPanel();
		logInPanel.setBackground(SystemColor.info);
		logInPanel.setBounds(101, 107, 421, 543);
		frame.getContentPane().add(logInPanel);
		logInPanel.setLayout(null);
		
		userNameField = new JTextField();
		userNameField.setBounds(119, 344, 272, 30);
		logInPanel.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(119, 385, 272, 30);
		logInPanel.add(passwordField);
		
		JButton signInBTN = new JButton("Sign In");
		signInBTN.setBounds(96, 441, 95, 42);
		logInPanel.add(signInBTN);
		signInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="INSERT INTO newUser (UserId, Name, Age, Sex, Contact, Address, userName, password) VALUES (?,?,?,?,?,?,?,?,?)  ";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, nameField.getText());
					pst.setString(2, ageField.getText());
					pst.setString(3, sexField.getText());
					pst.setString(4, contactField.getText());
					pst.setString(5, userNameField.getText());
					pst.setString(6, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					rs.close();
					pst.close();
				} catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);
				}
				
				
			}
		});
		signInBTN.setFont(new Font("Calisto MT", Font.BOLD, 18));
		
		JLabel passwordLBL = new JLabel("Password");
		passwordLBL.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLBL.setBounds(28, 384, 81, 28);
		logInPanel.add(passwordLBL);
		passwordLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel userNameLBL = new JLabel("Username");
		userNameLBL.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLBL.setBounds(28, 344, 81, 26);
		logInPanel.add(userNameLBL);
		userNameLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblNewLabel_2 = new JLabel("Sign Into Rental Services\r\n");
		lblNewLabel_2.setFont(new Font("Tekton Pro", Font.BOLD, 30));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 47, 329, 60);
		logInPanel.add(lblNewLabel_2);
		
		JTextArea txtrBackReadyTo = new JTextArea();
		txtrBackReadyTo.setBackground(SystemColor.info);
		txtrBackReadyTo.setWrapStyleWord(true);
		txtrBackReadyTo.setLineWrap(true);
		txtrBackReadyTo.setFont(new Font("Kozuka Gothic Pro M", Font.PLAIN, 12));
		txtrBackReadyTo.setText("Welcome back! Ready to find your next perfect space?  Sign in to explore the best rental options tailored just for you.");
		txtrBackReadyTo.setBounds(45, 95, 342, 48);
		txtrBackReadyTo.setEditable(false);
		logInPanel.add(txtrBackReadyTo);
		
		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.setBounds(119, 221, 272, 30);
		logInPanel.add(ageField);
		
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(119, 303, 272, 30);
		logInPanel.add(contactField);
		
		sexField = new JTextField();
		sexField.setColumns(10);
		sexField.setBounds(119, 262, 272, 30);
		logInPanel.add(sexField);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(119, 180, 272, 30);
		logInPanel.add(nameField);
		
		nameLBL = new JLabel("First Name");
		nameLBL.setHorizontalAlignment(SwingConstants.LEFT);
		nameLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		nameLBL.setBounds(28, 184, 81, 26);
		logInPanel.add(nameLBL);
		
		sexLBL = new JLabel("Sex");
		sexLBL.setHorizontalAlignment(SwingConstants.LEFT);
		sexLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		sexLBL.setBounds(28, 262, 81, 26);
		logInPanel.add(sexLBL);
		
		contactLBL = new JLabel("Contact");
		contactLBL.setHorizontalAlignment(SwingConstants.LEFT);
		contactLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		contactLBL.setBounds(28, 303, 81, 26);
		logInPanel.add(contactLBL);
		
		ageLBL = new JLabel("Age");
		ageLBL.setHorizontalAlignment(SwingConstants.LEFT);
		ageLBL.setFont(new Font("Dialog", Font.BOLD, 15));
		ageLBL.setBounds(28, 221, 81, 26);
		logInPanel.add(ageLBL);
		
		JButton backToLogInBTN = new JButton("Back");
		backToLogInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				login log = new login();
				log.frame.setVisible(true);
			}
		});
		backToLogInBTN.setFont(new Font("Calisto MT", Font.BOLD, 18));
		backToLogInBTN.setBounds(262, 441, 95, 42);
		logInPanel.add(backToLogInBTN);
		
		String quote = "Welcome back! Ready to find your next perfect space? \n "+" Log in to explore the best rental options tailored just for you.";
		

		ImageIcon taxi1 = new ImageIcon(signIn.class.getResource("/Image/rental 1.jpg"));
		Image TaxiImage = taxi1.getImage().getScaledInstance(475, 550, Image.SCALE_DEFAULT);
		sidePiclbl = new JLabel("");
		sidePiclbl.setBounds(522, 107, 475, 543);
		frame.getContentPane().add(sidePiclbl);
		sidePiclbl.setIcon(new ImageIcon(TaxiImage));
		
//		int imageWidth = 100;
//		int imageHeight = 95;
//		ImageIcon mabiniIcon = new ImageIcon(getClass().getResource("/Image/Mabini.png"));
//		Image scaledImg = mabiniIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		
	}
	
	public void signInData(String firstNAME, String lastName) {
		
		
		
	}
}
