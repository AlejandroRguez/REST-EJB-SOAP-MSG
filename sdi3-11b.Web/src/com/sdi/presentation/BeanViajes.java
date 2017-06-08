package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.SeatService;
import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.persistence.exception.NotPersistedException;
//import alb.util.log.Log;
import com.sdi.business.ApplicationService;


@ManagedBean(name = "viajes")
@SessionScoped
public class BeanViajes extends Trip implements Serializable {
	private static final long serialVersionUID = 55556L;

	public BeanViajes() {

	}
	
	private User user;
	private User promotor;
	private List<User> participantes = new ArrayList<User>();
	private List<Trip> viajes = new ArrayList<Trip>();
	private List<Trip> viajesConfirmados = new ArrayList<Trip>();

	
	public User getPromotor() {
		return promotor;
	}

	public void setPromotor(User promotor) {
		this.promotor = promotor;
	}

	public List<Trip> getViajes() {
		return viajes;
	}

	public void setViajes(List<Trip> viajes) {
		this.viajes = viajes;
	}
	
	
	
	public List<Trip> getViajesConfirmados() {
		return viajesConfirmados;
	}

	public void setViajesConfirmados(List<Trip> viajesConfirmados) {
		this.viajesConfirmados = viajesConfirmados;
	}
	
	@PostConstruct
	public void init() {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "viajes", this); 
	}

	
	public User getUser() {
		user = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String verParticipantes(Trip trip) throws NotPersistedException{
		String resultado = "exito";
		List<Seat> seats = Factories.services.getSeatsService().findByTrip(trip.getId());
		List<User> users = new ArrayList<User>();
		for (Seat s : seats){
			User u = Factories.services.getUsersService().findById(s.getUserId());
			users.add(u);
		}
		Log.debug("Cargando el listado de participantes para el viaje seleccionado");
		setParticipantes(users);
		return resultado;
		
	}
	
	public void controlarEstadoViajes() throws NotPersistedException{
		TripService ts = Factories.services.getTripsService();
		List<Trip> trips = ts.getTrips();
		for (Trip t : trips){
			if(t.getClosingDate().before(new Date())){
				t.setStatus(TripStatus.CLOSED);
				ts.updateTrip(t);
			}
			if(t.getDepartureDate().before(new Date())){
				t.setStatus(TripStatus.DONE);
				ts.updateTrip(t);
			}
		}
	}

	public String disponibles() throws NotPersistedException {
		controlarEstadoViajes();
		SeatService ss =  Factories.services.getSeatsService();
		ApplicationService as =  Factories.services.getApplicationsService();
		List<Trip> trips = Factories.services.getTripsService().getTrips();
		List<Trip> disponibles = new ArrayList<Trip>();
		for(Trip t : trips){
			if(!t.getPromoterId().equals(getUser().getId()) && t.getStatus().ordinal() == (TripStatus.OPEN.ordinal()) && t.getAvailablePax() > 0){
				Seat s =ss.findByUserAndTrip(getUser().getId(), t.getId());
				Application a = as.findById(getUser().getId(), t.getId());
				if (s == null && a == null){
					disponibles.add(t);
			}
		}
	}
		Log.debug("Cargando el listado de viajes disponibles para el usuario actual");
		setViajes(disponibles);
		return "exito";
	}
	
	public String disponiblesPublico() throws NotPersistedException {
		controlarEstadoViajes();
		List<Trip> trips = Factories.services.getTripsService().getTrips();
		List<Trip> disponibles = new ArrayList<Trip>();
		for(Trip t : trips){
			if(t.getAvailablePax() > 0 && t.getStatus().equals(TripStatus.OPEN)){
				disponibles.add(t);
			}
		}
		Log.debug("Cargando el listado de viajes disponibles");
		setViajes(disponibles);
		return "exito";
	}

	
	
	public String promotor() {
		List<Trip> trips = Factories.services.getTripsService().getTrips();
		List<Trip> disponibles = new ArrayList<Trip>();
		List<Trip> hechos = new ArrayList<Trip>();
		List<Trip> porHacer = new ArrayList<Trip>();
		for(Trip t : trips){
			if(t.getPromoterId().equals(getUser().getId())){
				disponibles.add(t);
			}
		}
		for(Trip t : disponibles){
			if(t.getStatus().equals(TripStatus.OPEN)){
				porHacer.add(t);
			}

			else	hechos.add(t);
		}
		Log.debug("Cargando el listado de viajes promovidos por el usuario actual");
		setViajes(porHacer);
		setViajesConfirmados(hechos);
		return "exito";
	}
	
	public String misSolicitudes() {
		List<Trip> disponibles = new ArrayList<Trip>();
		List<Trip> confirmados = new ArrayList<Trip>();
		List<Application> applications = Factories.services.getApplicationsService().getApplications();
		List<Seat> seats = Factories.services.getSeatsService().getSeats();
		TripService ts = Factories.services.getTripsService();
		for(Seat s : seats){
			if(s.getUserId().equals(getUser().getId())){
				Trip t = ts.findById(s.getTripId()); 
				confirmados.add(t);
			}
		}
		for(Application a : applications){
			if(a.getUserId().equals(getUser().getId())){
				Trip t = ts.findById(a.getTripId()); 
				disponibles.add(t);
			}
		}
		Log.debug("Cargando las solicitudes del usuario actual");
		setViajes(disponibles);
		setViajesConfirmados(confirmados);
		return "exito";
	}

	
	public String verPromotor(Trip trip){
		String resultado = "exito";
		User user = null;
		try {
			user = Factories.services.getUsersService().findById(trip.getPromoterId());
		} catch (NotPersistedException e) {
			//Log.error("No se ha encontrado el promotor");
			resultado = "error";
		}
		Log.debug("Cargando el promotor del viaje seleccionado");
		setPromotor(user);
		return resultado;
	}
	
	public SeatStatus getSeatStatus(Long tripId , Long userId){
		Seat s = Factories.services.getSeatsService().findByUserAndTrip(userId, tripId);
		return s.getStatus();
	}

	public List<User> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<User> participantes) {
		this.participantes = participantes;
	}
	

	
	
}
