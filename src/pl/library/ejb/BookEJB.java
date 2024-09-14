package pl.library.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;

import javax.ejb.Stateful;

import pl.library.dao.Book;
import pl.library.dao.Reader;
import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.BookCreateDTO;
import pl.library.dto.BookReturnDTO;
import pl.library.dto.BookUpdateDTO;
import pl.library.dto.BookVolumeReturnDTO;
import pl.library.dto.RentalDTO;

@Stateful
public class BookEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	
	public Book create(BookCreateDTO bookDTO){
	        
	        Book book = new Book();
	        book.setAuthorName(bookDTO.getAuthorName());
	        book.setAuthorSurname(bookDTO.getAuthorSurname());
	        book.setTitle(bookDTO.getTitle());
	        book.setVersion(bookDTO.getVersion());
	        book.setDescription(bookDTO.getDescription());	        
	        manager.persist(book);
	        return book;
	} 
	

	public List<BookReturnDTO> getAll(){
		
		String queryString = "SELECT v FROM Book v";		
		Query query = manager.createQuery(queryString);		
		
		@SuppressWarnings("unchecked")		
		List<Book> bookList = query.getResultList();
		List<BookReturnDTO> bookListDTO = new ArrayList<BookReturnDTO>();
		for(Book b : bookList)  {
			BookReturnDTO bookReturnDTO = new BookReturnDTO();
			bookReturnDTO.setId(b.getId());
			bookReturnDTO.setAuthorName(b.getAuthorName());
			bookReturnDTO.setAuthorSurname(b.getAuthorSurname());
			bookReturnDTO.setTitle(b.getTitle());
			bookReturnDTO.setDescription(b.getDescription());
			bookReturnDTO.setVersion(b.getVersion());
			
			Set<Volume> vols = b.getVolumes();
			Set<BookVolumeReturnDTO> volsDTO = new HashSet<BookVolumeReturnDTO>();
			for(Volume v : vols){
				BookVolumeReturnDTO bvrDTO = new BookVolumeReturnDTO();
				bvrDTO.setId(v.getId());
				bvrDTO.setCondition(v.getCondition());
				bvrDTO.setPages(v.getPages());
				bvrDTO.setYearOfPublication(v.getYearOfPublication());
				bvrDTO.setBookCover(v.getBookCover());
				volsDTO.add(bvrDTO);
			}
			bookReturnDTO.setVolumes(volsDTO);
			bookListDTO.add(bookReturnDTO);
		}

		return bookListDTO;
	}
	

	
	public BookReturnDTO get(int id) {
		Book b = manager.find(Book.class, id);
		BookReturnDTO bookReturnDTO = new BookReturnDTO();
		bookReturnDTO.setId(b.getId());
		bookReturnDTO.setAuthorName(b.getAuthorName());
		bookReturnDTO.setAuthorSurname(b.getAuthorSurname());
		bookReturnDTO.setTitle(b.getTitle());
		bookReturnDTO.setDescription(b.getDescription());
		bookReturnDTO.setVersion(b.getVersion());
		return bookReturnDTO;
	}
	
	public BookUpdateDTO update(BookUpdateDTO book)  {
		Book existingBook = manager.find(Book.class, book.getId());
		
		if (existingBook == null) {
            throw new EntityNotFoundException("Book not found with id: " + book.getId());
        }
		Set<Volume> v = existingBook.getVolumes();
		
		existingBook.setId(existingBook.getId());
		
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
        
        existingBook.setVolumes(v);
        manager.persist(existingBook);
        
        BookUpdateDTO ret = new BookUpdateDTO();
		ret.setAuthorName(existingBook.getAuthorName());
		ret.setAuthorSurname(existingBook.getAuthorSurname());
		ret.setDescription(existingBook.getDescription());
		ret.setId(existingBook.getId());
		ret.setTitle(existingBook.getTitle());
		ret.setVersion(existingBook.getVersion());
        return ret;
	}
	
	public void delete(int id) {
		Book book = manager.find(Book.class, id);
		if (book == null) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
		manager.remove(book);
	}

}
