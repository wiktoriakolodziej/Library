package pl.library.dto;

import java.util.Set;

import pl.library.dao.Volume;

public class BookReturnDTO {
	int id;
	String title;
	String authorName;
	String authorSurname;
	int version;
	String description;
	Set<VolumeWithoutBookDTO> volumes;
	
	public BookReturnDTO(){}
	
	public BookReturnDTO(int id, String title, String authorName, String authorSurname, int version, 
			String description) {
	        this.id = id;
	        this.title = title;
	        this.authorName = authorName;
	        this.authorSurname = authorSurname;
	        this.version = version;
	        this.description = description;
	}
	
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
	
	public Set<VolumeWithoutBookDTO> getVolumes() {return volumes;}
	public void setVolumes(Set<VolumeWithoutBookDTO> volumes) {this.volumes =  volumes;}
}
