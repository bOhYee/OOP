package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	public Sink(String name) {
		super(name);
	}
	
	/**
	 * Method overridden to compute the flow for a Sink element
	 */
	@Override
	public double computeFlow(double inputFlow) {
			
		return SimulationObserver.NO_FLOW;
	}
	
}
