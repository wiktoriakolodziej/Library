package pl.library.dto;

import javax.persistence.Column;

public class BookDTO {
	
	String title;
	String authorName;
	String authorSurname;
	int version;
	String description;
	
	public String getTitle(){
		return title;
	}
	
	public String getAuthorName(){
		return authorName;
	}
	
	public String getAuthorSurname(){
		return authorSurname;
	}
	
	public int getVersion(){
		return version;
	}
	
	public String getDescription(){
		return description;
	}
	
	

}
