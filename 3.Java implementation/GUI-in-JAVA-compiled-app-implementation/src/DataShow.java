package app.home;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JTable;
import java.awt.Scrollbar;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.BevelBorder;
import java.awt.List;

public class DataShow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	static String sql_s;
	
	public static void myMain(String query) {
		sql_s=query;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataShow frame = new DataShow();
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
	public DataShow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List list = new List();
		DataAccess da= new DataAccess();
		ResultSet rs=da.getResultSet(sql_s);
		try {
			while(rs.next()) {
				list.add(rs.getString("tweets"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.setBounds(10, 10, 580, 458);
		contentPane.add(list);
	}
}
