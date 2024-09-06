package pl.library.dto;

import pl.library.dao.Book;
import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Conditionn;

public class VolumeReturnDTO {
	int id;
	int yearOfPublication;
	BookCover bookCover;
	int pagess;
	Conditionn conditionn;
	BookReturnDTO book;
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public int getYearOfPublication(){return yearOfPublication;}
	public void setYearOfPublication(int year){this.yearOfPublication = year;}
	
	public BookCover getBookCover(){return bookCover;}
	public void setBookCover(BookCover cover){this.bookCover = cover;}
	
	public int getPages(){return pagess;}
	public void setPages(int pagess){this.pagess = pagess;}
	
	public BookReturnDTO getBook(){return book;}
	public void setBook(BookReturnDTO book){this.book = book;}
	
	public Conditionn getCondition(){return conditionn;}
	public void setCondition(Conditionn conditionn){this.conditionn = conditionn;}

}
