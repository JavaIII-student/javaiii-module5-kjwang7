package module5;

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
//import javafx.scene.text.Text;

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
    
    public void initialize() {
    	
    	
    	
    	
    	
    	
    	
    	tableView.setItems(movieList);
    	
    	//tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	TableColumn<Movie,Integer> idCol = new TableColumn<Movie,Integer>("id");
    	idCol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("id"));
    	TableColumn<Movie,String> nameCol = new TableColumn<Movie,String>("name");
    	//nameCol.setCellValueFactory(new PropertyValueFactory<Movie,String>("name"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<Movie,String>("name"));
    	TableColumn<Movie,Integer> ratingCol = new TableColumn<Movie,Integer>("rating");
    	ratingCol.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("rating"));
    	TableColumn<Movie,String> descriptionCol = new TableColumn<Movie,String>("description");
    	descriptionCol.setCellValueFactory(new PropertyValueFactory<Movie,String>("description"));
    	 
    	tableView.getColumns().setAll(idCol, nameCol, ratingCol, descriptionCol);
    	//tableView.getColumns().setAll(idCol, nameCol, ratingCol);
    	//tableView.getColumns().setAll(nameCol, descriptionCol);

    	
    	
    	getAllEntries();
    	
    	tableView.getSelectionModel().selectedItemProperty().addListener(
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
    	tableView.getSelectionModel().selectFirst();
    }
    
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

    
    
    @FXML
    void removeMovieButtonPressed(ActionEvent event) {
    	//int ratingNumber = 0;
    	//try {
    	//	ratingNumber = Integer.parseInt(ratingField.getText());
    	
    		
    		// Debug
    		//System.out.println(tableView.getSelectionModel().getSelectedItem());
    		
    	Movie currentlySelectedMovie = tableView.getSelectionModel().getSelectedItem();

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

    		
    		int result = movieQueries.UpdateMovie(idNumber, nameField.getText().trim(), ratingNumber, descriptionText.getText().trim(),
    				currentlySelectedMovie.getId(), currentlySelectedMovie.getName().toUpperCase(), currentlySelectedMovie.getRating(),
    				currentlySelectedMovie.getDescription().toUpperCase());

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
