package com.sdi.business.impl.classes.admin;

import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.NotPersistedException;

public class DisableUser {

	public void disable(Long id) {
		UserDao userDao = Factories.persistence.newUserDao();
		User u = userDao.findById(id);
		SeatDao seatDao = Factories.persistence.newSeatDao();
		List<Seat> seats = seatDao.findSeatsByUser(u.getId());
		for(Seat s: seats){
			if(Factories.persistence.newTripDao().findById(s.getTripId())
					.getStatus().equals(TripStatus.OPEN)){
				s.setStatus(SeatStatus.EXCLUDED);
			}
			try {
				seatDao.update(s);
			} catch (NotPersistedException e) {
				Log.error("No existe el usuario");
			}
		}
		u.setStatus(UserStatus.CANCELLED);
		try {
			userDao.update(u);
			Log.info("Usuario deshabilitado");
		} catch (NotPersistedException e) {
			Log.error("No existe el usuario");
		}
		
		
	}

}
