package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.users.Delete;
import com.sdi.business.impl.classes.users.FindById;
import com.sdi.business.impl.classes.users.FindByLogin;
import com.sdi.business.impl.classes.users.FindByLoginAndPassword;
import com.sdi.business.impl.classes.users.GetUsers;
import com.sdi.business.impl.classes.users.Save;
import com.sdi.business.impl.classes.users.Update;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@Stateless
public class EjbUserService implements LocalUserService, RemoteUserService {

	@Override
	public void saveUser(User user) throws AlreadyPersistedException {
		new Save().save(user);
		
	}

	@Override
	public void deleteUser(Long id) throws NotPersistedException {
		new Delete().delete(id);
		
	}

	@Override
	public User findById(Long id) throws NotPersistedException {
		return new FindById().findById(id);
	}

	@Override
	public User findByLogin(String login)  {
		return new FindByLogin().findByLogin(login);
	}

	@Override
	public void uptadeUser(User user) throws NotPersistedException {
		new Update().update(user);
		
	}

	@Override
	public List<User> getUsers() {
		return new GetUsers().getAll();
	}
	
	@Override
	public User findByLoginAndPassword(String login, String password)  {
		return new FindByLoginAndPassword().findByLoginAndPassword(login, password);
	}

	


	

	
	

}
