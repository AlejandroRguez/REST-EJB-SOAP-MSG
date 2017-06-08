package com.sdi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;

public class Main {
	
	private static final String BASE_URL = "http://localhost:8280/sdi3-11b.Web/rest/PromoterServiceRs";
	private Scanner in;
	private User user;
	private Long tripId;
	private Long userId;
	private List<Long> viajesValidos = new ArrayList<Long>();
	private List<Long> usuariosValidos = new ArrayList<Long>();
	
	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		System.out
				.println("Identifíquese para poder acceder a los servicios de usuario promotor");

		in = new Scanner(System.in);

		verificar();
		if (getViajes(this.user.getId()).isEmpty()) {
			System.out.println("No tiene viajes promovidos");
			return;
		} else {
			do {
				System.out.println("ID del viaje(0 para salir): ");
				long id = in.nextLong();
				if (id == 0L) {
					return;
				} else
					tripId = id;

			} while (!(validar(tripId, viajesValidos) && 
					verificarSolicitudes(getSolicitudes(tripId))));

		}

		do {
			System.out.println("ID del usuario a confirmar (0 para salir): ");
			long id = in.nextLong();
			if (id == 0L) {
				return;
			} else
				userId = id;
		} while (!validar(userId, usuariosValidos));

		saveSeat(userId, tripId);
		reiniciar();
}

	private void verificar(){
		System.out.print("Nombre de usuario: ");
		String login = in.next();
		System.out.print("Contraseña: ");
		String password = in.next();

		if (autenticacion(login, password)!= null) {
			System.out.println("Bienvenido " + login);
		} else {
			System.out.println("Usuario y/o contraseña incorrectos");
			verificar();
		}
	}

	private User autenticacion(String login, String password) {
	try{
		User res = ClientBuilder
					.newClient()
					.register(new Authenticator(login, password))
					.target(BASE_URL + "/verify?login=" + login
							+ "&password=" + password).request()
					.accept(MediaType.APPLICATION_XML).get()
					.readEntity(User.class);
			this.user = res;
			return res;
		}catch(ProcessingException e){
			return null;
		}
	}
	
	private List<Trip> getViajes(Long promoterId){
		try{
		 GenericType<List<Trip>> listaViajes = new GenericType<List<Trip>>(){};
		 List<Trip> trips = ClientBuilder
							.newClient()
							.target(BASE_URL + "/trips/" + promoterId).request()
							.accept(MediaType.APPLICATION_XML).get()
							.readEntity(listaViajes);
		System.out.println("LISTADO DE VIAJES");
		 for(Trip t : trips){
			 viajesValidos.add(t.getId());
			 printTrip(t);
		 }
		 
		 return trips;
		
		}catch(ProcessingException e){
			return new ArrayList<Trip>();
		}
	}
	
	private void printTrip(Trip t){
		System.out.println();
		System.out.println("ID del viaje: " + t.getId());
		System.out.println("\tCiudad de salida: "
				+ t.getDeparture().getCity());
		System.out.println("\tCiudad de destino: "
				+ t.getDestination().getCity());
		System.out.println("_____________________________________");
	}
	
	
	private List<Application> getSolicitudes(Long tripId){
		try{
		 List<Application> applications = ClientBuilder
							.newClient()
							.target(BASE_URL + "/applications/" + tripId).request()
							.accept(MediaType.APPLICATION_XML).get()
							.readEntity(new GenericType<List<Application>>(){});
		 
		 for(Application a : applications){
				usuariosValidos.add(a.getUserId());
				System.out.println("Usuario con ID " + a.getUserId());
		}
		 return applications;
	}catch(ProcessingException e){
		return new ArrayList<Application>();
	}
}
	
	private void saveSeat(Long userId, Long tripId){
		 Seat seat = ClientBuilder
				.newClient()
				.target(BASE_URL + "/seat?userId=" + userId
						+ "&tripId=" + tripId).request()
				.accept(MediaType.APPLICATION_XML).get()
				.readEntity(Seat.class);
		
		if (seat == null){
			System.out.println("No quedan plazas en el viaje");
		}
		else{
			ClientBuilder.newClient()
			.target( BASE_URL )
			.request()
			.put( Entity.entity(seat, MediaType.APPLICATION_JSON) );
			
			System.out.println("Plaza confirmada con éxito");
			System.out.println("Usuario: " + seat.getUserId());
			System.out.println("Viaje: " + seat.getTripId());
			System.out.println("Estado: " + seat.getStatus());
			deleteApplication(seat.getUserId(), seat.getTripId());
		}
	}
	
	
	private void deleteApplication(Long userId, Long tripId){
		ClientBuilder
		.newClient()
		.target(BASE_URL + "/delete?userId=" + userId
				+ "&tripId=" + tripId).request()
		.delete();
	}
	
		

	private boolean validar(Long id , List<Long> ids){
		if(ids.contains(id)){
			return true;
		
		}
		else{
			System.out.println("Id incorrecto");
			return false;
		}
		
	}
	
	private boolean verificarSolicitudes(List<Application> apps){
		if(apps.isEmpty()){
			System.out.println("No hay solicitudes disponibles");
			return false;
		}
		return true;
		
	}
	
	private void reiniciar(){
		usuariosValidos.clear();
		viajesValidos.clear();
	}
		

}
