package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.PersistenceException;


public class UserDaoJdbcImpl implements UserDao {

	private static String CONFIG_FILE = "/persistence.properties";
	private JdbcHelper jdbc = new JdbcHelper(CONFIG_FILE);
	
	@Override
	public void save(User a){
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_INSERT"));
			
			ps.setLong(1, a.getId());
			ps.setString(2 , a.getEmail());
			ps.setString(3, a.getLogin());
			ps.setString(4, a.getName());
			ps.setString(5, a.getPassword());
			ps.setInt(6 , 0);
			ps.setString(7, a.getSurname());
			
			ps.execute();

			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}
	@Override
	public void update(User a) {
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_UPDATE"));
			ps.setLong(7, a.getId());
			ps.setString(1 , a.getEmail());
			ps.setString(2, a.getLogin());
			ps.setString(3, a.getName());
			ps.setString(4, a.getPassword());
			ps.setInt(5 , a.getStatus().ordinal());
			ps.setString(6, a.getSurname());
			
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
			ps = con.prepareStatement(jdbc.getSql("USER_DELETE"));
			
			ps.setLong(1, id);

			ps.execute();

		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps, con); 
		}
	}
	@Override
	public User findById(Long id)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		User user = null;
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_FIND_BY_ID"));
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("ID"));
				user.setEmail(rs.getString("EMAIL"));
				user.setLogin(rs.getString("LOGIN"));
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				Integer estado = rs.getInt("STATUS");
				user.setStatus(UserStatus.values()[estado]);
				user.setSurname(rs.getString("SURNAME"));
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return user;
	}

	@Override
	public List<User> getUsers() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		
		List<User> users = new ArrayList<User>();

		try {

			// Obtenemos la conexiÃ³n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_SELECT_ALL"));
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("ID"));
				user.setEmail(rs.getString("EMAIL"));
				user.setLogin(rs.getString("LOGIN"));
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				Integer estado = rs.getInt("STATUS");
				user.setStatus(UserStatus.values()[estado]);
				user.setSurname(rs.getString("SURNAME"));
				
				users.add(user);
			}
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		
		return users;
	}
	@Override
	public User findByLogin(String login) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		User user = null;
		
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_FIND_BY_LOGIN"));
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("ID"));
				user.setEmail(rs.getString("EMAIL"));
				user.setLogin(rs.getString("LOGIN"));
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				Integer estado = rs.getInt("STATUS");
				user.setStatus(UserStatus.values()[estado]);
				user.setSurname(rs.getString("SURNAME"));
				
				
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		return user;
	}
	
	@Override
	public User findByLoginAndPassword(String login, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		User user = null;
		
		
		try {
			// Obtenemos la conexiï¿½ï¿½n a la base de datos.
			con = jdbc.createConnection();
			ps = con.prepareStatement(jdbc.getSql("USER_FIND_BY_LOGIN_AND_PASSWORD"));
			ps.setString(1, login);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("ID"));
				user.setEmail(rs.getString("EMAIL"));
				user.setLogin(rs.getString("LOGIN"));
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				Integer estado = rs.getInt("STATUS");
				user.setStatus(UserStatus.values()[estado]);
				user.setSurname(rs.getString("SURNAME"));
			
			}
			
		} catch (SQLException e) { 
			throw new PersistenceException("Invalid SQL or database schema", e); 
		} finally { 
			jdbc.close(ps,rs, con); 
		}
		return user;
	}



	
			
	

}









