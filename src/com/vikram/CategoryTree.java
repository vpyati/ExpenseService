package com.vikram;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/categorytree")
public class CategoryTree {

	
	@POST
	@Path("/reload")
	public Response add(@QueryParam("categoryjson") String categoryTree) {
 
		return Response.status(200).build();
 
	}
	
}
