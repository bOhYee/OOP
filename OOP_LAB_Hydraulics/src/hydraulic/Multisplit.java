package hydraulic;

/**
 * Represents a multisplit element, an extension Element that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */
public class Multisplit extends Element {

	private final static int MIN_INDEX_CONNECTION = 0;
	private int totalConnections;
	
	protected int usedConnections;
	protected Element[] downstreamConnections;
	protected double[] proportions;
	
	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name); 
		this.totalConnections = numOutput;
		this.downstreamConnections = new Element[this.totalConnections];
		this.proportions = new double[this.totalConnections];
	}
	
	/**
	 * @return the usedConnections
	 */
	public int getUsedConnections() {
		return usedConnections;
	}
	
	/**
	 * @return the totalConnections
	 */
	public int getTotalConnections() {
		return totalConnections;
	}
    
	/**
	 * Returns the downstream elements (not a copy of the internal state of the Multi-split, like it's done for the Split element)
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	
    	Element[] retValue; 
    	
    	if(this.usedConnections > 0) {
    		retValue = new Element[this.usedConnections];
	    	
	    	for(int i = 0, j = 0; i < this.totalConnections; i++)
	    		if(this.downstreamConnections[i] != null) 
	    			retValue[j++] = this.downstreamConnections[i];
    	}
    	else {
    		retValue = null;
    	}
    	
    	return retValue;    	
    }

    /**
     * Connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		
    	if((noutput >= MIN_INDEX_CONNECTION && noutput < this.totalConnections) && this.downstreamConnections[noutput] == null) {
    		this.downstreamConnections[noutput] = elem;
    		this.usedConnections++;
    	}
    	else {
    		System.out.println("It's impossible to connect these two elements. Check if a connection has already been made.");
    	}
	}
	
	/**
	 * Disconnects the element from the previous downstream element connected
	 */
	public void disconnect(int noutput) {
	
		if(noutput >= MIN_INDEX_CONNECTION && noutput < this.totalConnections) {
			this.downstreamConnections[noutput] = null;
		}
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		
		if(checkNumOfProportions(proportions) && checkProportionsAddToOne(proportions)) {
			for(int i = 0, j = 0; i < this.totalConnections; i++)
				if(this.downstreamConnections[i] != null)
					this.proportions[i] = proportions[j++];
		}
		else {
			System.out.println("Parameters sent to the method invalid.");
		}		
	}
	
	/**
	 * Used to verify if the number of proportions match the number of components connected to the Multi-split
	 */
	private Boolean checkNumOfProportions(double[] proportions) {
		
		return this.usedConnections == proportions.length;
	}
	
	/**
	 * Used to verify if the proportions sent to the object are valid (add to 1)
	 */
	private Boolean checkProportionsAddToOne(double[] proportions) {

		double total = 0;
		
		for(int i = 0; i < proportions.length; i++)
			total += proportions[i];
		
		return total == 1;		
	}	

	/*
     * Override of the computeFlow method to disable it for the class Multisplit since it operates in a different manner.
     */
    @Override
    public double computeFlow(double inputFlow) {
    	System.out.println("Error: the Split element does not permit the call of this method without a second parameter.");
    	return -1;
    }

    /**
	 * Method that returns a double array with the flows passing through the different connections 
	 */
	public double[] computeFlows(double inputFlow) {
		
		double[] retValue;
    	
    	if(this.usedConnections > 0) {
    		retValue = new double[this.usedConnections];
	    	
	    	for(int i = 0, j = 0; i < this.totalConnections; i++)
	    		if(this.downstreamConnections[i] != null) 
	    			retValue[j++] = this.proportions[i] * inputFlow;
    	}
    	else {
    		retValue = null;
    	}
    	
    	return retValue;  		
	}
}
