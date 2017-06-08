package com.sdi.rest;

import java.util.List;

import com.sdi.business.PromoterUserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;

public class PromoterUserServiceRestImpl implements PromoterUserServiceRest {
	
	PromoterUserService service = Factories.services.getPromoterUserService();

	@Override
	public User findByLoginAndPassword(String login, String password) {
		return service.findByLoginAndPassword(login, password);
	}

	@Override
	public List<Trip> findByPromoterId(String auth, Long id) {
		return service.findByPromoterId(id);
	}

	@Override
	public List<Application> findByTripId(String auth, Long id) {
		return service.findByTripId(id);
	}

	
	@Override
	public void saveSeat(String auth, Seat seat) throws BusinessException {
		service.saveSeat(seat);
		
	}

	@Override
	public Seat createSeatByApplication(String auth, Long userId, Long tripId) {
		return service.createSeatByApplication(userId, tripId);
	}

	@Override
	public void deleteApplication(String auth, Long userId, Long tripId) {
		service.deleteApplication(userId, tripId);
		
	}

	

}
