package com.sdi.presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

//import alb.util.log.Log;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;


@ManagedBean(name = "applications")
@SessionScoped
public class BeanApplications {

	private Long userId;
	private List<Application> applications = new ArrayList<Application>();
	private List<Seat> seats = new ArrayList<Seat>();
	private Trip trip;
	
	@ManagedProperty("#{viajes}")
	private BeanViajes viajes;
	
	public BeanViajes getViajes(){
		return viajes;
	}
	
	public void setViajes (BeanViajes viajes){
		this.viajes = viajes;
	}
	

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public BeanApplications() {
	}
	
	@PostConstruct
	public void init() {
		
		viajes  = 
				(BeanViajes)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(new String("viajes"));
	}

	public Long getUserId() {
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		userId = u.getId();
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	/*
	 * public Long getTripId() { return tripId; }
	 * 
	 * public void setTripId(Long tripId) { this.tripId = tripId; }
	 */
	// El usuario solicita plaza en un viaje
	
	public String cargarSolicitudes(Trip trip){
		String resultado = "exito";
		setTrip(trip);
		setApplications(Factories.services.getApplicationsService().findByTrip(getTrip().getId()));
		setSeats(Factories.services.getSeatsService().findByTrip(getTrip().getId()));

		return resultado;
	}
	
	public String getNombreBySeat(Seat seat){
		User user = null;
		try {
			user = Factories.services.getUsersService().findById(seat.getUserId());
		} catch (NotPersistedException e) {
			Log.error("No se ha encontrado al usuario");
		}
		return user.getName();
	}
	
	public String getNombreByApplication(Application app){
		User user = null;
		try {
			user = Factories.services.getUsersService().findById(app.getUserId());
		} catch (NotPersistedException e) {
			Log.error("No se ha encontrado al usuario");
			
		}
		return user.getName();
	}
	
	public boolean solicitado(Trip t){
		Application ap = Factories.services.getApplicationsService()
				.findById(getUserId(), t.getId());
		if(ap != null){
			return true;
		}
		return false;
	}

	public String solicitarPlaza(Trip trip) {
		Long tripId = trip.getId();
		String resultado = "exito";
		if (!trip.getPromoterId().equals(getUserId())) {
			Application application = new Application();
			application.setTripId(tripId);
			application.setUserId(getUserId());
			try {
				Factories.services.getApplicationsService().saveApplication(
						application);
			} catch (AlreadyPersistedException e) {
				resultado = "error";
				Log.error("Ya ha solicitado plaza en este viaje");
			}
		} else {
			resultado = "error";
			Log.error("No puede solicitar plaza en un viaje propio");
		}
		Log.info("Se ha solicitado plaza");
		FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO" ,"Plaza solicitada con éxito"));
		return resultado;
	}

	/*
	 * EL promotor del viaje confirma la solicitud de un usuario en uno de sus
	 * viajes
	 */

	public String confirmarPlaza(Application application) {
		String resultado = "exito";
		setTrip(Factories.services.getTripsService().findById(
				application.getTripId()));
		if (getTrip().getAvailablePax() > 0) {
			if (getTrip().getClosingDate().after(new Date())) {
				Seat seat = new Seat();
				seat.setTripId(application.getTripId());
				seat.setUserId(application.getUserId());
				seat.setStatus(SeatStatus.ACCEPTED);
				getTrip().setAvailablePax(getTrip().getAvailablePax()-1);
				try {
					Factories.services.getTripsService().updateTrip(getTrip());
					Factories.services.getApplicationsService()
							.deleteApplication(application.getUserId(),
									application.getTripId());
					FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO" ,"Plaza confirmada"));
				} catch (NotPersistedException e) {
					resultado = "error";
					Log.error("No se ha podido confirmar la solicitud");
				}
				try {
					Factories.services.getSeatsService().saveSeat(seat);
				} catch (AlreadyPersistedException e) {
					resultado = "error";
					Log.error("No se ha podido confirmar la solicitud");

				}
			}
		}
		else FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING" ,"No hay plazas disponibles"));
		cargarSolicitudes(getTrip());
		Log.info("Plaza confirmada");
		return resultado;
	}
	


	public String eliminarSolicitud(Long userId , Long tripId) throws NotPersistedException {
		String resultado = "exito";
				Factories.services.getApplicationsService()
						.deleteApplication(userId, tripId);
			
				Log.error("No se ha podido eliminar la solicitud");
		
		
		Log.info("Solicitud eliminada");
		getViajes().misSolicitudes();
		FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO" ,"Solicitud anulada"));
		return resultado;
	}
	
	public String cambiarEstadoSeat(Seat seat) throws NotPersistedException{
		String resultado = "exito";
		setTrip(Factories.services.getTripsService().findById(
				seat.getTripId()));
		if(seat.getStatus().equals(SeatStatus.ACCEPTED)){
			seat.setStatus(SeatStatus.EXCLUDED);
			Factories.services.getSeatsService().updateSeat(seat);
			getTrip().setAvailablePax(getTrip().getAvailablePax()+1);
			Factories.services.getTripsService().updateTrip(getTrip());
			
		}
		else{
			if(getTrip().getAvailablePax() > 0){
			seat.setStatus(SeatStatus.ACCEPTED);
			Factories.services.getSeatsService().updateSeat(seat);
			getTrip().setAvailablePax(getTrip().getAvailablePax()-1);
			Factories.services.getTripsService().updateTrip(getTrip());
		}
			else {
				FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING" ,"Ya no hay plazas disponibles en este viaje"));
			}
		}
		Log.info("Se ha modificado el estado de la plaza");
		return resultado;
	}

	public String rechazarSolicitud(Application application) {
		String resultado = "exito";
			Seat seat = new Seat();
			seat.setTripId(application.getTripId());
			seat.setUserId(application.getUserId());
			seat.setStatus(SeatStatus.EXCLUDED);
	
			try {
				Factories.services.getApplicationsService()
						.deleteApplication(application.getUserId(),
								application.getTripId());
				Factories.services.getSeatsService().saveSeat(seat);
			} catch (NotPersistedException e) {
				resultado = "error";
				Log.error("No se ha podido rechazar la solicitud");
			//}
			} catch (AlreadyPersistedException p) {
				resultado = "error";
				Log.error("No se ha podido rechazar la solicitud");
			}
		Log.info("Solicitud rechazada");
		cargarSolicitudes(getTrip());
		FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO" ,"Solicitud rechazada"));
		return resultado;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}






}
