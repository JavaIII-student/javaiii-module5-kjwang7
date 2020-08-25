package module5;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieTest {



	public static void main(String[] args) {

//		private static final String URL = "jdbc:derby:MovieDB;create=true";
		final String URL = "jdbc:derby:MovieDB";
		final String USERNAME = "javaiii";
		final String PASSWORD = "";

		String query = "Select * from MOVIES";
		
		try {
			//Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection connection = DriverManager.getConnection(URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.printf("%-8s\t",metaData.getColumnName(i));
			}
			System.out.println();

			while (resultSet.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.printf("%-8s\t",  resultSet.getObject(i));
				}
				System.out.println();
			}
			
				
			// Close
			resultSet.close();
			statement.close();
			connection.close();
				
				
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
