package com.sdi.business.impl.classes.seats;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;

public class FindByTrip {
	
	public List<Seat> findByTrip(Long tripId) {
		List<Seat> seats = Factories.persistence.newSeatDao().findByTrip(tripId);
		if(seats.isEmpty()){
			Log.error("No hay plazas");
			return new ArrayList<Seat>();
		}
		else return seats;
	}

}
