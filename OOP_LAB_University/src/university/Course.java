package university;

/**
 * @author MattWin
 * 
 * This class represent a course.
 *
 */
public class Course {

	private String name;
	private String responsibleName;
	
	
	public Course(String name, String teacher) {
		
		this.name = name;
		this.responsibleName = teacher;
		
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getRespondibleName() {
		
		return this.responsibleName;
	}
}
