import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int opt;
		boolean menu = true;

		System.out.println("Welcome to the library database.");
		Queries.initializeDB(in);

		/*	Present interface options to user	*/
		while(menu) {
			System.out.println("Enter \'1\' to Search, \'2\' to Add New Record, \'3\' to Order Items, \'4\' to Edit Records,\'5\' to Print Reports, or \'0\' to Quit.");
			in.reset();
			opt = in.nextInt();
			in.nextLine();
			/*	Call method for action based on user input	*/
			switch(opt) {
			case 0:
				System.out.println("Quitting program.");
				menu = false;
				break;
			case 1:
				search(in);
				break;
			case 2:
				addRecord(in);
				break;
			case 3:
				order(in);
				break;
			case 4:
				edit(in);
				break;
			case 5:
				report(in);
				break;
			default:
				System.out.println("Invalid command. Try again.");
			}

		}
		in.close();
	}

	/*	Search for either Artist or Track and display respective information	*/
	private static void search(Scanner in) {
		String input;
		boolean choice = true;
		String name;
		/*	Get user input for Artist or Track search	*/
		while(choice) {
			System.out.println("Input \'a\' to search by Artist, \'b\' to search by Track, or 'q' to return to the previous menu.");
			input = in.nextLine().toLowerCase();
			switch(input) {
			case "a":
				Queries.getArtistInformation();
				break;
			case "b":
				Queries.getTrackInformation();
				break;
			case "q":
				System.out.println("Returning to previous menu...");
				choice = false;
				break;
			default:
				System.out.println("Invalid input.");		/*	Invalid choice from user	*/
			}
		}

	}

	/*	Add either a new Artist or new Track	*/
	private static void addRecord(Scanner in) {
		String input;
		int id;
		boolean choice = true, found = false;
		/*	Get user input for what will be added	*/
		while(choice) {
			System.out.println("Input \'a\' to add an Artist, \'b\' to add a Track, or 'q' to return to the previous menu.");
			input = in.nextLine().toLowerCase();
			switch(input) {
			case "a":
				String name;
				/*	Input new artist attributes	*/
				System.out.println("Enter Artist Name:");
				name = in.nextLine();
				/*	Check if artist already exists	*/
				Queries.addArtist(name);
				break;
			case "b":
				String title, artist;
				int length;
				/*	Input Track attributes	*/
				System.out.println("Enter Track ID:");
				id = in.nextInt();
				in.nextLine();
				System.out.println("Enter Track title:");
				title = in.nextLine();
				System.out.println("Enter Artist name:");
				artist = in.nextLine();
				System.out.println("Enter Track length:");
				length = in.nextInt();
				in.nextLine();

				Queries.addTrack(id, title, length);
				Queries.addArtistCreated(artist, id);

				break;
			case "q":
				System.out.println("Returning to previous menu...");
				choice = false;
				break;
			default:
				System.out.println("Invalid input.");
			}
		}

	}

	/*	Create new Movie order	*/
	private static void order(Scanner in) {
		String input;
		boolean choice = true, found = false;
		/*	Get user option input	*/
		while(choice) {
			System.out.println("Input \'a\' to order a Movie, \'b\' to activate item received, or 'q' to return to the previous menu.");
			input = in.nextLine();
			switch(input) {
			case "a":
				String title, director, star, rating, arrival, genre;
				int id, length, year, orderId, physNumOrdered, digNumOrdered, price;
				/*	Input Movie attributes	*/
				System.out.println("Enter Movie ID:");
				id = in.nextInt();
				in.nextLine();
				
				System.out.println("Enter Movie Title:");
				title = in.nextLine();

				System.out.println("Enter Movie Genre:");
				genre = in.nextLine();

				System.out.println("Enter Movie Length:");
				length = in.nextInt();
				in.nextLine();
				
				System.out.println("Enter Movie Year:");
				year = in.nextInt();
				in.nextLine();

				System.out.println("Enter Movie Director:");
				director = in.nextLine();

				System.out.println("Enter Movie Star:");
				star = in.nextLine();

				System.out.println("Enter Movie Rating:");
				rating = in.nextLine();

				Queries.addMedia(id, title, genre, length, year);
				Queries.addMovie(id, director, rating);
				Queries.addActor(star);
				Queries.addMovieStars(star, id);

				/*	Input order attributes	*/
				System.out.println("Enter Order ID:");
				orderId = in.nextInt();
				in.nextLine();

				System.out.println("Enter number of physical copies ordered:");
				physNumOrdered = in.nextInt();
				in.nextLine();

				System.out.println("Enter number of digital copies ordered:");
				digNumOrdered = in.nextInt();
				in.nextLine();

				System.out.println("Enter Price of order:");
				price = in.nextInt();
				in.nextLine();

				System.out.println("Enter Est. Order arrival date (yyyy-mm-dd):");
				arrival = in.nextLine();

				Queries.addOrder(id, orderId, price, physNumOrdered, digNumOrdered, arrival);

				break;
			case "b":
				int instId, row, section;
				String license, exp;
				System.out.println("Enter Media ID:");
				id = in.nextInt();
				in.nextLine();

				System.out.println("Enter Order ID:");
				orderId = in.nextInt();
				in.nextLine();

				System.out.println("Enter number of physical copies ordered:");
				physNumOrdered = in.nextInt();
				in.nextLine();

				System.out.println("Enter number of digital copies ordered:");
				digNumOrdered = in.nextInt();
				in.nextLine();

				for (int i = 0; i < physNumOrdered; i++) {
					System.out.println("Enter Instance ID " + (i+1) + ":");
					instId = in.nextInt();
					in.nextLine();
					
					Queries.addInv(id, instId);

					System.out.println("Enter Date of arrival (mm/dd/yyyy):");
					arrival = in.nextLine();

					System.out.println("Enter Shelf Row Location:");
					row = in.nextInt();
					in.nextLine();

					System.out.println("Enter Shelf Section:");
					section = in.nextInt();
					in.nextLine();

					Queries.addPhysicalMedia(instId, arrival, row, section);
				}

				for (int j = 0; j < digNumOrdered; j++) {
					System.out.println("Enter Instance ID " + (j+1) + ":");
					instId = in.nextInt();
					in.nextLine();
					
					Queries.addInv(id, instId);

					System.out.println("Enter Digital License:");
					license = in.nextLine();

					System.out.println("Enter Date of License Expiration (yyyy-mm-dd):");
					exp = in.nextLine();

					Queries.addLicense(license, exp);
					Queries.addDigitalMedia(instId, license);
				}

				Queries.deleteOrder(orderId);

				break;
			case "q":
				System.out.println("Returning to previous menu...");
				choice = false;
				break;
			default:
				System.out.println("Invalid input.");
			}
		}

	}

	/*	Edit attributes of an artist	*/
	private static void edit(Scanner in) {
		String name, newName;
		String input;
		boolean choice = true;
		while(choice) {
			System.out.println("Input \'a\' for editing an artist or 'q' to return to the previous menu.");
			input = in.nextLine().toLowerCase();
			switch(input) {
			case "a":
				System.out.println("Enter an artist to edit:");		/*	Select artist to edit	*/
				name = in.nextLine();
				System.out.println("Enter the new name of the artist:"); /*	Provide new name of artist	*/
				newName = in.nextLine();

				Queries.editArtist(name, newName);
				break;
			case "q":
				System.out.println("Returning to previous menu...");
				choice = false;
				break;
			default:
				System.out.println("Invalid input.");
			}
		}
	}

	private static void report(Scanner in) {
		String input;
		boolean choice = true;
		/*	Get user input	*/
		while(choice) {
			System.out.println("Input \'a\' for print tracks by ARTIST before YEAR, \'b\' for num of albums under one patron, \'c\' for most popular actor, \'d\' for most listened to artist, \'e\' for patron with most videos checked out, or 'q' to return to the previous menu.");
			input = in.nextLine().toLowerCase();
			switch(input) {
			case "a":
				Queries.tracksByArtistBeforeYear();
				break;
			case "b":
				Queries.albumsCheckedOutByPatron();
				break;
			case "c":
				Queries.mostPopularActor();
				break;
			case "d":
				Queries.mostListenedToArtist();
				break;
			case "e":
				Queries.patronWithMostMovies();
				break;
			case "q":
				System.out.println("Returning to previous menu...");
				choice = false;
				break;
			default:
				System.out.println("Invalid input.");
			}
		}

	}

}
