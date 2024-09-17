package pl.library.ejb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	

	public List<VolumeReturnDTO> getAllAll(boolean available){
		List<Volume> resultList = new ArrayList<Volume>();
		if(available){

			String queryString1 = "SELECT v "
			        + "FROM Volume v "
			        + "WHERE v.id NOT IN ("
			        + "    SELECT v2.id "
			        + "    FROM Rental r "
			        + "    JOIN r.volumes v2 "
			        + "    WHERE r.rentalDate <= :date "
			        + "    AND r.dueDate >= :date"
			        + ")";
			Query query1 = manager.createQuery(queryString1);
			query1.setParameter("date", Date.from(Instant.now()));
			@SuppressWarnings("unchecked")
			List<Volume> rentalVolumes = query1.getResultList();

			
			String queryString2 = "SELECT v "
			        + "FROM Volume v "
			        + "WHERE v.id NOT IN (SELECT v2.id FROM Rental r JOIN r.volumes v2)";
			Query query2 = manager.createQuery(queryString2);
			@SuppressWarnings("unchecked")
			List<Volume> noRentalVolumes = query2.getResultList();
			
			Set<Volume> uniqueVolumes = new HashSet<Volume>();
			uniqueVolumes.addAll(rentalVolumes);		
			uniqueVolumes.addAll(noRentalVolumes);
			resultList = new ArrayList<Volume>(uniqueVolumes);
		
		}
		else{
			String queryString = "SELECT v FROM Volume v";		
			Query query = manager.createQuery(queryString);
			@SuppressWarnings("unchecked")
		    List<Volume> list = query.getResultList();
			resultList = list;
		}
		

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
        
        String queryString2 = "SELECT v "
                + "FROM Volume v "
                + "WHERE v.id = :volumeId "
                + "AND NOT EXISTS ("
                + "    SELECT r "
                + "    FROM Rental r "
                + "    JOIN r.volumes rv "
                + "    WHERE rv.id = :volumeId "
                + "    AND ("
                + "        (:rentalDate <= r.rentalDate AND :returnDate >= r.rentalDate) OR "
                + "		   ((r.returnDate IS NOT NULL AND :rentalDate <= r.returnDate AND :returnDate >= r.rentalDate) OR "
                + "			(r.returnDate IS NULL AND r.dueDate < :now AND :returnDate >= r.rentalDate) OR "
                + "			(r.returnDate IS NULL AND :rentalDate <= r.dueDate AND :returnDate >= r.rentalDate))"
                + "    )"
                + ")";

        Query query2 = manager.createQuery(queryString2);
        query2.setParameter("volumeId", id);  // Volume ID to check
        query2.setParameter("rentalDate", rentalDate);  // Start of requested rental period
        query2.setParameter("returnDate", dueDate);  // End of requested rental period
        query2.setParameter("now", Date.from(Instant.now()));

        @SuppressWarnings("unchecked")
        List<Volume> availableVolumes = query2.getResultList();
        
        
	    System.out.println("resultList.isEmpty(): " + availableVolumes.isEmpty());
	    System.out.println(availableVolumes.size());
	    if(!availableVolumes.isEmpty()){
	    	//dostepny
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}
	
	
}
