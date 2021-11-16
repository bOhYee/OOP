package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	public final static int MAX_CONNECTIONS_T = 2;
	
	private Element[] downstreamConnections; 
	
	/**
	 * Constructor of the complex element 'Split'
	 * @param name
	 */
	public Split(String name) {
		super(name);
		this.downstreamConnections = new Element[MAX_CONNECTIONS_T];
	}
    
	/**
	 * Returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	
    	return this.downstreamConnections.clone();
    }
    
    /*
     * Override of the connect method to disable it for the class Split since it operates in a different manner.
     */
    @Override
    public void connect(Element elem) {
    	System.out.println("Error: the Split element does not permit the call of this method without a second parameter.");
    }

    /**
     * Connect one of the outputs of this split to a downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		
		if((noutput == 0 || noutput == 1) && (this.downstreamConnections[noutput] == null)) {
			this.downstreamConnections[noutput] = elem;
		}
		else {
			System.out.println("It's impossible to connect these two elements. Check if a connection has already been made.");
		}
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
