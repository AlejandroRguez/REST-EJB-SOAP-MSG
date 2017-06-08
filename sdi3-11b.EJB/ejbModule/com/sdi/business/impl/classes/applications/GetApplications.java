package com.sdi.business.impl.classes.applications;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;

public class GetApplications {

	public List<Application> getAll() {
		List<Application> apps = Factories.persistence.newApplicationDao().
				getApplications();
		if(apps.isEmpty()){
			Log.error("No hay solicitudes");
			return new ArrayList<Application>();
		}
		else return apps;
	}

}
