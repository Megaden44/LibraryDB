import java.util.ArrayList;

public class Artist {
	String name;
	ArrayList<Track> trackList = new ArrayList<Track>();
	

	/**
	 * @param name
	 * @param trackList
	 */
	public Artist(String name, ArrayList<Track> trackList) {
		this.name = name;
		this.trackList = trackList;
	}

	/**
	 * @param name
	 */
	public Artist(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the trackList
	 */
	public void getTrackList() {
		for (Track t : this.trackList) {
			System.out.println(t.getTitle() + ", " + t.getLength() + "mins");
		}
	}
	
	
}
