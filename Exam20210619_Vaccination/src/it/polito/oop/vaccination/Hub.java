/**
 * 
 */
package it.polito.oop.vaccination;

/**
 * @author Matteo
 *
 */
public class Hub {
	
	private String name;
	private int numOfDoctors;
	private int numOfNurses;
	private int numOfStaffs;
	
	/**
	 * Constructor of the Vaccination Hub
	 * Its name is a unique identifier
	 * 
	 * @param name: name of the hub
	 */
	public Hub(String name) {
		this.name = name;
		this.numOfDoctors = 0;
		this.numOfNurses = 0;
		this.numOfStaffs = 0;
	}
	
	/**
	 * Method to set the number of various personnel that can operate inside the vaccination hub
	 * 
	 * @param doctors: number of doctors
	 * @param nurses: number of nurses
	 * @param other: number of other non-specialized staff
	 */
	public void setStaffConfiguration(int doctors, int nurses, int other) {
		this.numOfDoctors = doctors;
		this.numOfNurses = nurses;
		this.numOfStaffs = other;
	}
	
	/**
	 * Getter of the hub's name
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method to check if the staff configuration was previously defined
	 * 
	 * @return true if number of personnel are defined; if at least one category is not defined it returns false
	 */
	public Boolean isStaffDefined() {
		return (this.numOfDoctors <= 0 || this.numOfNurses <= 0 || this.numOfStaffs <= 0);
	}

	
	/**
	 * Getter for the number of doctors inside an hub
	 * 
	 * @return the numOfDoctors
	 */
	public int getNumOfDoctors() {
		return this.numOfDoctors;
	}

	
	/**
	 * Getter for the number of nurses inside an hub
	 * 
	 * @return the numOfNurses
	 */
	public int getNumOfNurses() {
		return this.numOfNurses;
	}


	/**
	 * Getter for the number of other staff members inside an hub
	 * 
	 * @return the numOfStaffs
	 */
	public int getNumOfStaffs() {
		return this.numOfStaffs;
	}

	
}
