package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 * 
	 * @return Boolean indicating if the operation is successful or not
	 */
	public Boolean deleteElement(String name) {
		
		int indexOfComponent;
		Boolean retValue = false;
		Element newDownstreamElement;
						
		indexOfComponent = isDeletable(name);
		if(indexOfComponent != -1) {
			/** 
			 * I take only the first element of the returned array because the method only works
			 * with Split and Multisplit element with max one connected element 
			 */ 
			if(this.components[indexOfComponent] instanceof Multisplit)
				newDownstreamElement = ((Multisplit) this.components[indexOfComponent]).getOutputs()[0];
			else
				newDownstreamElement = this.components[indexOfComponent].getOutput();
			
			
			for(int i = 0; i < this.numberOfElements && i != indexOfComponent; i++) {
				if((this.components[i] instanceof Multisplit) && (((Multisplit) this.components[i]).getOutputs()[0] == this.components[indexOfComponent])){
					((Multisplit) this.components[i]).disconnect(0);
					((Multisplit) this.components[i]).connect(newDownstreamElement, 0);
				}
				else {
					if(this.components[i].getOutput() == this.components[indexOfComponent]) {
						this.components[i].disconnect();
						this.components[i].connect(newDownstreamElement);
					}
				}
			}
			
			for(int i = indexOfComponent; i <= (this.numberOfElements-1); i++) 
				this.components[indexOfComponent] = this.components[indexOfComponent+1];
			
			this.numberOfElements--;
			retValue = true;
		}
		
		return retValue;
	}
	
	/**
	 * Method to search the 'to be deleted' component inside the system and to check if it really is deletable without causing anomalies into the system
	 */
	private int isDeletable(String name) {
		
		int indexOfComponent;
		
		// Finding the component inside the array 
		for(indexOfComponent = 0; indexOfComponent < this.numberOfElements; indexOfComponent++) 
			if(this.components[indexOfComponent].getName().equals(name))
				break;
		
		// Check to see if the component is deletable
		if((this.components[indexOfComponent] instanceof Multisplit) && ((Multisplit) this.components[indexOfComponent]).getUsedConnections() > 1) {
			indexOfComponent = -1;
		}
		
		return indexOfComponent;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		// TODO: to be implemented
	}
	
}
