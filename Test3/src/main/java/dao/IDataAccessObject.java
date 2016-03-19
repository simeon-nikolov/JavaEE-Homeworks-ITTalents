package dao;

import java.sql.Connection;

public interface IDataAccessObject {
	Connection getConnection();
}