package app.home;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTextField search;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		search = new JTextField();
		search.setBounds(77, 30, 293, 26);
		contentPane.add(search);
		search.setColumns(10);
		JComboBox comboBoxDivision = new JComboBox();
		comboBoxDivision.setModel(new DefaultComboBoxModel(new String[] {"All", "Dhaka", "Rajshahi", "Khulna", "Barishal", "Chittagong", "Sylhet", "Rangpur"}));
		comboBoxDivision.setSelectedIndex(0);
		comboBoxDivision.setBounds(147, 78, 161, 27);
		contentPane.add(comboBoxDivision);
		
		JButton btnNewButton = new JButton("SEARCH");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql;
				String key=search.getText();
				String area=(String) comboBoxDivision.getSelectedItem();
				if(area.equals("All")) {
					sql="SELECT count(tweets) as count ,date FROM `tweets_table` where tweets like '%"+key+"%' GROUP by date order by date asc ";
				}else {
					sql="SELECT count(tweets) as count ,date FROM `tweets_table` where tweets like '%"+key+"%' and Location like '%"+area+"%' GROUP by date order by date asc";
				}
				
				LineChart_AWT.myMain(sql);
			}
		});
		btnNewButton.setBounds(169, 145, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("SHOW DATA");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql;
				String key=search.getText();
				String area=(String) comboBoxDivision.getSelectedItem();
				if(area.equals("All")) {
					sql="SELECT tweets ,date FROM `tweets_table` where tweets like '%"+key+"%' order by date asc ";
				}else {
					sql="SELECT tweets ,date FROM `tweets_table` where tweets like '%"+key+"%' and Location like '%"+area+"%' order by date asc";
				}
				DataShow.myMain(sql);
			}
		});
		btnNewButton_1.setBounds(169, 186, 117, 29);
		contentPane.add(btnNewButton_1);
		
		
	}
}
