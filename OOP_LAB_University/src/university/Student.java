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
	
	/* 
	 * Between the Course and Int array there is a 1:1 relationship
	 * In other words, for the x-th attended course it is associated the x-th grade in the grades array
	 */
	private int numAttendedCourses;
	private Course[] attendedCourses;
	private int[] grades;
	
	public Student(int id, String first, String last){		
		
		this.id = id;
		this.name = first;
		this.surname = last;	
		this.numAttendedCourses = 0;
		this.attendedCourses = new Course[MAX_COURSES];
		this.grades = new int[MAX_COURSES];
		
	}
	
	// Getter for the matricule of the student
	public int getId() {
		
		return this.id;
	}
	
	// Getter for the name of the student 
	public String getName() {
		
		return this.name;
	}

	// Getter for the surname of the student
	public String getSurname() {
				
		return this.surname;
	}
	
	// Method that returns a formatted string to output the student's information
	// The string's formatting is defined in R2.
	public String toString() {
		
		return (this.id + " " + this.name + " " + this.surname);
	}
	
	// Method used to verify if the student attends more than MAX_COURSES 
	public Boolean canAttendCourse() {
		
		Boolean retValue = true;
		
		if(this.numAttendedCourses >= MAX_COURSES) {
			retValue = false;
		}
		
		return retValue;
	}
	
	// Method used to keep track of which courses every student is registered on 
	public void attendCourse(Course newAttendedCourse) {
		
		// When a student is registered for the first time to a course
		// he hasn't done the exam yet => value initialized at -1
		this.attendedCourses[numAttendedCourses] = newAttendedCourse;
		this.grades[numAttendedCourses] = -1;
		this.numAttendedCourses++;
	
	}
	
	// Method used to show information about student's attended courses
	public String showAttendedCourses() {
		
		String info = "";
		
		for(int i = 0; i < this.numAttendedCourses; i++)
			info  += this.attendedCourses[i].toString() + "\n";
		
		return info;
	}
	
	// Method that register a course's grade
	public Boolean registerGrade(int courseCode, int grade) {
		
		Boolean setGrade = false;
		
		for(int i = 0; (i < this.numAttendedCourses && !setGrade); i++) {
			if(courseCode == this.attendedCourses[i].getCode()) {
				this.grades[i] = grade;
				setGrade = true;
			}
		}
		
		return setGrade;
	}
	
	// It returns the grade for a specific exam
	public int getGrade(int courseCode) {
		
		int retValue = -1;
		
		for(int i = 0; (i < this.numAttendedCourses && retValue == -1); i++) {
			if(courseCode == this.attendedCourses[i].getCode())
				retValue = this.grades[i];
		}
		
		return retValue;
	}
	
	// It returns the average grade from all the exam
	public float getAvgGrade() {
		
		float retValue = -1;
		int totalGrade = 0;
		int numExams = 0;
		
		for(int i = 0; i < this.numAttendedCourses; i++) {
			if(this.grades[i] != -1) {
				totalGrade += this.grades[i];
				numExams++;
			}
		}
		
		if(numExams > 0)
			retValue = (float) (totalGrade/numExams);
		
		return retValue;		
	}
}
