package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataAccessObject implements IDataAccessObject {
	private final Connection connection = DatabaseConnection.getInstance().getConnection();
	
	public Connection getConnection() {
		return this.connection;
	}
	
	protected void closeResourses(Statement statement, ResultSet result) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
