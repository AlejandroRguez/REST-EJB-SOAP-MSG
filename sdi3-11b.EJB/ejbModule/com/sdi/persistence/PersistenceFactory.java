package com.sdi.persistence;



public interface PersistenceFactory  {
	
	 RatingDao newRatingDao(); 

	 UserDao newUserDao();

	 TripDao newTripDao();

	 SeatDao newSeatDao();

	 ApplicationDao newApplicationDao();

}
