package com.sdi.business.impl.classes.ratings;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;
import com.sdi.persistence.exception.NotPersistedException;

public class Update {

	public void update(Rating rating) {
		try {
			Factories.persistence.newRatingDao().update(rating);
		} catch (NotPersistedException e) {
			Log.error("No existe el comentario");
		}
		
	}

}
