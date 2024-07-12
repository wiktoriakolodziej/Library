package pl.library.controller;


import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.library.dao.Rental;
import pl.library.ejb.RentalEJB;


@Path("/rental")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class RentalController {
	@EJB
	RentalEJB bean;
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	@POST
	public Response create(Rental rental) {
		if (rental.getRentalDate() == null || rental.getDueDate() == null || rental.getReturnDate() == null) {
	        return Response.status(Response.Status.BAD_REQUEST).build();
		}
		bean.create(rental);
		return Response.status(Response.Status.CREATED).entity(rental).build(); 
	}
	
	@GET
	@Path("/{id}")
	public Response getRentalById(@PathParam("id") int id) { 
		Rental result = bean.get(id);
		if(result == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(bean.get(id)).build();
	}
	
	@GET
	public Response getAll() {
		return Response.ok(bean.getAll()).build();
	}
	@PUT
	public Response update(Rental rental) {
		try 
		{
			Rental result = bean.update(rental);
			return Response.ok(result).build();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		bean.delete(id);
		return Response.ok().build();
	}
	
	@GET
	@Path("/test")
	public Rental test(){
		return bean.test();
	}
}
