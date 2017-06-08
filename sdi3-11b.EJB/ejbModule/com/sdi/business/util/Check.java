package com.sdi.business.util;

import alb.util.log.Log;

public class Check {
	
	public Check(){}
	
	public static Object check(Object object, String mensaje){
		if(object == null){
			Log.error(mensaje);
			return null;
		}
		else return object;
	}

	

}
