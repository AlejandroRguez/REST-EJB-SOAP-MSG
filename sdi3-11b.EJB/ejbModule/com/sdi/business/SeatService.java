package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface SeatService {
	
	
	void saveSeat(Seat seat) throws AlreadyPersistedException;
	
	void deleteSeat(Long idUser, Long idTrip) throws NotPersistedException;
	
	Seat findByUserAndTrip(Long userId , Long tripId);
	
	List<Seat> findByTrip(Long tripId);
	
	void updateSeat(Seat seat) throws NotPersistedException;
	
	List<Seat> getSeats();
	
	List<Seat> findSeatsByUser(Long id);

}
