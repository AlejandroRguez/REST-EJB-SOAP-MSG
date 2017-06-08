package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface ApplicationDao {
	
	
	List<Application> findByUserId( Long userId );
	
	List<Application> findByTripId( Long tripId );
	
	List<Application> getApplications();
	
	void delete(Long idUser, Long idTrip) throws NotPersistedException;
	
	Application findById(Long idUser, Long idTrip);
	
	void save(Application app) throws AlreadyPersistedException;
	
}
