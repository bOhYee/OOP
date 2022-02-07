/**
 * 
 */
package libraryMgmt;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

/**
 * @author S273386
 *
 */
public class User {

	private String name;
	private int maxNOfBooks;
	private int booksLoaned;
	private int loanDuration;
	
	public User(String name, int maxNOfBooks, int loanDuration) {
		this.name = name;
		this.maxNOfBooks = maxNOfBooks;
		this.loanDuration = loanDuration;
		this.booksLoaned = 0;
	}

	public String getName() {
		return name;
	}

	public int getMaxNOfBooks() {
		return maxNOfBooks;
	}

	public int getLoanDuration() {
		return loanDuration;
	}
	
	public boolean canStillLoan() {
		return ((this.booksLoaned - this.maxNOfBooks) < 0);
	}
	
	public void loan() {
		this.booksLoaned += 1;
	}
	
	public void removeLoan() {
		this.booksLoaned -= 1;
	}
	
}
