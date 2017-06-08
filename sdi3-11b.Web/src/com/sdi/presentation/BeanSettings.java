package com.sdi.presentation;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import alb.util.log.Log;

//import alb.util.log.Log;




@ManagedBean(name="settings")
@SessionScoped
public class BeanSettings implements Serializable{
	private static final long serialVersionUID = 2L;           
	private static final Locale ENGLISH = new Locale("en");
	private static final Locale SPANISH = new Locale("es");   
	private static final Locale FRENCH = new Locale("fr"); 
	private static final Locale PORTUGUESE = new Locale("pt"); 
	private Locale locale = new Locale("es"); 
	//uso de inyección de dependencia
	@ManagedProperty(value="#{login}") 
	private BeanLogin user;
	public BeanLogin getUser() { return user; }
	public void setUser(BeanLogin user) {this.user = user;}
	public Locale getLocale() { return(locale);} 
	public void setLocale(Locale locale) {this.locale = locale;	}
	//Se inicia correctamente el Managed Bean inyectado si JSF lo hubiera creado
	//y en caso contrario se crea. 
	//(hay que tener en cuenta que es un Bean de sesión)
	
	//Se usa @PostConstruct, ya que en el contructor no se sabe todavía si 
	//el MBean ya estaba construido y en @PostConstruct SI.
	@PostConstruct
	public void init() {
		System.out.println("BeanSettings - PostConstruct");
		//Buscamos el alumno en la sesión. Esto es un patrón factoría claramente.
		user = 
				(BeanLogin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(new String("user"));

		//si no existe lo creamos e inicializamos
		if (user == null) { 
			//Log.debug("BeanSettings - No existia");
			user = new BeanLogin();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "user", user);
		} 
	}
	//Es sólo a modo de traza.
	@PreDestroy
	public void end()
	{
		Log.debug("BeanSettings - PreDestroy");
	}
	
	public void setSpanish(ActionEvent event) {
		locale = SPANISH;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			
		} catch (Exception ex) {
			Log.error("No se puede cambiar el idioma a español");
		}
	}

	public void setEnglish(ActionEvent event) {
		locale = ENGLISH;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			
		} catch (Exception ex) {
			Log.error("No se puede cambiar el idioma a inglés");
		}
	}
	
	public void setFrench(ActionEvent event) {
		locale = FRENCH;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			
		} catch (Exception ex) {
			Log.error("No se puede cambiar el idioma a francés");
		}
	}

	public void setPortuguese(ActionEvent event) {
		locale = PORTUGUESE;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			
		} catch (Exception ex) {
			Log.error("No se puede cambiar el idioma a portugués");
		}
	}


	
	
	
}
