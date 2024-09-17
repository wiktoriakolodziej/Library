package pl.library.ejb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Book;
import pl.library.dao.Volume;
import pl.library.dto.BookCreateDTO;
import pl.library.dto.BookReturnDTO;
import pl.library.dto.BookWithoutVolumeDTO;
import pl.library.dto.VolumeWithoutBookDTO;

@Stateful
public class BookEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	
	public BookReturnDTO get(int id) throws Exception {
		Book b = manager.find(Book.class, id);
		if (b == null) {
	            throw new Exception("Book not found with id: " + id);
	    }
		return ConvertBookToReturnDto(b);		
	}
	

	public List<BookReturnDTO> getAll(String surname){
		
		String queryString = "SELECT v FROM Book v WHERE 1=1";
		if(surname != null && !surname.isEmpty()){
			 queryString += " AND v.authorSurname = :surname";
		}
		Query query = manager.createQuery(queryString);	
		if(surname != null && !surname.isEmpty()){
			query.setParameter("surname", surname);
		}
		
		@SuppressWarnings("unchecked")		
		List<Book> bookList = query.getResultList();
		
		
		List<BookReturnDTO> bookListDTO = new ArrayList<BookReturnDTO>();
		for(Book b : bookList)  {					
			BookReturnDTO bookReturnDTO = ConvertBookToReturnDto(b);			
			Set<Volume> vols = b.getVolumes();
			Set<VolumeWithoutBookDTO> volsDTO = new HashSet<VolumeWithoutBookDTO>();
			for(Volume v : vols){
				VolumeWithoutBookDTO bvrDTO = ConvertVolumeToBookVolumeReturnDto(v);
				volsDTO.add(bvrDTO);
			}
			bookReturnDTO.setVolumes(volsDTO);
			bookListDTO.add(bookReturnDTO);
		}
		return bookListDTO;
	}
	
	
	public BookReturnDTO create(BookCreateDTO bookDTO){

	        Book book = new Book();
	        book.setAuthorName(bookDTO.getAuthorName());
	        book.setAuthorSurname(bookDTO.getAuthorSurname());
	        book.setTitle(bookDTO.getTitle());
	        book.setVersion(bookDTO.getVersion());
	        book.setDescription(bookDTO.getDescription());	        
	        manager.persist(book);
	        return ConvertBookToReturnDto(book);
	} 
	
	
	public BookWithoutVolumeDTO update(BookWithoutVolumeDTO book)  {
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
        
        BookWithoutVolumeDTO ret = new BookWithoutVolumeDTO();
		ret.setAuthorName(existingBook.getAuthorName());
		ret.setAuthorSurname(existingBook.getAuthorSurname());
		ret.setDescription(existingBook.getDescription());
		ret.setId(existingBook.getId());
		ret.setTitle(existingBook.getTitle());
		ret.setVersion(existingBook.getVersion());
        return ret;
	}
	
	public void delete(int id) throws Exception {
		Book book = manager.find(Book.class, id);
		if (book == null) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

		manager.remove(book);
	}
	
	
	private VolumeWithoutBookDTO ConvertVolumeToBookVolumeReturnDto(Volume v){
		VolumeWithoutBookDTO bvrDTO = new VolumeWithoutBookDTO();
		bvrDTO.setId(v.getId());
		bvrDTO.setCondition(v.getCondition());
		bvrDTO.setPages(v.getPages());
		bvrDTO.setYearOfPublication(v.getYearOfPublication());
		bvrDTO.setBookCover(v.getBookCover());
		return bvrDTO;
	}
	
	private BookReturnDTO ConvertBookToReturnDto(Book b){
		BookReturnDTO bookReturnDTO = new BookReturnDTO();
		bookReturnDTO.setId(b.getId());
		bookReturnDTO.setAuthorName(b.getAuthorName());
		bookReturnDTO.setAuthorSurname(b.getAuthorSurname());
		bookReturnDTO.setTitle(b.getTitle());
		bookReturnDTO.setDescription(b.getDescription());
		bookReturnDTO.setVersion(b.getVersion());
		return bookReturnDTO;
	}

}
