package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Seat;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface SeatDao  {

	Seat findByUserAndTrip(Long userId, Long tripId) ;
	
	List<Seat> findByTrip(Long tripId);
	
	void save(Seat seat) throws AlreadyPersistedException;
	
	void update(Seat seat) throws NotPersistedException;
	
	void delete(Long trip, Long user) throws NotPersistedException;
	
	List<Seat> getSeats();
	
	List<Seat> findSeatsByUser(Long id);

}
