package pl.library.dto;

import pl.library.dao.Book;
import pl.library.dao.Volume.BookCover;
import pl.library.dao.Volume.Condition;

public class BookVolumeReturnDTO { //chyba to samo co VolumeUpdateDTO
	int id;
	int yearOfPublication;
	BookCover bookCover;
	int pages;
	Condition condition;
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public int getYearOfPublication(){return yearOfPublication;}
	public void setYearOfPublication(int year){this.yearOfPublication = year;}
	
	public BookCover getBookCover(){return bookCover;}
	public void setBookCover(BookCover cover){this.bookCover = cover;}
	
	public int getPages(){return pages;}
	public void setPages(int pagess){this.pages = pagess;}
	
	public Condition getCondition(){return condition;}
	public void setCondition(Condition conditionn){this.condition = conditionn;}

}
