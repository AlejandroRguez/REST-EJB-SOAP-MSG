package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.PersistenceException;


public class ApplicationDaoJdbcImpl implements ApplicationDao {
	
	private static String CONFIG_FILE = "/persistence.properties";
	private JdbcHelper jdbc = new JdbcHelper(CONFIG_FILE);

	@Override
	public void save(Application app) {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_INSERT"));
			
			ps.setLong(1, app.getTripId());
			ps.setLong(2, app.getUserId());
			ps.execute();
			

			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}

	}

	@Override
	public Application findById(Long idUser , Long idTrip) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Application app = null;
	
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_FIND_BY_ID"));
			ps.setLong(1 , idUser);
			ps.setLong(2, idTrip);
			rs = ps.executeQuery();
			if (rs.next()) {
				app = new Application();
				app.setUserId(rs.getLong("applicants_ID"));
				app.setTripId(rs.getLong("appliedTrips_ID"));

			}
				

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		return app;
		
	}

	@Override
	public void delete(Long idUser , Long idTrip)  {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_DELETE"));
			
			ps.setLong(1, idUser);
			ps.setLong(2, idTrip);

			ps.execute();
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		
	}

	@Override
	public List<Application> getApplications()  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<Application> apps = new ArrayList<Application>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_SELECT_ALL"));
			rs = ps.executeQuery();

			while (rs.next()) {
				Application app = new Application();
				app.setUserId(rs.getLong("applicants_ID"));
				app.setTripId(rs.getLong("appliedTrips_ID"));
				apps.add(app);
				
				
			}
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		return apps;
	}

	@Override
	public List<Application> findByUserId(Long userId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<Application> apps = new ArrayList<Application>();
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_FIND_BY_USER_ID"));
			ps.setLong(1 , userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Application app = new Application();
				app.setUserId(rs.getLong("applicants_ID"));
				app.setTripId(rs.getLong("appliedTrips_ID"));
				
				apps.add(app);
			}

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		return apps;
		
	}

	@Override
	public List<Application> findByTripId(Long tripId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<Application> apps = new ArrayList<Application>();
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("APPLICATION_FIND_BY_TRIP_ID"));
			ps.setLong(1 , tripId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Application app = new Application();
				app.setUserId(rs.getLong("applicants_ID"));
				app.setTripId(rs.getLong("appliedTrips_ID"));
				
				apps.add(app);
				
				}

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
		
		return apps;
		
	}
	
	

}