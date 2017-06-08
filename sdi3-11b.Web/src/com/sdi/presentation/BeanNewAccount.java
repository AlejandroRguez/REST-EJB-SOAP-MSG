package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alb.util.log.Log;

import com.sdi.business.UserService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.persistence.exception.AlreadyPersistedException;
//import alb.util.log.Log;
import javax.faces.context.FacesContext;

@ManagedBean(name = "registro")
@SessionScoped
public class BeanNewAccount extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, surname, email, login, password;

	public BeanNewAccount() {

	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String createNewAccount() {
		String resultado = "exito";
		UserService service = Factories.services.getUsersService();
		User user = new User();
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setPassword(password);
		user.setStatus(UserStatus.ACTIVE);
		user.setSurname(surname);
		if(verificacion(login)){
			try {
				service.saveUser(user);
				Log.info("Usuario registrado");
			} catch (AlreadyPersistedException e) {
				Log.error("Login repetido, no se puede crear el usuario");
			}
		}
		else{
			Log.error("Login repetido, no se puede crear el usuario");
			resultado = "error";
		}
		return resultado;
		
	}
	
	public boolean verificacion(String login){
		User user = Factories.services.getUsersService().findByLogin(login);
		if(user == null){
			return true;
		}
		else return false;
	}
	
	
	public String initializeUser(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		setLogin(bundle.getString("valorDefectoLogin"));
		setName(bundle.getString("valorDefectoNombre"));
		setSurname(bundle.getString("valorDefectoApellidos"));
		setEmail(bundle.getString("valorDefectoCorreo"));
		return "exito";

	}

	
}
