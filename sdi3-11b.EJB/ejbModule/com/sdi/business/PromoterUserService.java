package com.sdi.business;

import java.util.List;

import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;


public interface PromoterUserService {
	
	User findByLoginAndPassword(String login, String password);
	
	List<Trip> findByPromoterId(Long id);
	
	List<Application> findByTripId(Long tripId);
	
	void saveSeat(Seat seat);

	Seat createSeatByApplication(Long userId, Long tripId);

	void deleteApplication(Long userId, Long tripId);
	

}
