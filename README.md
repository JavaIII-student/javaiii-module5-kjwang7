# Movie Database

A Movie Database built with JavaFX for the GUI and derby for the database.

Each movie has an ​id ​(int), ​name (varchar(255)), ​rating​ from 1 to 10 (int), and a ​description​ (varchar(255)). id is the primary key in the movies table.

The GUI will display the movie information to the user.

**To launch the application, Run src/main/java/module5/MovieDB.java as a Java Application**

#### User can:

**1. add additional movies to the database (when a new movie is added, it will be displayed to the user).**

Movie id must be entered as an integer. Otherwise, an error dialogue will pop up to remind the user.

Movie id must be unique, i.e., different from any id of existing movies in the database. Otherwise, an error dialogue will pop up to remind the user.

Movie rating must be an integer between 1 and 10. Otherwise, an error dialogue will pop up.

**2. remove movie from the database**

**3. update movie information in the database.**

Same requirements as for adding additional movies, described in 1.

**4. search movie based on name (partial match)**