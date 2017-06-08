package com.sdi.business.impl.classes;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;

public class CreateSeatByApplication {

	public Seat create(Long userId, Long tripId) {
		Application app = Factories.persistence.newApplicationDao().findById(userId, tripId);
		Trip t = Factories.persistence.newTripDao().findById(tripId);
		if(t.getAvailablePax() > 0){
			Seat seat = new Seat();
			seat.setTripId(app.getTripId());
			seat.setUserId(app.getUserId());
			seat.setStatus(SeatStatus.ACCEPTED);
			return seat;
		}
		else{
			
			Log.error("No quedan plazas");
			return null;
		}
	}
	
}
