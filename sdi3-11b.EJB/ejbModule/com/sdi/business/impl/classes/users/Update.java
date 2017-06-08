package com.sdi.business.impl.classes.users;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;

public class Update {

	public void update(User user) {
		try {
			Factories.persistence.newUserDao().update(user);
		} catch (NotPersistedException e) {
			Log.error("No existe el usuario");
		}
		
	}

}
