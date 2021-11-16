package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	
	private String name;
	private Element downstreamComponent;
	
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	public Element(String name){
		this.name = name;
		this.downstreamComponent = null;
	}

	/*
	 * Getter method
	 * @return the name of the element
	 */
	public String getName(){
		
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * If the object on which it is called the method is 'Sink', then the connection must not take effect
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem){
		
		if(this.downstreamComponent != null) {
			System.out.println("This " + this.getName() + " has already a connection with another element of type: " + this.downstreamComponent.getName());
		}
		else {
			if(!(this instanceof Sink))
				this.downstreamComponent = elem;
		}
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		
		return this.downstreamComponent;
	}
	
	/**
	 * Method that has to be overridden by subclasses in order to give the right output flow of an element
	 */
	public abstract double computeFlow(double inputFlow);
}
