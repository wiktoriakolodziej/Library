package pl.library.ejb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Book;
import pl.library.dao.Reader;
import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Conditionn;
import pl.library.dto.RentalDTO;
import pl.library.dto.VolumeDTO;
import pl.library.dto.VolumeReturnDTO;

@Stateful
public class VolumeEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	public Volume get(int id) {
		
		Volume v = manager.find(Volume.class, id);
		v.getBook().setVolumes(null);
		return  v;
	}

	public List<Volume> getAll(int bookId){
		
		//String jpql = "SELECT new BookDTO(b.id, b.title) FROM Book b";
	    //    TypedQuery<BookDTO> query = entityManager.createQuery(jpql, BookDTO.class);
		
		
		String queryString = "SELECT v FROM Volume v";
		if (bookId != 0) {
		    queryString += " WHERE v.book.id = :bookId";
		}
		
		Query query = manager.createQuery(queryString);
	    
	    if (bookId != 0) {
	        query.setParameter("bookId", bookId);
	    }
		
		
		/*Query q = manager.createQuery("select c from Volume c");
		@SuppressWarnings("unchecked")
		List<Volume> list = q.getResultList();
		return list; */
		
		
		@SuppressWarnings("unchecked")
	    List<Volume> resultList = query.getResultList();
		for(Volume v : resultList){
			v.getBook().setVolumes(null);
			//VolumeReturnDTO volumeReturn = new VolumeReturnDTO();
			
		}
	    return resultList;
		
	}
	
	public Volume update(Volume volume) {
		return manager.merge(volume);
	}
	
	public void delete(int id) {
		Volume volume = manager.find(Volume.class, id);
		manager.remove(volume);
	}
	
	
	public Volume create(Volume volume){
		manager.persist(volume);
		return volume;
	}
	
	
	public Volume create(VolumeDTO volumeDTO)  throws Exception{
		 Book book = manager.find(Book.class, volumeDTO.getBookId());
	        if (book == null) {
	            throw new Exception("Book not found with id: " + volumeDTO.getBookId());
	        }
	        
	        Volume volume = new Volume();
	        volume.setBook(book);
	        volume.getBook().setVolumes(null);
	        volume.setPages(volumeDTO.getPagess());
	        volume.setYearOfPublication(volumeDTO.getYearOfPublication());
			volume.setBookCover(volumeDTO.getBookCover());
			volume.setCondition(volumeDTO.getCondition());
	        

	        
	        manager.persist(volume);
	        return volume;
	} 
	
	public int test2(){
		Book book = new Book();
		book.setAuthorName("Adam");
		book.setAuthorSurname("Mickiewicz");
		book.setDescription("Epopeja narodowa");
		book.setTitle("Pan Tadeusz");
		book.setVersion(1);
		manager.persist(book);
		return book.getId();
	}
	
	
	public Volume test(){
		Volume x = new Volume();
		x.setBookCover(BookCover.paperback);
		x.setCondition(Conditionn.bad);
		x.setYearOfPublication(2000);
		x.setPages(300);
		Book book = new Book();
		book.setAuthorName("Adam");
		book.setAuthorSurname("Mickiewicz");
		book.setDescription("Epopeja narodowa");
		book.setTitle("Pan Tadeusz");
		book.setVersion(1);
		manager.persist(book);
		x.setBook(book);
		manager.persist(x);
		
		return x;
	}
	
	
}
