/**
 * 
 */
package it.polito.oop.vaccination;

/**
 * @author Matteo
 *
 */
public class Person {

	private String name;
	private String surname;
	private String SSN;
	private int birthYear;
	
	private Boolean assigned; // Is this person assigned for the vaccination?
	private String hubForVaccination;
	private int dayOfTheWeekForVaccination;
	
	/**
	 * Constructor of the Person class
	 * 
	 * @param name
	 * @param surname
	 * @param SSN: fiscal code of a person; it is a unique identifier
	 * @param birthYear
	 */
	public Person(String name, String surname, String SSN, int birthYear) {
		this.name = name;
		this.surname = surname;
		this.SSN = SSN;
		this.birthYear = birthYear;
		this.assigned = false;
		this.hubForVaccination = null;
		this.dayOfTheWeekForVaccination = -1;
	}
	
	/**
	 * Getter method for the fiscal code (SSN)
	 */
	public String getSSN() {
		return this.SSN;
	}
	
	/**
	 * Getter method for the surname
	 */
	public String getLastName() {
		return this.surname;
	}

	/**
	 * Getter method for the name
	 */
	public String getFirstName() {
		return this.name;
	}
	
	/**
	 * Getter method for the birth year
	 */
	public int getBirthYear() {
		return this.birthYear;
	}
	
	/**
	 * Getter method used to determine if a person has been assigned for the vaccination
	 */
	public Boolean isAssigned() {
		return this.assigned;
	}
	
	/**
	 * Set the flag 'assigned' to true to indicate that a person has been assigned for the vaccination
	 * 
	 * @param hubName: hub which holds the vaccination for this person
	 * @param day: day of the week when this person is going to get vaccinated
	 */
	public void assignForVaccination(String hubName, int day) {
		this.assigned = true;
		this.hubForVaccination = hubName;
		this.dayOfTheWeekForVaccination = day;
	}

	/**
	 * @return the hubForVaccination
	 */
	public String getHubForVaccination() {
		return this.hubForVaccination;
	}

	/**
	 * @return the dayOfTheWeekForVaccination
	 */
	public int getDayOfTheWeekForVaccination() {
		return this.dayOfTheWeekForVaccination;
	}

	/**
	 * It turns this person into a no-vax (temporarily)
	 */
	public void freeFromVaccination() {
		this.assigned = false;
		this.hubForVaccination = null;
		this.dayOfTheWeekForVaccination = -1;
	}	
	
}
