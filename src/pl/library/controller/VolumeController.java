package pl.library.controller;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.library.dao.Volume;
import pl.library.ejb.VolumeEJB;



@Path("/volume")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class VolumeController {
	@EJB
	VolumeEJB bean;
	@POST
	public Response create(Volume volume) {
//		if (rental.getRentalDate() == null || rental.getDueDate() == null || rental.getReturnDate() == null) {
//	        return Response.status(Response.Status.BAD_REQUEST).build();
//		}
		bean.create(volume);
		return Response.status(Response.Status.CREATED).entity(volume).build(); 
	}
	@GET
	public Response getAll() {
		return Response.ok(bean.getAll()).build();
	}
}

