package com.sdi.business.impl.classes.trips;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class FindById {

	public Trip findById(Long id) {
		return (Trip) Check.check(Factories.persistence.newTripDao().
				findById(id),"No existe el viaje");
	}

}
