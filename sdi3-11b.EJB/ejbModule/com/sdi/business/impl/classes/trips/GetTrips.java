package com.sdi.business.impl.classes.trips;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class GetTrips {

	public List<Trip> getAll() {
		List<Trip> trips = Factories.persistence.newTripDao().getTrips();
		if(trips.isEmpty()){
			Log.error("No hay viajes");
			return new ArrayList<Trip>();
		}
		else return trips;
	}

}
