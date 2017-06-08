package com.sdi.business.impl.classes.ratings;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;

public class GetRatings {

	public List<Rating> getAll() {
		List<Rating> ratings = Factories.persistence.newRatingDao().getRatings();
		if(ratings.isEmpty()){
			Log.error("No hay comentarios");
			return new ArrayList<Rating>();
		}
		else return ratings;
	}

}
