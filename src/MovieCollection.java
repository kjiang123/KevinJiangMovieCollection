import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    // same deal as the sortResults method, in fact it is almost a copy and paste.
    private void sortStringResults(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String tempTitle = listToSort.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, tempTitle);
        }
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T item : list) {
            if (!newList.contains(item)) {
                newList.add(item);
            }
        }
        return newList;
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        System.out.print("Enter a name: ");
        String searchName = scanner.nextLine();
        searchName = searchName.toLowerCase();
        ArrayList<String> dudes = new ArrayList<String>();

        for(int i = 0; i < movies.size(); i++){
            String[] people = movies.get(i).getCast().split("\\|");
            for( int x = 0; x < people.length; x++){
                people[x] = people[x].toLowerCase();
            }
            for(int j = 0; j < people.length; j++) {
                if (people[j].contains(searchName)) {
                    dudes.add(people[j]);
                }
            }
        }
        dudes = removeDuplicates(dudes);
        sortStringResults(dudes);
        for (int i = 0; i < dudes.size(); i++)
        {
            String name = dudes.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        searchName = dudes.get(choice - 1).toLowerCase();
        for (int i = 0; i < movies.size(); i++)
        {
            String movieActors = movies.get(i).getCast();
            movieActors = movieActors.toLowerCase();

            if (movieActors.contains(searchName))
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int Dachoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(Dachoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> word = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String keyWord = movies.get(i).getKeywords();
            keyWord = keyWord.toLowerCase();
            if(keyWord.contains(searchTerm)){
                word.add(movies.get(i));
            }
        }

        for (int i = 0; i < word.size(); i++)
        {
            String title = word.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = word.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> genres = new ArrayList<String>();
        String[] genreGet;
        for(int i = 0; i < movies.size(); i++){
            genreGet = movies.get(i).getGenres().split("\\|");
            for(int j = 0; j < genreGet.length; j++){
                genres.add(genreGet[j]);
            }
        }
        genres = removeDuplicates(genres);
        sortStringResults(genres);

        for (int i = 0; i < genres.size(); i++)
        {
            String genre = genres.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String searchTerm = genres.get(choice - 1).toLowerCase();

        for(Movie m : movies){
            String genre = m.getGenres().toLowerCase();
            if(genre.contains(searchTerm)){
                results.add(m);
            }
        }
        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int Dachoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(Dachoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRated()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i < 50; i++){
            Movie temp = new Movie("", "", "", "", "", "", 0, "", 0.0, 0, 25);
            for(Movie m : movies){
                if(!results.contains(m) && m.getUserRating() > temp.getUserRating()){
                    temp = m;
                }
            }
            results.add(temp);
        }
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int Dachoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(Dachoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i < 50; i++){
            Movie temp = new Movie("", "", "", "", "", "", 0, "", 0.0, 0, 25);
            for(Movie m : movies){
                if(!results.contains(m) && m.getRevenue() > temp.getRevenue()){
                    temp = m;
                }
            }
            results.add(temp);
        }
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int Dachoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(Dachoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}