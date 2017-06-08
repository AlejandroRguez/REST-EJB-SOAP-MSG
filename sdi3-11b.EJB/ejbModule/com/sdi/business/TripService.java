package com.sdi.business;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface TripService {
	
	 void saveTrip(Trip trip) throws AlreadyPersistedException;
	 
	 void deleteTrip(Long id) throws NotPersistedException;
	 
	 Trip findById(Long id);
	 
	 Trip findByPromoterAndArrivalDate(Long id , Date arrivalDate);
			 
	 List<Trip> findByPromoterId(Long id);
	 
	 void updateTrip(Trip trip) throws NotPersistedException;
	 
	 List<Trip> getTrips();

}
