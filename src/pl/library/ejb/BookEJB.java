package pl.library.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.time.Instant;
import java.util.Date;

import javax.ejb.Stateful;

import pl.library.dao.Book;
import pl.library.dao.Reader;
import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.BookDTO;
import pl.library.dto.BookUpdateDTO;
import pl.library.dto.RentalDTO;

@Stateful
public class BookEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	
	public Book create(Book bookDTO){
	        
	        Book book = new Book();
	        book.setAuthorName(bookDTO.getAuthorName());
	        book.setAuthorSurname(bookDTO.getAuthorSurname());
	        book.setTitle(bookDTO.getTitle());
	        book.setVersion(bookDTO.getVersion());
	        book.setDescription(bookDTO.getDescription());
	        // setVolumes?
	        
	        manager.persist(book);
	        return book;
	} 
	
	/*public Book create(Book book){
		manager.persist(book);
		return book;
	}*/

	public List<Book> getAll(){
		//Query q = manager.createQuery("SELECT b from Book b");
		
		String queryString = "SELECT v FROM Book v";		
		Query query = manager.createQuery(queryString);
	    
	    /*if (bookId != 0) {
	        query.setParameter("bookId", bookId);
	    }*/
		
		
		@SuppressWarnings("unchecked")
		
		List<Book> list = query.getResultList();
		for(Book b : list){
			Set<Volume> vList = b.getVolumes();
			for(Volume v : vList){
				v.setBook(null);
			}
			//VolumeReturnDTO volumeReturn = new VolumeReturnDTO();			
		}
		
		
		//List<Book> list = query.getResultList();
		return list;
	}
	

	
	public Book get(int id) {
		return manager.find(Book.class, id);
	}
	
	public Book update(BookUpdateDTO book) {
		Book existingBook = manager.find(Book.class, book.getId());
		if (existingBook == null) {
            throw new EntityNotFoundException("Book not found with id: " + book.getId());
        }
        if (book.getAuthorName() != null) {
        	existingBook.setAuthorName(book.getAuthorName());
        }
        if (book.getAuthorSurname() != null) {
        	existingBook.setAuthorSurname(book.getAuthorSurname());
        }
        if (book.getDescription() != null) {
        	existingBook.setDescription(book.getDescription());
        }
        if (book.getTitle() != null) {
        	existingBook.setTitle(book.getTitle());
        }
        if (book.getVersion() != 0) {
        	existingBook.setVersion(book.getVersion());
        }
        return existingBook;
		//return manager.merge(book);
	}
	
	public void delete(int id) {
		Book book = manager.find(Book.class, id);
		manager.remove(book);
	}

}
