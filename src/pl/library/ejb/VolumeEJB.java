package pl.library.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.library.dao.Book;
import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.VolumeBookReturnDTO;
import pl.library.dto.VolumeCreateDTO;
import pl.library.dto.VolumeReturnDTO;
import pl.library.dto.VolumeUpdateDTO; 

@Stateful
public class VolumeEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;	
	
	
	public VolumeReturnDTO get(int id) throws Exception {	
		Volume v = manager.find(Volume.class, id);
		if (v == null) {
	            throw new Exception("Volume not found with id: " + id);
	    }
		return ConvertVolumeToVolumeReturnDto(v);		
	}
	
	
	public List<VolumeReturnDTO> getAllAll(){
		String queryString = "SELECT v FROM Volume v";
		Query query = manager.createQuery(queryString);
		@SuppressWarnings("unchecked")
	    List<Volume> resultList = query.getResultList();
		List<VolumeReturnDTO> vrDTO = new ArrayList<VolumeReturnDTO>();
		for(Volume v : resultList){
			VolumeReturnDTO vr = ConvertVolumeToVolumeReturnDto(v);
			Book b = v.getBook();
			VolumeBookReturnDTO vbr = ConvertBookToVolumeBookReturnDTO(b);
			vr.setBook(vbr);
			vrDTO.add(vr);		
		}
	    return vrDTO;
	}
	
	
	public List<VolumeReturnDTO> getAll(int bookId) throws Exception{
		Book book = manager.find(Book.class, bookId); //zostawic?
		if (book == null) {
	            throw new Exception("Book not found with id: " + bookId);
	    }		
		String queryString = "SELECT v FROM Volume v";
		if (bookId != 0) {
		    queryString += " WHERE v.book.id = :bookId";
		}		
		Query query = manager.createQuery(queryString);
	    
	    if (bookId != 0) {
	        query.setParameter("bookId", bookId);
	    }				
		@SuppressWarnings("unchecked")
	    List<Volume> resultList = query.getResultList();
		List<VolumeReturnDTO> vrDTO = new ArrayList<VolumeReturnDTO>();
		for(Volume v : resultList){
			VolumeReturnDTO vr = ConvertVolumeToVolumeReturnDto(v);
			Book b = v.getBook();
			VolumeBookReturnDTO vbr = ConvertBookToVolumeBookReturnDTO (b);
			vr.setBook(vbr);
			vrDTO.add(vr);			
		}
	    return vrDTO;		
	}
	
	
	public VolumeReturnDTO create(VolumeCreateDTO volumeDTO)  throws Exception{

		 Book book = manager.find(Book.class, volumeDTO.getBookId());	    
	     if (book == null) {
	         throw new Exception("Book not found with id: " + volumeDTO.getBookId());
	     }        
	     Volume volume = new Volume();
	     volume.setBook(book);
	     volume.setPages(volumeDTO.getPagess());
	     volume.setYearOfPublication(volumeDTO.getYearOfPublication());
		 volume.setBookCover(volumeDTO.getBookCover());
		 volume.setCondition(volumeDTO.getCondition());
	     manager.persist(volume);
	     return ConvertVolumeToVolumeReturnDto(volume);
	} 	
	
	
public VolumeUpdateDTO update(VolumeUpdateDTO volume) {
		
		Volume existingVolume = manager.find(Volume.class, volume.getId());
		
		if (existingVolume == null) {
            throw new EntityNotFoundException("Volume not found with id: " + volume.getId());
        }
		Book book = existingVolume.getBook();
		
		existingVolume.setId(existingVolume.getId());
		
        if (volume.getBookCover() != null) {
        	existingVolume.setBookCover(volume.getBookCover());
        }
        if (volume.getCondition() != null) {
        	existingVolume.setCondition(volume.getCondition());
        }
        if (volume.getPages() != 0) {
        	existingVolume.setPages(volume.getPages());
        }
        if (volume.getYearOfPublication() != 0) {
        	existingVolume.setYearOfPublication(volume.getYearOfPublication());
        }
        
        existingVolume.setBook(existingVolume.getBook());
        manager.persist(existingVolume);
        
        VolumeUpdateDTO ret = new VolumeUpdateDTO();
        ret.setBookCover(existingVolume.getBookCover());
        ret.setCondition(existingVolume.getCondition());
        ret.setId(existingVolume.getId());
        ret.setPages(existingVolume.getPages());
        ret.setYearOfPublication(existingVolume.getYearOfPublication());
        
        return ret;
	}
	
	public void delete(int id) throws Exception {
		try {
		    Volume volume = manager.find(Volume.class, id);
	        if (volume == null) {
	            throw new Exception("Volume not found with id: " + id);
	        }
		    Book book = volume.getBook();
	        if (book == null) {
	            throw new Exception("Book not found with id: ");
	        }
	        book.getVolumes().remove(volume);
		    //System.out.println("Volumin:" + id + ", of " + volume.getBook().getId());
		    manager.remove(volume);
		}
		catch (Exception e) {
		    e.printStackTrace();
		} finally {
		}
	}
	
	
	private VolumeReturnDTO ConvertVolumeToVolumeReturnDto(Volume v){
		VolumeReturnDTO vr = new VolumeReturnDTO();
		vr.setBookCover(v.getBookCover());
		vr.setCondition(v.getCondition());
		vr.setId(v.getId());
		vr.setPages(v.getPages());
		vr.setYearOfPublication(v.getYearOfPublication());
		return vr;
	}
	
	
	
	private VolumeBookReturnDTO ConvertBookToVolumeBookReturnDTO(Book b){
		VolumeBookReturnDTO vbr = new VolumeBookReturnDTO();
		vbr.setAuthorName(b.getAuthorName());
		vbr.setAuthorSurname(b.getAuthorSurname());
		vbr.setDescription(b.getDescription());
		vbr.setId(b.getId());
		vbr.setTitle(b.getTitle());
		vbr.setVersion(b.getVersion());
		return vbr;
	}

	
	public boolean IsAvailable(int id, Date rentalDate, Date dueDate) throws Exception{

	    Volume volume = manager.find(Volume.class, id);
        if (volume == null) {
            throw new Exception("Volume not found with id: " + id);
        }
        if (rentalDate == null) {
            throw new Exception("Rental date is required");
        }
        if (dueDate == null) {
            throw new Exception("Due date is required");
        }
        String queryString = "SELECT r FROM Rental r WHERE r.id = :id AND "
				+ "((r.returnDate IS NULL AND r.dueDate > :rentalDate AND r.rentalDate  < :rentalDate AND r.dueDate < :dueDate) OR "
				+ "(r.returnDate IS NULL AND r.rentalDate > :rentalDate AND r.dueDate > :dueDate AND r.rentalDate < :dueDate) OR " 
				+ "(r.returnDate IS NULL AND r.rentalDate < :rentalDate AND r.dueDate > :dueDate) OR "
				+ "(r.returnDate IS NULL AND r.rentalDate > :rentalDate AND r.dueDate < :dueDate))";
		Query query = manager.createQuery(queryString);
	    
		query.setParameter("id", id);
		query.setParameter("rentalDate", rentalDate);
		query.setParameter("dueDate", dueDate);

	    
	    @SuppressWarnings("unchecked")
	    List<Rental> resultList = query.getResultList();
	    System.out.println("resultList.isEmpty(): " + resultList.isEmpty());
	    if(resultList.isEmpty()){
	    	//dostepny
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}
}
