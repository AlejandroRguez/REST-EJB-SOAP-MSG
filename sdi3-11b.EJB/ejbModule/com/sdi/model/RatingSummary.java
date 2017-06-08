package com.sdi.model;

import java.io.Serializable;
import java.util.Date;


public class RatingSummary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String destino;
	private String fromUserName;
	private String aboutUserName;
	private int valoracion;
	private String comentario;
	private Date fechaViaje;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getAboutUserName() {
		return aboutUserName;
	}
	public void setAboutUserName(String aboutUserName) {
		this.aboutUserName = aboutUserName;
	}
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getFechaViaje() {
		return fechaViaje;
	}
	public void setFechaViaje(Date fechaViaje) {
		this.fechaViaje = fechaViaje;
	}
	
	

}
