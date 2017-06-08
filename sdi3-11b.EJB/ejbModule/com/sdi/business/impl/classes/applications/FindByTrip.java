package com.sdi.business.impl.classes.applications;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;

public class FindByTrip {

	public List<Application> findByTrip(Long tripId) {
		List<Application> apps = Factories.persistence.
				newApplicationDao().
				findByTripId(tripId);
		 return apps;
	}

}
