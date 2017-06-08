package com.sdi.business.impl.classes.seats;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.exception.NotPersistedException;

public class Delete {

	public void delete(Long idUser , Long idTrip) {
		try {
			Factories.persistence.newSeatDao().delete(idUser, idTrip);
		} catch (NotPersistedException e) {
			Log.error("No existe la plaza");
		}
		
	}

}
