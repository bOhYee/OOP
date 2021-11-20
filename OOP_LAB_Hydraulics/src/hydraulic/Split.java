package hydraulic;

/**
 * Represents a split element, a.k.a. T element, an extension of Multi-split element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Multisplit {

	private final static int MAX_CONNECTIONS_T = 2;
		
	/**
	 * Constructor of the complex element 'Split'
	 * @param name
	 */
	public Split(String name) {
		super(name, MAX_CONNECTIONS_T);
	}
	    
	/**
	 * Method overridden to compute the flow for a Split element
	 * The return value is a single 'double' only because the outputFlow value is the same whether or not there is
	 * an element connected 
	 */
	@Override
	public double computeFlow(double inputFlow) {
		
		return (inputFlow/2);
	}
}
