package libraryMgmt;
import java.util.*;
import java.util.stream.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class LibraryMgmt {

	private LocalDate currentDate;
	private List<Book> volumes;
	private List<User> users;
	private List<Loan> loans;
	
	public LibraryMgmt() {
		volumes = new ArrayList<>();
		users = new LinkedList<>();
		loans = new ArrayList<>();
	}
	
	//R0
	/**
	 * Defines the current date
	 * @param date current date
	 */
	public void setCurrentDate(LocalDate date) {
		currentDate = date;
	}

	/**
	 * retrieves current library system date
	 * @return current date
	 */
	public LocalDate getCurrentDate () {
		return this.currentDate;
	}

	/**
	 * Moves current date forward
	 * @param nOfDays number of days forward
	 */
	public void addDays (long nOfDays) {
		this.currentDate = this.currentDate.plusDays(nOfDays);
	}


	//R1
	/**
	 * Add a new book with corresponding volumes
	 * 
	 * @param title    title of the book
	 * @param nVolumes number of volumes available
	 * @param authors  list of authors
	 * @return volume index range
	 * @throws LMException
	 */
	public String addBook(String title, int nVolumes, String... authors) throws LMException {
		
		LMException e = new LMException("Book with the same title already present!");
		int minR, maxR;
		
		if(volumes.stream().filter(b -> title.equals(b.getTitle())).findAny().isPresent())
			throw e;
		
		minR = volumes.size() + 1;
		maxR = minR + nVolumes - 1;
		for(int i = 0; i < nVolumes; i++)
			volumes.add(new Book(title, authors));
		
		return (minR + ":" + maxR);
	}

	/**
	 * Adds a new user with relative parameters
	 * 
	 * @param name
	 * @param maxNofBooks
	 * @param duration
	 * @return
	 * @throws LMException
	 */
	public String addUser (String name, int maxNofBooks, int duration) throws LMException {
		
		LMException e = new LMException("User already defined!");
		User u = null;
		
		if(users.stream().filter(user -> name.equals(user.getName())).findAny().isPresent())
			throw e;
		
		u = new User(name, maxNofBooks, duration);
		users.add(u);
		
		return (u.getName() + ":" + u.getMaxNOfBooks() + ":" + u.getLoanDuration());
	}

	//R2
	/**
	 * Adds a new volume loan in the system.
	 * 
	 * @param user : user name
	 * @param title: book title
	 * @return loan index
	 * @throws LMException
	 */
	public int addLoan (String user, String title) throws LMException {
		
		LMException e1 = new LMException("Book not present!");
		LMException e2 = new LMException("User has reached the maximum number of loans available!");
		LMException e3 = new LMException("User has not yet returned a book!");
		
		Optional<Book> opzBook = null;
		Book book = null;
		User userObj = null;
		Loan loanObj = null;
		
		int volumeIndex = 0;
		int retValue = 0;
		
		
		userObj = this.users.stream().filter(u -> user.equals(u.getName())).findAny().get();
		if(!userObj.canStillLoan())
			throw e2;
		
		for(Iterator<Loan> i = this.loans.iterator(); i.hasNext(); ) {
			loanObj = i.next();
			if(userObj.equals(loanObj.getUser()) && (getCurrentDate().compareTo(loanObj.getDueDate()) > 0))
				throw e3;
		}
		
		opzBook = this.volumes.stream().filter(b -> title.equals(b.getTitle())).filter(b -> b.isAvailable()).findFirst();
		if(!opzBook.isPresent())
			throw e1;
		
		book = opzBook.get();
		volumeIndex = volumes.indexOf(book);
		book.setAvailable(false);
		userObj.loan();
		retValue = this.loans.size() + 1;
		this.loans.add(new Loan(userObj, volumeIndex, getCurrentDate().plusDays(userObj.getLoanDuration())));
		
		return retValue;
	}

	/**
	 * Retrieves loan information
	 * 
	 * @param loanIndex
	 * @return information as string
	 */
	public String getLoanInfo (int loanIndex) {
		
		Loan l = this.loans.get(loanIndex-1);
		String stateLoan = null;
		
		LocalDate curr = getCurrentDate();
		LocalDate loanDueDate = l.getDueDate();
		
		if(l.getReturnDate() == null && curr.compareTo(loanDueDate) < 0)
			stateLoan = "ongoing";
		
		if(l.getReturnDate() == null && curr.compareTo(loanDueDate) > 0)
			stateLoan = "overdue";
		
		if(l.getReturnDate() != null)
			stateLoan = "closed";		
		
		return (l.getUser().getName() + ":" + (loanIndex) + ":" + (l.getVolumeIndex()+1) + ":" + l.getDueDate() + ":" + stateLoan);
	}

	/**
	 * Closes a loan
	 * 
	 * @param loanIndex loan index
	 * @return loan return date
	 */
	public LocalDate closeLoan (int loanIndex)  { //throws LMException
		
		Loan loanObj = this.loans.get(loanIndex-1);
		User u = loanObj.getUser();
		
		loanObj.setReturnDate(getCurrentDate());
		this.volumes.get(loanObj.getVolumeIndex()).setAvailable(true);
		u.removeLoan();

		return getCurrentDate();
	}


	/**
	 * Retrieves number of volumes currently on loan to user
	 * @param user
	 * @return number of volumes
	 */
	public int numberOfBooks (String user) {
		
		User u = this.users.stream().filter(a -> user.equals(a.getName())).findAny().get();
		Loan tmpLoan = null;
		int counter = 0;
		
		for(Iterator<Loan> i = this.loans.iterator(); i.hasNext(); ) {
			tmpLoan = i.next();
			if(tmpLoan.getUser().equals(u) && tmpLoan.getReturnDate() == null)
				counter++;
		}
		
		return counter;
	}

	//R3  statistics

	/**
	 * Returns map of authors grouped by title
	 * 
	 * @return map title -> author list
	 */
	public TreeMap<String, ArrayList<String>> authorsByTitle() {
		
		TreeMap<String, ArrayList<String>> retValue = new TreeMap<>();
		
		Book bookObj = null;
		String[] bookNames= null;
		String[] tmpAuthors = null;
		ArrayList<String> authorsList = null;
		
		bookNames = this.volumes.stream().map(v -> v.getTitle()).distinct().toArray(String[]::new);
		for(int i = 0; i < bookNames.length; i++) {
			
			String name = bookNames[i];
			bookObj = this.volumes.stream().filter(v -> name.equals(v.getTitle())).findAny().get();
			tmpAuthors = bookObj.getAuthors();
			
			authorsList = new ArrayList<>();
			for(int l = 0; l < tmpAuthors.length; l++)
				authorsList.add(tmpAuthors[l]);
			
			authorsList.sort(String::compareTo);
			retValue.put(name, authorsList);
		}
		
		return retValue;
	}


	/**
	 * Retrieves total loans for users (including closed ones)
	 * 
	 * @return map user -> loan number
	 */
	public TreeMap<String, Integer> numberOfTotalLoansByUser() {
		
		TreeMap<String, Integer> retValue = new TreeMap<>();
		Integer numOfLoan = null;
		String userName = null;
		Loan tmpLoan = null;
		
		for(Iterator<Loan> i = this.loans.iterator(); i.hasNext(); ) {
			tmpLoan = i.next();
			userName = tmpLoan.getUser().getName();
			
			if(retValue.containsKey(userName))
				numOfLoan = retValue.get(userName) + 1;
			else
				numOfLoan = 1;
			
			retValue.put(userName, numOfLoan);
		}
		
		return retValue;
	}

	//R4  queries

	/**
	 * returns the list of loans whose due date is equal to the current date.
	 * 
	 * @return list of loan indexes
	 */
	public List<Integer> dailyOverdue(){
		
		List<Integer> retValue = new LinkedList<>();
		Loan tmpLoan = null;
		
		for(Iterator<Loan> i = this.loans.iterator(); i.hasNext(); ) {
			
			tmpLoan = i.next();
			if(tmpLoan.getReturnDate() == null && tmpLoan.getDueDate().compareTo(getCurrentDate()) == 0)
				retValue.add(this.loans.indexOf(tmpLoan)+1);
			
		}	
		
		return retValue;
	}

	/**
	 * returns the average delay of loan returns for given user
	 * @param userName
	 * @return
	 */
	public double averageDelay(String userName) {
		
		double retValue = 0;
		int totalDays = 0;
		int countedLoan = 0;
		Loan tmpLoan = null;
		
		for(Iterator<Loan> i = this.loans.iterator(); i.hasNext(); ) {
			
			tmpLoan = i.next();
			if(tmpLoan.getReturnDate() == null)
				continue;
			
			countedLoan++;
			if(tmpLoan.getDueDate().compareTo(tmpLoan.getReturnDate()) < 0)
				totalDays += tmpLoan.getDueDate().until(tmpLoan.getReturnDate(), ChronoUnit.DAYS);
			
		}	
		
		retValue = (double) totalDays/countedLoan;
		return retValue;
	}

	/**
	 * returns the number of volumes available for the book with the given title
	 * @param title
	 * @return number of available volumes
	 */
	public long availableVolumes(String title) {
		
		List<Book> availableBooks = new LinkedList<>();
		
		availableBooks = this.volumes.stream().filter(b -> title.equals(b.getTitle())).filter(b -> b.isAvailable()).collect(LinkedList::new, List::add, List::addAll);
		return availableBooks.size();
	}


}
