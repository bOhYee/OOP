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
	Boolean isTapOpen;
	
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

}
