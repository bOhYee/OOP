package supermarket;
import java.util.*;
import java.util.stream.*;

public class Supermarket {
	
	private Map<String, List<Product>> productsByCategory;
	private Map<String, List<Integer>> categoryDiscounts;
	
	public Supermarket() {
		this.productsByCategory = new HashMap<>();
		this.categoryDiscounts = new HashMap<>();
	}
	
	//R1
	public int addProducts (String categoryName, String productNames, String productPrices) throws SMException {
		
		SMException se1 = new SMException("Category already defined!");
		SMException se2 = new SMException("One of the product is already present in a different category!");
		SMException se3 = new SMException("Number of products and prices passed as argument are incompatible!");
		String[] products = productNames.split(",");
		String[] prices = productPrices.split(",");
		List<String> s;
		List<Product> listP = new LinkedList<>();
		List<Integer> discounts = new LinkedList<Integer>();
		
		if(productsByCategory.containsKey(categoryName))
			throw se1;
		
		if(products.length != prices.length)
			throw se3;
		
		s = productsByCategory.values().stream().flatMap(l -> l.stream()).map(p -> p.getName()).collect(LinkedList::new, List::add, List::addAll);
		for(int i = 0; i < products.length; i++) {
			
			String prod = products[i];
			
			if(s.stream().filter(str -> prod.equals(str)).findAny().isPresent())
				throw se2;
			
			listP.add(new Product(prod, Double.valueOf(prices[i])));
		}
		
		productsByCategory.put(categoryName, listP);
		discounts.add(0);
		categoryDiscounts.put(categoryName, discounts);
		return listP.size();
	}

	public double getPrice (String productName) throws SMException {
		
		SMException se = new SMException("Product is not defined!");
		List<Product> prods = null;
		Optional<Product> prod = null;
		
		prods = productsByCategory.values().stream().flatMap(l -> l.stream()).collect(LinkedList::new, List::add, List::addAll);
		prod = prods.stream().filter(p -> productName.equals(p.getName())).findAny();
		if(!prod.isPresent())
			throw se;
		
		return prod.get().getPrice();
	}

	public SortedMap<String,String> mostExpensiveProductPerCategory () {
		
		Product max = null;		
		Product tmpP = null;
		String key = null;
		
		SortedMap<String, String> retValue = new TreeMap<>();
		Set<String> keys = null;
		List<Product> tmpList = null;
		
		keys = productsByCategory.keySet();
		for(Iterator<String> i = keys.iterator(); i.hasNext(); ) {
			max = new Product("", Double.MIN_VALUE);
			key = i.next();
			tmpList = productsByCategory.get(key);
			
			for(Iterator<Product> l = tmpList.iterator(); l.hasNext(); ) {
				tmpP = l.next();
				
				if(max.getPrice() <= tmpP.getPrice())
					max = tmpP;
			}
			
			retValue.put(key, max.getName());
		}
		
		return retValue;
	}

	//R2
	public void setDiscount (String categoryName, int percentage) throws SMException {
		
		SMException se1 = new SMException("Category not defined!");
		SMException se2 = new SMException("Percentage too high!");
		List<Integer> discounts = null;
		List<Product> tmpList = productsByCategory.get(categoryName);
		
		if(percentage > 40)
			throw se2;
		
		if(!productsByCategory.keySet().contains(categoryName))
			throw se1;
		
		discounts = categoryDiscounts.get(categoryName);
		discounts.add(percentage);
		categoryDiscounts.put(categoryName, discounts);
		
		setDiscount(percentage, tmpList.stream().map(p -> p.getName()).toArray(String[]::new));
		
	}

	public void setDiscount (int percentage, String... productNames) throws SMException {
		
		SMException se1 = new SMException("Product not defined!");
		SMException se2 = new SMException("Percentage too high!");
		Optional<Product> prod;
		
		if(percentage > 40)
			throw se2;
		
		List<Product> tmpList = productsByCategory.values().stream().flatMap(s -> s.stream()).collect(LinkedList::new, List::add, List::addAll);
		for(int i = 0; i < productNames.length; i++) {
			String prodName = productNames[i];
			prod = tmpList.stream().filter(p -> prodName.equals(p.getName())).findAny();
			if(prod.isPresent())
				prod.get().setDiscount(percentage);
		}
	}

	public List<Integer> getDiscountHistoryForCategory(String categoryName) {
		return categoryDiscounts.get(categoryName);
	}

	public List<Integer> getDiscountHistoryForProduct(String productName) {
		
		List<Product> tmpList = productsByCategory.values().stream().flatMap(s -> s.stream()).collect(LinkedList::new, List::add, List::addAll);
		
		return tmpList.stream().filter(p -> productName.equals(p.getName())).findAny().get().getDiscountHistory();
	}

	//R3
	public int issuePointsCard (String name, String dateOfBirth) throws SMException {
		return -1;
	}



	public void fromPointsToDiscounts (String points, String discounts) throws SMException {
	}

	public SortedMap<Integer, Integer>  getMapPointsDiscounts() {
		return null;
	}

	public int getDiscountFromPoints (int points) {
		return -1;
	}

	//R4

	public int getCurrentPoints (int pointsCardCode) throws SMException {
		return -1;
	}

	public int getTotalPoints (int pointsCardCode) throws SMException {
		return -1;
	}

	public int addPurchase (int pointsCardCode, int pointsRedeemed, String ... productNames) throws SMException {
		return -1;
	}


	public double getPurchasePrice (int purchaseCode) throws SMException {
		return -1;
	}

	public double getPurchaseDiscount (int purchaseCode) throws SMException {
		return -1;
	}

	
	//R5

	public SortedMap<Integer, List<Integer>> pointsCardsPerTotalPoints () {
		return null;
	}


	public SortedMap<String, SortedSet<String>> customersPerCategory () {
		return null;
	}

	public SortedMap<Integer, List<String>> productsPerDiscount() {
		return null;
	}


	// R6

	public int newReceipt() { // return code of new receipt
		return -1;
	}

	public void receiptAddCard(int receiptCode, int PointsCard)  throws SMException { // add the points card info to the receipt
	}

	public int receiptGetPoints(int receiptCode)  throws SMException { // return available points on points card if added before
		return -1;
	}

	public void receiptAddProduct(int receiptCode, String product)  throws SMException { // add a new product to the receipt
	}

	public double receiptGetTotal(int receiptCode)  throws SMException { // return current receipt code
		return -1;
	}

	public void receiptSetRedeem(int receiptCode, int points)  throws SMException { // sets the amount of points to be redeemed
	}

	public int closeReceipt(int receiptCode)  throws SMException { // close the receipt and add the purchase (calls addPurchase() ) and return purchase code (could be the same as receipt code)
		return -1;
	}


}