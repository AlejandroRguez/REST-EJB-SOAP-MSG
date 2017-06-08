package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Rating;
import com.sdi.persistence.RatingDao;
import com.sdi.persistence.exception.PersistenceException;


public class RatingDaoJdbcImpl implements RatingDao {

	private static String CONFIG_FILE = "/persistence.properties";
	private JdbcHelper jdbc = new JdbcHelper(CONFIG_FILE);
	
	@Override
	public Rating findByAboutFrom(Long aboutUserId, Long aboutTripId,
			Long fromUserId, Long fromTripId)   {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Rating rating = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_FIND_BY_ABOUT_FROM"));
			ps.setLong(1,aboutUserId );
			ps.setLong(2, aboutTripId);
			ps.setLong(3, fromUserId);
			ps.setLong(4, fromTripId);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				rating = new Rating();
				rating.setId(rs.getLong("ID"));
				rating.setComment(rs.getString("COMMENT"));
				rating.setValue(rs.getInt("VALUE"));
				rating.setSeatAboutTripId(rs.getLong("ABOUT_TRIP_ID"));
				rating.setSeatAboutUserId(rs.getLong("ABOUT_USER_ID"));
				rating.setSeatFromTripId(rs.getLong("FROM_TRIP_ID"));
				rating.setSeatFromUserId(rs.getLong("FROM_USER_ID"));
				
				
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		return rating;
	}
		
	

	@Override
	public void save(Rating rating)  {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_INSERT"));
			
			ps.setLong(1, rating.getId());
			ps.setString(2, rating.getComment());
			ps.setInt(3, rating.getValue());
			ps.setLong(4, rating.getSeatAboutTripId());
			ps.setLong(5, rating.getSeatAboutUserId());
			ps.setLong(6 , rating.getSeatFromTripId());
			ps.setLong(7, rating.getSeatFromUserId());

			ps.execute();

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}

	@Override
	public void update(Rating rating) {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_UPDATE"));
			
			ps.setLong(7, rating.getId());
			ps.setString(1, rating.getComment());
			ps.setInt(2, rating.getValue());
			ps.setLong(3, rating.getSeatAboutTripId());
			ps.setLong(4, rating.getSeatAboutUserId());
			ps.setLong(5 , rating.getSeatFromTripId());
			ps.setLong(6, rating.getSeatFromUserId());

			ps.execute();

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
	}

	@Override
	public void delete(Long id) {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_DELETE"));
			
			ps.setLong(1, id);
			ps.execute();
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		
	}

	@Override
	public List<Rating> getRatings()  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<Rating> ratings = new ArrayList<Rating>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_SELECT_ALL"));
			rs = ps.executeQuery();

			while (rs.next()) {
				Rating rating = new Rating();
				rating.setId(rs.getLong("ID"));
				rating.setComment(rs.getString("COMMENT"));
				rating.setValue(rs.getInt("VALUE"));
				rating.setSeatAboutTripId(rs.getLong("ABOUT_TRIP_ID"));
				rating.setSeatAboutUserId(rs.getLong("ABOUT_USER_ID"));
				rating.setSeatFromTripId(rs.getLong("FROM_TRIP_ID"));
				rating.setSeatFromUserId(rs.getLong("FROM_USER_ID"));
				ratings.add(rating);
				
			}
		
	} catch (SQLException e) { 
		throw new PersistenceException("Invalid SQL or database schema", e); 
	} finally { 
		jdbc.close(ps, rs, con); 
	}
		
		return ratings;
	}

	@Override
	public Rating findById(Long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Rating rating = null;
		
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_FIND_BY_ID"));
			ps.setLong(1,id);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				rating = new Rating();
				rating.setId(rs.getLong("ID"));
				rating.setComment(rs.getString("COMMENT"));
				rating.setValue(rs.getInt("VALUE"));
				rating.setSeatAboutTripId(rs.getLong("ABOUT_TRIP_ID"));
				rating.setSeatAboutUserId(rs.getLong("ABOUT_USER_ID"));
				rating.setSeatFromTripId(rs.getLong("FROM_TRIP_ID"));
				rating.setSeatFromUserId(rs.getLong("FROM_USER_ID"));
				
				
				
			}
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs,con); 
		}
		
		return rating;
	}



	@Override
	public List<Rating> findByTripId(Long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<Rating> ratings = new ArrayList<Rating>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("RATING_FIND_BY_TRIP_ID"));
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Rating rating = new Rating();
				rating.setId(rs.getLong("ID"));
				rating.setComment(rs.getString("COMMENT"));
				rating.setValue(rs.getInt("VALUE"));
				rating.setSeatAboutTripId(rs.getLong("ABOUT_TRIP_ID"));
				rating.setSeatAboutUserId(rs.getLong("ABOUT_USER_ID"));
				rating.setSeatFromTripId(rs.getLong("FROM_TRIP_ID"));
				rating.setSeatFromUserId(rs.getLong("FROM_USER_ID"));
				ratings.add(rating);
				
			}
		
	} catch (SQLException e) { 
		throw new PersistenceException("Invalid SQL or database schema", e); 
	} finally { 
		jdbc.close(ps, rs, con); 
	}
		
		return ratings;
	}

	

}
