package university;

/*
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	private final static int FIRST_ID_STUDENT = 10000;
	private final static int FIRST_COURSE_CODE = 10;
	private final static int MAX_STUDENTS = 1000;
	private final static int MAX_COURSES = 50;
		
	private String universityName;
	private String rectorName;
	private String rectorSurname;
	private int enrolledStudents;
	private int activeCourses;
	private Student[] students;	
	private Course[] courses;
	
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
		
		int position;			// Position of the student in the array
		int newId;				// Real ID of the student
		Student newStudent;
		
		position = this.enrolledStudents;	
		newId = position + FIRST_ID_STUDENT;
		newStudent = new Student(newId, first, last);
		
		this.students[position] = newStudent;
		this.enrolledStudents++;
		
		return newId;
	}
	
	// Retrieves the information for a given student
	public String student(int id){
		
		int recoverId;
		
		recoverId = id - FIRST_ID_STUDENT;
		return (this.students[recoverId].toString());
	}
	
	// Activates a new course with the given teacher
	public int activate(String title, String teacher){
		
		int position;			// Position of the student in the array
		int newId;				// Real ID of the student
		Course newCourse;
		
		position = this.activeCourses;
		newId = position + FIRST_COURSE_CODE;
		newCourse = new Course(newId, title, teacher);
		
		this.courses[position] = newCourse;
		this.activeCourses++;
		
		return newId;
	}
	
	// Retrieve the information for a given course.
	public String course(int code){
		
		int recoverId;
		
		recoverId = code - FIRST_COURSE_CODE;
		return (this.courses[recoverId].toString());
	}
	
	// Register a student to attend a course
	public void register(int studentID, int courseCode){
		
		int posStudent;
		int posCourse;
		
		posStudent = studentID - FIRST_ID_STUDENT;
		posCourse = courseCode - FIRST_COURSE_CODE;		
		this.courses[posCourse].addParticipant(studentID);
		this.students[posStudent].attendCourse(courseCode);
		
	}
	
	// Retrieve a list of attendees
	public String listAttendees(int courseCode){
		
		String info = "";
		courseCode = courseCode - FIRST_COURSE_CODE;	
		for(int i = 0; i < this.enrolledStudents; i++) {
			if(this.courses[courseCode].isParticipantRegistered(i+FIRST_ID_STUDENT))
				info += this.students[i].toString() + "\n";
		}
		
		return info;
	}

	// Retrieves the study plan for a student.
	public String studyPlan(int studentID){
		
		String info = "";
		
		studentID = studentID - FIRST_ID_STUDENT;		
		for(int i = 0; i < this.activeCourses; i++) {
			if(this.students[studentID].isCourseAttended(i+FIRST_COURSE_CODE))
				info += this.courses[i].toString() + "\n";
		}
		
		return info;
	}
}
