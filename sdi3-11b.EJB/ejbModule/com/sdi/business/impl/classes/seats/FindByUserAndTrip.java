package com.sdi.business.impl.classes.seats;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;

public class FindByUserAndTrip {

	public Seat findByUserAndTrip(Long userId, Long tripId) {
		return (Seat) Check.check(Factories.persistence.newSeatDao().
				findByUserAndTrip(userId, tripId),"No existe la plaza");
	}

}
