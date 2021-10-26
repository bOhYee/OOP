package university;

/**
 * @author MattWin
 * 
 * This class represent a course.
 *
 */
public class Course {

	private final static int MAX_STUDENTS = 100;
	
	private String name;
	private String responsibleName;
	private int numAttendees;
	private Student[] attendees;	
	
	public Course(String name, String teacher) {
		
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
	
	public void addParticipant(Student participant) {
		
		this.attendees[numAttendees] = participant;
		this.numAttendees++;
		
	}
	
	public String getParticipantsInformation() {
		
		String info = "";
		
		return info;
	}
}
