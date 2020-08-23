package module5;

public class Movie {
	private int id;
	private String name;
	private int rating;
	private String description;
	
	public Movie() {
		
	}
	
	public Movie (int id, String name, int rating, String description) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name + "," + this.rating + "," + description;
	}
	
}
