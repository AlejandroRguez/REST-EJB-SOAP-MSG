package com.sdi.business.impl.classes.applications;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;

public class FindById {

	public Application findById(Long userId, Long tripId) {
		return (Application) (Check.check(Factories.persistence
				.newApplicationDao().findById
				(userId, tripId),"No existe la solicitud"));
		}

}
