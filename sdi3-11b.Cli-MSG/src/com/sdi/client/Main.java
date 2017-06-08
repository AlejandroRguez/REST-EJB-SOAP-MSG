package com.sdi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import com.sdi.util.JndiUtil;
import com.sdi.util.LogConfig;
import com.sdi.ws.AdminService;
import com.sdi.ws.EjbAdminServiceService;
import com.sdi.ws.EjbPromoterUserServiceService;
import com.sdi.ws.PromoterUserService;
import com.sdi.ws.Trip;
import com.sdi.ws.User;
import com.sdi.ws.UserInfo;

public class Main {

	private static final String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String SHARE_MY_TRIP_QUEUE = "jms/queue/ShareMyTripQueue";
	private static final String SHARE_MY_TRIP_TOPIC = "jms/topic/ChatTopic";
	private Connection con;
	private Session session;
	private MessageProducer sender;
	private TopicSubscriber subscriber;
	private Scanner in;
	private User user = null;
	private Long tripId;
	private PromoterUserService userService = new EjbPromoterUserServiceService()
			.getPromoterUserServicePort();
	private AdminService adminService = new EjbAdminServiceService()
			.getAdminServicePort();
	private int opcion;

	public static void main(String[] args) throws JMSException {
		LogConfig.config();
		new Main().run();
	}

	private void run() {
		iniciarSesion();
		List<Long> validos;
		do {
			validos = listarViajes();
			System.out.print("ID del viaje: ");
			tripId = in.nextLong();
			if (!validos.contains(tripId))
				System.out.println("ID Incorrecto");
		} while (!validos.contains(tripId));
		initialize();
		leerMensajes(tripId);

		do {
			System.out.println("Introduzca 1 para enviar un mensaje");
			System.out.println("0 para salir");
			opcion = in.nextInt();
			MapMessage msg = null;
			if (opcion == 1) {
				msg = crearMensaje("chat", user.getLogin(), tripId);
				try {
					sender.send(msg);
				} catch (JMSException e) {
					System.out
							.println("Ha ocurrido un error enviando el mensaje");
				}
			}
		} while (opcion == 1);

	}

	private void initialize() {
		ConnectionFactory factory = (ConnectionFactory) JndiUtil
				.getConnectionFactory(JMS_CONNECTION_FACTORY);
		Destination queue = JndiUtil.getDestination(SHARE_MY_TRIP_QUEUE);
		Topic topic = (Topic) JndiUtil.getDestination(SHARE_MY_TRIP_TOPIC);
		try {
			con = factory.createConnection("sdi", "password");
			con.setClientID(user.getLogin());
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createProducer(queue);
			String idTrip = tripId.toString();
			subscriber = session.createDurableSubscriber(topic,
					user.getLogin(), "id = '" + idTrip + "'", true);
			con.start();
		} catch (JMSException e) {
			System.out
					.println("No se ha podido iniciar el sistema de mensajería");
		}
	}

	private void iniciarSesion() {
		System.out.print("Nombre de usuario: ");
		in = new Scanner(System.in);
		String login = in.next();
		System.out.print("Contraseña: ");
		String password = in.next();
		User user = userService.findByLoginAndPassword(login, password);
		if (user != null) {
			this.user = user;
		} else {
			System.out.println("Usuario y/o contraseña incorrectos");
			iniciarSesion();
		}
	}

	private List<Trip> getViajesPorUsuario() {
		List<Trip> viajesUsuario = new ArrayList<Trip>();
		List<UserInfo> users = adminService.getUserInfo();
		for (UserInfo u : users) {
			if (u.getId().equals(this.user.getId())) {
				for (Trip t : u.getParticipaciones()) {
					viajesUsuario.add(t);
				}
				for (Trip v : u.getViajesPromovidos()) {
					viajesUsuario.add(v);
				}
			}
		}
		return viajesUsuario;
	}

	private List<Long> listarViajes() {
		List<Long> disponibles = new ArrayList<Long>();
		System.out.println("VIAJES DISPONIBLES");
		System.out.println("----------------------------");
		System.out.println("----------------------------");
		for (Trip t : getViajesPorUsuario()) {
			disponibles.add(t.getId());
			System.out.println();
			System.out.println("\tID del viaje: " + t.getId());
			System.out.println("\tCiudad de salida: "
					+ t.getDeparture().getCity());
			System.out.println("\tCiudad de destino: "
					+ t.getDestination().getCity());
			System.out.println("_____________________________________");
		}
		return disponibles;
	}

	private MapMessage crearMensaje(String tipo, String login, Long tripId) {
		MapMessage msg = null;
		try {
			msg = session.createMapMessage();
			msg.setString("command", tipo);
			msg.setString("login", login);
			msg.setLong("tripId", tripId);
			msg.setStringProperty("id", tripId.toString());
			System.out.println("Introduzca el texto del mensaje");
			String mensaje = leer();
			msg.setString("mensaje", mensaje);
		} catch (JMSException e) {
			System.out.println("Ha ocurrido un error creando el mensaje");
		}
		return msg;
	}

	private String leer() {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(input);
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("Error al leer los datos");
		}
		return "";
	}

	private void leerMensajes(Long idViaje) {
		try {
			subscriber.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message msg) {
					String texto = null;

					try {
						texto = ((MapMessage) msg).getString("login") + ": "
								+ ((MapMessage) msg).getString("mensaje");
					} catch (JMSException e) {
						System.out
								.println("No se han podido leer los mensajes");
					}
					System.out.println(texto);

				}
			});
		} catch (JMSException e) {
			System.out.println("No se han podido leer los mensajes");
		}
		return;
	}

}
