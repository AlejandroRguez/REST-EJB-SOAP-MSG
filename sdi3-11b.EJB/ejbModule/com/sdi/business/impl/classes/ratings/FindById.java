package com.sdi.business.impl.classes.ratings;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;

public class FindById {

	public Rating findById(Long id) {
		return (Rating) Check.check(Factories.persistence.newRatingDao().findById(id)
				, "No existe el comentario");
	}

}
