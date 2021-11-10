import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * <h1>CSE3241 Introduction to Database Systems - Sample Java application.</h1>
 * 
 * <p>
 * Sample app to be used as guidance and a foundation for students of CSE3241
 * Introduction to Database Systems at The Ohio State University.
 * </p>
 * 
 * <h2>!!! - Vulnerable to SQL injection - !!!</h2>
 * <p>
 * Correct the code so that it is not vulnerable to a SQL injection attack.
 * ("Parameter substitution" is the usual way to do this.)
 * </p>
 * 
 * <p>
 * Class is written in Java SE 8 and in a procedural style. Implement a
 * constructor if you build this app out in OOP style.
 * </p>
 * <p>
 * Modify and extend this app as necessary for your project.
 * </p>
 *
 * <h2>Language Documentation:</h2>
 * <ul>
 * <li><a href="https://docs.oracle.com/javase/8/docs/">Java SE 8</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/api/">Java SE 8
 * API</a></li>
 * <li><a href=
 * "https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC
 * API</a></li>
 * <li><a href="https://www.sqlite.org/docs.html">SQLite</a></li>
 * <li><a href="http://www.sqlitetutorial.net/sqlite-java/">SQLite Java
 * Tutorial</a></li>
 * </ul>
 *
 * <h2>MIT License</h2>
 *
 * <em>Copyright (c) 2019 Leon J. Madrid, Jeff Hachtel</em>
 * 
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * </p>
 *
 * 
 * @author Leon J. Madrid (madrid.1), Jeff Hachtel (hachtel.5)
 * 
 *         Class modified by Paige Moden, Paige Bormann, Derek Nelson, and Luke
 *         Wilcox
 */

public class Queries {

	/**
	 * The database file name.
	 * 
	 * Make sure the database file is in the root folder of the project if you only
	 * provide the name and extension.
	 * 
	 * Otherwise, you will need to provide an absolute path from your C: drive or a
	 * relative path from the folder this class is in.
	 */
	private static String DATABASE = "librarydb.db";
	static Connection conn;
	static Scanner in;

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */

	/**
	 * Connects to the database if it exists, creates it if it does not, and saves
	 * the connection object
	 * 
	 * @param databaseFileName the database file name
	 * @return a connection object to the designated database
	 */
	static void initializeDB(Scanner in) {
		/**
		 * The "Connection String" or "Connection URL".
		 * 
		 * "jdbc:sqlite:" is the "subprotocol". (If this were a SQL Server database it
		 * would be "jdbc:sqlserver:".)
		 */
		Queries.in = in;
		String url = "jdbc:sqlite:" + DATABASE;
		conn = null; // If you create this variable inside the Try block it will be out of scope
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				// Provides some positive assurance the connection and/or creation was
				// successful.
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("The connection to the database was successful.");
			} else {
				// Provides some feedback in case the connection failed but did not throw an
				// exception.
				System.out.println("Null Connection");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("There was a problem connecting to the database.");
		}

	}

	/**
	 * Queries the database and prints the results.
	 * 
	 * @param conn a connection object
	 * @param sql  a SQL statement that returns rows This query is written with the
	 *             Statement class, tipically used for static SQL SELECT statements
	 */
	static void sqlQuery(String sql) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			printResultSet(rs);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static boolean sqlPreparedQuery(String sql, Map<Integer, String> strings, Map<Integer, Integer> integers) {
		boolean results = false;
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);

			// add in params
			for (Map.Entry<Integer, String> entry : strings.entrySet()) {
				stmt.setString(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<Integer, Integer> entry : integers.entrySet()) {
				stmt.setInt(entry.getKey(), entry.getValue());
			}

			ResultSet rs = stmt.executeQuery();
			results = printResultSet(rs);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return results;
	}

	static int sqlPreparedUpdateQuery(String sql, Map<Integer, String> strings, Map<Integer, Integer> integers) {
		int rs = 0;
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);

			// add in params
			for (Map.Entry<Integer, String> entry : strings.entrySet()) {
				stmt.setString(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<Integer, Integer> entry : integers.entrySet()) {
				stmt.setInt(entry.getKey(), entry.getValue());
			}

			rs = stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rs;
	}

	private static boolean printResultSet(ResultSet rs) {
		boolean results = false;
		int columnCount = 0;
		try {

			while (rs.next()) {
				if (!results) {
					ResultSetMetaData rsmd = rs.getMetaData();
					columnCount = rsmd.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String value = rsmd.getColumnName(i);
						System.out.print(value);
						if (i < columnCount)
							System.out.print(",  ");
					}
					System.out.print("\n");

					results = true;

				}
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
					if (i < columnCount)
						System.out.print(",  ");
				}
				System.out.print("\n");
			}

			if (!results) {
				System.out.println("Query returned no results.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	static void sqlPreparedInsert(String sql, Map<Integer, String> strings, Map<Integer, Integer> integers, Map<Integer, Float> floats) {
		try {
			PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);

			// add in params
			for (Map.Entry<Integer, String> entry : strings.entrySet()) {
				stmt.setString(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<Integer, Integer> entry : integers.entrySet()) {
				stmt.setInt(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<Integer, Float> entry : floats.entrySet()) {
				stmt.setFloat(entry.getKey(), entry.getValue());
			}

			int row = stmt.executeUpdate();

			System.out.print("Rows affected: " + row);
			System.out.print("\n");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String albumsWorkedOnByArtist = 
			"SELECT DISTINCT(M.Title)\r\n"
					+ "FROM Track AS T, Media AS M, Album AS A, Album_Contains AS AC, Artist_Created AS AR\r\n"
					+ "WHERE M.Media_id = A.Media_id AND A.Media_id = AC.Album_Media_id AND AC.Track_id = T.Track_id\r\n"
					+ "AND AC.Track_id = AR.Track_id AND AR.Artist_Name = ?;";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String tracksByArtist = 
			"SELECT T.Track_Title\r\n"
					+ "FROM Track AS T, Artist_Created AS AR\r\n"
					+ "WHERE AR.Track_id = T.Track_id AND AR.Artist_Name = ?;";

	static void getArtistInformation() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		System.out.println("Enter the artist you would like to search: ");
		String artist = in.nextLine();
		strings.put(1, artist);

		System.out.println("Here is the information for all albums worked on by " + artist + ":");
		sqlPreparedQuery(albumsWorkedOnByArtist, strings, integers);

		System.out.println();

		System.out.println("Here is the information for all tracks worked on by " + artist + ":");
		sqlPreparedQuery(tracksByArtist, strings, integers);

	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String getTrack = 
			"SELECT *\r\n"
					+ "FROM Track\r\n"
					+ "WHERE Track_id = ?;";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String tracksByTitle = 
			"SELECT T.Track_id, T.Track_Title, AR.Artist_Name\r\n"
					+ "FROM Track AS T, Artist_Created AS AR\r\n"
					+ "WHERE AR.Track_id = T.Track_id AND T.Track_Title = ?;";

	static void getTrackInformation() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		System.out.println("Enter the track you would like to search: ");
		String trackTitle = in.nextLine();

		System.out.println("Here is the information for all tracks with that name: ");
		strings.put(1, trackTitle);
		boolean results = sqlPreparedQuery(tracksByTitle, strings, integers);

		if(results) {
			strings = new HashMap<>();
			integers = new HashMap<>();

			System.out.println("Enter the track_id you would like more information on: ");
			int trackID = in.nextInt();
			in.nextLine();
			integers.put(1, trackID);
			sqlPreparedQuery(getTrack, strings, integers);
		}

	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String tracksByArtistBeforeYearSQL = 
			"SELECT T.Track_Title\r\n"
					+ "FROM Track AS T, Media AS M, Album AS A, Album_Contains AS AC, Artist_Created AS AR\r\n"
					+ "WHERE M.Media_id = A.Media_id AND A.Media_id = AC.Album_Media_id AND AC.Track_id = T.Track_id\r\n"
					+ "AND AC.Track_id = AR.Track_id AND AR.Artist_Name = ? AND M.Year < ?;";

	static void tracksByArtistBeforeYear() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		System.out.println("Enter the artist you would like to search: ");
		String artist = in.nextLine();
		strings.put(1, artist);

		System.out.println("Enter the year you would like to obtain tracks before: ");
		int year = in.nextInt();
		in.nextLine();
		integers.put(2, year);

		sqlPreparedQuery(tracksByArtistBeforeYearSQL, strings, integers);

	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String albumsCheckedOutByPatronSQL = 
			"SELECT COUNT(M.Media_id) as Albums_Checked_Out\r\n"
					+ "FROM Album AS A, Media AS M, Inventory_Media AS IM, Member_Checked_Out AS MC\r\n"
					+ "WHERE A.Media_id = M.Media_id AND M.Media_id = IM.Media_id AND\r\n"
					+ "IM.Instance_id = MC.Instance_id AND MC.Member_id = ?;";

	static void albumsCheckedOutByPatron() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		System.out.println("Enter the member id for the patron you would like to search: ");
		int id = in.nextInt();
		in.nextLine();
		integers.put(1, id);

		sqlPreparedQuery(albumsCheckedOutByPatronSQL, strings, integers);

	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String mostPopularActorSQL = 
			"SELECT T.Actor_Name\r\n" + "FROM\r\n"
					+ "(SELECT MS.Actor_Name, COUNT(MS.Movie_Media_id) AS ActorCount\r\n"
					+ "FROM Member_Checked_Out AS MC, Media AS M, Inventory_Media AS IM, Movie AS \r\n"
					+ "MO, Movie_Stars AS MS\r\n"
					+ "WHERE MC.Instance_id = IM.Instance_id AND IM.Media_id = M.Media_id AND \r\n"
					+ "M.Media_id = MO.Media_id AND MO.Media_id = MS.Movie_Media_id \r\n" + "GROUP BY Actor_Name) AS T\r\n"
					+ "WHERE T.ActorCount = \r\n" + "(SELECT MAX(ActorCount)\r\n" + "FROM\r\n"
					+ "(SELECT MS.Actor_Name, COUNT(MS.Movie_Media_id) AS ActorCount\r\n"
					+ "FROM Member_Checked_Out AS MC, Media AS M, Inventory_Media AS IM, Movie AS \r\n"
					+ "MO, Movie_Stars AS MS\r\n"
					+ "WHERE MC.Instance_id = IM.Instance_id AND IM.Media_id = M.Media_id AND \r\n"
					+ "M.Media_id = MO.Media_id AND MO.Media_id = MS.Movie_Media_id \r\n" + "GROUP BY Actor_Name));";

	static void mostPopularActor() {
		sqlQuery(mostPopularActorSQL);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String mostListenedToArtistSQL = 
			"SELECT T.Artist_Name\r\n" + "FROM\r\n"
					+ "(SELECT AC.Artist_Name, SUM(TR.Track_Length) AS TotalDuration, \r\n"
					+ "COUNT(M.Media_id) AS TotalLent\r\n"
					+ "FROM Member_Checked_Out AS MC, Media AS M, Inventory_Media AS IM, Album AS \r\n"
					+ "A, Album_Contains AS AL, Artist_Created AS AC, Track AS TR\r\n"
					+ "WHERE MC.Instance_id = IM.Instance_id AND IM.Media_id = M.Media_id AND \r\n"
					+ "M.Media_id = A.Media_id AND A.Media_id = AL.Album_Media_id AND AL.Track_id = AC.Track_id AND AL.Track_id = TR.Track_id\r\n"
					+ "GROUP BY Artist_Name) AS T\r\n" + "WHERE (TotalDuration*TotalLent) = \r\n"
					+ "(SELECT MAX(TotalDuration*TotalLent)\r\n" + "FROM\r\n"
					+ "(SELECT AC.Artist_Name, SUM(TR.Track_Length) AS TotalDuration, \r\n"
					+ "COUNT(M.Media_id) AS TotalLent\r\n"
					+ "FROM Member_Checked_Out AS MC, Media AS M, Inventory_Media AS IM, Album AS \r\n"
					+ "A, Album_Contains AS AL, Artist_Created AS AC, Track AS TR\r\n"
					+ "WHERE MC.Instance_id = IM.Instance_id AND IM.Media_id = M.Media_id AND \r\n"
					+ "M.Media_id = A.Media_id AND A.Media_id = AL.Album_Media_id AND AL.Track_id = AC.Track_id AND AL.Track_id = TR.Track_id\r\n"
					+ "GROUP BY Artist_Name));";

	static void mostListenedToArtist() {
		sqlQuery(mostListenedToArtistSQL);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String patronWithMostMoviesSQL = 
			"SELECT P.Patron_FName, P.Patron_LName, MAX(C.movieCount) AS Total_Movies\r\n"
					+ "FROM (SELECT P.Member_id AS movieLover, COUNT(M.Media_id) AS movieCount\r\n"
					+ "FROM Movie AS MO, Media AS M, Inventory_Media AS IM,\r\n" + "Member_Checked_Out AS MC, Patron AS P\r\n"
					+ "WHERE P.Member_id = MC.Member_id AND\r\n"
					+ "MC.Instance_id = IM.Instance_id AND IM.Media_id = M.Media_id AND M.Media_id = MO.Media_id\r\n"
					+ "GROUP BY P.Member_id) AS C, Patron AS P\r\n" + "WHERE C.movieLover = P.Member_id;\r\n";

	static void patronWithMostMovies() {
		sqlQuery(patronWithMostMoviesSQL);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String editArtist = 
			"UPDATE Artist\r\n" + "SET Artist_Name = ?\r\n" + "WHERE Artist_Name = ?;\r\n";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String editArtistCascade = 
			"UPDATE Artist_Created\r\n" + "SET Artist_Name = ?\r\n"
					+ "WHERE Artist_Name = ?;";

	static void editArtist(String artistName, String editedName) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		strings.put(1, editedName);
		strings.put(2, artistName);

		int success = sqlPreparedUpdateQuery(editArtist, strings, integers);
		if (success > 0) {
			System.out.println("Update complete.");
		} else if (success == 0) {
			System.out.println("Nothing to update.");
		}
		success = sqlPreparedUpdateQuery(editArtistCascade, strings, integers);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertArtistSQL = 
			"INSERT INTO Artist\r\n" + "VALUES (?);";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String artistExistsCheck = 
			"SELECT *\r\n"
					+ "FROM Artist\r\n"
					+ "WHERE Artist_Name = ?;";

	static void addArtist() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		System.out.println("Enter Artist Name:");
		String name = in.nextLine();

		strings.put(1, name);
		System.out.println("Checking for artist in database...");
		if(sqlPreparedQuery(artistExistsCheck, strings, integers)) {
			System.out.println("Artist already exists.");
		} else {
			System.out.println("Adding artist to database...");
			sqlPreparedInsert(insertArtistSQL, strings, integers, floats);
		}
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertTrackSQL = 
			"INSERT INTO Track\r\n" + "VALUES (?, ?, ?);";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String trackExistsCheck = 
			"SELECT *\r\n"
					+ "FROM Track\r\n"
					+ "WHERE Track_id = ?;";

	static void addTrack() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		/*	Input Track attributes	*/
		System.out.println("Enter Track ID:");
		int id = in.nextInt();
		in.nextLine();
		integers.put(1, id);

		System.out.println("Checking for track in database...");
		if(sqlPreparedQuery(trackExistsCheck, strings, integers)) {
			System.out.println("Track already exists with this id.");

		} else {
			System.out.println("Enter Track title:");
			String title = in.nextLine();

			System.out.println("Enter Track length:");
			int length = in.nextInt();
			in.nextLine();

			strings.put(2, title);
			integers.put(3, length);

			System.out.println("Adding track to database...");
			sqlPreparedInsert(insertTrackSQL, strings, integers, floats);

			Queries.addArtistCreated(id);
		}
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertArtistCreatedSQL = 
			"INSERT INTO Artist_Created\r\n" + "VALUES (?, ?);";

	static void addArtistCreated(int id) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		System.out.println("Enter Artist name:");
		String artist = in.nextLine();

		strings.put(1, artist);

		System.out.println("Checking for artist in database...");
		if(sqlPreparedQuery(artistExistsCheck, strings, integers)) {
			System.out.println("Artist already exists.");
		} else {
			System.out.println("Adding artist to database...");
			sqlPreparedInsert(insertArtistSQL, strings, integers, floats);
		}

		integers.put(2, id);

		System.out.println("Correlating track with artist in database...");
		sqlPreparedInsert(insertArtistCreatedSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String movieExistsCheck =
			"SELECT Me.Title\r\n"
					+ "FROM Movie AS Mo, Media AS Me\r\n"
					+ "WHERE Mo.Media_id = Me.Media_id AND Mo.Media_id = ?;";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String actorExistsCheck =
			"SELECT *\r\n"
					+ "FROM Actor\r\n"
					+ "WHERE Actor_Name = ?;";

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String mediaExistsCheck =
			"SELECT *\r\n"
					+ "FROM Media\r\n"
					+ "WHERE Media_id = ?;";

	static void orderMovie() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		boolean order = true;

		System.out.println("Enter Movie ID:");
		int id = in.nextInt();
		in.nextLine();

		integers.put(1,id);

		System.out.println("Checking for movie in database...");
		if(sqlPreparedQuery(movieExistsCheck, strings, integers)) {
			System.out.println("Movie already exists.");

		} else {
			System.out.println("Checking for id in media...");
			if(sqlPreparedQuery(mediaExistsCheck, strings, integers)) {
				System.out.println("Media already exists with this id.");
				order = false;
			} else {
				Queries.addMedia(id);
				Queries.addMovie(id);

				/* Input Movie Stars */
				boolean moreStars = true;
				while(moreStars) {
					System.out.println("Enter Movie Star:");
					String star = in.nextLine();

					strings.clear();
					integers.clear();
					strings.put(1,star);

					System.out.println("Checking for actor in database...");
					if(sqlPreparedQuery(actorExistsCheck, strings, integers)) {
						System.out.println("Actor already exists.");
					} else {
						System.out.println("Adding actor to database...");
						Queries.addActor(star);
					}

					System.out.println("Correlating actor with movie in database...");
					Queries.addMovieStars(star, id);

					boolean choice = true;
					while(choice) {
						System.out.println("Input \'a\' to add more stars or 'q' to continue with the order.");
						String input = in.nextLine();
						switch(input) {
						case "a":
							choice = false;
							break;
						case "q":
							System.out.println("Continuing with the order...");
							moreStars = false;
							choice = false;
							break;
						default:
							System.out.println("Invalid input.");
						}
					}
				}
			}
		}

		if(order) {
			//add order
			Queries.addOrder(id);
		}

	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String orderExistsCheck = 
			"SELECT *\r\n"
					+ "FROM Ordered_Media\r\n"
					+ "WHERE Media_id = ? AND Order_id = ?;";

	static void activateOrder() {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();

		System.out.println("Enter Media ID:");
		int id = in.nextInt();
		in.nextLine();

		System.out.println("Enter Order ID:");
		int orderId = in.nextInt();
		in.nextLine();

		integers.put(1, id);
		integers.put(2, orderId);

		System.out.println("Checking for order in database...");
		if(sqlPreparedQuery(orderExistsCheck, strings, integers)) {
			//get physical and digital copies
			System.out.println("Enter number of physical copies received:");
			int physNumOrdered = in.nextInt();
			in.nextLine();

			if (physNumOrdered > 0) {
				System.out.println("Enter data for physical copies:");
				for (int i = 0; i < physNumOrdered; i++) {
					System.out.println("Enter Instance ID " + (i+1) + ":");
					int instId = in.nextInt();
					in.nextLine();

					Queries.addInv(id, instId);

					System.out.println("Enter Date of arrival (yyyy-mm-dd):");
					String arrival = in.nextLine();

					System.out.println("Enter Shelf Row Location:");
					int row = in.nextInt();
					in.nextLine();

					System.out.println("Enter Shelf Section:");
					int section = in.nextInt();
					in.nextLine();

					Queries.addPhysicalMedia(instId, arrival, row, section);
				}
			}

			System.out.println("Enter number of digital copies received:");
			int digNumOrdered = in.nextInt();
			in.nextLine();

			if (digNumOrdered > 0) {
				System.out.println("Enter data for digital copies:");
				for (int j = 0; j < digNumOrdered; j++) {
					System.out.println("Enter Instance ID " + (j+1) + ":");
					int instId = in.nextInt();
					in.nextLine();

					Queries.addInv(id, instId);

					System.out.println("Enter Digital License:");
					String license = in.nextLine();

					System.out.println("Enter Date of License Expiration (yyyy-mm-dd):");
					String exp = in.nextLine();

					Queries.addLicense(license, exp);
					Queries.addDigitalMedia(instId, license);
				}
			}

			Queries.deleteOrder(id, orderId);

		} else {
			System.out.println("Order not found...");
		}
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertMediaSQL = 
			"INSERT INTO Media\r\n" + "VALUES (?, ?, ?, ?, ?);";

	static void addMedia(int id) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, id);

		/*	Input Media attributes	*/
		System.out.println("Enter Title:");
		String title = in.nextLine();

		System.out.println("Enter Genre:");
		String genre = in.nextLine();

		System.out.println("Enter Length:");
		int length = in.nextInt();
		in.nextLine();

		System.out.println("Enter Year:");
		int year = in.nextInt();
		in.nextLine();

		strings.put(2, title);
		strings.put(3, genre);
		integers.put(4, length);
		integers.put(5, year);

		sqlPreparedInsert(insertMediaSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertMovieSQL = 
			"INSERT INTO Movie\r\n" + "VALUES (?, ?, ?);";

	static void addMovie(int id) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, id);

		/* Input Movie attributes */
		System.out.println("Enter Movie Director:");
		String director = in.nextLine();
		System.out.println("Enter Movie Rating:");
		String rating = in.nextLine();

		strings.put(2, director);
		strings.put(3, rating);

		sqlPreparedInsert(insertMovieSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertActorSQL = 
			"INSERT INTO Actor\r\n" + "VALUES (?);";

	static void addActor(String name) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		strings.put(1, name);
		sqlPreparedInsert(insertActorSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertMovieStarsSQL = 
			"INSERT INTO Movie_Stars\r\n" + "VALUES (?, ?);";

	static void addMovieStars(String name, int id) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(2, id);
		strings.put(1, name);
		sqlPreparedInsert(insertMovieStarsSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertOrderedSQL = 
			"INSERT INTO Ordered_Media\r\n" + "VALUES (?, ?, ?, ?, ?, ?);";

	static void addOrder(int mid) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, mid);

		/*	Input order attributes	*/
		System.out.println("Enter Order ID:");
		int orderId = in.nextInt();
		in.nextLine();

		System.out.println("Enter number of physical copies ordered:");
		int physNumOrdered = in.nextInt();
		in.nextLine();

		System.out.println("Enter number of digital copies ordered:");
		int digNumOrdered = in.nextInt();
		in.nextLine();

		System.out.println("Enter Price of order:");
		float price = in.nextFloat();
		in.nextLine();

		System.out.println("Enter Est. Order arrival date (yyyy-mm-dd):");
		String arrival = in.nextLine();

		integers.put(2, orderId);
		floats.put(3, price);
		integers.put(4, physNumOrdered);
		integers.put(5, digNumOrdered);
		strings.put(6, arrival);
		sqlPreparedInsert(insertOrderedSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertInvSQL = 
			"INSERT INTO Inventory_Media\r\n" + "VALUES (?, ?);";

	static void addInv(int mid, int iid) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(2, mid);
		integers.put(1, iid);
		sqlPreparedInsert(insertInvSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertPhysicalSQL = 
			"INSERT INTO Physical_Media\r\n" + "VALUES (?, ?, ?, ?);";

	static void addPhysicalMedia(int id, String arrive, int row, int section) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, id);
		strings.put(2, arrive);
		integers.put(3, row);
		integers.put(4, section);
		sqlPreparedInsert(insertPhysicalSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertDigitalSQL = 
			"INSERT INTO Digital_Media\r\n" + "VALUES (?, ?);";

	static void addDigitalMedia(int id, String license) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, id);
		strings.put(2, license);
		sqlPreparedInsert(insertDigitalSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String insertLicenseSQL = 
			"INSERT INTO License\r\n" + "VALUES (?, ?);";

	static void addLicense(String license, String exp) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		strings.put(1, license);
		strings.put(2, exp);
		sqlPreparedInsert(insertLicenseSQL, strings, integers, floats);
	}

	/**
	 * The query statement to be executed.
	 * 
	 * Remember to include the semicolon at the end of the statement string. (Not
	 * all programming languages and/or packages require the semicolon (e.g.,
	 * Python's SQLite3 library))
	 */
	private static String deleteOrderSQL = 
			"DELETE FROM Ordered_Media\r\n" + "WHERE Media_id = ? AND Order_id = ?;";

	static void deleteOrder(int mid, int oid) {
		Map<Integer, String> strings = new HashMap<>();
		Map<Integer, Integer> integers = new HashMap<>();
		Map<Integer, Float> floats = new HashMap<>();

		integers.put(1, mid);
		integers.put(2, oid);
		System.out.println("Deleting order from database...");
		sqlPreparedInsert(deleteOrderSQL, strings, integers, floats);
	}

}

/*finally{
		/* From JSE7 onwards the try-with-resources statement is introduced. 
 * The resources in the try block will be closed automatically after the use,
 * at the end of the try block
 *  close JDBC objects
 * If not, use the following block:
	   try {
	      if(rs!=null) rs.close();
	   } catch (SQLException se) {
	      se.printStackTrace();
	   }
	   try {
	      if(stmt !=null) st.close();
	   } catch (SQLException se) {
	      se.printStackTrace();
	   }
	   try {
	      if(conn !=null) con.close();
	   } catch (SQLException se) {
	      se.printStackTrace();
	   }
	}*/
