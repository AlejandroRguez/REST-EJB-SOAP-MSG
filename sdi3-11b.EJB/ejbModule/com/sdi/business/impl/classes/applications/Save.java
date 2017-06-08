package com.sdi.business.impl.classes.applications;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(Application application) {
		try {
			Factories.persistence.newApplicationDao().save(application);
		} catch (AlreadyPersistedException e) {
			Log.error("Ya existe la solicitud");
		}
	}

}
