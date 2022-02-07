/**
 * 
 */
package libraryMgmt;

/**
 * @author S273386
 *
 */
public class Book {
	
	private String title;
	private String[] authors;
	private Boolean available;
	
	public Book(String title, String[] authors) {
		this.title = title;
		this.authors = authors;
		this.available = true;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getTitle() {
		return title;
	}

	public String[] getAuthors() {
		return authors;
	}
	
}
