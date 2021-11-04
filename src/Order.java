
public class Order {
	Movie movie;
	int numCopies, price;
	String arrival;
	
	/**
	 * @param movie
	 * @param numCopies
	 * @param price
	 * @param arrival
	 */
	public Order(Movie movie, int numCopies, int price, String arrival) {
		this.movie = movie;
		this.numCopies = numCopies;
		this.price = price;
		this.arrival = arrival;
	}

	/**
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * @param movie the movie to set
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	/**
	 * @return the numCopies
	 */
	public int getNumCopies() {
		return numCopies;
	}

	/**
	 * @param numCopies the numCopies to set
	 */
	public void setNumCopies(int numCopies) {
		this.numCopies = numCopies;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the arrival
	 */
	public String getArrival() {
		return arrival;
	}

	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	
	
}
