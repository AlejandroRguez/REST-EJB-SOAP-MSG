package com.sdi.business.impl.classes.trips;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(Trip trip) {
		
		try {
			Factories.persistence.newTripDao().save(trip);
		} catch (AlreadyPersistedException e) {
			Log.error("Ya existe el viaje");
		}
		
		
	}

}
