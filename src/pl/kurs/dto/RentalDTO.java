package pl.kurs.dto;

import java.sql.Date;
import java.util.List;

public class RentalDTO {
	  private Date rentalDate;
	    private Date returnDate;
	    private Date dueDate;
	    private int readerId;
	    private List<Integer> volumeIds;

	    public Date getRentalDate() {
	        return rentalDate;
	    }

	    public void setRentalDate(Date rentalDate) {
	        this.rentalDate = rentalDate;
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

	    public int getReaderId() {
	        return readerId;
	    }

	    public void setReaderId(int readerId) {
	        this.readerId = readerId;
	    }

	    public List<Integer> getVolumeIds() {
	        return volumeIds;
	    }

	    public void setVolumeIds(List<Integer> volumeIds) {
	        this.volumeIds = volumeIds;
	    }
}
