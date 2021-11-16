package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	/*
	 * Value used to check if the Tap is open
	 */
	private Boolean isTapOpen;
	
	public Tap(String name) {
		super(name);
		isTapOpen = false;
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	public void setOpen(Boolean open){
		this.isTapOpen = open;		
	}
	
	/*
	 * Getter to verify if the tap is open
	 */
	public Boolean isTapOpen() {
		
		return this.isTapOpen;
	}
	
	/**
	 * Method overridden to compute the flow for a Tap element
	 */
	@Override
	public double computeFlow(double inputFlow) {
		
		double retValue;
		
		if(this.isTapOpen()) {
			retValue = inputFlow;
		}
		else {
			retValue = SimulationObserver.NO_FLOW;
		}
		
		return retValue;
	}

}
