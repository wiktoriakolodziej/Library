package pl.library.ejb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.library.dao.Reader;



@Stateful
public class ReaderEJB {

    @PersistenceContext(name = "komis")
    EntityManager manager;

    public Reader create(Reader reader) throws Exception {
        if (reader == null) {
            throw new Exception("Reader object is null");
        }

        manager.persist(reader);
        return reader;
    }

    public Reader get(int id) {
        return manager.find(Reader.class, id);
    }

    public List<Reader> getAll(String name, String surname, float minPenalty) {
        String queryString = "SELECT r FROM Reader r WHERE 1=1";

        if (name != null && !name.isEmpty()) {
            queryString += " AND r.readerName = :name";
        }
        if (surname != null && !surname.isEmpty()) {
            queryString += " AND r.readerSurname = :surname";
        }
        if (minPenalty > 0) {
            queryString += " AND r.penalty >= :minPenalty";
        }

        Query query = manager.createQuery(queryString);

        if (name != null && !name.isEmpty()) {
            query.setParameter("name", name);
        }
        if (surname != null && !surname.isEmpty()) {
            query.setParameter("surname", surname);
        }
        if (minPenalty > 0) {
            query.setParameter("minPenalty", minPenalty);
        }

        @SuppressWarnings("unchecked")
        List<Reader> resultList = query.getResultList();
        return resultList;
    }

    public Reader update(Reader reader) {
    	
        return manager.merge(reader);
    }

    public void delete(int id) {
        Reader reader = manager.find(Reader.class, id);
        if (reader != null) {
            manager.remove(reader);
        }
    }

    public Reader test() {
        Reader reader = new Reader();
        reader.setReaderName("John");
        reader.setReaderSurname("Doe");
        reader.setBirthDate(Date.from(Instant.now()));
        reader.setJoiningDate(Date.from(Instant.now()));
        reader.setPenalty(0.0f);

        manager.persist(reader);
        return reader;
    }
}
