package it.polito.oop.production;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.*;

/**
 * Facade class used to interact with the system.
 *
 */
public class Carmaker {

	/** unique code for diesel engine **/
	public static final int DIESEL = 0;
	/** unique code for gasoline engine **/
	public static final int GASOLINE = 1;
	/** unique code for gpl engine **/
	public static final int GPL = 2;
	/** unique code for electric engine **/
	public static final int ELECTRIC = 3;

	
	private List<Model> models;
	private List<Factory> productionSites;
	private List<Warehouse> storages;
	private float minIS;
	private float maxIS;
	
	public Carmaker() {
		this.models = new LinkedList<>();
		this.productionSites = new LinkedList<>();
		this.storages = new LinkedList<>();
	}
	
	
	// **************** R1 ********************************* //

	/**
	 * Add a new model to the brand factory.
	 * 
	 * Models are uniquely identified by a code
	 * 
	 * @param code 	code
	 * @param name  name
	 * @param year	year of introduction in the market
	 * @param displacement  displacement of the engine in cc
	 * @param enginetype	the engine type (e.g., gasoline, diesel, electric)
	 * @return {@code false} if code is duplicate, 
	*/
	public boolean addModel(String code, String name,  int year, float displacement, int enginetype) {
		
		Boolean retValue = false;
		Optional<Model> mod = this.models.stream().filter(m -> code.equals(m.getCode())).findFirst();
		
		if(mod.isEmpty()) {
			retValue = true;
			this.models.add(new Model(code, name, year, displacement, enginetype));
		}
			
		return retValue;
	}
	
	/**
	 * Count the number of models produced by the brand
	 * 
	 * @return models count
	 */
	public int countModels() {
		return this.models.size();
	}
	
	/**
	 * Retrieves information about a model.
	 * Information is formatted as code, name, year, displacement, enginetype
	 * separate by {@code ','} (comma).
	 * 	 
	 * @param code code of the searched model
	 * @return info about the model
	 */
	public String getModel(String code) {
		
		String retValue = null;
		Optional<Model> mod = this.models.stream().filter(m -> code.equals(m.getCode())).findFirst();
		
		if(mod.isPresent())
			retValue = mod.get().toString();
		
		return retValue;
	}
	
	
	/**
	 * Retrieves the list of codes of active models.
	 * Active models not older than 10 years with respect to the execution time.
	 * 	 
	 * @return a list of codes of the active models
	 */
	public List<String> getActiveModels() {
		
		return this.models.stream().filter(m -> m.isModelActive()).map(m -> m.getCode()).collect(LinkedList::new, List::add, List::addAll);
	}
	
	
	/**
	 * Loads a list of models from a file.
	 * @param Filename path of the file
	 * @throws IOException in case of IO problems
	 */
	public void loadFromFile(String Filename) throws IOException  {
		
		FileReader file = new FileReader(Filename);
		BufferedReader input = new BufferedReader(file);
		
		input.lines().forEach(new Consumer<String>() {
			public void accept(String line) {				
				String[] modelData = line.split("\t");
				addModel(modelData[0], modelData[1], Integer.valueOf(modelData[2]), Float.valueOf(modelData[3]), Integer.valueOf(modelData[4]));
			}
		});
		
	}
	
	// **************** R2 ********************************* //

	
	
	/**
	 * Creates a new factory given its name. Throws Brand Exception if the name of the factory already exists.
	 * 
	 * @param name the unique name of the factory.
	 * @throws BrandException
	 */
	public void buildFactory(String name) throws BrandException {
		
		BrandException be = new BrandException("Factory name already present. Be more creative.");
		Optional<Factory> fac = this.productionSites.stream().filter(p -> name.equals(p.getName())).findFirst();
		
		if(fac.isPresent())
			throw be;
		
		this.productionSites.add(new Factory(name));
	}
	
	
	
	/**
	 * Returns a list of the factory names. The list is empty if no factories are created.
	 * @return A list of factory names.
	 */
	public List<String> getFactories() {
		return this.productionSites.stream().map(p -> p.getName()).collect(LinkedList::new, List::add, List::addAll);
	}
	
	
	/**
	 * Create a set of production lines for a factory.
	 * Each production line is identified by name,capacity and type of engines it can handle.
	 * 
	 * @param fname The factory name
	 * @parm  line	An array of strings. Each string identifies a production line.
	 * 
	 * @throws BrandException if factory name is not defined or line specification is malformed
	 */
	public void setProductionLines (String fname, String... line) throws BrandException {
	
		BrandException be1 = new BrandException("Factory not found. Make sure it is already defined before starting this operation!");
		BrandException be2 = new BrandException("Parameters not well defined! Interrupting operations...");
		Optional<Factory> fac = this.productionSites.stream().filter(p -> fname.equals(p.getName())).findAny();
		Factory f = null;
		String[] lineData;
		
		if(fac.isEmpty())
			throw be1;
		
		f = fac.get();
		for(int i = 0; i < line.length; i++) {
			
			lineData = line[i].split(":");
			if(lineData.length != 3)
				throw be2;
			
			f.setUpProductionLine(lineData[0], Integer.valueOf(lineData[1]), Integer.valueOf(lineData[2]));			
		}		
		
	}
	
	/**
	 * Returns a map reporting for each engine type the yearly production capacity of a factory.
	 * 
	 * @param fname factory name
	 * @return A map of the yearly productions
	 * @throws BrandException if factory name is not defined or it has no lines
	 */
	public Map<Integer, Integer> estimateYearlyProduction(String fname) throws BrandException {
		
		BrandException be1 = new BrandException("Factory not found. Make sure it is already defined before starting this operation!");
		BrandException be2 = new BrandException("No production lines defined for this factory!");
		Optional<Factory> fac = this.productionSites.stream().filter(p -> fname.equals(p.getName())).findAny();
		Factory f = null;
		int prodCapacity;
		Map<Integer, Integer> retValue = new HashMap<>();
		
		if(fac.isEmpty())
			throw be1;
		
		f = fac.get();
		if(f.countProductionLines() == 0)
			throw be2;
		
		for(int i = 0; i < 4; i++) {
			prodCapacity = f.carsProducedWithMotorization(i);
			if(prodCapacity > 0)
				retValue.put(i,prodCapacity);
		}
		
		return retValue;
	}

	// **************** R3 ********************************* //

	
	/**
	 * Creates a new storage for the car maker
	 * 
	 * @param name		Name of the storage
	 * @param capacity	Capacity (number of cars) of the storage
	 * @throws BrandException if name already defined or capacity &le; 0
	 */
	public void buildStorage (String name, int capacity) throws BrandException {
		
		BrandException be1 = new BrandException("Error: capacity is negative or equal to zero.");
		BrandException be2 = new BrandException("Storage already exists.");
		
		if(capacity <= 0)
			throw be1;
		
		if(this.storages.stream().filter(s -> s.getName().equals(name)).findAny().isPresent())
			throw be2;
		
		this.storages.add(new Warehouse(name, capacity));		
	}
	
	/**
	 * Retrieves the names of the available storages. 
	 * The list is empty if no storage is available
	 * 
	 * @return List<String> list of storage names
	 */
	public List<String> getStorageList() {
		
		List<String> retValue = new LinkedList<String>();
		retValue.addAll(this.storages.stream().map(s -> s.getName()).collect(LinkedList::new, List::add, List::addAll));
		
		return retValue;
	}
	
	/**
	 * Add a car to the storage if possible
	 * 
	 * @param sname		storage name
	 * @param model		car model
	 * 
	 * @throws BrandException if storage or model not defined or storage is full
	 */
	public void storeCar(String sname, String model) throws BrandException {
		
		BrandException be1 = new BrandException("Storage not found!");
		BrandException be2 = new BrandException("Model not found!");
		Boolean s1, s2;
		Optional<Warehouse> w = null;
		
		w = this.storages.stream().filter(s -> s.getName().equals(sname)).findAny();
		s1 = w.isEmpty();
		s2 = this.models.stream().filter(m -> m.getCode().equals(model)).findAny().isEmpty();
		
		if(s1)
			throw be1;
		
		if(s2)
			throw be2;
		
		w.get().parkCar(model);
	}
	
	/**
	 * Remove a car to the storage if possible.
	 * 
	 * @param sname		Storage name
	 * @param model		Car model
	 * @throws BrandException  if storage or model not defined or storage is empty
	 */
	public void removeCar(String sname, String model) throws BrandException {
		
		BrandException be1 = new BrandException("Storage not found!");
		BrandException be2 = new BrandException("Model not found!");
		Boolean s1, s2;
		Optional<Warehouse> w = null;
		
		w = this.storages.stream().filter(s -> s.getName().equals(sname)).findAny();
		s1 = w.isEmpty();
		s2 = this.models.stream().filter(m -> m.getCode().equals(model)).findAny().isEmpty();
		
		if(s1)
			throw be1;
		
		if(s2)
			throw be2;
		
		w.get().moveCar(model);
	}
	
	/**
	 * Generates a summary of the storage.
	 * 
	 * @param sname		storage name
	 * @return map of models vs. quantities
	 * @throws BrandException if storage is not defined
	 */
	public Map<String,Integer> getStorageSummary(String sname) throws BrandException {
		
		BrandException be1 = new BrandException("Storage not found!");
		Optional<Warehouse> w = null;
		
		w = this.storages.stream().filter(s -> s.getName().equals(sname)).findAny();
		if(w.isEmpty())
			throw be1;
		
		return w.get().getParkedCars();
	}
	
	// **************** R4 ********************************* //
	

	/**
	 * Sets the thresholds for the sustainability level.
	 * 
	 * @param ismin	lower threshold
	 * @param ismax upper threshold
	 */
	public void setISThresholds (float ismin, float ismax) {
		this.minIS = ismin;
		this.maxIS = ismax;
	}
	
	
	
	
	/**
	 * Retrieves the models classified in the given Sustainability class.
	 * 
	 * @param islevel sustainability level, 0:low 1:medium 2:high
	 * @return the list of model names in the class
	 */
	public List<String> getModelsSustainability(int islevel) {
		
		Predicate<Model> p = new Predicate<Model>() {
			public boolean test(Model m) {
			
				boolean retValue = false;
				float mIS;
				
				mIS = m.calculateIS();
				switch(islevel) {
					case 0:
						retValue = (mIS < minIS);
						break;
					case 1:
						retValue = ((mIS >= minIS) && (mIS <= maxIS));
						break;
					case 2:
						retValue = (mIS > maxIS);
						break;
				}
								
				return retValue;
			}
		};
		
		return this.models.stream().filter(p).map(m -> m.getCode()).collect(LinkedList::new, List::add, List::addAll);
	}
	
	
	/**
	 * Computes the Carmaker Sustainability level
	 * 
	 * @return sustainability index
	 */
	public float getCarMakerSustainability() {
		
		Warehouse storage = null;
		Map<String, Integer> storageCars = null;
		Map<String, Integer> totalMap = new HashMap<>();
		
		int tmpCarForModel = 0;
		int carProcessed = 0;
		float averageIS = 0;
		
		for(Iterator<Warehouse> i = this.storages.iterator(); i.hasNext(); ) {
			storage = i.next();
			storageCars = storage.getParkedCars();
			
			for(Iterator<String> key = storageCars.keySet().iterator(); key.hasNext(); ) {
				String tmpKey = key.next();
				
				if(totalMap.containsKey(tmpKey))
					tmpCarForModel = totalMap.get(tmpKey);
				else
					tmpCarForModel = 0;
				
				totalMap.put(tmpKey, tmpCarForModel + storageCars.get(tmpKey));				
			}
			
		}
		
		for(Iterator<String> key = totalMap.keySet().iterator(); key.hasNext(); ) {
			String tmpKey = key.next();
			
			tmpCarForModel = totalMap.get(tmpKey);
			averageIS += ((float) tmpCarForModel) * models.stream().filter(m -> m.getCode().equals(tmpKey)).findFirst().get().calculateIS();	
			carProcessed += tmpCarForModel;
		}
		
		return (averageIS/carProcessed);
	}
	
	// **************** R5 ********************************* //

	/**
	 * Generates an allocation production plan
	 * 
	 * @param request allocation request string
	 * @return {@code true} is planning was successful
	 */
	public boolean plan(String request) {
		
		int qta;
		boolean retValue = true;
		String[] requests = request.split(",");
		Factory ps = null;		
		
		for(int i = 0; i < requests.length; i++) {
			qta = Integer.valueOf(requests[i].split(":")[1]);

			for(Iterator<Factory> f = this.productionSites.iterator(); f.hasNext() && qta != 0; ) {
				ps = f.next();
				qta = ps.planProduction(qta);
			}
			
			if(qta != 0){
				retValue = false;
				break;
			}
			
		}
		
		return retValue;
	}
	
	
	
	/**
	 * Returns the capacity of a line
	 * 
	 * @param fname factory name
	 * @param lname line name
	 * @return total capacity of the line
	 */
	public int getLineCapacity(String fname, String lname) {
		
		// Assuming that the parameters are always right
		Factory fac = this.productionSites.stream().filter(f -> fname.equals(f.getName())).findFirst().get();
		
		return fac.getLineMaxCapacity(lname);
	}
	
	/**
	 * Returns the allocated capacity of a line
	 * @param fname factory name
	 * @param lname line name
	 * @return already allocated capacity for the line
	 */
	public int getLineAllocatedCapacity(String fname, String lname) {
		
		// Assuming that the parameters are always right
		Factory fac = this.productionSites.stream().filter(f -> fname.equals(f.getName())).findFirst().get();
		
		return fac.getLineAllocatedCapacity(lname);
	}
	
	
	// **************** R6 ********************************* //
	
	/**
	 * compute the proportion of lines that are fully allocated
	 * (i.e. allocated capacity == total capacity) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of lines fully allocated
	 */
	public float linesfullyAllocated() {
		
		int totalPL = 0;
		int totalAllocatedPL = 0;
		float retValue = 0;
		Factory ps = null;
		
		for(Iterator<Factory> i = this.productionSites.iterator(); i.hasNext(); ) {
			ps = i.next();
			totalPL += ps.countProductionLines();
			totalAllocatedPL += ps.countLine(1);
		}
		
		retValue = (float) totalAllocatedPL/totalPL;
		return retValue;
	}

	/**
	 * compute the proportion of lines that are unused
	 * (i.e. allocated capacity == 0) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of unused lines
	 */
	public float unusuedLines() {
		
		int totalPL = 0;
		int totalAllocatedPL = 0;
		float retValue = 0;
		Factory ps = null;
		
		for(Iterator<Factory> i = this.productionSites.iterator(); i.hasNext(); ) {
			ps = i.next();
			totalPL += ps.countProductionLines();
			totalAllocatedPL += ps.countLine(2);
		}
		
		retValue = (float) totalAllocatedPL/totalPL;
		return retValue;
	}

}
