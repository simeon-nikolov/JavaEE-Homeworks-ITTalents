package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String DB_USER = "ittstudent";
	private static final String DB_PASS = "ittstudent-123";
	private static final String DB_NAME = "season5_java2";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "192.168.8.22";
	
	private static DatabaseConnection instance = null;
	private Connection connection;

	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USER, DB_PASS);
			this.connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseConnection getInstance() {
		synchronized (DatabaseConnection.class) {
			if (instance == null) {
				instance = new DatabaseConnection();
			}
		}
		
		return instance;
	}
	
	public Connection getConnection() {
		return this.connection;
	}

}
