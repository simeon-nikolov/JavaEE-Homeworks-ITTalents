package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import smartcourier.Protocol;
import exceptions.DaoException;

public class ProtocolDao extends DataAccessObject {
	
	private Connection connection = super.getConnection();

	public int addProtocol(Protocol protocol) throws DaoException {
		PreparedStatement ps = null;
		ResultSet result = null;
		
		if (protocol != null) {
			try {
				ps = getConnection().prepareStatement("INSERT INTO mydb.protocols VALUES (null, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, protocol.getFrom().getName() + protocol.getFrom().getEgn());
				ps.setString(2, protocol.getTo().getName() + protocol.getTo().getEgn());

				ps.executeUpdate();
				this.connection.commit();
				result = ps.getGeneratedKeys();
				result.next();
				
				return result.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException("The protocol couldn't be added.", e);
			} finally {
				closeResourses(ps, result);
			}
		} else {
			throw new DaoException("Protocol is null!");
		}
	}
}
