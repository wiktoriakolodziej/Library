package pl.library.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Conditionn;

public class VolumeDTO {
	int yearOfPublication;
	BookCover bookCover;
	int pagess;
	Conditionn conditionn;
	int bookId;
	
	 
	public int getYearOfPublication() {
        return yearOfPublication;
    }
	
	public void setYearOfPublication(int yOP){
		this.yearOfPublication = yOP;
	}
	
	public int getPagess() {
        return pagess;
    }
	
	public void setPagess(int p){
		this.pagess = p;
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
	
	public Conditionn getCondition(){
		return conditionn;
	}
	
	public void setCondition(Conditionn con){
		this.conditionn = con;
	}

}
