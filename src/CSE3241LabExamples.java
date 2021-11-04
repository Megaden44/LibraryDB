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
 * <p>Sample app to be used as guidance and a foundation for students of 
 * CSE3241 Introduction to Database Systems at 
 * The Ohio State University.</p>
 * 
 * <h2>!!! - Vulnerable to SQL injection - !!!</h2>
 * <p>Correct the code so that it is not 
 * vulnerable to a SQL injection attack. ("Parameter substitution" is the usual way to do this.)</p>
 * 
 * <p>Class is written in Java SE 8 and in a procedural style. Implement a constructor if you build this app out in OOP style.</p>
 * <p>Modify and extend this app as necessary for your project.</p>
 *
 * <h2>Language Documentation:</h2>
 * <ul>
 * <li><a href="https://docs.oracle.com/javase/8/docs/">Java SE 8</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/api/">Java SE 8 API</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a></li>
 * <li><a href="https://www.sqlite.org/docs.html">SQLite</a></li>
 * <li><a href="http://www.sqlitetutorial.net/sqlite-java/">SQLite Java Tutorial</a></li>
 * </ul>
 *
 * <h2>MIT License</h2>
 *
 * <em>Copyright (c) 2019 Leon J. Madrid, Jeff Hachtel</em>
 * 
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.</p>
 *
 * 
 * @author Leon J. Madrid (madrid.1), Jeff Hachtel (hachtel.5)
 * 
 */

public class CSE3241LabExamples {
    
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	private static String DATABASE = "librarydb.db";
	
	/**
	 *  The query statement to be executed.
	 *  
	 *  Remember to include the semicolon at the end of the statement string.
	 *  (Not all programming languages and/or packages require the semicolon (e.g., Python's SQLite3 library))
	 */
	private static String allMedia = "SELECT * FROM Media;";
	private static String tracksOfArtistBeforeYear = "SELECT T.Track_Title\r\n"
													+ "FROM Track AS T, Media AS M, Album AS A, Album_Contains AS AC, Artist_Created AS AR\r\n"
													+ "WHERE M.Media_id = A.Media_id AND A.Media_id = AC.Album_Media_id AND AC.Track_id = T.Track_id\r\n"
													+ "AND AC.Track_id = AR.Track_id AND AR.Artist_Name = ? AND M.Year < ?;";
	private static String insertArtist = "INSERT INTO Artist\r\n"
										+ "VALUES (?);";
	
    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, tipically 
     * used for static SQL SELECT statements
     */
    public static void sqlQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sqlPreparedQuery(Connection conn, String sql, Map<Integer, String> strings, Map<Integer, Integer> integers){
        try {
        	PreparedStatement stmt = null;
        	ResultSet resultSet = null;
    		stmt = conn.prepareStatement(sql);

        	//add in params
    		for (Map.Entry<Integer,String> entry : strings.entrySet()) {
                stmt.setString(entry.getKey(),entry.getValue());
        	}
    		
    		for (Map.Entry<Integer,Integer> entry : integers.entrySet()) {
                stmt.setInt(entry.getKey(),entry.getValue());
        	}
        	
        	ResultSet rs = stmt.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sqlPreparedInsert(Connection conn, String sql, Map<Integer, String> strings, Map<Integer, Integer> integers){
        try {
        	PreparedStatement stmt = null;
        	ResultSet resultSet = null;
    		stmt = conn.prepareStatement(sql);

        	//add in params
    		for (Map.Entry<Integer,String> entry : strings.entrySet()) {
                stmt.setString(entry.getKey(),entry.getValue());
        	}
    		
    		for (Map.Entry<Integer,Integer> entry : integers.entrySet()) {
                stmt.setInt(entry.getKey(),entry.getValue());
        	}
        	
        	int row = stmt.executeUpdate();
        	
			System.out.print("Rows affected: " + row);
			System.out.print("\n");
        	
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public static void main(String[] args) {
    	Scanner keyboard = new Scanner(System.in);
    	System.out.println("This is a new run");
    	Connection conn = initializeDB(DATABASE);
    	    	
    	System.out.println("*********************************************************************");
    	System.out.println("Part 5 - Add another query");
    	System.out.println("*********************************************************************");
    	System.out.println("Part 6 - Add other queries - Use PreparedStatements");
    	
    	System.out.println("Choose your desired query:");
    	System.out.println("(1) Return all media entries.");
    	System.out.println("(2) Return tracks by an artist before a specific year.");
    	System.out.println("(3) Insert an artist into the database.");
    	
    	int selection = keyboard.nextInt();
    	keyboard.nextLine();
    	
    	if (selection == 1) {
        	sqlQuery(conn, allMedia);
    	} else if (selection == 2) {
        	Map<Integer, String> strings = new HashMap<>();
        	Map<Integer, Integer> integers = new HashMap<>();

        	System.out.println("Enter the artist you would like to search: ");
        	String artist = keyboard.nextLine();
        	strings.put(1, artist);
        	
        	System.out.println("Enter the year you would like to obtain tracks before: ");
        	int year = keyboard.nextInt();
        	keyboard.nextLine(); 
        	integers.put(2, year);
        	
        	sqlPreparedQuery(conn, tracksOfArtistBeforeYear, strings, integers);
    	} else if (selection == 3) {
    		Map<Integer, String> strings = new HashMap<>();
        	Map<Integer, Integer> integers = new HashMap<>();
        	
        	System.out.println("Enter the artist you would like to add: ");
        	String artist = keyboard.nextLine();
        	strings.put(1, artist);
        	
        	sqlPreparedInsert(conn, insertArtist, strings, integers);
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
    			
    }
}
