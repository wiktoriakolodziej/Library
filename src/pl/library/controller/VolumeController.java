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

import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.VolumeDTO;
import pl.library.ejb.VolumeEJB;



@Path("/volume")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class VolumeController {
	@EJB
	VolumeEJB bean;
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	@POST
	public Response create(VolumeDTO volume) {
		 try {
	         Volume createdVolume = bean.create(volume);
	         return Response.status(Response.Status.CREATED).entity(createdVolume).build();
		 } 
		 catch (Exception e) {
	           e.printStackTrace();
	           return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
		
		
//		if (rental.getRentalDate() == null || rental.getDueDate() == null || rental.getReturnDate() == null) {
//	        return Response.status(Response.Status.BAD_REQUEST).build();
//		}
		 
		 
		/*bean.create(volume);
		return Response.status(Response.Status.CREATED).entity(volume).build(); */
	}
	
	
	
	@GET
	public Response getAll(@QueryParam("bookId") @DefaultValue("1") int bookId) {
		try {
            List<Volume> volumes = bean.getAll(bookId);
            return Response.ok(volumes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
		
		//return Response.ok(bean.getAll()).build();
	}
	
	
	
	@GET
	@Path("/{id}")
	public Response getVolumeById(@PathParam("id") int id) { 
		Volume result = bean.get(id);
		if(result == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(bean.get(id)).build();
	}
	
	
	@PUT
	public Response update(Volume volume) {
		try 
		{
			Volume result = bean.update(volume);
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
	@Path("/test2")
	public int test2(){
		return bean.test2();
	}
	
	@GET
	@Path("/test")
	public Volume test(){
		return bean.test();
	}
	
}
