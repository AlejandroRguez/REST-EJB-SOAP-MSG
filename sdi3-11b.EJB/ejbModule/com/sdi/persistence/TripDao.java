package com.sdi.persistence;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface TripDao   {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate) ;
	
	Trip findById(Long id);
	
	void delete(Long id) throws NotPersistedException;

	void save(Trip dto) throws AlreadyPersistedException;

	void update(Trip dto) throws NotPersistedException;

	List<Trip> getTrips();

	List<Trip> findByPromoterId(Long id);
	
}
