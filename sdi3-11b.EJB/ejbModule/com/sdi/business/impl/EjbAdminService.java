package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.impl.classes.admin.DisableUser;
import com.sdi.business.impl.classes.admin.GetRatingSummary;
import com.sdi.business.impl.classes.admin.GetUserInfo;
import com.sdi.business.impl.classes.ratings.Delete;
import com.sdi.model.RatingSummary;
import com.sdi.model.UserInfo;

@Stateless
@WebService(name="AdminService")
public class EjbAdminService implements LocalAdminService, RemoteAdminService {

	@Override
	public List<UserInfo> getUserInfo() {
		return new GetUserInfo().getUserInfo();
	}

	@Override
	public void disableUser(Long userId) {
		new DisableUser().disable(userId);
		
	}

	@Override
	public List<RatingSummary> getLastMonthRatings() {
		return new GetRatingSummary().getLastMonthRatings();
	}

	@Override
	public void deleteRating(Long ratingId) {
		new Delete().delete(ratingId);
		
	}

}
