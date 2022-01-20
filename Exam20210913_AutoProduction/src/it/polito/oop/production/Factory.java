/**
 * 
 */
package it.polito.oop.production;

import java.util.List;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.Iterator;

/**
 * @author Matteo
 * 
 * Class representing a production site for a car manufacturer
 * 
 */
public class Factory {

	private String name;
	private List<ProductionLine> prodLines;
	
	/**
	 * Constructor of a production site
	 * 
	 * @param name: name of the factory
	 */
	public Factory(String name) {
		this.name = name;
		this.prodLines = new LinkedList<>();
	}

	/**
	 * Getter method for the name of the factory
	 * 
	 * @return name: name of the factory
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method used to create a production line or to update its information
	 * 	
	 * @param lineName: name of the production line
	 * @param capacity: cars produced annually by the line
	 * @param engineType: one of 4 possible engine types defined in Carmaker class
	 */
	public void setUpProductionLine(String lineName, int capacity, int engineType) {
		
		Optional<ProductionLine> pl = this.prodLines.stream().filter(l -> lineName.equals(l.getName())).findAny();
		
		if(pl.isEmpty()) {
			this.prodLines.add(new ProductionLine(lineName, capacity, engineType));
		}
		else {
			pl.get().setCapacity(capacity);
		}
	
	}

	/**
	 * Returns the number of production lines for the site
	 * 
	 * @return number of production lines
	 */
	public int countProductionLines() {
		return this.prodLines.size();
	}
	
	public int carsProducedWithMotorization(int motorization) {
		
		int retValue = 0;
		Stream<ProductionLine> pls = this.prodLines.stream().filter(l -> l.getMotorization() == motorization);												
		
		for(Iterator<ProductionLine> i = pls.iterator(); i.hasNext(); )
			retValue = retValue + i.next().getMaxCapacity();			
		
		return retValue;
	}
	
	/**
	 * Plan the production of a certain number of cars
	 * If a production line is full then it tries to produce them through another line, until all of the lines are full
	 * 
	 * @param qta: number of cars to be produced
	 * @return retValue: number of cars that this factory cannot produce
	 */
	public int planProduction(int qta) {
				
		for(Iterator<ProductionLine> pl = prodLines.iterator(); pl.hasNext() && qta != 0; ) 
			qta = pl.next().planProduction(qta);
		
		return qta;
	}
	
	/**
	 * Return the maximum capacity associated with a production line
	 * 
	 * @param lname: name of the production line
	 * @return max capacity of the line
	 */
	public int getLineMaxCapacity(String lname) {
		
		// Assuming the parameter is always correct
		ProductionLine prodLine = this.prodLines.stream().filter(pl -> lname.equals(pl.getName())).findFirst().get();
		
		return prodLine.getMaxCapacity();
	}
	
	/**
	 * Return the allocated capacity associated with a production line
	 * 
	 * @param lname: name of the production line
	 * @return allocated capacity of the line
	 */
	public int getLineAllocatedCapacity(String lname) {
				
		// Assuming the parameter is always correct
		ProductionLine prodLine = this.prodLines.stream().filter(pl -> lname.equals(pl.getName())).findFirst().get();
		
		return prodLine.getAllocatedCapacity();
	}
	
	/**
	 * Count the production line with specific criteria 
	 * 
	 * @param request: defines the criteria applied for counting the lines
	 * 				   1 - fully allocated; 2 - not allocated (unused)
	 * @return counter
	 */
	public int countLine(int request) {
		
		int counter = 0;
		ProductionLine pl = null;
		
		for(Iterator<ProductionLine> i = this.prodLines.iterator(); i.hasNext(); ) {
			pl = i.next();
			
			if(request == 1 && pl.getAllocatedCapacity() == pl.getMaxCapacity())
				counter++;
			
			if(request == 2 && pl.getAllocatedCapacity() == 0)
				counter++;
		}
				
		return counter;
	}
	
	
}
