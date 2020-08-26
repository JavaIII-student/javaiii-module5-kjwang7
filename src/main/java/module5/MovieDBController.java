package module5;

/**
 * Controller for the Movie Database application
 * @author Kevin
 */

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;


public class MovieDBController {

    @FXML
    private Button addMovieButton;
    
    @FXML
    private Button removeMovieButton;
    
    @FXML
    private Button updateMovieButton;

    @FXML
    private ImageView image;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextArea descriptionText;

    @FXML
    private TextField filterMovieNameField;

    @FXML
    private Button searchButton;

    @FXML
    private Button browseAllButton;

    @FXML
    private TableView<Movie> tableView;
    
    
    
    private final MovieQueries movieQueries = new MovieQueries();
    
    
    private final ObservableList<Movie> movieList =
    		FXCollections.observableArrayList();
    
    /**
     * The initialize method
     */
    public void initialize() {
    	
    	tableView.setItems(movieList);
    	
    	TableColumn<Movie,Integer> idCol = new TableColumn<Movie,Integer>("id");
    	idCol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("id"));
    	TableColumn<Movie,String> nameCol = new TableColumn<Movie,String>("name");
    	nameCol.setCellValueFactory(new PropertyValueFactory<Movie,String>("name"));
    	TableColumn<Movie,Integer> ratingCol = new TableColumn<Movie,Integer>("rating");
    	ratingCol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("rating"));
    	TableColumn<Movie,String> descriptionCol = new TableColumn<Movie,String>("description");
    	descriptionCol.setCellValueFactory(new PropertyValueFactory<Movie,String>("description"));
    	 
    	tableView.getColumns().setAll(idCol, nameCol, ratingCol, descriptionCol);
    	
    	getAllEntries();
    	
    	tableView.getSelectionModel().selectedItemProperty().addListener(
    			(observableValue, oldValue, newValue) -> {
    				displayMovie(newValue);
    			}
    	);
    }
    
    /**
     * Get all Movies
     */
    private void getAllEntries() {
    	movieList.setAll(movieQueries.getAllMovies());
    	selectFirstEntry();
    }

    /**
     * Select the first entry
     */
    private void selectFirstEntry() {
    	tableView.getSelectionModel().selectFirst();
    }

    /**
     * Display movie
     * @param movie Movie object to be displayed
     */
    private void displayMovie(Movie movie) {
    	if (movie != null) {
    		idField.setText(movie.getId()+"");
    		nameField.setText(movie.getName());
    		ratingField.setText(movie.getRating()+"");
    		descriptionText.setText(movie.getDescription());
    	}
    	else {
    		idField.clear();
    		nameField.clear();
    		ratingField.clear();
    		descriptionText.clear();
    	}
    }

    /**
     * Actions to take when the Add Movie button is pressed
     * @param event
     */
    @FXML
    void addMovieButtonPressed(ActionEvent event) {
    	int idNumber = 0;

    	try {
    		idNumber = Integer.parseInt(idField.getText());
    	}
    	catch (NumberFormatException e){
    		displayAlert(AlertType.ERROR, "Invalid entry",
    				"Please enter a valid integer for Movie ID and try again.");
    		return;
    	}

    	int ratingNumber = 0;
    	try {
    		ratingNumber = Integer.parseInt(ratingField.getText());

    		if ( (ratingNumber <1) || (ratingNumber > 10) ) {
    			displayAlert(AlertType.ERROR, "Invalid entry",
    					"Please enter an integer between 1 and 10 for rating and try again.");
    			return;
    		}

    	}
    	catch (NumberFormatException e){
    		displayAlert(AlertType.ERROR, "Invalid entry",
    				"Please enter a valid integer between 1 and 10 for rating and try again.");
    		return;
    	}

        if (movieQueries.movieIDExists(idNumber)) {
            displayAlert(AlertType.ERROR, "Entry Not Added",
                            "Movie ID must be unique. Please enter a different movie ID.");
            return;
        }
    	
    	int result = movieQueries.AddMovie(
    			idNumber, nameField.getText().trim(), ratingNumber, descriptionText.getText().trim());

    	// Debug
    	// System.out.println("Return value of the add movie operation: " + result);
    	
    	if (result == 1) {
    		displayAlert(AlertType.INFORMATION, "Entry Added",
    				"Movie successfully added.");
    	}
    	else {
    		displayAlert(AlertType.ERROR, "Entry Not Added",
    				"Unable to add entry.");
    	}
    	getAllEntries();

    }

    
    /**
     * Actions to take when the Remove Movie button is pressed    
     * @param event
     */
    @FXML
    void removeMovieButtonPressed(ActionEvent event) {
    		
    	Movie currentlySelectedMovie = tableView.getSelectionModel().getSelectedItem();

    	if (currentlySelectedMovie != null) {
    		int result = movieQueries.DeleteMovie(currentlySelectedMovie.getId(),
    				currentlySelectedMovie.getName().toUpperCase(), currentlySelectedMovie.getRating(), currentlySelectedMovie.getDescription().toUpperCase());

        	// Debug
        	// System.out.println("Return value of the remove movie operation: " + result);

        	if (result != -1) {
    			displayAlert(AlertType.INFORMATION, "Entry Removed",
    					"Movie was successfully removed.");
    		}
    		else {
    			displayAlert(AlertType.ERROR, "Entry Not Removed",
    					"Unable to remove movie.");
    		}
    	}
    	getAllEntries();

    }

    /**
     * Actions to take when the Update Movie button is pressed
     * @param event
     */
    @FXML
    void updateMovieButtonPressed(ActionEvent event) {
    	Movie currentlySelectedMovie = tableView.getSelectionModel().getSelectedItem();

    	if (currentlySelectedMovie != null) {
    		
    		int idNumber = 0;
    		
    		try {
        		idNumber = Integer.parseInt(idField.getText());
    		}
        	catch (NumberFormatException e){
        		displayAlert(AlertType.ERROR, "Invalid entry",
        				"Please enter a valid integer for Movie ID and try again.");
        		return;
        	}

    		int ratingNumber = 0;
        	try {
        		
        		ratingNumber = Integer.parseInt(ratingField.getText());
        		if ( (ratingNumber <1) || (ratingNumber > 10) ) {
        			displayAlert(AlertType.ERROR, "Invalid entry",
        					"Please enter an integer between 1 and 10 for rating and try again.");
        			return;
        		}

        	}
        	catch (NumberFormatException e){
        		displayAlert(AlertType.ERROR, "Invalid entry",
        				"Please enter a valid integer between 1 and 10 for rating and try again.");
        		return;
        	}

        	// When the user updates the current selected movie to an id that matches with the id of a different movie not in selection, pop up an error.
        	// If the user updates the id of the currently selected movie, this is fine and update will proceed.
            if ( (currentlySelectedMovie.getId() != idNumber) && (movieQueries.movieIDExists(idNumber)) ) {
                displayAlert(AlertType.ERROR, "Entry Not Updated",
                                "Movie ID already exists in the database. Please enter a different movie ID.");
                return;
            }

    		int result = movieQueries.UpdateMovie(idNumber, nameField.getText().trim(), ratingNumber, descriptionText.getText().trim(),
    				currentlySelectedMovie.getId(), currentlySelectedMovie.getName().toUpperCase(), currentlySelectedMovie.getRating(),
    				currentlySelectedMovie.getDescription().toUpperCase());

        	// Debug
        	// System.out.println("Return value of update movie operation: " + result);

        	if (result != -1) {
    			displayAlert(AlertType.INFORMATION, "Entry Updated",
    					"Movie was successfully updated.");
    		}
    		else {
    			displayAlert(AlertType.ERROR, "Entry Not Removed",
    					"Unable to update movie.");
    		}
        	
    	}


    	getAllEntries();
    }

    /**
     * Actions to take when the Search button is pressed
     * @param event
     */
    @FXML
    void searchButtonPressed(ActionEvent event) {
    	List<Movie> movies = movieQueries.getMovieByName(
    			filterMovieNameField.getText() + "%");
    	
    	if (movies.size() > 0) {
    		movieList.setAll(movies);
    		selectFirstEntry();
    	}
    	else {
    		displayAlert(AlertType.INFORMATION, "Movie name not Found",
    				"There are no entries with the specified movie name");
    	}
    }

    /**
     * Actions to take when the Browse All buton is pressed
     * @param event
     */
    @FXML
    void browseAllButtonPressed(ActionEvent event) {
    	getAllEntries();
    }

    /**
     * Display an Alert
     * @param type Alert type
     * @param title Alert title
     * @param message Alert message
     */
    private void displayAlert(AlertType type, String title, String message) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setContentText(message);
    	alert.showAndWait();
    }

}
