package com.sdi.business.impl.classes.users;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class GetUsers {

	public List<User> getAll() {
		List<User> users = Factories.persistence.newUserDao().getUsers();
		if(users.isEmpty()){
			Log.error("No hay usuarios");
			return new ArrayList<User>();
		}
		else return users;
	}

}
