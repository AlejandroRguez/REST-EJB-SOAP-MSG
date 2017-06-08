package com.sdi.business.impl.classes.trips;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.exception.NotPersistedException;

public class Update {

	public void update(Trip trip) {
		try {
			Factories.persistence.newTripDao().update(trip);
		} catch (NotPersistedException e) {
			Log.error("No existe el viaje");
		}
		
	}

}
