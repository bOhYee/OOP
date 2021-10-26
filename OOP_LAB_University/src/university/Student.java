package university;

/**
 * @author MattWin
 * 
 * This class represents a student.
 *
 */
public class Student {
	
	private String name;
	private String surname;
	
	public Student(String first, String last){		
		
		this.name = first;
		this.surname = last;	
		
	}
	
	// Getter for the name of the student 
	public String getName() {
		
		return this.name;
	}

	// Getter for the surnname of the student
	public String getSurname() {
				
		return this.surname;
	}
	
}
