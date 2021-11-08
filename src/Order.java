
public class Order {
	Movie movie;
	int numPhysCopies, numDigCopies, price;
	String arrival;
	
	/**
	 * @param movie
	 * @param numPhysCopies
	 * @param numDigCopies
	 * @param price
	 * @param arrival
	 */
	public Order(Movie movie, int numPhysCopies, int numDigCopies, int price, String arrival) {
		this.movie = movie;
		this.numPhysCopies = numPhysCopies;
		this.numDigCopies = numDigCopies;
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
	 * @return the numPhysCopies
	 */
	public int getNumPhysCopies() {
		return numPhysCopies;
	}

	/**
	 * @param numPhysCopies the numPhysCopies to set
	 */
	public void setNumPhysCopies(int numPhysCopies) {
		this.numPhysCopies = numPhysCopies;
	}

	/**
	 * @return the numDigCopies
	 */
	public int getNumDigCopies() {
		return numDigCopies;
	}

	/**
	 * @param numDigCopies the numDigCopies to set
	 */
	public void setNumDigCopies(int numDigCopies) {
		this.numDigCopies = numDigCopies;
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
