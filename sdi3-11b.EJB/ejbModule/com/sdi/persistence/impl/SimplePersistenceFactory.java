package com.sdi.persistence.impl;

import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.RatingDao;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.UserDao;

public class SimplePersistenceFactory implements PersistenceFactory {
	
	public  RatingDao newRatingDao() {
		return new RatingDaoJdbcImpl();
	}

	public  UserDao newUserDao() {
		return new UserDaoJdbcImpl();
	}

	public  TripDao newTripDao() {
		return new TripDaoJdbcImpl();
	}

	public  SeatDao newSeatDao() {
		return new SeatDaoJdbcImpl();
	}

	public  ApplicationDao newApplicationDao() {
		return new ApplicationDaoJdbcImpl();
	}

}
