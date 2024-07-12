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
import pl.library.dao.Reader;

@Stateful
public class RentalEJB {
	
	@PersistenceContext(name="komis")
	EntityManager manager;
	
	public Rental create(Rental rental) {
		manager.persist(rental);
		return rental;
	}
	
	public Rental get(int id) {
		return manager.find(Rental.class, id);
	}
	public List<Rental> getAll() {
		Query q = manager.createQuery("select c from Rental c");
		@SuppressWarnings("unchecked")
		List<Rental> list = q.getResultList();
		return list;
	}
	
	public Rental update(Rental rental) {
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
