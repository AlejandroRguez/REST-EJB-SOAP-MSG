package com.sdi.util;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiUtil {
	
	public static ConnectionFactory getConnectionFactory(
			String jmsConnectionFactory) {
		Context ctx;
		ConnectionFactory con = null;
		try {
			ctx = new InitialContext();
			con = (ConnectionFactory) ctx.lookup(jmsConnectionFactory);
		} catch (NamingException e) {

			e.printStackTrace();
		}

		return con;
	}

	public static Destination getDestination(String queueName) {
		Context ctx;
		Destination des = null;
		try {
			ctx = new InitialContext();
			des = (Destination) ctx.lookup(queueName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return des;
	}
}
