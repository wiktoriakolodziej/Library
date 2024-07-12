package pl.library.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;
import java.util.Set;

import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="readers")
@XmlRootElement
public class Reader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "readerName", nullable = false)
	private String readerName;
	@Column(name = "readerSurname", nullable = false)
	private String readerSurname;
	@Temporal(TemporalType.DATE)
	@Column(name = "birtDate", updatable = false, nullable = false)
	private Date birthDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "joiningDate")
	private Date joiningDate;
	@Column(name = "penalty")
	private float penalty;
	
	@OneToMany(mappedBy = "reader")
	Set<Rental> rentals;
	
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public String getReaderName() {return readerName;}
	public void setReaderName(String readerName) {this.readerName = readerName;}
	
	public String getReaderSurname() {return readerSurname;}
	public void setReaderSurname(String readerSurname) {this.readerSurname = readerSurname;}
	
    public Date getBirthDate() {return birthDate;}
	public void setBirthDate(Date birthDate) {this.birthDate = birthDate;}
	 
	public Date getJoiningDate() { return joiningDate;}
	public void setJoiningDate(Date joiningDate) { this.joiningDate = joiningDate;}
	
	public float getPenalty() {return penalty;}
	public void setPenalty(float penalty) { this.penalty = penalty;}

	 
	    
}
