package com.sdi.business.impl.classes.ratings;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;

public class FindByAboutForm {

	public Rating findByAboutForm(Long aboutUserId, Long aboutTripId,
			Long fromUserId, Long fromTripId) {
		return (Rating) Check.check(Factories.persistence.newRatingDao()
				.findByAboutFrom
		(aboutUserId, aboutTripId, fromUserId, fromTripId),
		"No existe el comentario");
	}

}
