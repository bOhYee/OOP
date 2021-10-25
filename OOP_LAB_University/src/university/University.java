package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	private final static int FIRST_ID_STUDENT = 10000;
	private final static int MAX_STUDENTS = 1000;
	private final static int MAX_COURSES = 50;
	
	private class Student{
		String name;
		String surname;
		
		public Student(String first, String last){
			
			this.name = first;
			this.surname = last;
			
		}
		
		public String getName() {
			
			return this.name;
		}
		
		public String getSurname() {
					
			return this.surname;
		}
	}
	
	private String universityName;
	private String rectorName;
	private String rectorSurname;
	private int enrolledStudents;
	private Student[] students;	
	
	//Constructor
	public University(String name){
		
		this.universityName  = name;
		this.rectorName = "";
		this.rectorSurname = "";
		this.enrolledStudents = 0;
		this.students = new Student[MAX_STUDENTS];
		
	}
	
	// Getter for the name of the university
	public String getName(){
		
		return this.universityName;
	}
	
	// Defines the rector for the university
	public void setRector(String first, String last){
		
		this.rectorName = first;
		this.rectorSurname = last;
		
	}
	
	// Retrieves the rector of the university
	public String getRector(){
		
		return (this.rectorName + " " + this.rectorSurname);
	}
	
	// Enroll a student in the university
	public int enroll(String first, String last){
		
		int newId;
		Student newStudent;
		
		newId = this.enrolledStudents;	
		newStudent = new Student(first, last);
		this.students[newId] = newStudent;
		this.enrolledStudents++;
		
		newId = newId + FIRST_ID_STUDENT;		
		return newId;
	}
	
	// Retrieves the information for a given student
	public String student(int id){
		
		int recoverId;
		
		recoverId = id - FIRST_ID_STUDENT;
		return (this.students[recoverId].getName() + " " + this.students[recoverId].getSurname());
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		//TODO: to be implemented
		return -1;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		//TODO: to be implemented
		return null;
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//TODO: to be implemented
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		//TODO: to be implemented
		return null;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		//TODO: to be implemented
		return null;
	}
}
