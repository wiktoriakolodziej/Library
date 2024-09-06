package pl.library.dao;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="volumes")
@XmlRootElement
public class Volume {
	
	public enum BookCover{paperback, hardcover;}
	public enum Conditionn{bad, average, good;}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(updatable = false, nullable = false)
	int yearOfPublication;
	@Enumerated(EnumType.STRING)
	BookCover bookCover;
	@Column(updatable = false, nullable = false)
	int pagess;
	@Enumerated(EnumType.STRING)
	Conditionn conditionn;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	Book book;
	
	//@ManyToMany(mappedBy = "volumes")
	//ArrayList<Rental> rentals;
	
	//private ArrayList<Reader> readers;
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public int getYearOfPublication(){return yearOfPublication;}
	public void setYearOfPublication(int year){this.yearOfPublication = year;}
	
	public BookCover getBookCover(){return bookCover;}
	public void setBookCover(BookCover cover){this.bookCover = cover;}
	
	public int getPages(){return pagess;}
	public void setPages(int pagess){this.pagess = pagess;}
	
	public Book getBook(){return book;}
	public void setBook(Book book){this.book = book;}
	
	public Conditionn getCondition(){return conditionn;}
	public void setCondition(Conditionn conditionn){this.conditionn = conditionn;}
	
	//public ArrayList<Rental> getRentals(){return rentals;}
	//public void setRentals(Set<Rental> rentals){this.rentals = rentals;} 
	
	//public ArrayList<Reader> getReaders(){return readers;}
	//public void setReaders(ArrayList<Reader> readers){this.readers = readers;} 

}
