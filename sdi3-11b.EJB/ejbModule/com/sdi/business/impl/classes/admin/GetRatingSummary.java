package com.sdi.business.impl.classes.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;
import com.sdi.model.RatingSummary;
import com.sdi.model.Trip;
import com.sdi.persistence.RatingDao;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.UserDao;

public class GetRatingSummary {
	
	RatingDao ratingDao = Factories.persistence.newRatingDao();
	UserDao userDao = Factories.persistence.newUserDao();
	TripDao tripDao = Factories.persistence.newTripDao();

	public List<RatingSummary> getLastMonthRatings() {
		List<RatingSummary> lastMonthRatings = new ArrayList<RatingSummary>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Date fecha = calendar.getTime();
		List<Trip> trips = new ArrayList<Trip>();
		for(Trip t : tripDao.getTrips()){
			if(t.getDepartureDate().after(fecha) &&
					t.getDepartureDate().before(new Date())){
				trips.add(t);
			}
		}
		
		for(Trip t : trips){
			for(Rating r : ratingDao.findByTripId(t.getId())){
				RatingSummary rs = new RatingSummary();
				rs.setAboutUserName(userDao.findById(r.getSeatAboutUserId()).getName());
				rs.setFromUserName(userDao.findById(r.getSeatFromUserId()).getName());
				rs.setId(r.getId());
				rs.setComentario(r.getComment());
				rs.setDestino(t.getDestination().getCity());
				rs.setValoracion(r.getValue());
				rs.setFechaViaje(t.getDepartureDate());
				lastMonthRatings.add(rs);
			}
		}
		Log.info("Obteniendo comentarios del último mes");
		return lastMonthRatings;
	}

}
