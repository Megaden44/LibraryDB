import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	static ArrayList<Artist> artists = new ArrayList<Artist>(1);
	static ArrayList<Track> tracks = new ArrayList<Track>(1);
	static ArrayList<Order> orders = new ArrayList<Order>(1);
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int opt;
		boolean menu = true;
		
		System.out.println("Welcome to the library database.");
		
		/*	Present interface options to user	*/
		while(menu) {
			System.out.println("Enter \'1\' to Search, \'2\' to Add New Record, \'3\' to Order Items, \'4\' to Edit Records,\'5\' to Print Reports, or \'0\' to Quit.");
			in.reset();
			opt = in.nextInt();
			/*	Call method for action based on user input	*/
			switch(opt) {
				case 0:
					System.out.println("Quitting program.");
					menu = false;
					break;
				case 1:
					search();
					break;
				case 2:
					addRecord();
					break;
				case 3:
					order();
					break;
				case 4:
					edit();
					break;
				case 5:
					report();
					break;
				default:
					System.out.println("Invalid command. Try again.");
			}
			
		}
		in.close();
	}
	
	/*	Search for either Artist or Track and display respective information	*/
	private static void search() {
		String input;
		boolean choice = true, found = false;
		String name;
		Scanner in = new Scanner(System.in);
		/*	Get user input for Artist or Track search	*/
		while(choice) {
			System.out.println("Input \'a\' to search by Artist or \'b\' to search by Track.");
			input = in.nextLine().toLowerCase();
			switch(input) {
				case "a":
					System.out.println("Enter Artist Name:");
					name = in.nextLine();
					/*	Search array of artists for desired artist	*/
					for (Artist artist : artists) {
						if (artist.getName().equals(name)) {
							System.out.println("Artist: " + artist.getName());	/*	Print name	*/
							System.out.println("Tracks: ");						/*	Print list of tracks	*/
							artist.getTrackList();
							found = true;
						}
					}
					/*	check if artist was found	*/
					if (!found) {
						System.out.println("Artist not found.");
					}
					choice = false;
					break;
				case "b":
					System.out.println("Enter Track Name:");
					name = in.nextLine();
					/*	Search array for desired track	*/
					for (Track track : tracks) {
						if (track.getTitle().equals(name)) {
							System.out.println("Title: " + track.getTitle());		/*	Print title	*/
							System.out.println("Track ID: " + track.getId());		/*	Print track ID	*/
							System.out.println("Artist: " + track.getArtist());		/*	Print artist	*/
							System.out.println("Length: " + track.getLength());		/*	Print track length	*/
							found = true;
						}
					}
					/*	Check if track is found	*/
					if (!found) {
						System.out.println("Track not found.");
					}
					choice = false;
					break;
				default:
					System.out.println("Invalid input.");		/*	Invalid choice from user	*/
			}
		}
		
	}
	
	/*	Add either a new Artist or new Track	*/
	private static void addRecord() {
		String input;
		boolean choice = true, found = false;
		Scanner in = new Scanner(System.in);
		/*	Get user input for what will be added	*/
		while(choice) {
			System.out.println("Input \'a\' to add an Artist or \'b\' to add a Track.");
			input = in.nextLine().toLowerCase();
			switch(input) {
				case "a":
					String name;
					/*	Input new artist attributes	*/
					System.out.println("Enter Artist Name:");
					name = in.nextLine();
					/*	Check if artist already exists	*/
					for (Artist a : artists) {
						if (a.getName().equals(name)) {
							found = true;
						}
					}
					if (!found) {
						artists.add(new Artist(name));		/*	Add new Artist	*/
					} else {
						System.out.println("Artist already exists.");
					}
					
					choice = false;
					break;
				case "b":
					String title, artist;
					int length;
					/*	Input Track attributes	*/
					System.out.println("Enter Track title:");
					title = in.nextLine();
					System.out.println("Enter Artist name:");
					artist = in.nextLine();
					System.out.println("Enter Track length:");
					length = in.nextInt();
					
					Track t = new Track(tracks.size(), length, title, artist);
					/*	Add track to @tracklist for artist	*/
					for (Artist a : artists) {
						if (a.getName().equals(artist)) {
							a.trackList.add(t);
							found = true;
						}
					}
					/*	Add artist to @artists if not found	*/
					if (!found) {
						Artist a = new Artist(artist);
						a.trackList.add(t);
						artists.add(a);
					}
					
					tracks.add(t);		/*	Add track to @tracks array	*/

					choice = false;
					break;
				default:
					System.out.println("Invalid input.");
			}
		}
		
	}
	
	/*	Create new Movie order	*/
	private static void order() {
		String input;
		boolean choice = true;
		Scanner in = new Scanner(System.in);
		/*	Get user option input	*/
		while(choice) {
			System.out.println("Input \'a\' to order a Movie or \'b\' to activate item received.");
			input = in.nextLine();
			switch(input) {
				case "a":
					String title, director, star, rating, arrival;
					int numOrdered, price;
					/*	Input Movie attributes	*/
					System.out.println("Enter Movie Title:");
					title = in.nextLine();
					
					System.out.println("Enter Movie Director:");
					director = in.nextLine();
					
					System.out.println("Enter Movie Star:");
					star = in.nextLine();
					
					System.out.println("Enter Movie Rating:");
					rating = in.nextLine();
					
					Movie m = new Movie(orders.size(), title, director, star, rating);	/*	Create Movie object	*/
					/*	Input order attributes	*/
					System.out.println("Enter number of copies ordered:");
					numOrdered = in.nextInt();
					
					System.out.println("Enter Price of order:");
					price = in.nextInt();
					
					System.out.println("Enter Est. Order arrival date (mm/dd/yyyy):");
					in.next();
					arrival = in.nextLine();
					
					orders.add(new Order(m, numOrdered, price, arrival));	/*	Add to list of orders	*/
					
					choice = false;
					break;
				case "b":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				default:
					System.out.println("Invalid input.");
			}
		}
		
	}
	
	/*	Edit attributes of an artist	*/
	private static void edit() {
		String name, newName;
		Scanner in = new Scanner(System.in);
		boolean invalid = true;
		while (invalid) {
			System.out.println("Enter an artist to edit:");		/*	Select artist to edit	*/
			name = in.nextLine();
			/*	Search for artist	*/
			for (Artist a : artists) {
				if (a.getName().equals(name)) {
					/*	Edit attributes of artist	*/
					System.out.println("Enter a new name for the Artist or press Enter to keep same:");
					newName = in.nextLine();
					if (!newName.equals("")) {
						a.setName(newName);			/*	Change artist name	*/
						for (Track t : tracks) {	/*	Change Track attribute for artist	*/
							if(t.getArtist().equals(name)) {
								t.setArtist(newName);
							}
						}
					}
					invalid = false;
				}
			}
			/*	Artist not found	*/
			if(invalid) {
				System.out.println("Artist not found.");
			}
		}
	}
	
	private static void report() {
		String input;
		boolean choice = true;
		Scanner in = new Scanner(System.in);
		/*	Get user input	*/
		while(choice) {
			System.out.println("Input \'a\' for print tracks by ARTIST before YEAR, \'b\' for num of albums under one patron, \'c\' for most popular actor, \'d\' for most listened to artist, or \'e\' for patron with most videos checked out.");
			input = in.nextLine().toLowerCase();
			switch(input) {
				case "a":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				case "b":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				case "c":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				case "d":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				case "e":
					System.out.println("Not yet implemented.");
					choice = false;
					break;
				default:
					System.out.println("Invalid input.");
			}
		}

	}

}
