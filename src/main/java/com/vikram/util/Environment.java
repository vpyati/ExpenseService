package com.vikram.util;

import javax.servlet.ServletRequest;

public class Environment {
	
	public static boolean isDevelopment(ServletRequest request){
		return "localhost".equals(request.getServerName());
	}

}
