package pl.library.ejb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.RentalDTO;
import pl.library.dao.Reader;


@Stateful
public class RentalEJB {
	
	@PersistenceContext(name="komis")
	EntityManager manager; 
	
	
	public RentalDTO create(RentalDTO rentalDTO)  throws Exception{
		Rental rental = GetRental(rentalDTO);
	    manager.persist(rental);
	    return GetRentalDTO(rental);
	} 
	
	public RentalDTO get(int id) {
		return GetRentalDTO(manager.find(Rental.class, id));
	}
	
	public List<RentalDTO> getAll(boolean delayed, Date afterDate, Date beforeDate, int readerId) {
		String queryString = "SELECT r FROM Rental r WHERE 1=1";
		 
		if (delayed) {
		    queryString += " AND (r.returnDate > r.dueDate OR (r.returnDate IS NULL AND :todayDate >= r.dueDate))";
	    }
	    if (afterDate != null) {
	        queryString += " AND r.returnDate >= :afterDate";
	    }
	    if (beforeDate != null) {
	        queryString += " AND r.returnDate <= :beforeDate";
	    }
	    if (readerId != 0) {
	        queryString += " AND r.reader.id = :readerId";
	    }
	    
	    Query query = manager.createQuery(queryString);
	    
	    if (afterDate != null) {
	        query.setParameter("afterDate", afterDate);
	    }
	    if (beforeDate != null) {
	        query.setParameter("beforeDate", beforeDate);
	    }
	    if (readerId != 0) {
	        query.setParameter("readerId", readerId);
	    }
	    if (delayed){
	    	query.setParameter("todayDate", Date.from(Instant.now()));
	    }
	    
	    @SuppressWarnings("unchecked")
	    List<Rental> queryResult = query.getResultList();
	    List<RentalDTO> result = new ArrayList<RentalDTO>();
	    for(Rental rental : queryResult){
	    	result.add(GetRentalDTO(rental));
	    }
	    return result;
}
	
	public RentalDTO update(RentalDTO rentalDTO) throws Exception {
		Rental rental = manager.find(Rental.class, rentalDTO.getId());
		
		if(rental == null){
			throw new Exception("Rental of given id doesn't exist:" + rentalDTO.getId());
		}
		Merge(rentalDTO, rental);
		
		return GetRentalDTO(manager.merge(rental));
	}
	
	public void delete(int id) {
		Rental rental = manager.find(Rental.class, id);
		manager.remove(rental);
	}
	
	
	
	private Rental GetRental (RentalDTO rentalDTO) throws Exception{
		if(rentalDTO == null) return null;
		Rental rental = new Rental();
		rental.setId(rentalDTO.getId());
		rental.setDueDate(rentalDTO.getDueDate());
		
		Reader reader = manager.find(Reader.class, rentalDTO.getReaderId());
        if (reader == null) {
            throw new Exception("Reader not found with id: " + rentalDTO.getReaderId());
        }
		rental.setReader(reader);
		
		rental.setRentalDate(rentalDTO.getRentalDate());
		rental.setReturnDate(rentalDTO.getReturnDate());
		
		if(rentalDTO.getVolumeIds() == null){
			throw new Exception("Volumes can't be empty");
		}
        List<Volume> volumes = new ArrayList<Volume>();
        for (Integer volumeId : rentalDTO.getVolumeIds()) {
            Volume volume = manager.find(Volume.class, volumeId);
            if (volume == null) {
                throw new Exception("Volume not found with id: " + volumeId);
            }
            volumes.add(volume);
        }		
		rental.setVolumes(volumes);
		return rental;
	}
	
	private Rental Merge(RentalDTO rentalDTO, Rental rental) throws Exception{
		if(rentalDTO.getId() != rental.getId()){
			throw new Exception("Rentals' ids don't match");
		}
		if(rentalDTO.getDueDate() != null) rental.setDueDate(rentalDTO.getDueDate());
		if(rentalDTO.getReaderId() != 0) {
			Reader reader = manager.find(Reader.class, rentalDTO.getReaderId());
			if(reader == null){
				throw new Exception("Reader of given id doesn't exist:" + rentalDTO.getReaderId());
			}
			rental.setReader(manager.find(Reader.class, rentalDTO.getReaderId()));
		}
		if(rentalDTO.getRentalDate() != null) rental.setRentalDate(rentalDTO.getRentalDate());
		if(rentalDTO.getReturnDate() != null) rental.setReturnDate(rentalDTO.getReturnDate());
		if(rentalDTO.getVolumeIds() != null){
	        List<Volume> volumes = rental.getVolumes();
	        for (Integer volumeId : rentalDTO.getVolumeIds()) {
	            Volume volume = manager.find(Volume.class, volumeId);
	            if (volume == null) {
	                throw new Exception("Volume not found with id: " + volumeId);
	            }
	            if(!volumes.contains(volume))
	            	volumes.add(volume);
	        }
	        rental.setVolumes(volumes);
		}
		return rental;
	}
	
	private RentalDTO GetRentalDTO(Rental rental){
		if(rental == null) return null;
		RentalDTO rentalDTO = new RentalDTO();
		rentalDTO.setId(rental.getId());
		rentalDTO.setDueDate(rental.getDueDate());
		rentalDTO.setRentalDate(rental.getRentalDate());
		rentalDTO.setReturnDate(rental.getReturnDate());
		
        List<Integer> volumes = new ArrayList<Integer>();
        for (Volume volume : rental.getVolumes()) {
            volumes.add(volume.getId());
        }
        rentalDTO.setVolumeIds(volumes);
        
        rentalDTO.setReaderId(rental.getReader().getId());
		return rentalDTO;

	}
	
	public void testReader(){
		Reader reader = new Reader();
		reader.setReaderName("domi");
		reader.setReaderSurname("domi");
		reader.setBirthDate(Date.from(Instant.now()));
		manager.persist(reader);
	}
}
