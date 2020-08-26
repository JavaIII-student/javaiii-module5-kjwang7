package module5;

/**
 * This class contains the methods, queries and PreparedStatements for the Movie Database application
 * @author Kevin
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieQueries {
	private static final String URL = "jdbc:derby:MovieDB;create=true";
	//private static final String USERNAME = "javaiii";
	//private static final String PASSWORD = "javaiii";

	private Connection connection;
	private PreparedStatement selectAllMovie;
	private PreparedStatement selectMovieByName;
	private PreparedStatement selectMovieByID;
	private PreparedStatement insertNewMovie;
	private PreparedStatement removeMovie;
	private PreparedStatement updateMovie;

	/**
	 * Default constructor. Set up connection to the database, reset the database and set up PreparedStatements.
	 */
	public MovieQueries() {
		try {
			System.out.println("Connecting to database URL: " + URL);
			// connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			connection = DriverManager.getConnection(URL);

			resetMovieDatabase();

			// System.out.println("Creating prepareStatement to select all movies");
			selectAllMovie = connection.prepareStatement("SELECT * FROM MOVIES ORDER BY ID");
			

			// System.out.println("Creating prepareStatement to select movies with name starting with specified character");
			selectMovieByName = connection.prepareStatement("SELECT * FROM MOVIES WHERE UPPER(NAME) LIKE ? "
								+ "ORDER BY NAME");

			selectMovieByID = connection.prepareStatement("SELECT * FROM MOVIES WHERE ID = ?");
			
			// System.out.println("Creating insert prepareStatement");
			insertNewMovie = connection.prepareStatement("INSERT INTO MOVIES " +
								"(ID, NAME, RATING, DESCRIPTION) " +
								"VALUES (?, ?, ?, ?)");

			// System.out.println("Creating delete prepareStatement");
			
			removeMovie = connection.prepareStatement("DELETE FROM MOVIES WHERE ID = " + "?" + " AND UPPER(NAME) = " + "?" +
							" AND RATING = " + "?" + " AND UPPER(DESCRIPTION) = " + "? ");
			
			
			updateMovie = connection.prepareStatement("UPDATE MOVIES SET ID = " + "?" + ", NAME = " + "?" + ", RATING = " + "?" + ", DESCRIPTION = " + "?"
									+ " WHERE ID = " + "?" + " AND UPPER(NAME) = " + "?" + " AND RATING = " + "?" + " AND UPPER(DESCRIPTION) = " + "? ");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	}

	
	/**
	 * Get all Movie objects in a list
	 * @return a List of Movie objects
	 */
	public List<Movie> getAllMovies() {
		try (ResultSet resultSet = selectAllMovie.executeQuery()) {
			List<Movie> results = new ArrayList<Movie>();

			while (resultSet.next()) {
				results.add(new Movie(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getInt("rating"),
						resultSet.getString("description")));
			}
			return results;
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Get a Movie object by its name
	 * @param name Any part of the movie name, case insensitive
	 * @return A list of Movie objects that partially match the name
	 */
	public List<Movie> getMovieByName(String name) {
		try {
			//selectMovieByName.setString(1, name.toUpperCase());
			selectMovieByName.setString(1, "%" + name.toUpperCase() + "%");
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return null;
		}
		
		
		try (ResultSet resultSet = selectMovieByName.executeQuery()) {
			List<Movie> results = new ArrayList<Movie>();
			
			while (resultSet.next()) {
				results.add(new Movie(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getInt("rating"),
						resultSet.getString("description")));
			}
			return results;
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return null;
		}		
	}

	/**
	 * Add a Movie to the database
	 * @param id Movie id
	 * @param name Movie name
	 * @param rating Movie rating
	 * @param description Movie description
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing. Return -1 if an error is encountered.
	 */
	public int AddMovie(int id, String name, int rating, String description) {
		try {
			insertNewMovie.setInt(1, id);
			insertNewMovie.setString(2, name);
			insertNewMovie.setInt(3, rating);
			insertNewMovie.setString(4, description);
			
			return insertNewMovie.executeUpdate();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return -1;
		}
	}

	/**
	 * Delete a movie from the database
	 * @param id Movie id
	 * @param name Movie name
	 * @param rating Movie rating
	 * @param description Moving description
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing. Return -1 if an error is encountered.
	 */
	public int DeleteMovie(int id, String name, int rating, String description) {
		try {
			removeMovie.setInt(1, id);
			removeMovie.setString(2, name);
			removeMovie.setInt(3, rating);
			removeMovie.setString(4, description);
			
			// Debug
			//System.out.println(name+","+rating+","+description);
						
			return removeMovie.executeUpdate();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Update movie in the database
	 * @param id_new New for the movie
	 * @param name_new New name for the movie
	 * @param rating_new New rating for the movie
	 * @param description_new New description for the movie
	 * @param id_curr Current id for the movie
	 * @param name_curr Current name for the movie
	 * @param rating_curr Current rating for the movie
	 * @param description_curr Current description for the movie
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing. Return -1 if an error is encountered.
	 */
	public int UpdateMovie(int id_new, String name_new, int rating_new, String description_new, int id_curr, String name_curr, int rating_curr, String description_curr) {
		try {
			updateMovie.setInt(1, id_new);
			updateMovie.setString(2, name_new);
			updateMovie.setInt(3, rating_new);
			updateMovie.setString(4, description_new);
			
			updateMovie.setInt(5, id_curr);
			updateMovie.setString(6, name_curr);
			updateMovie.setInt(7, rating_curr);
			updateMovie.setString(8, description_curr);
			
			// Debug
			//System.out.println(name+","+rating+","+description);
						
			return updateMovie.executeUpdate();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reset the movie database. Create the MOVIES table in the database and add an example row, if the table does not exist. If the table exist, do nothing.
	 */
	void resetMovieDatabase() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			//System.out.println("Creating Table - This will throw an exception if the table is already created.");
			// Debug
			//System.out.println("CREATE TABLE MOVIES (" + "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
			//				"NAME VARCHAR(255)," + "RATING INTEGER," + "description VARCGAR(255)" + ")");
			
			// Drop table first
			//stmt.execute("DROP TABLE MOVIES");
			
			if (!tableExistsInDB("MOVIES")) {
				//stmt.execute("CREATE TABLE MOVIES (" + "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				//			"NAME VARCHAR(255)," + "RATING INTEGER," + "description VARCHAR(255)" + ")");
				System.out.println("Create the MOVIES table in database and insert an example record to the table.");
				stmt.execute("CREATE TABLE MOVIES (" + "ID INTEGER PRIMARY KEY," +
						"NAME VARCHAR(255)," + "RATING INTEGER," + "description VARCHAR(255)" + ")");
				//System.out.println("adding values into MOVIES table");
				stmt.executeUpdate("INSERT INTO MOVIES VALUES (1, 'Example Movie', 5, 'Example Movie')");
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}


	/**
	 * Check if the table exists in the database.
	 * @param tableName table name
	 * @return true if the table already exists, false otherwise.
	 * @throws SQLException
	 */
	private boolean tableExistsInDB (String tableName) throws SQLException {
		if (connection != null) {
			DatabaseMetaData dM = connection.getMetaData();
			ResultSet rS = dM.getTables(null, null, tableName.toUpperCase(), null);
			if (rS.next()) {
				System.out.println(tableName + " already exists in the database. Will not create a new table.");
				return true;
			}
			else {
				System.out.println(tableName + " does not exist in the database.");
				return false;
			}
		}
		return false;
	}
	
	
	/**
	 * Check if the movie id already exists in the MOVIES table in the database
	 * @param id Movie id
	 * @return true of the movie id already exists, false otherwise.
	 */
    public boolean movieIDExists (int id) {
        try {
                selectMovieByID.setInt(1,  id);
        }
        catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return false;
        }

        try {
                ResultSet resultSet = selectMovieByID.executeQuery();
                if (resultSet.next()) {
                        return true;
                }
        }
        catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return false;
        }
        return false;
    }
	
    /**
     * Close the database connection
     */
	public void close() {
		try {
			connection.close();
		} catch (SQLException sqlExeption) {
			sqlExeption.printStackTrace();
		}
	}
}
