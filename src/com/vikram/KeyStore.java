package com.vikram;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.vikram.model.KeyValue;
import com.vikram.model.Temp;


@Path("/keyvalue")
@Produces("application/json")
public class KeyStore {
	

	@Autowired
	private Temp temp;
	
	
	@GET
	public KeyValue get(@QueryParam("keys") String keysJson) {
 
		KeyValue kvs = new KeyValue();
		kvs.addKeyValuePairs("firstName", "Vikram");
		kvs.addKeyValuePairs("lastName", "pyati");
		kvs.addKeyValuePairs("temp", temp.toString());
		return kvs; 
	}

}
