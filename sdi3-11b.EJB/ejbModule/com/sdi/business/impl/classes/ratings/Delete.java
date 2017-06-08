package com.sdi.business.impl.classes.ratings;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.exception.NotPersistedException;

public class Delete {

	public void delete(Long id) {
		try {
			Factories.persistence.newRatingDao().delete(id);
			Log.info("Comentario eliminado");
		} catch (NotPersistedException e) {
			Log.error("No existe el comentario");
		}
		
	}

}
