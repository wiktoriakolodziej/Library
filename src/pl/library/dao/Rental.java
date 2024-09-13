package pl.library.dao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;




@Entity
@XmlRootElement
public class Rental implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@XmlAttribute
	public int id;
	@Temporal(TemporalType.DATE)
	@Column(updatable = false, nullable = false)
	public Date rentalDate;
	@Temporal(TemporalType.DATE)
	public Date returnDate;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date dueDate;
	@ManyToOne
	@JoinColumn(name = "reader_id")
	public Reader reader;

	@ManyToMany(targetEntity = Volume.class, fetch = FetchType.EAGER)
	@JoinTable(
			name = "rental_volumes",
			joinColumns = @JoinColumn(name = "rental_id"),
			inverseJoinColumns = @JoinColumn(name = "volume_id"))
	@Column(nullable = false)
	public List<Volume> volumes;
	

	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public Date getRentalDate(){return rentalDate;}
	public void setRentalDate(Date rentalDate){this.rentalDate = rentalDate;}
	
	public Date getReturnDate(){return returnDate;}
	public void setReturnDate(Date returnDate){this.returnDate = returnDate;}
	
	public Date getDueDate(){return dueDate;}
	public void setDueDate(Date dueDate){this.dueDate = dueDate;}
	
	public Reader getReader(){return reader;}
	public void setReader(Reader reader){this.reader = reader;}

	public List<Volume> getVolumes(){return volumes;}
	public void setVolumes(List<Volume> volumes){this.volumes = volumes;}

}
