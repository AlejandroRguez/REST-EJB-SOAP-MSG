package com.sdi.presentation;

import java.io.Serializable;
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
import com.sdi.business.ApplicationService;
import com.sdi.business.SeatService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.AddressPoint;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.model.Waypoint;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

@ManagedBean(name = "viaje")
@SessionScoped
public class BeanViaje extends Trip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String plazasDisponibles , plazasMaximas, costeEstimado;
	private List<Trip> viajesCancelar = new ArrayList<Trip>();
	private boolean switchElem;
	
	@ManagedProperty("#{viajes}")
	private BeanViajes viajes;
	
	public BeanViajes getViajes(){
		return viajes;
	}
	
	public void setViajes (BeanViajes viajes){
		this.viajes = viajes;
	}

	public List<Trip> getViajesCancelar() {
		return viajesCancelar;
	}

	public void setViajesCancelar(List<Trip> viajesCancelar) {
		this.viajesCancelar = viajesCancelar;
	}

	public String getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(String plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
		try{
		this.setAvailablePax(Integer.parseInt(plazasDisponibles));		
		}catch (NumberFormatException e){
			this.setAvailablePax(null);
		}
		
	}

	public String getPlazasMaximas() {
		return plazasMaximas;
	}

	public void setPlazasMaximas(String plazasMaximas) {
		this.plazasMaximas = plazasMaximas;
		try{
			this.setMaxPax(Integer.parseInt(plazasMaximas));		
		}catch (NumberFormatException e){
			this.setMaxPax(null);
		}
	}
	
	@PostConstruct
	public void init() {
		
		viajes  = 
				(BeanViajes)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(new String("viajes"));
	}


	public void tripInicialize() {
		setArrivalDate(new Date());
		setPlazasDisponibles("0");
		setClosingDate(new Date());
		setDeparture(new AddressPoint());
		getDeparture().setAddress("Cibeles");
		getDeparture().setCity("Madrid");
		getDeparture().setCountry("España");
		getDeparture().setState("Madrid");
		getDeparture().setZipCode("45012");
		setDestination(new AddressPoint());
		getDestination().setAddress("Canaletas");
		getDestination().setCity("Barcelona");
		getDestination().setCountry("España");
		getDestination().setState("Cataluña");
		getDestination().setZipCode("33013");
		getDestination().setWaypoint(new Waypoint(0.0,0.0));
		setDepartureDate(new Date());
		setCosteEstimado("25.0");
		setPlazasMaximas("0");
		asignarPromotorYEstado();
	}
	
	public void tripVaciar() {
		setArrivalDate(null);
		setPlazasDisponibles("");
		setClosingDate(null);
		setDeparture(new AddressPoint("", "", "", "", "", null));
		setDestination(new AddressPoint("", "", "", "", "", null));
		setDepartureDate(null);
		setCosteEstimado("");
		setPlazasMaximas("");
		asignarPromotorYEstado();
	}
	
	public void precarga(){
		if(isSwitchElem()){
			tripInicialize();
		}else tripVaciar();
	}
	
	
	public String saveTrip() throws AlreadyPersistedException{
		boolean resultado = true;
		asignarPromotorYEstado();
		if(this.getMaxPax() < this.getAvailablePax() || this.getMaxPax() == 0){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Número de plazas incoherente"));
		}
		else if(this.getDepartureDate().before(new Date())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de salida no puede ser anterior a la de hoy"));
		}else if (this.getArrivalDate().before(this.getDepartureDate())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de llegada no puede ser anterior a la de salida"));
		}else if(this.getClosingDate().after(this.getArrivalDate())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de cierre no puede ser posterior a la de salida"));
		}
		if(Factories.services.getTripsService().findByPromoterAndArrivalDate(this.getPromoterId(), this.getArrivalDate()) != null){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Ya ha registrado un viaje en esta fecha"));
		}
		if(resultado){
			Factories.services.getTripsService().saveTrip(this.getTripBase());
			return "exito";
		}
		return "";
	}

	
	
	private Trip getTripBase() {
		Trip t = new Trip();
		t.setArrivalDate(getArrivalDate());
		t.setAvailablePax(getAvailablePax());
		t.setClosingDate(getClosingDate());
		t.setComments(getComments());
		t.setDeparture(getDeparture());
		t.setDepartureDate(getDepartureDate());
		t.setDestination(getDestination());
		t.setEstimatedCost(getEstimatedCost());
		t.setId(getId());
		t.setMaxPax(getMaxPax());
		t.setPromoterId(getPromoterId());
		t.setStatus(getStatus());
		return t;
	}

	public void asignarPromotorYEstado(){
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");
		setPromoterId(u.getId());
		setStatus(TripStatus.OPEN);
	}
	
	public String updateTrip() throws NotPersistedException{
		boolean resultado = true;
		asignarPromotorYEstado();
		if(this.getMaxPax() < this.getAvailablePax() || this.getMaxPax() == 0){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Número de plazas incoherente"));
		}
		else if(this.getDepartureDate().before(new Date())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de salida no puede ser anterior a la de hoy"));
		}else if (this.getArrivalDate().before(this.getDepartureDate())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de llegada no puede ser anterior a la de salida"));
		}else if(this.getClosingDate().after(this.getArrivalDate())){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"La fecha de cierre no puede ser posterior a la de salida"));
		}
		Trip t = Factories.services.getTripsService().findByPromoterAndArrivalDate(this.getPromoterId(), this.getArrivalDate());
		if( t != null && (this.getId().compareTo(t.getId())!= 0)){
			resultado = false;
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Ya ha registrado un viaje en esta fecha"));
		}
		if(resultado){
			Factories.services.getTripsService().updateTrip(this.getTripBase());
			viajes.promotor();
			return "exito";
			
		}
		return "";
	}
	
	public String rellenar(Trip trip) {
		setArrivalDate(trip.getArrivalDate());
		setPlazasDisponibles(trip.getAvailablePax().toString());
		setClosingDate(trip.getClosingDate());
		setDeparture(trip.getDeparture());
		getDeparture().setAddress(trip.getDeparture().getAddress());
		getDeparture().setCity(trip.getDeparture().getCity());
		getDeparture().setCountry(trip.getDeparture().getCountry());
		getDeparture().setState(trip.getDeparture().getState());
		getDeparture().setZipCode(trip.getDeparture().getZipCode());
		setDestination(trip.getDestination());
		getDestination().setAddress(trip.getDestination().getAddress());
		getDestination().setCity(trip.getDestination().getCity());
		getDestination().setCountry(trip.getDestination().getCountry());
		getDestination().setState(trip.getDestination().getState());
		getDestination().setZipCode(trip.getDestination().getZipCode());
		getDestination().setWaypoint(new Waypoint(0.0,0.0));
		setDepartureDate(trip.getDepartureDate());
		setCosteEstimado(trip.getEstimatedCost().toString());
		setPlazasMaximas(trip.getMaxPax().toString());
		setId(trip.getId());
		asignarPromotorYEstado();
		return "exito";
	}
	
	
	
	public String cancelTrip(Trip trip){
		String resultado = "éxito";
		if(trip.getStatus().equals(TripStatus.OPEN))
			trip.setStatus(TripStatus.CANCELLED);
		SeatService seatService = Factories.services.getSeatsService();
		List<Seat> seats = seatService.findByTrip(trip.getId());
		ApplicationService applicationService= Factories.services.getApplicationsService();
		List<Application> applications = applicationService.findByTrip(trip.getId());
		for(Seat s: seats){
			s.setStatus(SeatStatus.EXCLUDED);
			try {
				seatService.updateSeat(s);
			} catch (NotPersistedException e) {
				Log.error("No se han podido cancelar las plazas para el viaje seleccionado");
				FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Ha ocurrido un problema durante la cancelacion del viaje"));
			}
		}
		for(Application a : applications){
			Seat s = new Seat();
			s.setTripId(a.getTripId());
			s.setUserId(a.getUserId());
			s.setStatus(SeatStatus.EXCLUDED);
			try {
				applicationService.deleteApplication(a.getUserId(), a.getTripId());
				seatService.updateSeat(s);
			} catch (NotPersistedException e) {
				Log.error("No se han podido cancelar las solicitudes para el viaje seleccionado");
			}
		}
		try {
			Factories.services.getTripsService().updateTrip(trip);
			Log.info("Viaje cancelado con éxito");
		} catch (NotPersistedException e) {
			Log.error("No se ha podido anular el viaje");
			FacesContext.getCurrentInstance().addMessage (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR" ,"Ha ocurrido un problema durante la cancelacion del viaje"));
		}
		viajes.promotor();
		return resultado;
	}
	
	public String cancelTrips(){
		String resultado = "éxito";
		List<Trip> trips = this.getViajesCancelar();
		for(Trip trip: trips){
		if(trip.getStatus().equals(TripStatus.OPEN))
			trip.setStatus(TripStatus.CANCELLED);
		SeatService seatService = Factories.services.getSeatsService();
		List<Seat> seats = seatService.findByTrip(trip.getId());
		ApplicationService applicationService= Factories.services.getApplicationsService();
		List<Application> applications = applicationService.findByTrip(trip.getId());
		for(Seat s: seats){
			s.setStatus(SeatStatus.EXCLUDED);
			try {
				seatService.updateSeat(s);
			} catch (NotPersistedException e) {
				Log.error("No se han podido cancelar las plazas para el viaje seleccionado");
			}
		}
		for(Application a : applications){
			Seat s = new Seat();
			s.setTripId(a.getTripId());
			s.setUserId(a.getUserId());
			s.setStatus(SeatStatus.EXCLUDED);
			try {
				applicationService.deleteApplication(a.getUserId(), a.getTripId());
				seatService.updateSeat(s);
			} catch (NotPersistedException e) {
				Log.error("No se han podido cancelar las solicitudes para el viaje seleccionado");
			}
		}
		try {
			Factories.services.getTripsService().updateTrip(trip);
			Log.info("Viaje cancelado con éxito");
		} catch (NotPersistedException e) {
			Log.error("No se ha podido anular el viaje");
		}
		}
		viajes.promotor();
		return resultado;
	}
	
	

	public BeanViaje() {}

	public String getCosteEstimado() {
		return costeEstimado;
	}

	public void setCosteEstimado(String costeEstimado) {
		this.costeEstimado = costeEstimado;
		try{
			this.setEstimatedCost(Double.parseDouble(costeEstimado));		
			}catch (NumberFormatException e){
				this.setEstimatedCost(null);
			}
	}

	public boolean isSwitchElem() {
		return switchElem;
	}

	public void setSwitchElem(boolean switchElem) {
		this.switchElem = switchElem;
	}

}