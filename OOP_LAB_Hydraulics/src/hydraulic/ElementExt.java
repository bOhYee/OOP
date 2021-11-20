package hydraulic;

public abstract class ElementExt extends Element{

	private double maxFlowRate;
	
	public ElementExt(String name) {
		super(name);
	}

	/**
	 * Setter of the max flow rate manageable by an element
	 * If the object calling this method is a 'Source' type, this should not have any effect
	 * 
	 * @param maxFlow
	 */
	public void setMaxFlow(double maxFlow) {
		
		if(!(this instanceof Source))
			this.maxFlowRate = maxFlow;
		else
			this.maxFlowRate = 0;
	}
	
	public double getMaxFlow() {
		
		return this.maxFlowRate;
	}

}
