package pl.library.controller;

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

import pl.library.dao.Volume;
import pl.library.dto.VolumeCreateDTO;
import pl.library.dto.VolumeReturnDTO;
import pl.library.dto.VolumeWithoutBookDTO;
import pl.library.ejb.VolumeEJB;



@Path("/volume")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class VolumeController {
	@EJB
	VolumeEJB bean;
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	
	@GET
	@Path("/{id}")
	public Response getVolumeById(@PathParam("id") int id) { 
		try{
			VolumeReturnDTO result = bean.get(id);
			return Response.ok(bean.get(id)).build();
		}
		catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
	}
	
	
	@GET
	public Response getAll(@QueryParam("available") @DefaultValue("false") boolean available) {
		try {
            List<VolumeReturnDTO> volumes = bean.getAll(available);
            return Response.ok(volumes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }		
	}
	
	
	@GET
	@Path("forbook/{bookId}")
	public Response getAll(@PathParam("bookId") int bookId) {
		try {
            List<VolumeReturnDTO> volumes = bean.getAllForBook(bookId);
            return Response.ok(volumes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }		
	}
	
	
	@POST
	public Response create(VolumeCreateDTO volume) {
		 try {
	         VolumeReturnDTO createdVolume = bean.create(volume);
	         return Response.status(Response.Status.CREATED).entity(createdVolume).build();
		 } 
		 catch (Exception e) {
	           e.printStackTrace();
	           return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}
	
	
	@PUT
	public Response update(VolumeWithoutBookDTO volume) {
		try 
		{
			VolumeReturnDTO result = bean.update(volume);	
			return Response.ok(result).build();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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

			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}	
	
}

