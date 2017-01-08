package com.kricko.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
public class OrderService {

	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public String printMessage() {

		String result = "Restful example : Kris";

		return result;

	}

}