package com.sdi.integration;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import com.sdi.business.AdminService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.UserInfo;

@MessageDriven(activationConfig = { 
		@ActivationConfigProperty(propertyName = "destination", 
				propertyValue = "java:/queue/ShareMyTripQueue") 
		})

public class MsgListener implements MessageListener{
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private TopicConnectionFactory factory;

	@Resource(mappedName = "java:jboss/exported/jms/topic/ChatTopic")
	private Topic chat;

	@Resource(mappedName = "java:/queue/AuditQueue")
	private Destination descartados;

	@Override
	public void onMessage(Message msg) {
		System.out.println("Mensaje recibido");
		try{
			if(usuarioInvolucrado((MapMessage) msg)){
				procesar((MapMessage) msg);
			}
			else descartar((MapMessage) msg);
		}catch(JMSException e){
			System.out.println("Ha ocurrido un problema");
		}
		
	}
	
	private void descartar(MapMessage msg) throws JMSException {
		System.out.println("MENSAJE DESCARTADO");
		Connection con = factory.createConnection("sdi", "password");
		Session sesion = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = sesion.createProducer(descartados);
		producer.send(msg);
		con.close();
		
	}

	private void procesar(MapMessage msg) throws JMSException {
		TopicConnection con = factory.createTopicConnection("sdi", "password");
		TopicSession sesion = con.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);
		TopicPublisher producer = sesion.createPublisher(chat);
		producer.publish(msg);
		System.out.println("Mensaje enviado a la cola");
		con.close();
	}

	private boolean usuarioInvolucrado(MapMessage msg) throws JMSException{
		String login = msg.getString("login");
		Long tripId = msg.getLong("tripId");
		AdminService service = Factories.services.getAdminService();
		for (UserInfo u : service.getUserInfo()){
			if(u.getLogin().equals(login)){
				for(Trip t : u.getParticipaciones()){
					if(t.getId().equals(tripId)){
						return true;
					}
				}
				for(Trip t : u.getViajesPromovidos()){
					if(t.getId().equals(tripId)){
						return true;
					}
				}
			}
		}
		return false;
	}

}
