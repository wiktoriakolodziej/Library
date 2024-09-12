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
	
	public Rental create(RentalDTO rentalDTO)  throws Exception{
		 Reader reader = manager.find(Reader.class, rentalDTO.getReaderId());
	        if (reader == null) {
	            throw new Exception("Reader not found with id: " + rentalDTO.getReaderId());
	        }
	        
	        List<Volume> volumes = new ArrayList<Volume>();
	        for (Integer volumeId : rentalDTO.getVolumeIds()) {
	            Volume volume = manager.find(Volume.class, volumeId);
	            if (volume == null) {
	                throw new Exception("Volume not found with id: " + volumeId);
	            }
	            volumes.add(volume);
	        }

	        Rental rental = new Rental();
	        rental.setRentalDate(rentalDTO.getRentalDate());
	        rental.setReturnDate(rentalDTO.getReturnDate());
	        rental.setDueDate(rentalDTO.getDueDate());
	        rental.setReader(reader);
	        rental.setVolumes(volumes);
	        
	        manager.persist(rental);
	        return rental;
	} 
	
	public Rental get(int id) {
		return manager.find(Rental.class, id);
	}
	public List<Rental> getAll(boolean delayed, Date afterDate, Date beforeDate, int readerId) {
		String queryString = "SELECT r FROM Rental r WHERE 1=1";
		 
		if (delayed) {
		        queryString += " AND r.returnDate > r.dueDate";
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
	    
	    @SuppressWarnings("unchecked")
	    List<Rental> resultList = query.getResultList();
	    return resultList;
}
	
	public Rental update(RentalDTO rentalDTO) throws Exception {
		if(rentalDTO.getId() == 0){
			throw new Exception("Rental id was not provided");
		}
		Rental rental = manager.find(Rental.class, rentalDTO.getId());
		if(rental == null){
			throw new Exception("Rental of given id doesn't exist:" + rentalDTO.getId());
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
	            volumes.add(volume);
	        }
	        rental.setVolumes(volumes);
		}
		return manager.merge(rental);
	}
	public void delete(int id) {
		Rental rental = manager.find(Rental.class, id);
		manager.remove(rental);
	}
	
	public Rental test(){
		Rental x = new Rental();
		x.setDueDate(Date.from(Instant.now()));
		x.setRentalDate(Date.from(Instant.now()));
		x.setReturnDate(Date.from(Instant.now()));
		Reader reader = new Reader();
		reader.setReaderName("domi");
		reader.setReaderSurname("domi");
		reader.setBirthDate(Date.from(Instant.now()));
		manager.persist(reader);
		x.setReader(reader);
		Volume y = new Volume();
		y.setYearOfPublication(1);
		y.setPages(1);
		manager.persist(y);
		Volume z = new Volume();
		z.setYearOfPublication(2);
		z.setPages(2);
		manager.persist(z);
		List<Volume> list = new ArrayList<Volume>();
		list.add(y);
		list.add(z);
		x.setVolumes(list);
		manager.persist(x);
		return x;
	}

}
