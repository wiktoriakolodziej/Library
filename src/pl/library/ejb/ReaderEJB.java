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
import pl.library.dto.ReaderDTO;



@Stateful
public class ReaderEJB {

    @PersistenceContext(name = "komis")
    EntityManager manager;

    public ReaderDTO create(ReaderDTO readerDTO) throws Exception {
      
        if (readerDTO.getReaderName() == null || readerDTO.getReaderSurname() == null) {
            throw new Exception("Required fields (readerName or readerSurname) are missing");
        }
        Reader reader = getDAO(readerDTO);

        manager.persist(reader);
        return getDTO(reader);
    	
    }
    public ReaderDTO get(int id) throws Exception {
        Reader result = manager.find(Reader.class, id);
        if(result == null) throw new Exception("Reader of id: " + id + " doesn't exist");
        return getDTO(result);
    }

    public List<ReaderDTO> getAll(String name, String surname, float minPenalty) {
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
        List<Reader> queryList = query.getResultList();
        List<ReaderDTO> resultList = new ArrayList<ReaderDTO>();
        for(Reader reader : queryList){
        	resultList.add(getDTO(reader));
        }
        return resultList;
    }

    public ReaderDTO update(ReaderDTO readerDTO) throws Exception {
    	
        Reader existingReader = manager.find(Reader.class, readerDTO.getId());
        if (existingReader == null) {
            throw new Exception("Reader not found");
        }

        if (readerDTO.getReaderName() != null) {
            existingReader.setReaderName(readerDTO.getReaderName());
        }
        if (readerDTO.getReaderSurname() != null) {
            existingReader.setReaderSurname(readerDTO.getReaderSurname());
        }
        if (readerDTO.getBirthDate() != null) {
            existingReader.setBirthDate(readerDTO.getBirthDate());
        }
        if (readerDTO.getJoiningDate() != null) {
            existingReader.setJoiningDate(readerDTO.getJoiningDate());
        }
        if (readerDTO.getPenalty() != 0) {
            existingReader.setPenalty(readerDTO.getPenalty());
        }

        return getDTO(manager.merge(existingReader));
    	
    }
    
    public void delete(int id) throws Exception {
        Reader reader = manager.find(Reader.class, id);
        if(reader == null) throw new Exception ("Reader of id " + id + " doesn't exist");
        manager.remove(reader);
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
    
    private Reader getDAO (ReaderDTO readerDTO){
    	Reader reader = new Reader();

        reader.setReaderName(readerDTO.getReaderName());
        reader.setReaderSurname(readerDTO.getReaderSurname());
        reader.setBirthDate(readerDTO.getBirthDate() != null ? readerDTO.getBirthDate() : null); 
        reader.setJoiningDate(readerDTO.getJoiningDate() != null ? readerDTO.getJoiningDate() : null); 
        reader.setPenalty(readerDTO.getPenalty() != 0 ? readerDTO.getPenalty() : 0.0f); 
        return reader;
    }
    
    private ReaderDTO getDTO(Reader reader){
    	ReaderDTO readerDTO = new ReaderDTO();
    	readerDTO.setId(reader.getId());
    	readerDTO.setBirthDate(reader.getBirthDate());
    	readerDTO.setReaderName(reader.getReaderName());
    	readerDTO.setReaderSurname(reader.getReaderSurname());
    	readerDTO.setJoiningDate(reader.getJoiningDate());
    	readerDTO.setPenalty(reader.getPenalty());
    	return readerDTO;
    }
}