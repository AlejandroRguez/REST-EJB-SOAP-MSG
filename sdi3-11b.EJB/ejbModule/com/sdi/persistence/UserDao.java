package com.sdi.persistence;


import java.util.List;

import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface UserDao  {
	
	void delete(Long id) throws NotPersistedException;

	List<User> getUsers();

	User findByLogin(String login);

	User findByLoginAndPassword(String login, String password);

	void update(User a) throws NotPersistedException;

	void save(User a) throws AlreadyPersistedException;

	User findById(Long id);

}
