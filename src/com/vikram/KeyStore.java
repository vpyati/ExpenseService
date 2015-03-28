package com.vikram;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.vikram.beans.Temp;


@Path("/keyvalue")
@Produces("application/json")
public class KeyStore {
	
	private Temp temp;
	
	
	@GET
	public Temp get(@QueryParam("keys") String keysJson) {
 
		//String authCode = headers.getRequestHeader("X-HEADER-AUTH_CODE").get(0);
		
		return new Temp("Vikram","Pyati");
		//return "{name:vikram}";
 
	}

}
