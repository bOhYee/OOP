package university;

import java.util.logging.Logger;

/*
 * This class is an extended version of the {@Link University} class.
 */
public class UniversityExt extends University {
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}

	// Records the grade (integer 0-30) for an exam
	public void exam(int studentId, int courseID, int grade) {
		
		if(isCourseCodeValid(courseID) && isStudentIdValid(studentId) && (grade >= 0 && grade <= 30)) {			
			studentId = studentId - FIRST_ID_STUDENT;
			if(!(this.students[studentId].registerGrade(courseID, grade)))
				System.out.println("Student " + Integer.toString(studentId + FIRST_ID_STUDENT) + " isn't registered to course " + Integer.toString(courseID));
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
		return null;
	}
}
