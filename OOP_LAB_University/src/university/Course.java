package university;

/**
 * @author MattWin
 * 
 * This class represent a course.
 *
 */
public class Course {

	private final static int MAX_STUDENTS = 100;
	
	private int code;
	private String name;
	private String responsibleName;
	private int numAttendees;
	private Student[] attendees;	
	
	public Course(int code, String name, String teacher) {
		
		this.code = code;
		this.name = name;
		this.responsibleName = teacher;
		this.numAttendees = 0;
		this.attendees = new Student[MAX_STUDENTS];
		
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getRespondibleName() {
		
		return this.responsibleName;
	}
	
	// Method that returns a formatted string to output the course's information
	// The string's formatting is defined in R3.
	public String toString() {
		
		return (this.code + "," + this.name + "," + this.responsibleName);
	}
	
	// Method used to verify if a course can get one more participant or it is already full 
	public Boolean canGetParticipant() {
		
		Boolean retValue = true;
		
		if(this.numAttendees >= MAX_STUDENTS) {
			retValue = false;
		}
		
		return retValue;
	}
	
	// Method used to add a student to the list of attendees of a course
	public void addParticipant(Student participant) {
		
		this.attendees[numAttendees] = participant;
		this.numAttendees++;
		
	}
	
	// Method used to get a course's participants information
	public String getParticipantsInformation() {
		
		String info = "";
				
		for(int i = 0; i < this.numAttendees; i++) 
			info += this.attendees[i].toString() + "\n"; 
		
		return info;
	}
}
