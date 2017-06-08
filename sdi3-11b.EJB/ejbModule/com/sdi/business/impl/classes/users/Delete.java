package com.sdi.business.impl.classes.users;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.exception.NotPersistedException;

public class Delete {

	public void delete(Long id) {
		try {
			Factories.persistence.newUserDao().delete(id);
		} catch (NotPersistedException e) {
			Log.error("No existe el usuario");
		}
		
	}

}
