package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.applications.Delete;
import com.sdi.business.impl.classes.applications.FindById;
import com.sdi.business.impl.classes.applications.FindByTrip;
import com.sdi.business.impl.classes.applications.FindByUser;
import com.sdi.business.impl.classes.applications.GetApplications;
import com.sdi.business.impl.classes.applications.Save;
import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@Stateless
public class EjbApplicationService implements LocalApplicationService, RemoteApplicationService {

	@Override
	public void saveApplication(Application application) throws AlreadyPersistedException {
		new Save().save(application);
		
	}

	@Override
	public void deleteApplication(Long idUser, Long idTrip)
			 throws NotPersistedException{
		new Delete().delete(idUser, idTrip);
		
	}

	@Override
	public Application findById(Long userId, Long tripId) {
		return new FindById().findById(userId, tripId);
	}

	@Override
	public List<Application> findByTrip(Long tripId) {
		return new FindByTrip().findByTrip(tripId);
	}

	@Override
	public List<Application> findByUser(Long userId) {
		return new FindByUser().findByUser(userId);
	}

	@Override
	public List<Application> getApplications() {
		return new GetApplications().getAll();
	}
	
	

}
