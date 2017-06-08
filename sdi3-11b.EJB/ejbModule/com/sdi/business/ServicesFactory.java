package com.sdi.business;

public interface ServicesFactory {
		
	SeatService getSeatsService();
	
	ApplicationService getApplicationsService();
	
	UserService getUsersService();
	
	RatingService getRatingService();
	
	TripService getTripsService();
	
	AdminService getAdminService();
	
	PromoterUserService getPromoterUserService();

}
