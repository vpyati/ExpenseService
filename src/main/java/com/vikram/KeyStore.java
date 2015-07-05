package com.vikram;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.vikram.model.KeyValue;


@Path("/keyvalue")
@Produces("application/json")
public class KeyStore {
	
	@GET
	public KeyValue get(@QueryParam("keys") String keysJson) {
 
		KeyValue kvs = new KeyValue();
		kvs.addKeyValuePairs("firstName", "Vikram");
		kvs.addKeyValuePairs("lastName", "pyati");
		return kvs; 
	}

}
