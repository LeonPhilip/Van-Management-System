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

public class login {

	public JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
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
	/**
	 * Create the application.
	 */
	public login() {
		initialize();
		
		connection = SQLiteConnection.getConnection();
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(login.class.getResource("/Image/Untitled-2.jpg")));
		frame.setBounds(0, 0, 1147, 806);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		logInPanel = new JPanel();
		logInPanel.setBackground(SystemColor.info);
		logInPanel.setBounds(576, 107, 421, 543);
		frame.getContentPane().add(logInPanel);
		logInPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(114, 190, 272, 30);
		logInPanel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(114, 246, 272, 30);
		logInPanel.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(99, 297, 95, 42);
		logInPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="select * from login where username=? and password=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next()) {
						count++;
					}
					
					if (count == 1)
					{
						JOptionPane.showMessageDialog(null, "Welcome");

						frame.dispose();
						home window = new home();
						window.setVisible(true);
						
					}
					
					else if (count>1)
					{
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					}
					
					else {
						JOptionPane.showMessageDialog(null, "Incorrect Username and Password");
					}
					rs.close();
					pst.close();
				} catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
				
				
			}
		});
		btnNewButton.setFont(new Font("Roboto", Font.BOLD, 15));
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(23, 248, 81, 28);
		logInPanel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(23, 190, 81, 26);
		logInPanel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel lblNewLabel_2 = new JLabel("Log Into Rental Services\r\n");
		lblNewLabel_2.setFont(new Font("Tekton Pro", Font.BOLD, 30));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 47, 329, 60);
		logInPanel.add(lblNewLabel_2);
		
		JTextArea txtrBackReadyTo = new JTextArea();
		txtrBackReadyTo.setBackground(SystemColor.info);
		txtrBackReadyTo.setWrapStyleWord(true);
		txtrBackReadyTo.setLineWrap(true);
		txtrBackReadyTo.setFont(new Font("Kozuka Gothic Pro M", Font.PLAIN, 12));
		txtrBackReadyTo.setText("Welcome back! Ready to find your next perfect space?  Log in to explore the best rental options tailored just for you.");
		txtrBackReadyTo.setBounds(45, 95, 342, 48);
		txtrBackReadyTo.setEditable(false);
		logInPanel.add(txtrBackReadyTo);
		
		JButton signInBTN = new JButton("Signin");
		signInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				signIn sign = new signIn();
				sign.frame.setVisible(true);
			}
		});
		signInBTN.setFont(new Font("Dialog", Font.BOLD, 15));
		signInBTN.setBounds(262, 297, 95, 42);
		logInPanel.add(signInBTN);
		
		String quote = "Welcome back! Ready to find your next perfect space? \n "+" Log in to explore the best rental options tailored just for you.";
		

		ImageIcon taxi1 = new ImageIcon(login.class.getResource("/Image/rental 1.jpg"));
		Image TaxiImage = taxi1.getImage().getScaledInstance(475, 550, Image.SCALE_DEFAULT);
		sidePiclbl = new JLabel("");
		sidePiclbl.setBounds(101, 107, 475, 543);
		frame.getContentPane().add(sidePiclbl);
		sidePiclbl.setIcon(new ImageIcon(TaxiImage));
		
//		int imageWidth = 100;
//		int imageHeight = 95;
//		ImageIcon mabiniIcon = new ImageIcon(getClass().getResource("/Image/Mabini.png"));
//		Image scaledImg = mabiniIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
		
		
	}
}
