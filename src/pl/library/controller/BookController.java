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

import pl.library.dao.Book;
import pl.library.dao.Rental;
import pl.library.dto.BookDTO;
import pl.library.dto.BookUpdateDTO;
import pl.library.dto.RentalDTO;
import pl.library.ejb.BookEJB;

@Path("/book")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class BookController {
	
	
	@EJB
	BookEJB bean;
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	@POST
	public Response create(Book book) {
		 try {
            Book createdBook = bean.create(book);
            return Response.status(Response.Status.CREATED).entity(createdBook).build();
	     } 
		 catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}
	
	@GET
	@Path("/{id}")
	public Response getBookById(@PathParam("id") int id) { 
		Book result = bean.get(id);
		if(result == null) 
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(bean.get(id)).build();
	}
	
	@GET
	public Response getAll() 
	{
		 try {
	            List<Book> books = bean.getAll();
	            return Response.ok(books).build();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	        }
	}
	
	@PUT
	public Response update(BookUpdateDTO book) {
		try 
		{
			Book result = bean.update(book);
			return Response.ok(result).build();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	//TO DO on delete cascade
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		bean.delete(id);
		return Response.ok().build();
	}
	
	/*@GET
	@Path("/test")
	public Rental test(){
		return bean.test();
	}*/
	
	

}
