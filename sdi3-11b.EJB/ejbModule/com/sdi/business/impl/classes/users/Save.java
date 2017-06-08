package com.sdi.business.impl.classes.users;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(User user) {
		try {
			Factories.persistence.newUserDao().save(user);
		} catch (AlreadyPersistedException e) {
			Log.error("Ya existe el usuario");

		}
		
	}

}
