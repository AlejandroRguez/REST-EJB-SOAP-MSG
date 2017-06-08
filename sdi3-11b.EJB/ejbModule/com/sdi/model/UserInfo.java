package com.sdi.model;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String apellido;
	private String email;
	private List<Trip> viajesPromovidos;
	private List<Trip> participaciones;
	private UserStatus status;
	private String login;

	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login){
		this.login = login;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Trip> getViajesPromovidos() {
		return viajesPromovidos;
	}
	public void setViajesPromovidos(List<Trip> viajesPromovidos) {
		this.viajesPromovidos = viajesPromovidos;
	}
	public List<Trip> getParticipaciones() {
		return participaciones;
	}
	public void setParticipaciones(List<Trip> participaciones) {
		this.participaciones = participaciones;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	
	

}
