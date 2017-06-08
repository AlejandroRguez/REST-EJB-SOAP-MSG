package com.sdi.business.impl.classes.trips;

import java.util.Date;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class FindByPromoterAndArrivalDate {

	public Trip findByPromoterAndArrivalDate(Long id, Date arrivalDate) {
		return (Trip) Check.check(Factories.persistence.newTripDao().findByPromoterIdAndArrivalDate
				(id, arrivalDate),"No existe el viaje");
	}

}
