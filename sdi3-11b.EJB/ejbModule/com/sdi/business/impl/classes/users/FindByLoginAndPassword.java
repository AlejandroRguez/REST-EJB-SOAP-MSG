package com.sdi.business.impl.classes.users;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class FindByLoginAndPassword {

	public User findByLoginAndPassword(String login, String password) {
		Log.info("Verificando credenciales");
		return Factories.persistence.newUserDao().
				findByLoginAndPassword(login, password);
	}

}
