package com.sdi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sdi.business.AdminService;
import com.sdi.business.impl.RemoteEjbServiceLocator;
import com.sdi.model.RatingSummary;
import com.sdi.model.UserInfo;


public class Main {
	
	Scanner in = new Scanner(System.in);
	AdminService adminService = new RemoteEjbServiceLocator().getAdminService();

	public static void main(String[] args) {
		new Main().run();

	}

	private void run() {
		
		Integer a;
		
			do {
				System.out.println("Para listar usuarios pulse 1");
				System.out.println("Para listar comentarios pulse 2");
				System.out.println("Para eliminar comentarios pulse 3");
				System.out.println("Para deshabilitar usuarios pulse 4");
				System.out.println("0 para salir");
				a = in.nextInt();
				if (a.equals(1))
					printUsers();
				else if (a.equals(2))
					printRatings();
				else if (a.equals(3))
					deleteRatings();
				else if (a.equals(4))
					disableUser();
				else if(a.compareTo(0) != 0)
					System.out
							.println("Opción incorrecta , vuelva a intentarlo");

			} while (a > 0);

		in.close();
	}

	private void disableUser()  {
		List<Long> idsPermitidos = new ArrayList<Long>();
		for(UserInfo u : adminService.getUserInfo()){
			System.out.println("ID: " + u.getId());
			System.out.println("Login: " + u.getLogin());
			System.out.println("Nombre: " + u.getName());
			System.out.println("Estado: " + u.getStatus());
			idsPermitidos.add(u.getId());
		}
		System.out
				.println("Introduzca ID del usuario a deshabilitar (0 para salir)");
		Integer a = in.nextInt();
		while (a != 0) {
			Long b = a.longValue();
			if(idsPermitidos.contains(b)){
				adminService.disableUser(b);
				System.out.println("El usuario con ID " + b
						+ " ha sido deshabilitado.");
			}else System.out.println("ID incorrecto");
			System.out
					.println("Introduzca ID del usuario a deshabilitar (0 para salir)");
			a = in.nextInt();

		}

	}

	private void deleteRatings() {
		List<Long> idsPermitidos = new ArrayList<Long>();
		for(RatingSummary r : adminService.getLastMonthRatings()){
			idsPermitidos.add(r.getId());
		}
		System.out
				.println("Introduzca ID del comentario a eliminar (0 para salir)");
		Integer a = in.nextInt();
		while (a != 0) {
			Long b = a.longValue();
			if(idsPermitidos.contains(b)){
			adminService.deleteRating(b);
			System.out.println("El comentario ha sido eliminado con éxito");
			}else System.out.println("ID incorrecto");
			System.out
			.println("Introduzca ID del comentario a eliminar (0 para salir)");
			a = in.nextInt();
		}

	}

	private void printUsers() {
		System.out.println("LISTADO DE USUARIOS DEL SISTEMA");
		for (UserInfo u: adminService.getUserInfo()){
			System.out.println("ID: " + u.getId());
			System.out.println("Nombre: " + u.getName());
			System.out.println("Login: " + u.getLogin());
			System.out.println("Apellido: " + u.getApellido());
			System.out.println("E-mail: " + u.getEmail());
			System.out.println("Número de participaciones: " + u.getParticipaciones().size());
			System.out.println("Número de viajes promovidos: " + u.getViajesPromovidos().size());
			System.out.println("___________________________________");
			System.out.println("___________________________________");

		}
		
		

	}

	
	private void printRatings()  {
		System.out.println("LISTADO DE COMENTARIOS");
		for(RatingSummary r : adminService.getLastMonthRatings() ){
			System.out.println("ID: " + r.getId());
			System.out.println("Comentario del viaje con destino: " + r.getDestino());
			System.out.println("Fecha de salida: " + r.getFechaViaje());
			System.out.println("Realizado sobre el usuario: "+r.getAboutUserName());
			System.out.println("Realizado por el usuario: " +r.getFromUserName());
			System.out.println("Comentario: " + r.getComentario());
			System.out.println("Valoración: " + r.getValoracion());
			System.out.println("___________________________________");
			System.out.println("___________________________________");
		}
		
	}
	
	
	
	

}
