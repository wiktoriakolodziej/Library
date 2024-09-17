package pl.library.ejb;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Rental;
import pl.library.dao.Volume;
import pl.library.dto.RentalDTO;
import pl.library.dto.RentalUpdateDTO;
import pl.library.dao.Reader;
import pl.library.ejb.VolumeEJB;


@Stateful
public class RentalEJB {
	
	@PersistenceContext(name="komis")
	EntityManager manager; 
	
	@EJB
	VolumeEJB bean;
	
	
	public RentalDTO create(RentalDTO rentalDTO)  throws Exception{
		Calendar todayCal = Calendar.getInstance();
		todayCal.set(Calendar.HOUR_OF_DAY, 0);
		todayCal.set(Calendar.MINUTE, 0);
		todayCal.set(Calendar.SECOND, 0);
		todayCal.set(Calendar.MILLISECOND, 0);
		Date today = todayCal.getTime();
		if(rentalDTO.getRentalDate().before(today)) throw new Exception("Rental date can't be from the past");
		if(rentalDTO.getDueDate().before(rentalDTO.getRentalDate())) throw new Exception("Due date can't be before rental date");
		for(int id : rentalDTO.getVolumeIds()){
			if(!bean.IsAvailable(id, rentalDTO.getRentalDate(), rentalDTO.getDueDate())){

				throw new Exception("Volume of id: " + id + " is not available in chosen dates");
			}
		}
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
	
	public RentalDTO update(RentalUpdateDTO rentalDTO) throws Exception {
		Rental rental = manager.find(Rental.class, rentalDTO.getId());
		
		if(rental == null){
			throw new Exception("Rental of given id doesn't exist:" + rentalDTO.getId());
		}
		
		Date date = new Date();
		if(rentalDTO.getReturnDate() != null) date = rentalDTO.getReturnDate();
		else if(rentalDTO.getDueDate() != null) date = rentalDTO.getDueDate();
		else date = (rental.getReturnDate() == null ? rental.getDueDate() : rental.getReturnDate());
		
//		for(Volume volume : rental.getVolumes()){
//			if(!bean.IsAvailable(volume.getId(), rental.getRentalDate(),date)){
//				throw new Exception("Volume of id: " + volume.getId() + " is not available in chosen dates");
//			}
//		}

		Rental merged = Merge(rentalDTO, rental);
		manager.persist(merged);
		return GetRentalDTO(merged);
	}
	
	public void delete(int id) throws Exception {
		Rental rental = manager.find(Rental.class, id);
		if(rental == null) throw new Exception ("Rental of id " + id + " doesn't exist");
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
	
	private Rental Merge(RentalUpdateDTO rentalDTO, Rental rental) throws Exception{
		if(rentalDTO.getId() != rental.getId()){
			throw new Exception("Rentals' ids don't match");
		}
		if(rentalDTO.getDueDate() != null) rental.setDueDate(rentalDTO.getDueDate());
		if(rentalDTO.getReturnDate() != null) rental.setReturnDate(rentalDTO.getReturnDate());
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
	

	public void test(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Rental rental = new Rental();
		try {
			rental.setRentalDate(dateFormat.parse("2022-09-09"));
			rental.setDueDate(dateFormat.parse("2022-10-10"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rental.setReader(manager.find(Reader.class, 1));
		rental.setVolumes(Arrays.asList((manager.find(Volume.class, 1))));
		manager.persist(rental);
	}
}
