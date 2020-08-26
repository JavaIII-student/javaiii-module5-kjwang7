# Movie Database

A Movie Database built with JavaFX for the GUI and derby for the database.

The GUI will display the movie information to the user.

Name of the derby database is MovieDB. Movies are stored in the table named MOVIES.

Each movie has an ​id ​(int), ​name (varchar(255)), ​rating​ from 1 to 10 (int), and a ​description​ (varchar(255)). id is the primary key in the MOVIES table.

The application initially has two example movies in the database.

**To run the application, import as a Maven project and then run src/main/java/module5/MovieDB.java as a Java Application**

#### User can:

**1. Add movies to the database.** When a new movie is added, it will be displayed to the user.

Movie id must be entered as an integer. Otherwise, an error dialogue will pop up to remind the user.

Movie id must be unique, i.e., different from any id of existing movies in the database. Otherwise, an error dialogue will pop up to remind the user.

Movie rating must be an integer between 1 and 10. Otherwise, an error dialogue will pop up.

**2. Remove movies from the database.**

**3. Update movies in the database.**

Same requirements as for adding movies, as described in 1.

**4. Search movies based on name (partial match).**
