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

import pl.library.dao.Book;
import pl.library.dto.BookCreateDTO;
import pl.library.dto.BookReturnDTO;
import pl.library.dto.BookUpdateDTO;
import pl.library.ejb.BookEJB;

@Path("/book")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class BookController {
	
	
	@EJB
	BookEJB bean;
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	
	@GET
	@Path("/{id}")
	public Response getBookById(@PathParam("id") int id) { 
		try{
			BookReturnDTO result = bean.get(id);
			return Response.ok(bean.get(id)).entity(result).build();
		}
		catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	public Response getAll(@QueryParam("authorSurname") String surname) 
	{
		 try {
	            List<BookReturnDTO> books = bean.getAll(surname);
	            return Response.ok(books).build();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	        }
	}
	
	
	@POST
	public Response create(BookCreateDTO book) {
		 try {
			 BookReturnDTO createdBook = bean.create(book);
            return Response.status(Response.Status.CREATED).entity(createdBook).build();
	     } 
		 catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}
	
	
	@PUT
	public Response update(BookUpdateDTO book) {
		try 
		{
			BookUpdateDTO result = bean.update(book);
			
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
		try{
			bean.delete(id);
			return Response.ok().build();
		}
		catch(Exception e){
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
