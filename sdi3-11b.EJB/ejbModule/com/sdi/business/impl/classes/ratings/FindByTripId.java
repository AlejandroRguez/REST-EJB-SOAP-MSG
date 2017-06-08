package com.sdi.business.impl.classes.ratings;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;

public class FindByTripId {

	public List<Rating> find(Long id) {
		return Factories.persistence.newRatingDao().findByTripId(id);
	}

}
