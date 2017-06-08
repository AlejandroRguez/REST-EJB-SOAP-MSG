package com.sdi.presentation;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;
//import alb.util.log.Log;
import com.sdi.business.UserService;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultado;
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
	public String verify() throws NotPersistedException{
		UserService userService = Factories.services.getUsersService();
		setUser(userService.findByLoginAndPassword(getLogin(), getPassword()));
		if(getUser() != null){
			setResultado("exito");
			userInSession();
			Log.info("Sesión iniciada con éxito");
		}
		else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = facesContext.getApplication().getResourceBundle(
					facesContext, "msgs");
			facesContext.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
							bundle.getString("credencialesIncorrectas")));
			setResultado("");
			Log.error("Credenciales introducidas incorrectas");
		}
		return resultado;
	}
	
	private void userInSession() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.put("LOGGEDIN_USER", getUser());
	}
	

	
	public String closeSession(){
		Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap();
		session.remove("LOGGEDIN_USER");
		return "exito";
		
	}
	
	
	
	
	

	

}
