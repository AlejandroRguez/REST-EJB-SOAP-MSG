package com.sdi.business.impl.classes.users;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class FindByLogin {

	public User findByLogin(String login) {
		return (User) Check.check(Factories.persistence.newUserDao().findByLogin(login),
				"No existe el usuario");	
		}

}
