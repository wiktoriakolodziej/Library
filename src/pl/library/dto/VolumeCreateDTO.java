package pl.library.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Condition;

public class VolumeCreateDTO {
	int yearOfPublication;
	BookCover bookCover;
	int pages;
	Condition condition;
	int bookId;
	
	 
	public int getYearOfPublication() {
        return yearOfPublication;
    }
	
	public void setYearOfPublication(int yOP){
		this.yearOfPublication = yOP;
	}
	
	public int getPagess() {
        return pages;
    }
	
	public void setPagess(int p){
		this.pages = p;
	}
	
	public int getBookId() {
        return bookId;
    }
	
	public void setBookId(int bId){
		this.bookId = bId;
	}
	
	public BookCover getBookCover(){
		return bookCover;
	}
	
	public void setBookCover(BookCover c){
		this.bookCover = c;
	}
	
	public Condition getCondition(){
		return condition;
	}
	
	public void setCondition(Condition con){
		this.condition = con;
	}

} 
