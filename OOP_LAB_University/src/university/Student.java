package university;

/**
 * @author MattWin
 * 
 * This class represents a student.
 *
 */
public class Student {
	
	private final static int MAX_COURSES = 25;
	
	private int id;
	private String name;
	private String surname;
	
	private int numAttendedCourses;
	private Course[] attendedCourses;
	
	public Student(int id, String first, String last){		
		
		this.id = id;
		this.name = first;
		this.surname = last;	
		this.numAttendedCourses = 0;
		this.attendedCourses = new Course[MAX_COURSES];
		
	}
	
	// Getter for the name of the student 
	public String getName() {
		
		return this.name;
	}

	// Getter for the surnname of the student
	public String getSurname() {
				
		return this.surname;
	}
	
	// Method that returns a formatted string to output the student's information
	// The string's formatting is defined in R2.
	public String toString() {
		
		return (this.id + " " + this.name + " " + this.surname);
	}
	
	
	// Method used to keep track of which courses every student is registered on 
	public void attendCourse(Course newAttendedCourse) {
		
		this.attendedCourses[numAttendedCourses] = newAttendedCourse;
		this.numAttendedCourses++;
		
	}
	
	// Method used to show information about student's attended courses
	public String showAttendedCourses() {
		
		String info = "";
		
		for(int i = 0; i < this.numAttendedCourses; i++)
			info  += this.attendedCourses[i].toString() + "\n";
		
		return info;
	}
}
