package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Rating;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface RatingDao  {

	Rating findByAboutFrom(Long aboutUserId,Long aboutTripId,Long fromUserId, Long fromTripId);

	void save(Rating rating) throws AlreadyPersistedException;

	void update(Rating rating) throws NotPersistedException;

	void delete(Long id) throws NotPersistedException;

	List<Rating> getRatings();

	Rating findById(Long id);
	
	List<Rating> findByTripId(Long id);
	
	
}
