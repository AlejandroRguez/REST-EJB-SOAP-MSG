package com.sdi.business.impl.classes.users;

import com.sdi.business.util.Check;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class FindById {

	public User findById(Long id) {
		return (User) Check.check(Factories.persistence.newUserDao().findById(id),
				"No existe el usuario");
	}

}
