/**
 * 
 */
package it.polito.oop.vaccination;

import java.util.regex.*;
/**
 * @author Matteo
 *
 */
public class AgeInterval {

	private int lowerEnd;
	private int upperEnd;
	
	public AgeInterval(int min, int max){
		this.lowerEnd = min;
		this.upperEnd = max;
	}
	
	public String getInterval() {
		return ("[" + this.lowerEnd + "," + (this.upperEnd==Integer.MAX_VALUE? "+":this.upperEnd)+ ")");
	}
	
	public Boolean isInInterval(int value) {
		return (value >= this.lowerEnd && value < this.upperEnd);
	}
	
	public static AgeInterval getAgeInterval(String interval) {
		
		int min;
		int max;
		
		Pattern p = Pattern.compile(".(?<min>[0-9]+).(?<max>[0-9]+|.).");
		Matcher m = p.matcher(interval);
		
		m.find();
		min = Integer.valueOf(m.group("min"));
		
		if(m.group("max").equals("+"))
			max = Integer.MAX_VALUE;
		else
			max = Integer.valueOf(m.group("max"));
		
		return new AgeInterval(min, max);
	}

}
