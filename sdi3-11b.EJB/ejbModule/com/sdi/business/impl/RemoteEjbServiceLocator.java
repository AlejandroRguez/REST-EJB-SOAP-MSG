package com.sdi.business.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sdi.business.AdminService;
import com.sdi.business.ApplicationService;
import com.sdi.business.RatingService;
import com.sdi.business.SeatService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.UserService;
import com.sdi.business.PromoterUserService;

public class RemoteEjbServiceLocator implements ServicesFactory {

	private static final String SEATS_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbSeatService!com.sdi.business.impl.RemoteSeatService";
	private static final String USERS_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbUserService!com.sdi.business.impl.RemoteUserService";
	private static final String TRIPS_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbTripService!com.sdi.business.impl.RemoteTripService";
	private static final String RATINGS_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbRatingService!com.sdi.business.impl.RemoteRatingService";
	private static final String APPLICATIONS_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbApplicationService!com.sdi.business.impl.RemoteApplicationService";
	private static final String ADMIN_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbAdminService!com.sdi.business.impl.RemoteAdminService";
	private static final String PROMOTER_USER_SERVICE_JNDI_KEY = "sdi3-11b/sdi3-11b.EJB/EjbPromoterUserService!com.sdi.business.impl.RemotePromoterUserService";

	@Override
	public SeatService getSeatsService() {
		try {
			Context ctx = new InitialContext();
			return (SeatService) ctx.lookup(SEATS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public ApplicationService getApplicationsService() {
		try {
			Context ctx = new InitialContext();
			return (ApplicationService) ctx.lookup(APPLICATIONS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public UserService getUsersService() {
		try {
			Context ctx = new InitialContext();
			return (UserService) ctx.lookup(USERS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public RatingService getRatingService() {
		try {
			Context ctx = new InitialContext();
			return (RatingService) ctx.lookup(RATINGS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public TripService getTripsService() {
		try {
			Context ctx = new InitialContext();
			return (TripService) ctx.lookup(TRIPS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public AdminService getAdminService() {
		try {
			Context ctx = new InitialContext();
			return (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
	
	@Override
	public PromoterUserService getPromoterUserService() {
		try {
			Context ctx = new InitialContext();
			return (PromoterUserService) ctx.lookup(PROMOTER_USER_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
}



