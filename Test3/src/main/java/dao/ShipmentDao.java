package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import smartcourier.Shipment;
import exceptions.DaoException;

public class ShipmentDao extends DataAccessObject {
	
	private Connection connection = super.getConnection();

	public int addShipment(Shipment shipment) throws DaoException {
		PreparedStatement ps = null;
		ResultSet result = null;
		
		if (shipment != null) {
			try {
				ps = getConnection().prepareStatement("INSERT INTO mydb.shipments VALUES (?, ?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setInt(1, shipment.getId());
				ps.setString(2, shipment.getContent());
				ps.setString(3, shipment.getSender().getName() + shipment.getSender().getEgn());
				ps.setString(4, shipment.getReceiver().getName() + shipment.getReceiver().getEgn());
				ps.setDate(5, (java.sql.Date) shipment.getDateTimeOfSending());
				ps.setBoolean(6, shipment.getIsDelivered());
				ps.executeUpdate();
				this.connection.commit();
				result = ps.getGeneratedKeys();
				result.next();
				
				return result.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException("The shipment couldn't be added.", e);
			} finally {
				closeResourses(ps, result);
			}
		} else {
			throw new DaoException("Shipment is null!");
		}
	}
}
