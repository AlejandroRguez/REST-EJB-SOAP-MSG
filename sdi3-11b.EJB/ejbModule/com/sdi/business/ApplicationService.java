package com.sdi.business;

import java.util.List;

import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface ApplicationService {
	
	
	void saveApplication(Application application) throws AlreadyPersistedException;
	
	void deleteApplication(Long idUser, Long idTrip) throws NotPersistedException;
	
	Application findById(Long userId, Long tripId);
	
	List<Application> findByTrip(Long tripId);
	
	List<Application> findByUser(Long userId);
	
	List<Application> getApplications();
	

}
