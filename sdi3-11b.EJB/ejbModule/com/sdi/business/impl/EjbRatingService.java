package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.ratings.Delete;
import com.sdi.business.impl.classes.ratings.FindByAboutForm;
import com.sdi.business.impl.classes.ratings.FindById;
import com.sdi.business.impl.classes.ratings.FindByTripId;
import com.sdi.business.impl.classes.ratings.GetRatings;
import com.sdi.business.impl.classes.ratings.Save;
import com.sdi.business.impl.classes.ratings.Update;
import com.sdi.model.Rating;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@Stateless
public class EjbRatingService implements RemoteRatingService, LocalRatingService {

	@Override
	public void saveRating(Rating rating)throws AlreadyPersistedException {
		new Save().save(rating);
		
	}

	@Override
	public void deleteRating(Long id) throws NotPersistedException {
		new Delete().delete(id);
		
	}

	@Override
	public Rating findById(Long id) {
		return new FindById().findById(id);
		
	}

	@Override
	public Rating findByAboutForm(Long aboutUserId, Long aboutTripId,
			Long fromUserId, Long fromTripId) {
		return new FindByAboutForm().findByAboutForm(aboutUserId, aboutTripId,
				fromUserId, fromTripId);
	}

	@Override
	public void updateRating(Rating rating) throws NotPersistedException {
		new Update().update(rating);
		
	}

	@Override
	public List<Rating> getRatings() {
		return new GetRatings().getAll();
	}

	@Override
	public List<Rating> findByTripId(Long id) {
		return new FindByTripId().find(id);
	}

	
	

}
