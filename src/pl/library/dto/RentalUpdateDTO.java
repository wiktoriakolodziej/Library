package pl.library.dto;

import java.util.Date;

public class RentalUpdateDTO {

      private int id;
	    private Date returnDate;
	    private Date dueDate;

	    public int getId(){
	    	return id;
	    }
	    
	    public void setId(int id){
	    	this.id = id;
	    }

	    public Date getReturnDate() {
	        return returnDate;
	    }

	    public void setReturnDate(Date returnDate) {
	        this.returnDate = returnDate;
	    }

	    public Date getDueDate() {
	        return dueDate;
	    }

	    public void setDueDate(Date dueDate) {
	        this.dueDate = dueDate;
	    }

}
