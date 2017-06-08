package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sdi.model.AddressPoint;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.Waypoint;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.exception.PersistenceException;


public class TripDaoJdbcImpl implements TripDao {
	
	private static String CONFIG_FILE = "/persistence.properties";
	private JdbcHelper jdbc = new JdbcHelper(CONFIG_FILE);



	@Override
	public void save(Trip dto){
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_INSERT"));
			
			
			ps.setLong(1, dto.getId());
			ps.setDate(2, (java.sql.Date) dto.getArrivalDate());
			ps.setInt (3, dto.getAvailablePax());
			ps.setDate (4, (java.sql.Date) dto.getClosingDate());
			ps.setString(5,dto.getComments());
			ps.setString(6 , dto.getDeparture().getAddress());
			ps.setString(7 , dto.getDeparture().getCity());
			ps.setString(8, dto.getDeparture().getCountry());
			ps.setString(9, dto.getDeparture().getState());
			ps.setDouble(10 , 0.0);
			ps.setDouble(11 , 0.0);
			ps.setString(12, dto.getDeparture().getZipCode());
			ps.setDate(13, (java.sql.Date) dto.getDepartureDate());
			ps.setString(14 , dto.getDestination().getAddress());
			ps.setString(15 , dto.getDestination().getCity());
			ps.setString(16, dto.getDestination().getCountry());
			ps.setString(17, dto.getDestination().getState());
			ps.setDouble(18 , 0.0);
			ps.setDouble(19 , 0.0);
			ps.setString(20, dto.getDestination().getZipCode());
			ps.setDouble(21 , dto.getEstimatedCost());
			ps.setInt(22, dto.getMaxPax());
			ps.setInt(23 , dto.getStatus().ordinal());
			ps.setLong(24, dto.getPromoterId());
			
			ps.execute();


			

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}
	
	@Override
	public void update(Trip dto){
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_UPDATE"));
			
			ps.setLong(24, dto.getId());
			ps.setDate(1, (java.sql.Date) dto.getArrivalDate());
			ps.setInt (2, dto.getAvailablePax());
			ps.setDate (3, (java.sql.Date) dto.getClosingDate());
			ps.setString(4,dto.getComments());
			ps.setString(5 , dto.getDeparture().getAddress());
			ps.setString(6 , dto.getDeparture().getCity());
			ps.setString(7, dto.getDeparture().getCountry());
			ps.setString(8, dto.getDeparture().getState());
			ps.setDouble(9 , 0.0);
			ps.setDouble(10 , 0.0);
			ps.setString(11, dto.getDeparture().getZipCode());
			ps.setDate(12, (java.sql.Date) dto.getDepartureDate());
			ps.setString(13 , dto.getDestination().getAddress());
			ps.setString(14 , dto.getDestination().getCity());
			ps.setString(15, dto.getDestination().getCountry());
			ps.setString(16, dto.getDestination().getState());
			ps.setDouble(17 , 0.0);
			ps.setDouble(18 , 0.0);
			ps.setString(19, dto.getDestination().getZipCode());
			ps.setDouble(20 , dto.getEstimatedCost());
			ps.setInt(21, dto.getMaxPax());
			ps.setInt(22 , dto.getStatus().ordinal());
			ps.setLong(23, dto.getPromoterId());
			
			ps.execute();

			

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}
	

	
	public void delete(Long id){
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_DELETE"));
			
			ps.setLong(1, id);

			ps.execute();

			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}

	public Trip findById(Long id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Trip trip = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_FIND_BY_ID"));
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				trip = new Trip();
				trip.setId(rs.getLong("ID"));
				trip.setArrivalDate(rs.getDate("ARRIVALDATE"));
				trip.setAvailablePax(rs.getInt("AVAILABLEPAX"));
				trip.setClosingDate(rs.getDate("CLOSINGDATE"));
				trip.setComments(rs.getString("COMMENTS"));
				trip.setDeparture(new AddressPoint(rs.getString("DEPARTURE_ADDRESS")
				,rs.getString("DEPARTURE_CITY"),rs.getString("DEPARTURE_COUNTRY"),
				rs.getString("DEPARTURE_STATE"),rs.getString("DEPARTURE_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setDepartureDate(rs.getDate("DEPARTUREDATE"));
				trip.setDestination(new AddressPoint(rs.getString("DESTINATION_ADDRESS")
				,rs.getString("DESTINATION_CITY"),rs.getString("DESTINATION_COUNTRY"),
				rs.getString("DESTINATION_STATE"),rs.getString("DESTINATION_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setEstimatedCost(rs.getDouble("ESTIMATEDCOST"));
				trip.setMaxPax(rs.getInt("MAXPAX"));
				Integer estado = rs.getInt("STATUS");
				trip.setStatus(TripStatus.values()[estado]);
				trip.setPromoterId(rs.getLong("PROMOTER_ID"));
				
				
			}
			
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return trip;
	}


	@Override
	public List<Trip> getTrips() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<Trip> trips = new ArrayList<Trip>();
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_SELECT_ALL"));
			
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(rs.getLong("ID"));
				trip.setArrivalDate(rs.getDate("ARRIVALDATE"));
				trip.setAvailablePax(rs.getInt("AVAILABLEPAX"));
				trip.setClosingDate(rs.getDate("CLOSINGDATE"));
				trip.setComments(rs.getString("COMMENTS"));
				trip.setDeparture(new AddressPoint(rs.getString("DEPARTURE_ADDRESS")
				,rs.getString("DEPARTURE_CITY"),rs.getString("DEPARTURE_COUNTRY"),
				rs.getString("DEPARTURE_STATE"),rs.getString("DEPARTURE_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setDepartureDate(rs.getDate("DEPARTUREDATE"));
				trip.setDestination(new AddressPoint(rs.getString("DESTINATION_ADDRESS")
				,rs.getString("DESTINATION_CITY"),rs.getString("DESTINATION_COUNTRY"),
				rs.getString("DESTINATION_STATE"),rs.getString("DESTINATION_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setEstimatedCost(rs.getDouble("ESTIMATEDCOST"));
				trip.setMaxPax(rs.getInt("MAXPAX"));
				Integer estado = rs.getInt("STATUS");
				trip.setStatus(TripStatus.values()[estado]);
				trip.setPromoterId(rs.getLong("PROMOTER_ID"));
				
				
				trips.add(trip);
				
				
			}
			
			
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return trips;
	}
	

	@Override
	public Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Trip trip = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_FIND_BY_PROMOTER_ID_AND_ARRIVAL_DATE"));
			ps.setLong(1, id);
			ps.setDate(2, (java.sql.Date) arrivalDate);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				trip = new Trip();
				trip.setId(rs.getLong("ID"));
				trip.setArrivalDate(rs.getDate("ARRIVALDATE"));
				trip.setAvailablePax(rs.getInt("AVAILABLEPAX"));
				trip.setClosingDate(rs.getDate("CLOSINGDATE"));
				trip.setComments(rs.getString("COMMENTS"));
				trip.setDeparture(new AddressPoint(rs.getString("DEPARTURE_ADDRESS")
				,rs.getString("DEPARTURE_CITY"),rs.getString("DEPARTURE_COUNTRY"),
				rs.getString("DEPARTURE_STATE"),rs.getString("DEPARTURE_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setDepartureDate(rs.getDate("DEPARTUREDATE"));
				trip.setDestination(new AddressPoint(rs.getString("DESTINATION_ADDRESS")
				,rs.getString("DESTINATION_CITY"),rs.getString("DESTINATION_COUNTRY"),
				rs.getString("DESTINATION_STATE"),rs.getString("DESTINATION_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setEstimatedCost(rs.getDouble("ESTIMATEDCOST"));
				trip.setMaxPax(rs.getInt("MAXPAX"));
				Integer estado = rs.getInt("STATUS");
				trip.setStatus(TripStatus.values()[estado]);
				trip.setPromoterId(rs.getLong("PROMOTER_ID"));
				
				
				
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return trip;
	}

	@Override
	public List<Trip> findByPromoterId(Long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<Trip> trips = new ArrayList<Trip>();
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("TRIP_FIND_BY_PROMOTER_ID"));
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(rs.getLong("ID"));
				trip.setId(rs.getLong("ID"));
				trip.setArrivalDate(rs.getDate("ARRIVALDATE"));
				trip.setAvailablePax(rs.getInt("AVAILABLEPAX"));
				trip.setClosingDate(rs.getDate("CLOSINGDATE"));
				trip.setComments(rs.getString("COMMENTS"));
				trip.setDeparture(new AddressPoint(rs.getString("DEPARTURE_ADDRESS")
				,rs.getString("DEPARTURE_CITY"),rs.getString("DEPARTURE_COUNTRY"),
				rs.getString("DEPARTURE_STATE"),rs.getString("DEPARTURE_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setDepartureDate(rs.getDate("DEPARTUREDATE"));
				trip.setDestination(new AddressPoint(rs.getString("DESTINATION_ADDRESS")
				,rs.getString("DESTINATION_CITY"),rs.getString("DESTINATION_COUNTRY"),
				rs.getString("DESTINATION_STATE"),rs.getString("DESTINATION_ZIPCODE"), new Waypoint(0.0,0.0)));
				trip.setEstimatedCost(rs.getDouble("ESTIMATEDCOST"));
				trip.setMaxPax(rs.getInt("MAXPAX"));
				Integer estado = rs.getInt("STATUS");
				trip.setStatus(TripStatus.values()[estado]);
				trip.setPromoterId(rs.getLong("PROMOTER_ID"));
				
				trips.add(trip);
				
				
			}
			
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return trips;
	}

}
