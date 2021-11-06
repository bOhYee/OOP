package university;

import java.util.logging.Logger;

/*
 * This class is an extended version of the {@Link University} class.
 */
public class UniversityExt extends University {
	
	private final static Logger logger = Logger.getLogger("University");
	
	/*
	 * Used to obtain the score of a student
	 * Formula used to calculate it is in the R6 request:
	 * "The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10 is added."
	 *  
	 */
	private float getScoreOfStudent(Student tmpStudent) {
		
		float score;
		float bonus;
		
		bonus = (float)(tmpStudent.getNumOfExamsTaken()/tmpStudent.getNumOfCoursesAttended());
		bonus *= 10;		
		score = tmpStudent.getAvgGrade() + bonus;
		
		return score;
	}

	/*
	 * Used to verify the presence of a student in the topThree array
	 */
	private Boolean isInTopThree(Student tmpStudent, Student[] topThree, int i) {
		
		for(int j = 0; j < i; j++)
			if(tmpStudent == topThree[j])
				return true;
		
		return false;
	}
	
	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}

	// Records the grade (integer 0-30) for an exam
	public void exam(int studentId, int courseID, int grade) {
		
		StringBuffer logMessage = new StringBuffer();
		logMessage.append("Student ").append(studentId).append(" took an exam in course ").append(courseID).append(" with grade ").append(grade);
		
		if(isCourseCodeValid(courseID) && isStudentIdValid(studentId) && (grade >= 0 && grade <= 30)) {			
			studentId = studentId - FIRST_ID_STUDENT;
			if(!(this.students[studentId].registerGrade(courseID, grade)))
				System.out.println("Student " + Integer.toString(studentId + FIRST_ID_STUDENT) + " isn't registered to course " + Integer.toString(courseID));
			else
				logger.info(logMessage.toString());
			
		}
		else {
			System.out.println("One of the arguments is invalid!");
		}
		
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 */
	public String studentAvg(int studentId) {
		
		float avgGrade;
		String retValue = "";
		
		if(isStudentIdValid(studentId)) {
			studentId = studentId - FIRST_ID_STUDENT;
			avgGrade = this.students[studentId].getAvgGrade();
			
			if(avgGrade != -1)
				retValue = "Student " + Integer.toString(studentId) + " : " + Float.toString(avgGrade);
			else
				retValue = "Student " + Integer.toString(studentId) + " hasn't taken any exams";
		}
		else {
			retValue = "Student " + Integer.toString(studentId) + " doesn't exist"; 
		}
		
		return retValue;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 */
	public String courseAvg(int courseId) {
		
		float avgGrade;
		String retValue = "";
		
		if(isCourseCodeValid(courseId)) {
			courseId = courseId - FIRST_COURSE_CODE;
			avgGrade = this.courses[courseId].getAvgGrade();
			
			if(avgGrade != -1)
				retValue = "The average for the course " + this.courses[courseId].getName() + " is: " + Float.toString(avgGrade);
			else
				retValue = "No student has taken the exam in " + this.courses[courseId].getName();
		}
		else {
			retValue = "Course " + Integer.toString(courseId) + " doesn't exist"; 
		}
		
		return retValue;
	}
	
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info of the best three students.
	 */
	public String topThreeStudents() {
		
		float compareScore;
		float tmpScore;
		String retValue = "";
		
		/*
		 * Used considering in position 0 the best student
		 * It contains the top three students's references, not the score
		 * 
		 */
		Student[] topThree = new Student[3];		
		
		for(int i = 0; (i < 3 && i < this.getNumOfEnrolledStudents()); i++) {
			for(Student tmpStudent : this.students) {
				if(tmpStudent == null)
					break;
				
				if(topThree[i] == null) {
					topThree[i] = tmpStudent;
				}
				else if(topThree[i] != null && !isInTopThree(tmpStudent, topThree, i)){
					tmpScore = getScoreOfStudent(tmpStudent);
					compareScore = getScoreOfStudent(topThree[i]);
					if(tmpScore > compareScore)
						topThree[i] = tmpStudent;
				}
			}
			
		}		
		
		for(Student tmpStudent : topThree) {
			if(tmpStudent == null)
				break;
						
			retValue += tmpStudent.getName() + " " + tmpStudent.getSurname() + " : " + Float.toString(getScoreOfStudent(tmpStudent)) + "\n";
		}
		
		return retValue;
	}

	// Overridden methods for logging information about operations done by operators
	@Override
	public int enroll(String first, String last) {
		
		int newId;
		StringBuffer logMessage = new StringBuffer("New student enrolled: ");
		
		newId = super.enroll(first, last);
		if(newId > 0) {
			logMessage.append(newId).append(", ").append(first).append(' ').append(last);
			logger.info(logMessage.toString());
		}
		
		return newId;
	}
	
	@Override
	public int activate(String title, String teacher) {
		
		int newCourseCode;
		StringBuffer logMessage = new StringBuffer("New course activated: ");
		
		newCourseCode = super.activate(title, teacher);
		if(newCourseCode > 0) {
			logMessage.append(newCourseCode).append(", ").append(title).append(' ').append(teacher);
			logger.info(logMessage.toString());
		}
		
		return newCourseCode;
	}
	

	/*
	 * In my implementation this method can actually print on the console errors related to 
	 * the validity of the arguments or related to the capacity of study plan and course attendees not respected...
	 * In this case the log is incorrect, but at the same time i cannot pass a return value from the super.register method
	 * so... i'll leave it like this and discuss with the teacher if my approach is correct or not
	 */
	@Override
	public void register(int studentID, int courseCode) {
		
		StringBuffer logMessage = new StringBuffer();
		
		super.register(studentID, courseCode);
		logMessage.append("Student ").append(studentID).append(" signed up for course ").append(courseCode);
		logger.info(logMessage.toString());
		
		
	}

}
