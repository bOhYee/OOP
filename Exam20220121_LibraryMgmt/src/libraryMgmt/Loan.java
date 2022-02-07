/**
 * 
 */
package libraryMgmt;
import java.time.*;

/**
 * @author S273386
 *
 */
public class Loan {
	
	private User user;
	private int volumeIndex;
	private LocalDate dueDate;
	private LocalDate returnDate;
	
	//Constructor
	public Loan(User u, int volumeIndex, LocalDate dueDate) {
		this.user = u;
		this.volumeIndex = volumeIndex;
		this.dueDate = dueDate;
		this.returnDate = null;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public User getUser() {
		return user;
	}

	public int getVolumeIndex() {
		return volumeIndex;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

}
