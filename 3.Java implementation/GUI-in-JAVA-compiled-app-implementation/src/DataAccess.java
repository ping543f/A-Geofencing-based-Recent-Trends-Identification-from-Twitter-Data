package app.home;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataAccess{
	private String host = "localhost";
	private int port = 3306;
	private String database = "trends_db";
	private String username = "root";
	private String password = "";
	private String connectionString;
	
	private Connection connection;
	private Statement statement;
	
	public DataAccess() {
		this.connectionString = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.connectionString, this.username, this.password);
			this.statement = this.connection.createStatement();
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DataAccess(String host, int port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		
		this.connectionString = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.connectionString, this.username, this.password);
			this.statement = this.connection.createStatement();
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(String sql) {
		try {
			return this.statement.executeQuery(sql);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public int executeQuery(String sql) {
		try {
			return this.statement.executeUpdate(sql);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	protected void finalize() {
		try {
			this.statement.close();
			this.connection.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setHost(String host) {this.host = host;}
	public void setPort(int port) {this.port = port;}
	public void setDatabase(String database) {this.database = database;}
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}
	
	public String getHost() {return this.host;}
	public int getPort() {return this.port;}
	public String getDatabase() {return this.database;}
	public String getUsername() {return this.username;}
	public String getPassword() {return this.password;}
	public String getConnectionString() {return this.connectionString;}
}