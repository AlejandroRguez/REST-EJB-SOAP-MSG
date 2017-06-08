package com.sdi.business.impl.classes.trips;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class FindByPromoterId {

	public List<Trip> find(Long id) {
		List<Trip> trips = Factories.persistence.newTripDao().findByPromoterId(id);
		return trips;
	}

}
