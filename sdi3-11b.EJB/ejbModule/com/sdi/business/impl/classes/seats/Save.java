package com.sdi.business.impl.classes.seats;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(Seat seat) {
		Trip t = Factories.persistence.newTripDao().findById(seat.getTripId());
		try {
			if(t.getAvailablePax() > 0){
				Factories.persistence.newSeatDao().save(seat);
				t.setAvailablePax(t.getAvailablePax() - 1);
				Log.info("Solicitud confirmada");
			}
			else Log.error("El viaje no tiene plazas");
		} catch (AlreadyPersistedException e) {
			Log.error("Ya existe la plaza");
		}
		
	}

}
