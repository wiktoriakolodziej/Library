package pl.library.ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Volume;

@Stateful
public class VolumeEJB {
	@PersistenceContext(name="komis")
	EntityManager manager;

	public List<Volume> getAll(){
		Query q = manager.createQuery("select c from Volume c");
		@SuppressWarnings("unchecked")
		List<Volume> list = q.getResultList();
		return list;
	}
	
	public Volume create(Volume volume){
		manager.persist(volume);
		return volume;
	}
}
