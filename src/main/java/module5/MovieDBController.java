package module5;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    private Text informUserText;

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
    private ListView<Movie> listView;
    
    
    private final MovieQueries movieQueries = new MovieQueries();
    
    
    private final ObservableList<Movie> movieList =
    		FXCollections.observableArrayList();
    
    public void initialize() {
    	listView.setItems(movieList);;
    	getAllEntries();
    	
    	listView.getSelectionModel().selectedItemProperty().addListener(
    			(observableValue, oldValue, newValue) -> {
    				displayMovie(newValue);
    			}
    	);
    }
    
    private void getAllEntries() {
    	movieList.setAll(movieQueries.getAllMovies());
    	selectFirstEntry();
    }
    
    private void selectFirstEntry() {
    	listView.getSelectionModel().selectFirst();
    }
    
    private void displayMovie(Movie movie) {
    	if (movie != null) {
    		nameField.setText(movie.getName());
    		ratingField.setText(movie.getRating()+"");
    		descriptionText.setText(movie.getDescription());
    	}
    	else {
    		nameField.clear();
    		ratingField.clear();
    		descriptionText.clear();
    	}
    }

    @FXML
    void addMovieButtonPressed(ActionEvent event) {
    	int ratingNumber = 0;
    	try {
    		ratingNumber = Integer.parseInt(ratingField.getText());
    		
    		if ( (ratingNumber >= 1) && (ratingNumber <= 10) ) {
    			int result = movieQueries.AddMovie(
    					nameField.getText().trim(), ratingNumber, descriptionText.getText().trim());

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
    		else {
    			displayAlert(AlertType.ERROR, "Invalid entry",
        				"Rating must be between 1 and 10, Please try again.");    	
    		}

    	}
    	catch (NumberFormatException e){
    		displayAlert(AlertType.ERROR, "Invalid entry",
    				"Please enter a valid integer between 1 and 10 and try again.");    		
    	}

    }
    
    
    @FXML
    void removeMovieButtonPressed(ActionEvent event) {
    	//int ratingNumber = 0;
    	//try {
    	//	ratingNumber = Integer.parseInt(ratingField.getText());
    	
    		
    		// Debug
    		//System.out.println(listView.getSelectionModel().getSelectedItem());
    		
    	Movie currentlySelectedMovie = listView.getSelectionModel().getSelectedItem();

    	if (currentlySelectedMovie != null) {
    		int result = movieQueries.DeleteMovie(currentlySelectedMovie.getId(),
    				currentlySelectedMovie.getName().toUpperCase(), currentlySelectedMovie.getRating(), currentlySelectedMovie.getDescription().toUpperCase());

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
    	//}
    	//catch (NumberFormatException e){
    	//	displayAlert(AlertType.ERROR, "Invalid entry",
    	//			"Please enter a valid integer between 1 and 10 for rating and try again.");    		
    	//}

    }

    @FXML
    void updateMovieButtonPressed(ActionEvent event) {
    	Movie currentlySelectedMovie = listView.getSelectionModel().getSelectedItem();

    	if (currentlySelectedMovie != null) {
    		
    		int ratingNumber = 0;
        	try {
        		ratingNumber = Integer.parseInt(ratingField.getText());
    		
    		
    		int result = movieQueries.UpdateMovie(nameField.getText().trim(), ratingNumber, descriptionText.getText().trim(),
    				currentlySelectedMovie.getName().toUpperCase(), currentlySelectedMovie.getRating(), currentlySelectedMovie.getDescription().toUpperCase());

    		if (result != -1) {
    			displayAlert(AlertType.INFORMATION, "Entry Updated",
    					"Movie was successfully updated.");
    		}
    		else {
    			displayAlert(AlertType.ERROR, "Entry Not Removed",
    					"Unable to update movie.");
    		}
        	}
        	catch (NumberFormatException e){
        		displayAlert(AlertType.ERROR, "Invalid entry",
        				"Please enter a valid integer between 1 and 10 and try again.");    		
        	}
    	}


    	getAllEntries();
    }
    
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

    @FXML
    void browseAllButtonPressed(ActionEvent event) {
    	getAllEntries();
    }

    private void displayAlert(AlertType type, String title, String message) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setContentText(message);
    	alert.showAndWait();
    }

}
