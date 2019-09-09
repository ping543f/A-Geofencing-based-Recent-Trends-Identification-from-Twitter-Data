package app.home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class Home {

	private JFrame frame;
	private JTextField dateFrom;
	private JTextField dateTo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 460, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("TRENDS ANALYSIS FROM TWEER TWEETS DATA IN BANGLADESH");
		label.setBounds(24, 18, 419, 16);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Select Area");
		label_1.setBounds(189, 94, 82, 16);
		frame.getContentPane().add(label_1);
		
		JComboBox comboBoxDivision = new JComboBox();
		comboBoxDivision.setModel(new DefaultComboBoxModel(new String[] {"All", "Dhaka", "Rajshahi", "Khulna", "Barishal", "Chittagong", "Sylhet", "Rangpur"}));
		comboBoxDivision.setSelectedIndex(0);
		comboBoxDivision.setBounds(153, 122, 152, 27);
		frame.getContentPane().add(comboBoxDivision);
		
		JLabel label_2 = new JLabel("Select Date");
		label_2.setBounds(189, 174, 82, 16);
		frame.getContentPane().add(label_2);
		
		dateFrom = new JTextField();
		dateFrom.setColumns(10);
		dateFrom.setBounds(92, 231, 130, 26);
		frame.getContentPane().add(dateFrom);
		
		dateTo = new JTextField();
		dateTo.setColumns(10);
		dateTo.setBounds(234, 231, 130, 26);
		frame.getContentPane().add(dateTo);
		
		JButton button = new JButton("LOAD DATA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataPrepare read= new DataPrepare();
				String sql;
				String d1=dateFrom.getText();
				String d2=dateTo.getText();
				String area=(String) comboBoxDivision.getSelectedItem();
				
				if(d1.equals("") || d2.equals("")) {
					if(area.equals("All")) {
						sql="select * from tweets_table where Lang='en' ";
					}else {
						sql="select * from tweets_table where Lang='en' and Location like '%"+area+"%'";
					}
				}else {
					if(area.equals("All")) {
						sql="select * from tweets_table where Lang='en' and  date>= '"+d1+"' and date<='"+d2+"'";
					}else {
						sql="select * from tweets_table where Lang='en' and Location like '%"+area+"%' and date>= '"+d1+"' and date<='"+d2+"' ";
					}
				}
				read.myMain(sql);
				
				
			}
		});
		button.setBounds(176, 279, 117, 29);
		frame.getContentPane().add(button);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(99, 211, 61, 16);
		frame.getContentPane().add(lblFrom);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(244, 211, 61, 16);
		frame.getContentPane().add(lblTo);
		
		JButton btnNewButton = new JButton("CLEAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataAccess da = new DataAccess();
				da.executeQuery("TRUNCATE TABLE frequency");
			}
		});
		btnNewButton.setBounds(37, 320, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("GRAPH");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BarChart_AWT.myMain();
			}
		});
		btnNewButton_1.setBounds(308, 320, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("SEARCH");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Menu().setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton_2.setBounds(176, 320, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
	}
}
