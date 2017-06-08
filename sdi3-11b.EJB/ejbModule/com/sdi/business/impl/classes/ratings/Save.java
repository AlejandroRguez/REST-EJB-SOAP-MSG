package com.sdi.business.impl.classes.ratings;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(Rating rating) {
		try {
			Factories.persistence.newRatingDao().save(rating);
		} catch (AlreadyPersistedException e) {
			Log.error("Ya existe el comentario");
		}
		
	}

}
