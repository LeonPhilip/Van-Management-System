
package rental;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private History histo = new History();
	private IssueItem itemIssue = new IssueItem();
	private NewItem newItem = new NewItem();
	private ReturnItem returnItem = new ReturnItem();
	private NewBorrower newUser = new NewBorrower();
	private Items item = new Items();
	JPanel testPanel;
	JPanel allPanel[] = {histo.historyPanel, itemIssue.issueItemPanel, newItem.newItemPanel, returnItem.returnPanel, newUser.panel, item.panel, returnItem.returnPanel};

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					home frame = new home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 234, 812);
		panel.setBackground(SystemColor.info);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewBorrower = new JButton("New borrower");
		btnNewBorrower.setFont(new Font("Courier New", Font.BOLD, 13));
		btnNewBorrower.setBounds(48, 263, 136, 41);
		panel.add(btnNewBorrower);
		
		JButton newItemBTN = new JButton("New item");
		newItemBTN.setFont(new Font("Courier New", Font.BOLD, 15));
		newItemBTN.setBounds(48, 336, 136, 41);
		panel.add(newItemBTN);
		
		JButton historyBTN = new JButton("History");
		historyBTN.setFont(new Font("Courier New", Font.BOLD, 15));
		historyBTN.setBounds(48, 489, 136, 41);
		panel.add(historyBTN);
		
		JButton testBTN = new JButton("Test System");
		testBTN.setFont(new Font("Juice ITC", Font.BOLD, 20));
		testBTN.setBounds(48, 672, 136, 41);
		panel.add(testBTN);
		
		JButton itemUserBTN = new JButton("Items & User");
		itemUserBTN.setFont(new Font("Courier New", Font.BOLD, 13));
		itemUserBTN.setBounds(48, 412, 136, 41);
		panel.add(itemUserBTN);
		
		JLabel Design1LBL = new JLabel("\r\n");
		Design1LBL.setBackground(new Color(128, 0, 0));
		Design1LBL.setBounds(252, 0, 939, 112);
		panel.add(Design1LBL);
		
		JLabel WelcomeLBL = new JLabel("Welcome");
		WelcomeLBL.setFont(new Font("Sitka Heading", Font.BOLD, 28));
		WelcomeLBL.setHorizontalAlignment(SwingConstants.CENTER);
		WelcomeLBL.setBounds(37, 21, 154, 50);
		panel.add(WelcomeLBL);
		
		JLabel nameLBL = new JLabel("");
		nameLBL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameLBL.setHorizontalAlignment(SwingConstants.CENTER);
		nameLBL.setBounds(10, 82, 187, 41);
		panel.add(nameLBL);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBounds(0, 0, 1443, 180);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		testPanel = new JPanel();
		testPanel.setBounds(386, 232, 783, 478);
		contentPane.add(testPanel);
		testPanel.setVisible(false);
		testPanel.setLayout(null);
		getContentPane().add(testPanel);

		JButton issueItemBTN = new JButton("Issue Item");
		issueItemBTN.setBounds(302, 85, 226, 77);
		issueItemBTN.setFont(new Font("Juice ITC", Font.BOLD, 20));
		testPanel.add(issueItemBTN);
		
		JButton returnItemBTN = new JButton("Return Item");
		returnItemBTN.setBounds(302, 285, 226, 77);
		returnItemBTN.setFont(new Font("Juice ITC", Font.BOLD, 20));
		testPanel.add(returnItemBTN);
		
		ImageIcon logomabiniIcon = new ImageIcon(home.class.getResource("/Image/Mabini.png"));
		Image TaxiImage = logomabiniIcon.getImage().getScaledInstance(154, 160, Image.SCALE_DEFAULT);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TaxiImage));
		lblNewLabel.setBounds(268, 0, 154, 177);
		panel_1.add(lblNewLabel);
		
		JLabel idkLBL = new JLabel("Home");
		idkLBL.setForeground(Color.DARK_GRAY);
		idkLBL.setVerticalAlignment(SwingConstants.TOP);
		idkLBL.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 35));
		idkLBL.setHorizontalAlignment(SwingConstants.CENTER);
		idkLBL.setBounds(524, 38, 524, 77);
		panel_1.add(idkLBL);
		
		contentPane.add(histo.historyPanel);
		histo.historyPanel.setVisible(false);
		histo.historyPanel.setBounds(330, 190, 800, 700);
		
		contentPane.add(itemIssue.issueItemPanel);
		itemIssue.issueItemPanel.setVisible(false);
		itemIssue.issueItemPanel.setBounds(270, 120, 1000, 700);
		
		contentPane.add(newItem.newItemPanel);
		newItem.newItemPanel.setVisible(false);
		newItem.newItemPanel.setBounds(270, 170, 1000, 700);
		
		contentPane.add(returnItem.returnPanel);
		returnItem.returnPanel.setVisible(false);
		returnItem.returnPanel.setBounds(270, 170, 1000, 700);
		
		contentPane.add(newUser.panel);
		newUser.panel.setVisible(false);
		newUser.panel.setBounds(270, 170, 1000, 700);

		contentPane.add(item.panel);
		item.panel.setVisible(false);
		item.panel.setBounds(330, 190, 800, 700);
		
		JLabel MabiniLogo11 = new JLabel("");
		MabiniLogo11.setBounds(1193, 0, 154, 177);
		MabiniLogo11.setIcon(new ImageIcon(TaxiImage));
		panel_1.add(MabiniLogo11);
		
		
		itemUserBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("Item and User");
				item.panel.setVisible(true);
				
			}
		});
		testBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("Customer");
				testPanel.setVisible(true);
				
			}
		});
		historyBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("History");
				histo.historyPanel.setVisible(true);
				
			}
		});
		newItemBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("New Item");
				newItem.newItemPanel.setVisible(true);
				
			}
		});
		btnNewBorrower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("New Borrow");
				newUser.panel.setVisible(true);
				
			}
		});
		issueItemBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("Issue Item");
				itemIssue.issueItemPanel.setVisible(true);
			}
		});
		returnItemBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				clear();
				idkLBL.setText("Return Item");
				returnItem.returnPanel.setVisible(true);
				
			}
		});

	}
	
	public void panelTest() {

	}
	
	public void hidePanel() {
		
		for(JPanel p : allPanel) {
			p.setVisible(false);
			
			testPanel.setVisible(false);
		}
	}
	public void clear() {
		
		itemIssue.clear();
		newItem.clear();
		returnItem.clear();
		newUser.clear();
		
		
	}
}
