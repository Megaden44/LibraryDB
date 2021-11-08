import java.util.ArrayList;

public class Movie {
	int id;
	String title, director, star, rating;
	
	/**
	 * @param id
	 * @param title
	 * @param director
	 * @param star
	 * @param rating
	 */
	public Movie(int id, String title, String director, String star, String rating) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.star = star;
		this.rating = rating;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the star
	 */
	public String getStar() {
		return star;
	}

	/**
	 * @param star the star to set
	 */
	public void setStar(String star) {
		this.star = star;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	
}
