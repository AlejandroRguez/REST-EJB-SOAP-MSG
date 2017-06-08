package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.impl.classes.CreateSeatByApplication;
import com.sdi.business.impl.classes.applications.Delete;
import com.sdi.business.impl.classes.applications.FindByTrip;
import com.sdi.business.impl.classes.seats.Save;
import com.sdi.business.impl.classes.trips.FindByPromoterId;
import com.sdi.business.impl.classes.users.FindByLoginAndPassword;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;

@Stateless
@WebService(name="PromoterUserService")
public class EjbPromoterUserService implements LocalPromoterUserService,
									RemotePromoterUserService{

	@Override
	public User findByLoginAndPassword(String login, String password){
		return new FindByLoginAndPassword().findByLoginAndPassword(login, password);
	}

	@Override
	public List<Trip> findByPromoterId(Long id) {
		return new FindByPromoterId().find(id);
	}

	@Override
	public List<Application> findByTripId(Long tripId) {
		return new FindByTrip().findByTrip(tripId);
	}

	
	@Override
	public void saveSeat(Seat seat) {
		 new Save().save(seat);
		
	}

	@Override
	public Seat createSeatByApplication(Long userId, Long tripId) {
		return new CreateSeatByApplication().create(userId, tripId);
	}

	@Override
	public void deleteApplication(Long userId, Long tripId) {
		new Delete().delete(userId, tripId);;
		
	}

	

}
