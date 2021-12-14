package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut.
 * 
 * It is linked to a {@link Municipality}
 *
 */
public class MountainHut {

	private String name;
	private String category;
	private Integer numOfBeds;
	private Optional<Integer> altitude;
	private Municipality location;
	
	
	/*
	 * Constructor of the MountainHut class
	 */
	public MountainHut(String name, String category, Integer numOfBeds, Optional<Integer> altitude, Municipality location) {
		this.name = name;
		this.category = category;
		this.numOfBeds = numOfBeds;
		this.location = location;
		this.altitude = altitude;
	}
	
	/**
	 * Unique name of the mountain hut
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Altitude of the mountain hut.
	 * May be absent, in this case an empty {@link java.util.Optional} is returned.
	 * 
	 * @return optional containing the altitude
	 */
	public Optional<Integer> getAltitude() {
		return this.altitude;
	}

	/**
	 * Category of the hut
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Number of beds places available in the mountain hut
	 * @return number of beds
	 */
	public Integer getBedsNumber() {
		return this.numOfBeds;
	}

	/**
	 * Municipality where the hut is located
	 *  
	 * @return municipality
	 */
	public Municipality getMunicipality() {
		return this.location;
	}

}
