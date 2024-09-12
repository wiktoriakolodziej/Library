package pl.library.dao;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="books")
@XmlRootElement
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(updatable = false, nullable = false)
	String title;
	@Column(updatable = false, nullable = false)
	String authorName;
	@Column(updatable = false, nullable = false)
	String authorSurname;
	int version;
	String description;
		
	
	//@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Volume> volumes;
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public String getTitle(){return title;}
	public void setTitle(String title){this.title = title;}
	
	public String getAuthorName(){return authorName;}
	public void setAuthorName(String name){this.authorName = name;}
	
	public String getAuthorSurname(){return authorSurname;}
	public void setAuthorSurname(String surname){this.authorSurname = surname;}
	
	public int getVersion(){return version;}
	public void setVersion(int version){this.version = version;}
	
	public String getDescription(){return description;}
	public void setDescription(String description){this.description = description;}
	
	public Set<Volume> getVolumes() {return volumes;}
	public void setVolumes(Set<Volume> volumes) {this.volumes =  volumes;}
	
}