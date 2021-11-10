import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int opt;
		boolean menu = true;

		System.out.println("Welcome to the library database.");
		Queries.initializeDB(in);

		/*	Present interface options to user	*/
		while(menu) {
			printSeparator(in);
			System.out.println("Enter \'1\' to Search, \'2\' to Add New Record, \'3\' to Order Items, \'4\' to Edit Records,\'5\' to Print Reports, or \'0\' to Quit.");
			in.reset();
			opt = in.nextInt();
			in.nextLine();
			printSeparator(in);
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
		System.out.println("Welcome to database search!");
		String input;
		boolean choice = true;
		/*	Get user input for Artist or Track search	*/
		while(choice) {
			printSeparator(in);
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
		System.out.println("Welcome to database add new record!");
		String input;
		boolean choice = true;
		/*	Get user input for what will be added	*/
		while(choice) {
			printSeparator(in);
			System.out.println("Input \'a\' to add an Artist, \'b\' to add a Track, or 'q' to return to the previous menu.");
			input = in.nextLine().toLowerCase();
			switch(input) {
			case "a":
				/*	Input new artist attributes	*/
				Queries.addArtist();
				break;
			case "b":
				Queries.addTrack();

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
		System.out.println("Welcome to database order items!");
		String input;
		boolean choice = true;
		/*	Get user option input	*/
		while(choice) {
			printSeparator(in);
			System.out.println("Input \'a\' to order a Movie, \'b\' to activate item received, or 'q' to return to the previous menu.");
			input = in.nextLine();
			switch(input) {
			case "a":
				Queries.orderMovie();
				break;
			case "b":
				Queries.activateOrder();
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
		System.out.println("Welcome to database edit records!");
		String name, newName;
		String input;
		boolean choice = true;
		while(choice) {
			printSeparator(in);
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
		System.out.println("Welcome to database print reports!");
		String input;
		boolean choice = true;
		/*	Get user input	*/
		while(choice) {
			printSeparator(in);
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
	
	private static void printSeparator(Scanner in) {
		for(int n = 0; n < 100; n++)
		{
		    System.out.print("-");
		}
		System.out.println();
	}

}
