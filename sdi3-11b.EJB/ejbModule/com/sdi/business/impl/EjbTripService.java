package com.sdi.business.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.trips.Delete;
import com.sdi.business.impl.classes.trips.FindById;
import com.sdi.business.impl.classes.trips.FindByPromoterAndArrivalDate;
import com.sdi.business.impl.classes.trips.FindByPromoterId;
import com.sdi.business.impl.classes.trips.GetTrips;
import com.sdi.business.impl.classes.trips.Save;
import com.sdi.business.impl.classes.trips.Update;
import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@Stateless
public class EjbTripService implements LocalTripService, RemoteTripService {

	@Override
	public void saveTrip(Trip trip) throws AlreadyPersistedException {
		new Save().save(trip);
		
	}

	@Override
	public void deleteTrip(Long id) throws NotPersistedException {
		new Delete().delete(id);
		
	}

	@Override
	public Trip findById(Long id) {
		return new FindById().findById(id);
	}

	@Override
	public Trip findByPromoterAndArrivalDate(Long id, Date arrivalDate){
		return new FindByPromoterAndArrivalDate().findByPromoterAndArrivalDate
				(id, arrivalDate);
	}

	@Override
	public void updateTrip(Trip trip) throws NotPersistedException {
		new Update().update(trip);
		
	}

	@Override
	public List<Trip> getTrips() {
		return new GetTrips().getAll();
	}

	@Override
	public List<Trip> findByPromoterId(Long id) {
		return new FindByPromoterId().find(id);
	}

	
	

}
