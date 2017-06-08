package com.sdi.business;

import java.util.List;

import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface UserService {
	
	
	void saveUser(User user) throws AlreadyPersistedException;
	
	void deleteUser(Long id) throws NotPersistedException;
	
	User findById(Long id) throws NotPersistedException;
	
	User findByLogin(String login) ;
	
	void uptadeUser(User user) throws NotPersistedException;
	
	List<User> getUsers();
	 
	User findByLoginAndPassword(String login, String password);

		
	

}
