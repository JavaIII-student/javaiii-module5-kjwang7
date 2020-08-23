package module5;

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
	private PreparedStatement insertNewMovie;
	private PreparedStatement removeMovie;
	private PreparedStatement updateMovie;

	public MovieQueries() {
		try {
			System.out.println("Connecting to database URL: " + URL);
			// connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			connection = DriverManager.getConnection(URL);

			resetMovieDatabase();

			System.out.println("Creating prepareStatement to select all movies");
			selectAllMovie = connection.prepareStatement("SELECT * FROM MOVIES ORDER BY NAME");

			System.out.println("Creating prepareStatement to select movies with name starting with specified character");
			selectMovieByName = connection.prepareStatement("SELECT * FROM MOVIES WHERE UPPER(NAME) LIKE ? "
								+ "ORDER BY NAME");
			
			System.out.println("Creating insert prepareStatement");
			insertNewMovie = connection.prepareStatement("INSERT INTO MOVIES " +
								"(NAME, RATING, DESCRIPTION) " +
								"VALUES (?, ?, ?)");

			System.out.println("Creating delete prepareStatement");
			System.out.println("DELETE FROM MOVIES WHERE UPPER(NAME) = " + "?" +
							" AND RATING = " + "?" + " AND UPPER(DESCRIPTION) = " + "? ");
			
			removeMovie = connection.prepareStatement("DELETE FROM MOVIES WHERE ID = " + "?" + " AND UPPER(NAME) = " + "?" +
							" AND RATING = " + "?" + " AND UPPER(DESCRIPTION) = " + "? ");
			
			
			updateMovie = connection.prepareStatement("UPDATE MOVIES SET NAME = " + "?" + ", RATING = " + "?" + ", DESCRIPTION = " + "?"
									+ " WHERE UPPER(NAME) = " + "?" + " AND RATING = " + "?" + " AND UPPER(DESCRIPTION) = " + "? ");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	}

	

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

	public int AddMovie(String name, int rating, String description) {
		try {
			insertNewMovie.setString(1, name);
			insertNewMovie.setInt(2, rating);
			insertNewMovie.setString(3, description);
			
			return insertNewMovie.executeUpdate();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		}
	}

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
	
	public int UpdateMovie(String name_new, int rating_new, String description_new, String name_curr, int rating_curr, String description_curr) {
		try {
			updateMovie.setString(1, name_new);
			updateMovie.setInt(2, rating_new);
			updateMovie.setString(3, description_new);
			
			updateMovie.setString(4, name_curr);
			updateMovie.setInt(5, rating_curr);
			updateMovie.setString(6, description_curr);
			
			// Debug
			//System.out.println(name+","+rating+","+description);
			
			
			return updateMovie.executeUpdate();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return -1;
		}
	}

	void resetMovieDatabase() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			//System.out.println("Creating Table - This will throw an exception if the table is already created.");
			// Debug
			System.out.println("CREATE TABLE MOVIES (" + "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
							"NAME VARCHAR(255)," + "RATING INTEGER," + "description VARCGAR(255)" + ")");
			
			// Drop table first
			//stmt.execute("DROP TABLE MOVIES");
			
			if (!tableExistsInDB("MOVIES")) {
				stmt.execute("CREATE TABLE MOVIES (" + "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
							"NAME VARCHAR(255)," + "RATING INTEGER," + "description VARCHAR(255)" + ")");
				System.out.println("adding values into MOVIES table");
				stmt.executeUpdate("INSERT INTO MOVIES VALUES (DEFAULT, 'Example Movie', 5, 'Example Movie')");
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	
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
	
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException sqlExeption) {
			sqlExeption.printStackTrace();
		}
	}
}
