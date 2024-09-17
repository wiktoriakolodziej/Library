package pl.library.controller;


import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import pl.library.dto.RentalDTO;
import pl.library.dto.RentalUpdateDTO;
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
	public Response create(RentalDTO rental) {
		 try {
            RentalDTO createdRental = bean.create(rental);
            return Response.status(Response.Status.CREATED).entity(createdRental).build();
	     } 
		 catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}
	
	
	@GET
	@Path("/{id}")
	public Response getRentalById(@PathParam("id") int id) { 
		RentalDTO result = bean.get(id);
		if(result == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(bean.get(id)).build();
	}
	
	@GET
	public Response getAll(@QueryParam("delayed") @DefaultValue("false") boolean delayed,
	        @QueryParam("afterRentalDate") Date afterDate,
	        @QueryParam("beforeRentalDate") Date beforeDate,
	        @QueryParam("readerId") @DefaultValue("0") int readerId) 
	{
		 try {
	            List<RentalDTO> rentals = bean.getAll(delayed, afterDate, beforeDate, readerId);
	            return Response.ok(rentals).build();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	        }
	}
	
	@PUT
	public Response update(RentalUpdateDTO rental) {
		try 
		{
			RentalDTO result = bean.update(rental);
			return Response.ok(result).build();
		} 
		catch (Exception e) 
		{
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		try{
			bean.delete(id);
			return Response.ok().build();
		}
		catch(Exception e){
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}		
	}
	
	
	@GET
	@Path("/test")
	public void testReader(){
		bean.test();
	}
	
}
