package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	/*
	 *  The velocity at which the fluid moves within the elements.
	 *  It's unit of measurement is (m^3)/hour
	 */	
	private double velocityOfFluid;
	
	public Source(String name) {
		super(name);
		velocityOfFluid = 0;
	}

	/**
	 * Defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow){
		this.velocityOfFluid = flow;
	}
	
}