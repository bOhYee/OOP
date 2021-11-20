package hydraulic;

/*
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	private final static int MAX_COMPONENTS = 100;
	
	protected int numberOfElements;
	protected Element[] components;	
	
	/*
	 * Constructor of the Hydraulic System
	 * Here variables are initialized and objects are instatiated
	 */
	public HSystem() {
		this.numberOfElements = 0;
	}
	
	/*
	 * Adds a new element to the Hydraulic System
	 */
	public void addElement(Element elem){
		
		if(this.components == null)
			this.components = new Element[MAX_COMPONENTS];
		
		this.components[numberOfElements] = elem;
		this.numberOfElements++;
	}
	
	/**
	 * Returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		
		Element[] retValue = new Element[this.numberOfElements];
		
		for(int i = 0; i < this.numberOfElements; i++)
			retValue[i] = this.components[i];
		
		return retValue;
	}
	
	/**
	 * Starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		
		for(int i = 0; i < this.numberOfElements; i++) {
		
			// If it finds a Source then the simulation begins
			if(this.components[i] instanceof Source)
				this.simulateFlow(this.components[i], SimulationObserver.NO_FLOW, observer);
		}
	}
	
	/**
	 * Recursive method to simulate the flow
	 */
	private void simulateFlow(Element component, double inputFlow, SimulationObserver observer) {
		
		double outputFlow = 0;
		Element downstreamComponent;
		Element[] downstreamComponents;		
		
		if(component == null)
			return;
		
		outputFlow = component.computeFlow(inputFlow);
		observer.notifyFlow(component.getClass().getSimpleName(), component.getName(), inputFlow, outputFlow);
		
		if(component instanceof Multisplit) {
			downstreamComponents = ((Multisplit) component).getOutputs();
			for(int i = 0; i < ((Multisplit) component).usedConnections; i++)
				simulateFlow(downstreamComponents[i], outputFlow, observer);
		}
		else {
			downstreamComponent = component.getOutput();
			simulateFlow(downstreamComponent, outputFlow, observer);
		}			
		
	}

}
