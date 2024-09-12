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

import pl.library.dao.Reader;
import pl.library.ejb.ReaderEJB;

@Path("/reader")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class ReaderController {

	
    @EJB
    ReaderEJB bean;

    @PersistenceContext(name="komis")
    EntityManager manager;

    @POST
    public Response create(Reader reader) {
        try {
            Reader createdReader = bean.create(reader);
            return Response.status(Response.Status.CREATED).entity(createdReader).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getReaderById(@PathParam("id") int id) {
        Reader result = bean.get(id);
        if(result == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(result).build();
    }

    @GET
    public Response getAll(@QueryParam("name") String name,
                           @QueryParam("surname") String surname,
                           @QueryParam("minPenalty") @DefaultValue("0") float minPenalty) {
        try {
            List<Reader> readers = bean.getAll(name, surname, minPenalty);
            return Response.ok(readers).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response update(Reader reader) {
    	
        try {
            Reader result = bean.update(reader);
            return Response.ok(result).build();
        } catch (Exception e) {
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
    public Reader test(){
       return bean.test();
    }
}
