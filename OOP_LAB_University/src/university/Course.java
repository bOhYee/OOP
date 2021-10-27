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
	private int[] attendees;			// Contains the code of the students participating
	
	public Course(int code, String name, String teacher) {
		
		this.code = code;
		this.name = name;
		this.responsibleName = teacher;
		this.numAttendees = 0;
		this.attendees = new int[MAX_STUDENTS];
		
	}
	
	// Getter of the course code
	public int getCode() {
		
		return this.code;
	}
	
	// Getter for the course's name
	public String getName() {
		
		return this.name;
	}
	
	// Getter for the course's responsible teacher's name
	public String getRespondibleName() {
		
		return this.responsibleName;
	}
	
	// Method that returns a formatted string to output the course's information
	// The string's formatting is defined in R3.
	public String toString() {
		
		return (this.code + "," + this.name + "," + this.responsibleName);
	}
	
	// Method that adds a student to a course
	public void addParticipant(int studentID) {
		
		this.attendees[numAttendees] = studentID;
		this.numAttendees++;
		
	}
	
	// Method used to verify the attendance to a certain course
	public Boolean isParticipantRegistered(int studentID) {
		
		Boolean registered = false;
		
		for(int i = 0; (i < this.numAttendees && !registered); i++)
			if(this.attendees[i] == studentID)
				registered = true;
		
		
		return registered;
	}
}
