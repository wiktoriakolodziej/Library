package pl.library.dto;

import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Conditionn;

public class VolumeUpdateDTO {
	int id;
	int yearOfPublication;
	BookCover bookCover;
	int pagess;
	Conditionn conditionn;
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public int getYearOfPublication(){return yearOfPublication;}
	public void setYearOfPublication(int year){this.yearOfPublication = year;}
	
	public BookCover getBookCover(){return bookCover;}
	public void setBookCover(BookCover cover){this.bookCover = cover;}
	
	public int getPages(){return pagess;}
	public void setPages(int pagess){this.pagess = pagess;}
	
	public Conditionn getCondition(){return conditionn;}
	public void setCondition(Conditionn conditionn){this.conditionn = conditionn;}


}
