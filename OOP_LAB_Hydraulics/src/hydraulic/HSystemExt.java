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
	 * Starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		
		for(int i = 0; i < this.numberOfElements; i++) {
			
			// If it finds a Source then the simulation begins
			if(this.components[i] instanceof Source)
				simulateFlowExt(this.components[i], SimulationObserverExt.NO_FLOW, observer, enableMaxFlowCheck);
		}
	}
	
	/**
	 * An extension of the simulateFlow method
	 * It has the same effect as the first version but here it checks if the input flow rate
	 * of an element is greater than the max flow rate expected
	 * 
	 * @param component: Source element which start the simulation 
	 * @param inputFlow: initial value of the flow rate
	 * @param observer: object used to print the notification
	 * @param enableMaxFlowCheck: Boolean that indicates if the checks for the max flow rate has to be done
	 */
	private void simulateFlowExt(Element component, double inputFlow, SimulationObserverExt observer, Boolean enableMaxFlowCheck) {
			
		double maxFlow;
		double[] outputFlow;
		Element downstreamComponent;
		Element[] downstreamComponents;		
		
		if(component == null)
			return;
		
		if((enableMaxFlowCheck) && (component instanceof ElementExt)) {
			maxFlow = ((ElementExt) component).getMaxFlow();
			if(maxFlow > 0 && inputFlow > maxFlow)
				observer.notifyFlowError(component.getClass().getSimpleName(), component.getName(), inputFlow, maxFlow);
		}
		
		if(component.getClass().getSimpleName().equals("Multisplit")) {
			outputFlow = ((Multisplit) component).computeFlows(inputFlow);
		}
		else{
			outputFlow = new double[1];
			outputFlow[0] = component.computeFlow(inputFlow);
		}
		
		observer.notifyFlow(component.getClass().getSimpleName(), component.getName(), inputFlow, outputFlow);
		
		if(component instanceof Multisplit) {
			downstreamComponents = ((Multisplit) component).getOutputs();
			for(int i = 0; i < ((Multisplit) component).usedConnections; i++)
				simulateFlowExt(downstreamComponents[i], outputFlow[i], observer, enableMaxFlowCheck);
		}
		else {
			downstreamComponent = component.getOutput();
			simulateFlowExt(downstreamComponent, outputFlow[0], observer, enableMaxFlowCheck);
		}			
	
	}
	
}
