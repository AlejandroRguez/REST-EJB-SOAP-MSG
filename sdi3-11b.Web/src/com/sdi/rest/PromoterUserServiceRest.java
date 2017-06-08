package com.sdi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sdi.business.exception.BusinessException;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;

@Path("/PromoterServiceRs")
public interface PromoterUserServiceRest {
	
	@GET
	@Path("verify")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findByLoginAndPassword(@QueryParam("login") String login,
			@QueryParam("password") String password);
	
	@GET
	@Path("trips/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Trip> findByPromoterId(@HeaderParam("authorization") String auth,
	@PathParam("id") Long id);
	
	@GET
	@Path("applications/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Application> findByTripId(@HeaderParam("authorization") String auth,
			@PathParam("id") Long id);
	
	@GET
	@Path("seat")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Seat createSeatByApplication(@HeaderParam("authorization") String auth,
			@QueryParam("userId") Long userId,
			@QueryParam("tripId") Long tripId);
	
	@DELETE
	@Path("delete")
	void deleteApplication(@HeaderParam("authorization") String auth, 
			@QueryParam("userId") Long userId,
			@QueryParam("tripId") Long tripId);
	
	
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void saveSeat(@HeaderParam("authorization") String auth,  Seat seat)
	throws BusinessException;
	
	
}
