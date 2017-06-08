package com.sdi.business;

import java.util.List;

import com.sdi.model.RatingSummary;
import com.sdi.model.UserInfo;

public interface AdminService {
	
	List<UserInfo> getUserInfo();
	
	void disableUser (Long userId);
	
	List<RatingSummary> getLastMonthRatings();
	
	void deleteRating (Long ratingId);

}
