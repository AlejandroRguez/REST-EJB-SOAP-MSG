package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.exception.PersistenceException;


public class SeatDaoJdbcImpl implements SeatDao {

	private static String CONFIG_FILE = "/persistence.properties";
	private JdbcHelper jdbc = new JdbcHelper(CONFIG_FILE);

	@Override
	public void save(Seat seat){
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_INSERT"));
			
			ps.setString(1,seat.getComment());
			ps.setInt(2 , seat.getStatus().ordinal());
			ps.setLong(3, seat.getTripId());
			ps.setLong(4, seat.getUserId());

			ps.execute();

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}
		
	

	@Override
	public void update(Seat seat)  {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_UPDATE"));
			
			ps.setString(1,seat.getComment());
			ps.setInt(2, seat.getStatus().ordinal());
			ps.setLong(3, seat.getTripId());
			ps.setLong(4, seat.getUserId());

			ps.execute();
			

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
	}

	@Override
	public void delete(Long trip, Long user)  {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_DELETE"));
			
			ps.setLong(1, trip);
			ps.setLong(2, user);

			ps.execute();

			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
	}

	@Override
	public List<Seat> getSeats()  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<Seat> seats = new ArrayList<Seat>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_SELECT_ALL"));
			rs = ps.executeQuery();

			while (rs.next()) {
				Seat seat = new Seat();
				seat.setComment(rs.getString("COMMENT"));
				seat.setStatus(SeatStatus.values()[rs.getInt("STATUS")]);
				seat.setTripId(rs.getLong("TRIP_ID"));
				seat.setUserId(rs.getLong("USER_ID"));
				
				
				seats.add(seat);
				
				
			}
		
	} catch (SQLException e) { 
		throw new PersistenceException("Invalid SQL or database schema", e); 
	} finally { 
		jdbc.close(ps,rs,con); 
	}
		
		return seats;
	}

	@Override
	public Seat findByUserAndTrip(Long userId, Long tripId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Seat seat = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_FIND_BY_USER_AND_TRIP"));
			ps.setLong(1, userId);
			ps.setLong(2, tripId);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				seat = new Seat();
				seat.setComment(rs.getString("COMMENT"));
				seat.setUserId(rs.getLong("USER_ID"));
				seat.setTripId(rs.getLong("TRIP_ID"));
				seat.setStatus(SeatStatus.values()[rs.getInt("STATUS")]);
				
				
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return seat;
	}



	@Override
	public List<Seat> findByTrip(Long tripId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<Seat> seats = new ArrayList<Seat>();
		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_FIND_BY_TRIP"));
			ps.setLong(1, tripId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Seat seat = new Seat();
				seat.setComment(rs.getString("COMMENT"));
				seat.setStatus(SeatStatus.values()[rs.getInt("STATUS")]);
				seat.setTripId(rs.getLong("TRIP_ID"));
				seat.setUserId(rs.getLong("USER_ID"));
				
				
				seats.add(seat);
				
				
			}
		
	} catch (SQLException e) { 
		throw new PersistenceException("Invalid SQL or database schema", e); 
	} finally { 
		jdbc.close(ps,rs,con); 
	}
		
		return seats;
		
	}
	
	@Override
	public List<Seat> findSeatsByUser(Long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<Seat> seats = new ArrayList<Seat>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("SEAT_FIND_BY_USER"));
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Seat seat = new Seat();
				seat.setComment(rs.getString("COMMENT"));
				seat.setStatus(SeatStatus.values()[rs.getInt("STATUS")]);
				seat.setTripId(rs.getLong("TRIP_ID"));
				seat.setUserId(rs.getLong("USER_ID"));
				
				
				seats.add(seat);
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		return seats;
	}

}

