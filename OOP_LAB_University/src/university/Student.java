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
	private int[] attendedCourses;		// Contains the code of the attended courses
	
	public Student(int id, String first, String last){		
		
		this.id = id;
		this.name = first;
		this.surname = last;	
		this.numAttendedCourses = 0;
		this.attendedCourses = new int[MAX_COURSES];
		
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
	public void attendCourse(int courseCode) {
		
		this.attendedCourses[numAttendedCourses] = courseCode;
		this.numAttendedCourses++;
		
	}
	
	// Method used to verify the attendance to a certain course
	public Boolean isCourseAttended(int courseCode) {
		
		Boolean attended = false;
		
		for(int i = 0; (i < this.numAttendedCourses && !attended); i++)
			if(this.attendedCourses[i] == courseCode)
				attended = true;
		
		
		return attended;
	}
}
