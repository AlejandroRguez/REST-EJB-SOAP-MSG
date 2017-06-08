package com.sdi.business.impl.classes.applications;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.exception.NotPersistedException;

public class Delete {

	public void delete(Long idUser , Long idTrip){
		ApplicationDao dao = Factories.persistence.newApplicationDao();
			try {
				dao.delete(idUser, idTrip);
			} catch (NotPersistedException e) {
				Log.error("No existe la solicitud");
			}		
	}

}
