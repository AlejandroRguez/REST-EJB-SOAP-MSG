package com.sdi.business.impl.classes.seats;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.exception.NotPersistedException;

public class Update {

	public void update(Seat seat) {
		try {
			Factories.persistence.newSeatDao().update(seat);
		} catch (NotPersistedException e) {
			Log.error("No existe la plaza");
		}
		
	}

}
