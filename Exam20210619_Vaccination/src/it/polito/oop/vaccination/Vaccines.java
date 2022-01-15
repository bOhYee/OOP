package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiConsumer;

public class Vaccines {
	
	// To make it work the way it was intended CURRENT_YEAR needs to be exactly 2021
	public final static int CURRENT_YEAR= 2021; 
	private final static String ACCEPTED_HEADER_CSV = "SSN,LAST,FIRST,YEAR";
	
	private List<Person> people;
	private List<AgeInterval> ageIntervals;
	private List<Hub> hubs;
	private int[] hours;
	
	/**
	 * Constructor of Vaccines facade class
	 */
	public Vaccines() {
		people = new LinkedList<>();
		ageIntervals = new LinkedList<>();
		hubs = new LinkedList<>();
		hours = null;
	}
	
// R1

	/**
	 * Add a new person to the vaccination system.
	 * 
	 * Persons are uniquely identified by SSN (italian "codice fiscale")
	 * 
	 * @param first first name
	 * @param last  last name
	 * @param ssn	italian "codice fiscale"
	 * @param year  birth year
	 * @return {@code false} if ssn is duplicate, 
	 */
	public boolean addPerson(String first, String last, String ssn, int year) {
		
		Boolean retValue;
		
		if(people.stream().anyMatch(p -> ssn.equals(p.getSSN()))) {
			retValue = false;
		}
		else {
			people.add(new Person(first, last, ssn, year));
			retValue = true;
		}
		
		return retValue;
	}
	
	/**
	 * Count the number of people added to the system
	 * 
	 * @return person count
	 */
	public int countPeople() {
		return ((int) people.stream().count());
	}
	
	/**
	 * Retrieves information about a person.
	 * Information is formatted as ssn, last name, and first name
	 * separate by {@code ','} (comma).
	 * 	 
	 * @param ssn "codice fiscale" of person searched
	 * @return info about the person
	 */
	public String getPerson(String ssn) {
		
		Person pers;
		String retValue = null;
		
		if(people.stream().anyMatch(p -> ssn.equals(p.getSSN()))) {
			pers = people.stream().filter(p -> ssn.equals(p.getSSN())).findAny().get();
			retValue = pers.getSSN() + "," + pers.getLastName() +  "," + pers.getFirstName();
		}
		
		return retValue;
	}
	
	
	/**
	 * Retrieves of a person given their SSN (codice fiscale).
	 * 	 
	 * @param ssn 	"codice fiscale" of person searched
	 * @return age of person (in years)
	 */
	public int getAge(String ssn) {
		
		Person pers;
		int age = -1;
		
		if(people.stream().anyMatch(p -> ssn.equals(p.getSSN()))) {
			pers = people.stream().filter(p -> ssn.equals(p.getSSN())).findAny().get();
			age = CURRENT_YEAR - pers.getBirthYear();
		}
		
		return age;
	}

	
	/**
	 * Define the age intervals by providing the breaks between intervals.
	 * The first interval always start at 0 (non included in the breaks) 
	 * and the last interval goes until infinity (not included in the breaks).
	 * All intervals are closed on the lower boundary and open at the upper one.
	 * <p>
	 * For instance {@code setAgeIntervals(40,50,60)} 
	 * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
	 * 
	 * @param breaks the array of breaks
	 */
	public void setAgeIntervals(int... breaks) {
		
		ageIntervals.add(new AgeInterval(0, breaks[0]));
		
		for(int i = 1; i < breaks.length; i++)
			ageIntervals.add(new AgeInterval(breaks[i-1], breaks[i]));
		
		ageIntervals.add(new AgeInterval(breaks[breaks.length-1], Integer.MAX_VALUE));
		
	}
	
	
	/**
	 * Retrieves the labels of the age intervals defined.
	 * 
	 * Interval labels are formatted as {@code "[0,10)"},
	 * if the upper limit is infinity {@code '+'} is used
	 * instead of the number.
	 * 
	 * @return labels of the age intervals
	 */
	public Collection<String> getAgeIntervals(){
		return ageIntervals.stream().map(a -> a.getInterval()).toList();
	}
	
	
	/**
	 * Retrieves people in the given interval.
	 * 
	 * The age of the person is computed by subtracting
	 * the birth year from current year.
	 * 
	 * @param interval age interval label
	 * @return collection of SSN of person in the age interval
	 */
	public Collection<String> getInInterval(String interval){
		
		AgeInterval a = AgeInterval.getAgeInterval(interval);
		List<String> retValue = new LinkedList<>();
		
		this.people.stream().forEach(new Consumer<Person>() {
			public void accept(Person p) {
				if(a.isInInterval(CURRENT_YEAR - p.getBirthYear()))
					retValue.add(p.getSSN());
			}
		});
		
		return retValue;
	}
	
	// R2
	/**
	 * Define a vaccination hub
	 * 
	 * @param name name of the hub
	 * @throws VaccineException in case of duplicate name
	 */
	public void defineHub(String name) throws VaccineException {
		
		VaccineException ve = new VaccineException("This hub already exists!");
		
		if(hubs.stream().map(h -> h.getName()).anyMatch(s -> s.equals(name)))
			throw ve;
		
		hubs.add(new Hub(name));
	}
		
	/**
	 * Retrieves hub names
	 * 
	 * @return hub names
	 */
	public Collection<String> getHubs() {
		return hubs.stream().map(h -> h.getName()).toList();
	}

	/**
	 * Define the staffing of a hub in terms of
	 * doctors, nurses and other personnel.
	 * 
	 * @param name name of the hub
	 * @param doctors number of doctors
	 * @param nurses number of nurses
	 * @param other number of other personnel
	 * 
	 * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
	 */
	public void setStaff(String name, int doctors, int nurses, int other) throws VaccineException {
		
		VaccineException ve1 = new VaccineException("Hub not defined!");
		VaccineException ve2 = new VaccineException("One or more values are not correct!");
		Optional<Hub> opzH;
		Hub h;
		
		if(doctors <= 0 || nurses <= 0 || other <= 0)
			throw ve2;
		
		opzH = hubs.stream().filter(a -> a.getName().equals(name)).findAny();
		if(opzH.isEmpty())
			throw ve1;
		
		h = opzH.get();
		h.setStaffConfiguration(doctors, nurses, other);
				
	}
	
	
	/**
	 * Estimates the hourly vaccination capacity of a hub
	 * 
	 * The capacity is computed as the minimum among 
	 * 10*number_doctor, 12*number_nurses, 20*number_other
	 * 
	 * @param hubName name of the hub
	 * @return hourly vaccination capacity
	 * 
	 * @throws VaccineException in case of undefined or hub without staff
	 */
	public int estimateHourlyCapacity(String hubName)  throws VaccineException {
		
		VaccineException ve1 = new VaccineException("Hub not defined!");
		VaccineException ve2 = new VaccineException("Staff not defined for this hub!");
		Optional<Hub> opzH;
		Hub h;
		int estDoc, estNur, estOth, retValue;
		
		opzH = hubs.stream().filter(a -> a.getName().equals(hubName)).findAny();
		if(opzH.isEmpty())
			throw ve1;
		
		h = opzH.get();
		if(h.isStaffDefined())
			throw ve2;
		
		estDoc = 10 * h.getNumOfDoctors();
		estNur = 12 * h.getNumOfNurses();
		estOth = 20 * h.getNumOfStaffs();
		
		// Check who's the minimum estimate
		if(estDoc <= estNur) {
			if(estDoc <= estOth)
				retValue = estDoc;
			else
				retValue = estOth;
		}
		else {
			if(estNur <= estOth)
				retValue = estNur;
			else
				retValue = estOth;
		}
		
		return retValue;
	}
	
	// R3
	/**
	 * Load people information stored in CSV format.
	 * 
	 * The header must start with {@code "SSN,LAST,FIRST"}.
	 * All lines must have at least three elements.
	 * 
	 * In case of error in a person line the line is skipped.
	 * 
	 * @param people {@code Reader} for the CSV content
	 * @return number of correctly added people
	 * @throws IOException in case of IO error
	 * @throws VaccineException in case of error in the header
	 */
	public long loadPeople(Reader people) throws IOException, VaccineException {
		
		VaccineException ve = new VaccineException("File header not recognized!");
		BufferedReader br = new BufferedReader(people);
		
		String header = br.readLine();
		String line = null;
		String[] fields = null;
		long counter = 0;
		
		if(!(header.contains(ACCEPTED_HEADER_CSV)))
			throw ve;
				
		while((line = br.readLine()) != null) {
			fields = line.split(",");
			this.addPerson(fields[2], fields[1], fields[0], Integer.valueOf(fields[3]));
			counter++;
		}

		
		br.close();
		return counter;
	}
	
	// R4
	/**
	 * Define the amount of working hours for the days of the week.
	 * 
	 * Exactly 7 elements are expected, where the first one correspond to Monday.
	 * 
	 * @param hours workings hours for the 7 days.
	 * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
	 */
	public void setHours(int... hours) throws VaccineException {
		
		VaccineException ve = new VaccineException("Hours not well defined!");
		
		if(hours.length != 7)
			throw ve;
		
		this.hours = hours;		
	}
	
	/**
	 * Returns the list of standard time slots for all the days of the week.
	 * 
	 * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
	 * they cover the number of working hours defined through method {@link #setHours}.
	 * <p>
	 * Times are formatted as {@code "09:00"} with both minuts and hours on two
	 * digits filled with leading 0.
	 * <p>
	 * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
	 * 
	 * @return the list hours for each day of the week
	 */
	public List<List<String>> getHours(){
		
		List<List<String>> retValue = new LinkedList<>();
		List<String> tmpList = null;
		
		for(int hoursPerDay : this.hours) {
			tmpList = new LinkedList<>();
			
			for(int i = 9; i < (hoursPerDay+9); i++) {
				for(int l = 0; l < 60; l = l +15) {
					tmpList.add(String.format("%02d", (long) i) + ":" + String.format("%02d", (long) l));
				}
			}		
			retValue.add(tmpList);
		}		
		
		return retValue;
	}

	/**
	 * Compute the available vaccination slots for a given hub on a given day of the week
	 * <p>
	 * The availability is computed as the number of working hours of that day
	 * multiplied by the hourly capacity (see {@link #estimateCapacity} of the hub.
	 *  
	 * @return
	 */
	public int getDailyAvailable(String hubName, int day){
		
		long hourlyEstimate;
		int retValue;
		
		try {
			hourlyEstimate = estimateHourlyCapacity(hubName);
			retValue = (int) (hourlyEstimate * this.hours[day]);
		}
		catch(VaccineException ve) {
			retValue = -1;
		}
		
		return retValue;
	}

	/**
	 * Compute the available vaccination slots for each hub and for each day of the week
	 * <p>
	 * The method returns a map that associates the hub names (keys) to the lists
	 * of number of available hours for the 7 days.
	 * <p>
	 * The availability is computed as the number of working hours of that day
	 * multiplied by the capacity (see {@link #estimateCapacity} of the hub.
	 *  
	 * @return
	 */
	public Map<String,List<Integer>> getAvailable(){
		
		Map<String, List<Integer>> retValue = null;
		Collection<String> hubsName = getHubs();
		List<Integer> weekCapacity = null;
		String hubName;
		
		retValue = new HashMap(hubsName.size()/2);
		for(Iterator<String> i = hubsName.iterator(); i.hasNext(); ) {
			
			weekCapacity = new LinkedList<>();
			hubName = i.next();
			// Loop over the days of the week
			for(int l = 0; l < 7; l++) 
				weekCapacity.add(getDailyAvailable(hubName, l));
			
			retValue.put(hubName, weekCapacity);			
		}
		
		return retValue;
	}
	
	/**
	 * Computes the general allocation plan a hub on a given day.
	 * Starting with the oldest age intervals 40% 
	 * of available places are allocated
	 * to persons in that interval before moving the the next
	 * interval and considering the remaining places.
	 * <p>
	 * The returned value is the list of SSNs (codice fiscale) of the
	 * persons allocated to that day 
	 * <p>
	 * <b>N.B.</b> no particular order of allocation is guaranteed
	 * 
	 * @param hubName  name of the hub
	 * @param day 	   day of week index (0 = Monday)
	 * @return the list of daily allocations
	 */
	public List<String> allocate(String hubName, int day){
		
		int dailyCapacity = getDailyAvailable(hubName, day);
		int remainingCapacity;
		int capacityPerAge;
		Collection<String> ageIntervals = getAgeIntervals().stream().sorted(Collections.reverseOrder()).toList(); 
		String ageInterval = null;
		Collection<String> peopleInAgeInterval = null;
		Collection<String> peopleToAdd = new LinkedList<>();
		List<String> retValue = new LinkedList<>();
		
		remainingCapacity = dailyCapacity;
		
		while(remainingCapacity != 0) {
			for(Iterator<String> i = ageIntervals.iterator(); i.hasNext(); ) {
				
				ageInterval = i.next();
				peopleInAgeInterval = getInInterval(ageInterval);
				capacityPerAge = (int) (remainingCapacity * 0.4);
				
				if((remainingCapacity - capacityPerAge) < 0)
					capacityPerAge = remainingCapacity;
			
				for(Iterator<String> l = peopleInAgeInterval.iterator(); l.hasNext(); ) {
					
					String SSN = l.next();
					Person pers = this.people.stream().filter(p -> p.getSSN().equals(SSN)).findFirst().get();
					if(!(pers.isAssigned())) {
						pers.assignForVaccination();
						peopleToAdd.add(pers.getSSN());
					}
					
				}
				
				peopleToAdd = peopleToAdd.stream().limit(capacityPerAge).toList();
				retValue.addAll(peopleToAdd);
				
				remainingCapacity = remainingCapacity - peopleToAdd.size();
				if(remainingCapacity == 0)
					break;
				
			}
		}
		
		return retValue;
	}
	
	/**
	 * Removes all people from allocation lists and 
	 * clears their allocation status
	 * 
	 */
	public void clearAllocation() {
	}
	
	/**
	 * Computes the general allocation plan for the week.
	 * For every day, starting with the oldest age intervals
	 * 40% available places are allocated
	 * to persons in that interval before moving the the next
	 * interval and considering the remaining places.
	 * <p>
	 * The returned value is a list with 7 elements, one
	 * for every day of the week, each element is a map that 
	 * links the name of each hub to the list of SSNs (codice fiscale) 
	 * of the persons allocated to that day in that hub
	 * <p>
	 * <b>N.B.</b> no particular order of allocation is guaranteed
	 * but the same invocation (after {@link #clearAllocation}) must return the same 
	 * allocation.
	 * 
	 * @return the list of daily allocations
	 */
	public List<Map<String,List<String>>> weekAllocate(){
		return null;
	}
	
	// R5
	/**
	 * Returns the proportion of allocated people
	 * w.r.t. the total number of persons added
	 * in the system
	 * 
	 * @return proportion of allocated people
	 */
	public double propAllocated() {
		return -1.0;
	}
	
	/**
	 * Returns the proportion of allocated people
	 * w.r.t. the total number of persons added
	 * in the system, divided by age interval.
	 * <p>
	 * The map associates the age interval label
	 * to the proportion of allocates people in that interval
	 * 
	 * @return proportion of allocated people by age interval
	 */
	public Map<String,Double> propAllocatedAge(){
		return null;
	}

	/**
	 * Retrieves the distribution of allocated persons
	 * among the different age intervals.
	 * <p>
	 * For each age intervals the map reports the
	 * proportion of allocated persons in the corresponding
	 * interval w.r.t the total number of allocated persons 
	 * 
	 * @return
	 */
	public Map<String,Double> distributionAllocated(){
		return null;
	}

	// R6
	/**
	 * Defines a listener for the file loading method.
	 * The {@ accept()} method of the listener is called
	 * passing the line number and the offending line.
	 * <p>
	 * Lines start at 1 with the header line.
	 * 
	 * @param listener the listener for load errors
	 */
	public void setLoadListener(BiConsumer<Integer,String> listener) {
	}

}
