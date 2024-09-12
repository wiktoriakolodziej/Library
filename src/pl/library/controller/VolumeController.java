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
import pl.library.dto.VolumeReturnDTO;
import pl.library.dto.VolumeUpdateDTO;
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
	         //return Response.status(Response.Status.CREATED).entity(createdVolume).build();
	         return Response.status(Response.Status.CREATED).build();
		 } 
		 catch (Exception e) {
	           e.printStackTrace();
	           return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }

	}
	
	@GET
	public Response getAll() {
		try {
            List<VolumeReturnDTO> volumes = bean.getAllAll();
            return Response.ok(volumes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }		
	}
	
	
	
	//public Response getAll(@QueryParam("bookId") @DefaultValue("0") int bookId) {
	@GET
	@Path("forbook/{bookId}")
	public Response getAll(@PathParam("bookId") int bookId) {
		try {
            List<VolumeReturnDTO> volumes = bean.getAll(bookId);
            return Response.ok(volumes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }		
	}
	
	
	
	@GET
	@Path("/{id}")
	public Response getVolumeById(@PathParam("id") int id) { 
		VolumeReturnDTO result = bean.get(id);
		//Volume result = bean.get(id);
		if(result == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(bean.get(id)).build();
	}
	
	
	@PUT
	public Response update(VolumeUpdateDTO volume) {
		try 
		{
			VolumeUpdateDTO result = bean.update(volume);
			
			//Volume result = bean.update(volume);
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
		try {
			bean.delete(id);
			return Response.ok().build();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}	
}

