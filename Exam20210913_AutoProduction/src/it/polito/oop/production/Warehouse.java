/**
 * 
 */
package it.polito.oop.production;

import java.util.Map;
import java.util.HashMap;
/**
 * @author Matteo
 *
 * Class representing a warehouse which holds cars for a car manufacturer
 * 
 */
public class Warehouse {

	private String name;
	private int maxCapacity;
	private int capacity;
	private Map<String, Integer> parkedCars;
	
	/**
	 * Constructor of a Warehouse object
	 * 
	 * @param name: name of the warehouse
	 * @param capacity: how many cars it can hold
	 */
	public Warehouse(String name, int capacity) {
		this.name = name;
		this.maxCapacity = capacity;
		this.capacity = 0;
		this.parkedCars = new HashMap();
	}

	/**
	 * Getter method for the name of the storage
	 * 
	 * @return name of the storage
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method to park a car inside a warehouse
	 * If max capacity is reached then the car cannot be stored and the method will launch a BrandException
	 * 
	 * @param carsModel
	 * @throws BrandException
	 */
	public void parkCar(String carsModel) throws BrandException {
		
		BrandException be = new BrandException("The storage room is full!");
		int numOfCarsPerModel = 0;
		
		if(this.capacity >= this.maxCapacity)
			throw be;
		
		if(this.parkedCars.containsKey(carsModel))
			numOfCarsPerModel = this.parkedCars.get(carsModel);
		
		this.parkedCars.put(carsModel, numOfCarsPerModel+1);	
		this.capacity++;
	}
	
	/**
	 * Method to remove a car stored in a warehouse
	 * If the warehouse is empty the method will launch a BrandException
	 * If the method will not find the model specified inside the warehouse it will do nothing
	 * 
	 * @param carsModel
	 * @throws BrandException
	 */
	public void moveCar(String carsModel) throws BrandException {
		
		BrandException be = new BrandException("The storage room is empty!");
		int numOfCarsPerModel = 0;
		
		if(this.capacity <= 0)
			throw be;
		
		if(this.parkedCars.containsKey(carsModel)) {
			numOfCarsPerModel = this.parkedCars.get(carsModel);
			if(numOfCarsPerModel == 1)
				this.parkedCars.remove(carsModel);
			else
				this.parkedCars.put(carsModel, numOfCarsPerModel-1);
			
			this.capacity--;
		}		
	}
	
	/**
	 * Getter method for the parked cars inside a warehouse
	 * 
	 * @return
	 */
	public Map<String, Integer> getParkedCars() {
		return this.parkedCars;
	}
	
}
