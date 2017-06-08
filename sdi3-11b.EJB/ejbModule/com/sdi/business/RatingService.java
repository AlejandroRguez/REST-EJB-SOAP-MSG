package com.sdi.business;

import java.util.List;

import com.sdi.model.Rating;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface RatingService {
	
	
	void saveRating(Rating rating) throws AlreadyPersistedException;
	
	void deleteRating(Long id) throws NotPersistedException;
	
	Rating findById(Long id);
	
	Rating findByAboutForm(Long aboutUserId,Long aboutTripId,Long fromUserId, 
			Long fromTripId);
	
	void updateRating(Rating rating) throws NotPersistedException;
	
	List<Rating> getRatings();
	
	List<Rating> findByTripId(Long id);

}
