package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	protected final static int FIRST_ID_STUDENT = 10000;
	protected final static int FIRST_COURSE_CODE = 10;
	private final static int MAX_STUDENTS = 1000;
	private final static int MAX_COURSES = 50;
		
	private String universityName;
	private String rectorName;
	private String rectorSurname;
	private int enrolledStudents;
	private int activeCourses;
	protected Student[] students;	
	protected Course[] courses;
	
	//Constructor
	public University(String name){
		
		this.universityName  = name;
		this.rectorName = "";
		this.rectorSurname = "";
		this.enrolledStudents = 0;
		this.activeCourses = 0;
		this.students = new Student[MAX_STUDENTS];
		this.courses = new Course[MAX_COURSES];
		
	}
	
	// Set of private method for successive usage
	protected Boolean isCourseCodeValid(int toVerifyCourseCode) {
		
		Boolean retValue;	// Return value
		
		retValue = false;
		toVerifyCourseCode = toVerifyCourseCode - FIRST_COURSE_CODE;
		
		if(toVerifyCourseCode >= 0 && toVerifyCourseCode < this.activeCourses)
			retValue = true;
		
		return retValue;
	}
	
	protected Boolean isStudentIdValid(int toVerifyStudentId) {
		
		Boolean retValue;	// Return value
		
		retValue = false;
		toVerifyStudentId = toVerifyStudentId - FIRST_ID_STUDENT;
		
		if(toVerifyStudentId >= 0 && toVerifyStudentId < this.enrolledStudents)
			retValue = true;
		
		return retValue;
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
	
	/* 
	 * Enroll a student in the university
	*  @return : newId >= FIRST_ID_STUDENT => ok 
	*  			 newId < 0 => error
	*  					-1 => there isn't any space available for the student
	*/
	public int enroll(String first, String last){
		
		int position;			// Position of the student in the array
		int newId;				// Real ID of the student
		Student newStudent;
		
		if(this.enrolledStudents >= MAX_STUDENTS) {
			// if there isn't anymore space available for the student the method returns an error value (as indicated above)
			newId = -1;
		}
		else {
			position = this.enrolledStudents;	
			newId = position + FIRST_ID_STUDENT;
			newStudent = new Student(newId, first, last);
			
			this.students[position] = newStudent;
			this.enrolledStudents++;
		}		
		
		return newId;
	}
	
	/* 
	*  Retrieves the information for a given student
	*  @return : String <> "" => ok 
	*  			 String == "" => there isn't any student with that id => error
	*/
	public String student(int id){
		
		int recoverId;
		String retValue = ""; // Return value
		
		if(isStudentIdValid(id)) {
			recoverId = id - FIRST_ID_STUDENT;			
			retValue = this.students[recoverId].toString();
		}
		
		return retValue;
	}
	
	/* 
	 * Activates a new course with the given teacher
	*  @return : newId >= FIRST_COURSE_CODE => ok 
	*  			 newId < 0 => error
	*  					-1 => there isn't any space available for a new course
	*/
	public int activate(String title, String teacher){
		
		int position;			// Position of the student in the array
		int newId;				// Real ID of the student
		Course newCourse;
		
		if(this.activeCourses >= MAX_COURSES) {
			// if there isn't anymore space available for a new course the method returns an error value (as indicated above)
			newId = -1;
		}
		else {
			position = this.activeCourses;
			newId = position + FIRST_COURSE_CODE;
			newCourse = new Course(newId, title, teacher);
			
			this.courses[position] = newCourse;
			this.activeCourses++;
		}	
		
		return newId;
	}
	
	/* 
	*  Retrieve the information for a given course.
	*  @return : String <> "" => ok 
	*  			 String == "" => there isn't any course with that code => error
	*/
	public String course(int code){
		
		int recoverId;
		String retValue = ""; // Return value
		
		if(isCourseCodeValid(code)) {
			recoverId = code - FIRST_COURSE_CODE;
			retValue = this.courses[recoverId].toString();
		}
		
		return retValue;
	}
	
	// Register a student to attend a course
	public void register(int studentID, int courseCode){
		
		if(isStudentIdValid(studentID) && isCourseCodeValid(courseCode)) {
			studentID = studentID - FIRST_ID_STUDENT;
			courseCode = courseCode - FIRST_COURSE_CODE;
			
			if(this.students[studentID].canAttendCourse() && this.courses[courseCode].canGetParticipant()) {
				this.courses[courseCode].addParticipant(this.students[studentID]);
				this.students[studentID].attendCourse(this.courses[courseCode]);
			}
			else {
				System.out.println("Impossible to register the student to the selected course.");
			}						
		}
		else {
			System.out.println("StudentId or Course code are not valid!\n Please check them.");
		}
		
	}
	
	/* 
	*  Retrieve a list of attendees
	*  @return : String <> "" => ok 
	*  			 String == "" => there isn't any course with that code => error
	*/
	public String listAttendees(int courseCode){
		
		String retValue = "";
		
		if(isCourseCodeValid(courseCode)) {
			courseCode = courseCode - FIRST_COURSE_CODE;		
			retValue = this.courses[courseCode].getParticipantsInformation();
		}
		
		return retValue;
	}

	/* 
	*  Retrieves the study plan for a student
	*  @return : String <> "" => ok 
	*  			 String == "" => there isn't any student with that id => error
	*/
	public String studyPlan(int studentID){
		
		String retValue = "";
		
		if(isStudentIdValid(studentID)) {
			studentID = studentID - FIRST_ID_STUDENT;
			retValue = this.students[studentID].showAttendedCourses();
		}
		
		return retValue;
	}
}
