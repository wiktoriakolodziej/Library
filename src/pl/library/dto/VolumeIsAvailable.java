package pl.library.dto;

import java.util.Date;

public class VolumeIsAvailable {
	int id;
	private Date rentalDate;
    private Date dueDate;
	
    public int getId() {
        return id;
    }
    
    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    

}
