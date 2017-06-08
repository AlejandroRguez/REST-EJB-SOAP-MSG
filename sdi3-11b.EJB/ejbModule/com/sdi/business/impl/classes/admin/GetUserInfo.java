package com.sdi.business.impl.classes.admin;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.model.UserInfo;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.UserDao;

public class GetUserInfo {
	
	UserDao userDao = Factories.persistence.newUserDao();
	TripDao tripDao = Factories.persistence.newTripDao();
	SeatDao seatDao = Factories.persistence.newSeatDao();

	public List<UserInfo> getUserInfo() {
		
		List<UserInfo> userInfo = new ArrayList<UserInfo>();
		for (User u : userDao.getUsers()){
				List<Trip> participaciones = new ArrayList<Trip>();
				UserInfo info = new UserInfo();
				info.setName(u.getName());
				info.setApellido(u.getSurname());
				info.setEmail(u.getEmail());
				List<Seat> seats = seatDao.findSeatsByUser(u.getId());
				for(Seat s : seats){
					Trip t = tripDao.findById(s.getTripId());
					participaciones.add(t);
				}
				info.setParticipaciones(participaciones);
				info.setViajesPromovidos(tripDao.findByPromoterId(u.getId()));
				info.setId(u.getId());
				info.setStatus(u.getStatus());
				info.setLogin(u.getLogin());
				userInfo.add(info);
		
		}
		Log.info("Obteniendo el listado de usuarios");
		return userInfo;
	}

}
