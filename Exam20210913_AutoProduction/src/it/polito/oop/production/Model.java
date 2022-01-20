/**
 * 
 */
package it.polito.oop.production;

/**
 * @author Matteo
 * Class representing a model produced by a car manufacturer
 *
 */
public class Model {
	
	private final int YEARS_BEFORE_EXPIRING = 10;
	
	private String code;		// Unique identifier for a car's model
	private String name;
	private int yearOfRelease;
	private float engineSize;
	private int engineType;

	/**
	 * Constructor of a model
	 * 	
	 * @param code
	 * @param name
	 * @param yearOfRelease
	 * @param engineSize
	 * @param engineType
	 */
	public Model(String code, String name, int yearOfRelease, float engineSize, int engineType) {
		this.code = code;
		this.name = name;
		this.yearOfRelease = yearOfRelease;
		this.engineSize = engineSize;
		this.engineType = engineType;
	}

	/**
	 * Getter method for the identifier of a car's model
	 * 
	 * @return code: identifier of the model
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Getter method for the name of a car's model
	 * 
	 * @return code: name of the model
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * Getter method for the year of release of this model
	 * 
	 * @return yearOfRelease: year of release of the model
	 */
	public int getYearOfRelease() {
		return this.yearOfRelease;
	}
	
	/**
	 * toString method of a model
	 * It returns all of a model's data separated by a comma
	 * 
	 * return String
	 */
	public String toString() {
		return (this.code + "," + this.name + "," + this.yearOfRelease + "," + this.engineSize + "," + this.engineType);
	}
	
	/**
	 * It tells if this model is still being produced by the car manufacturer
	 * 
	 * @return Boolean
	 */
	public Boolean isModelActive() {
		return ((java.time.LocalDate.now().getYear() - this.yearOfRelease) <= YEARS_BEFORE_EXPIRING);
	}
	
	public float calculateIS() {
		float retValue;
		
		retValue = this.engineType * 100;
		retValue = retValue/(2021-this.yearOfRelease);
		
		return retValue;
	}
}
