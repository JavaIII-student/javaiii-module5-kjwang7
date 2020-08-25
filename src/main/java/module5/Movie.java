package module5;

/**
 * Movie Class
 * @author Kevin
 *
 */
public class Movie {
	private int id;
	private String name;
	private int rating;
	private String description;
	
	/**
	 * Default constructor
	 */
	public Movie() {
		
	}
	
	/**
	 * Custom constructor
	 * @param id Movie id
	 * @param name Movie name
	 * @param rating Movie rating
	 * @param description Movie description
	 */
	public Movie (int id, String name, int rating, String description) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.description = description;
	}

	/**
	 * Getter method for id
	 * @return Movie id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter method for id
	 * @param id Movie id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter method for name
	 * @return Movie name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for name
	 * @param name Movie name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for rating
	 * @return Movie rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Setter method for rating
	 * @param rating Movie rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Getter method for description
	 * @return Movie description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method for description
	 * @param description Movie description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * String representation of the Movie object
	 * @return String with the movie field values separated by ,
	 */
	@Override
	public String toString() {
		return this.name + "," + this.rating + "," + description;
	}
	
}
