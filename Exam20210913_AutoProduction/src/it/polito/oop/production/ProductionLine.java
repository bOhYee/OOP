/**
 * 
 */
package it.polito.oop.production;

/**
 * @author Matteo
 *
 * Class representing a production line inside a factory
 * Every factory can have n production lines 
 * 
 */
public class ProductionLine {

	private String name;
	private int maxCapacity;
	private int capacity;
	private int motorization;
	
	/**
	 * Constructor for a production line
	 * 
	 * @param name
	 * @param capacity
	 * @param motorization
	 */
	public ProductionLine(String name, int capacity, int motorization) {
		this.name = name;
		this.maxCapacity = capacity;
		this.capacity = 0;
		this.motorization = motorization;
	}


	/**
	 * Getter method for the name of the production line
	 * 
	 * @return name: name of the production line
	 */
	public String getName() {
		return name;
	}

	/**
	 * It updates the capacity of a production line
	 * 
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.maxCapacity = capacity;
	}

	/**
	 * Getter method for the capacity of the line
	 * 
	 * @return capacity of the line
	 */
	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	
	/**
	 * Getter method for the allocated capacity of the line
	 * 
	 * @return capacity of the line
	 */
	public int getAllocatedCapacity() {
		return this.capacity;
	}
	
	/**
	 * Getter method for the engine type produced by the line
	 * 
	 * @return engine type produced
	 */
	public int getMotorization() {
		return this.motorization;
	}
	
	/**
	 * Plan the production of a certain number of cars
	 * It decreases the capacity available to produce other cars
	 * 
	 * @param qta: quantity of cars to be produced
	 * @return retValue: number of cars that cannot be produced in this line if max capacity is reached
	 */
	public int planProduction(int qta) {
		
		int retValue = 0;
		
		this.capacity += qta;
		if(this.capacity > this.maxCapacity) {
			retValue = this.capacity - this.maxCapacity;
			this.capacity = this.maxCapacity;
		}
		
		return retValue;
	}

}
