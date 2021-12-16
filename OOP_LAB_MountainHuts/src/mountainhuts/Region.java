package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {

	private final static String NO_RANGE = "0-INF";
	
	private String name;
	private String[] altitudeRanges;
	
	private LinkedList<Municipality> municipalities;
	private LinkedList<MountainHut> huts;	
	
	/**
	 * Create a region with the given name.
	 * 
	 * @param name: the name of the region
	 */
	public Region(String name) {
		this.name = name;
		this.altitudeRanges = null;
		this.huts = new LinkedList<>();
		this.municipalities = new LinkedList<>();
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	
	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges: an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		this.altitudeRanges = ranges;
	}
	
	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude: the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		
		int minValue;
		int maxValue;
		String retValue = NO_RANGE;
		String[] range;
		
		// If ranges haven't been set up it will return null
		if(this.altitudeRanges == null)
			return retValue;
		
		for(String tmp : this.altitudeRanges){
			// I know the format of the string and therefore i know the number of value (of type String) are contained inside range[]
			range = tmp.split("-");
			minValue = Integer.parseInt(range[0]); 
			maxValue = Integer.parseInt(range[1]);
			
			if(altitude >= minValue && altitude <= maxValue)
				retValue = tmp;
			
		}
		
		return retValue;
	}
	

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name: the municipality name
	 * @param province: the municipality province
	 * @param altitude: the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		
		Municipality retValue;
		Iterator<Municipality> i = this.municipalities.iterator();
		
		for(; i.hasNext(); ) {			
			retValue = i.next();
			if(retValue.getName().equals(name))
				return retValue;
		}
		
		retValue = new Municipality(name, province, altitude);
		this.municipalities.add(retValue);
		
		return retValue;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		
		return this.municipalities;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name: the mountain hut name
	 * @param category: the mountain hut category
	 * @param bedsNumber: the number of beds in the mountain hut
	 * @param municipality: the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {
		
		return createOrGetMountainHut(name, null, category, bedsNumber, municipality);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name: the mountain hut name
	 * @param altitude: the mountain hut altitude
	 * @param category: the mountain hut category
	 * @param bedsNumber: the number of beds in the mountain hut
	 * @param municipality: the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		
		MountainHut retValue;
		Iterator<MountainHut> i = this.huts.iterator();
		Optional<Integer> alt = Optional.ofNullable(altitude);
		
		for(; i.hasNext(); ) {
			retValue = i.next();
			if(retValue.getName().equals(name))
				return retValue;
		}
				
		retValue = new MountainHut(name, category, bedsNumber, alt, municipality);
		this.huts.add(retValue);
				
		return retValue;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.huts;
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name: the name of the region
	 * @param file: the path of the file
	 */
	public static Region fromFile(String name, String file) {
		
		Region newRegion;
		List<String> lines = readData(file);
		
		if(lines == null) {
			newRegion = null;
		}
		else {
			newRegion = new Region(name);
			
			lines.stream().skip(1).forEach(new Consumer<String>() {
				public void accept(String line) {
					String[] fields = line.split(";");
					Integer altitude;
					
					// Create the Municipality
					Municipality tmp = newRegion.createOrGetMunicipality(fields[1], fields[0], Integer.valueOf(fields[2]));
					
					// Check if the altitude is empty
					if(fields[4].isBlank()) {
						altitude = null;
					}
					else {
						altitude = Integer.valueOf(fields[4]);
					}
					
					// Create the MountainHut
					newRegion.createOrGetMountainHut(fields[3], altitude, fields[5], Integer.valueOf(fields[6]), tmp);
				}
			});
		}
		
		return newRegion;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		
		Map<String, Long> retValue = new HashMap<>(this.municipalities.size()/2);
		
		this.municipalities.stream().map(Municipality::getProvince).forEach(new Consumer<String>(){
			public void accept(String province) {
				
				Long numInProvince = Long.valueOf(0);
				
				if(retValue.containsKey(province))
					numInProvince = retValue.get(province);	
				
				numInProvince = numInProvince + 1;
				retValue.put(province, numInProvince);
			}
		});;
		
		return retValue;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		
		Map<String, Map<String, Long>> retValue = new HashMap<>(this.municipalities.size()/2);
		Map<String, Long> insideMap = new HashMap<>((this.huts.size())/2);
		
		this.municipalities.stream().map(Municipality::getProvince).distinct().forEach(new Consumer<String>() {
			public void accept(String province) {
				
				huts.stream().filter(h -> h.getMunicipality().getProvince().equals(province)).forEach(new Consumer<MountainHut>() {
					public void accept(MountainHut hut) {
						
						Long numInMunicipality = Long.valueOf(0);
						
						if(insideMap.containsKey(hut.getMunicipality().getName()))
							numInMunicipality = insideMap.get(hut.getMunicipality().getName());
						
						numInMunicipality = numInMunicipality + 1;
						insideMap.put(hut.getMunicipality().getName(), numInMunicipality);
					}
				});
				
				retValue.put(province, insideMap);
			}
		});
		
		return retValue;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		
		Map<String, Long> retValue = new HashMap<>(this.huts.size()/2);		
		Stream<Integer> altitudes = this.huts.stream().map(new Function<MountainHut, Integer>(){
			public Integer apply(MountainHut tmp) {				
				if(tmp.getAltitude().isPresent())
					return tmp.getAltitude().get();
				else
					return tmp.getMunicipality().getAltitude();
			}
		});
		
		altitudes.map(a -> this.getAltitudeRange(a)).distinct().forEach(new Consumer<String>() {
			public void accept(String range) {
				
				
			}
		});
		
		return null;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return null;
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return null;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return null;
	}

}
