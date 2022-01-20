/**
 * 
 */
package supermarket;
import java.util.*;

/**
 * @author Matteo
 *
 */
public class Product {

	private String name;
	private double price;
	private List<Integer> discounts;
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
		this.discounts = new LinkedList<Integer>();
		this.discounts.add(0);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	public void setDiscount(int percentage) {
		this.discounts.add(percentage);
	}
	
	public List<Integer> getDiscountHistory(){
		return discounts;
	}
	
}
