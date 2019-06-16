package com.verizon.media.SolutionOne;

import com.verizon.media.*;
import com.verizon.media.Movie;

import java.io.*;
import java.util.*;

/**
 * @author Shiva Prasad Gaddameedi
 *
 * Java program that takes as input each of these files, and the number of elements to display (X).
 * The program then reads and process each log file sequentially, print out the start and end time for each processing,
 * and overall start and end time.  Account for error checking and handle bad data.
 *
 * Use the processLogFile() method to pass the file path and process the given file.
 */
public class LogFileProcessorOne {

    private Map<String, List<Movie>> moviesListMap = new HashMap<String, List<Movie>>();
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sorting Criteria's
     */
    public static class SortCriteria {
        public static final String CATEGORY = "Category";
        public static final String TITLE = "Title";
        public static final String LENGTH = "Length";
        public static final String WATCHED = "Watched";
        public static final String VIEWS = "Views";
    }

    /**
     *
     * @param filePath
     * @param moviesList
     */
    private void readFile(String filePath, List<Movie> moviesList) {
        try {
            FileInputStream fstream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                //Line format: SPORTS Zeus plays soccer with Bruno 66ms 47ms 7486 views
                String[] words = strLine.split(" ");
                if (words.length < 6) {
                    //Not a valid line, so skip it
                } else {
                    String title = "";
                    for (int i = 1; i < words.length - 4; i++) {
                        title += words[i];
                        if (i != words.length - 5) {
                            title += " ";
                        }
                    }
                    Movie newMovie = new Movie();
                    newMovie.setCategory(words[0]);
                    newMovie.setTitle(title);
                    newMovie.setLength(Integer.parseInt(words[words.length - 4].substring(0, words[words.length - 4].indexOf("ms"))));
                    newMovie.setAvgWatched(Integer.parseInt(words[words.length - 3].substring(0, words[words.length - 3].indexOf("ms"))));
                    newMovie.setViews(Integer.parseInt(words[words.length - 2]));
                    newMovie.computeRatio();
                    moviesList.add(newMovie);
                }
            }
            //Close the input stream
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param moviesList
     * @param sortCriteria
     */
    private void display(List<Movie> moviesList, String sortCriteria) {
        System.out.println("Movie Sorting " + sortCriteria);
        switch (sortCriteria) {
            case SortCriteria.LENGTH:
                Collections.sort(moviesList, new MovieLengthCompare());
                break;
            case SortCriteria.TITLE:
                Collections.sort(moviesList, new MovieTitleCompare());
                break;
            case SortCriteria.VIEWS:
                Collections.sort(moviesList, new MovieViewsCompare());
                break;
            case SortCriteria.WATCHED:
                Collections.sort(moviesList, new MovieWatchedCompare());
                break;
            default:
                Collections.sort(moviesList);
                break;
        }
        System.out.println(" Start Time:" + new Date());
        for (int i = 0; i < 5 && i < moviesList.size(); i++) {
            //Category: Sports Title: Lola plays volleyball with Baxter,
            // Movie Length: 103, Movie Watched: 90, Ratio: 0.8737864077669902, # of Views 99366
            if(sortCriteria.equals(SortCriteria.WATCHED) && moviesList.get(i).getRatio()<0.50){
                //Skip it
            }else{
                System.out.println("Category: " + moviesList.get(i).getCategory()
                        + ", Title: " + moviesList.get(i).getTitle()
                        + ", Movie Length: " + moviesList.get(i).getLength()
                        + ", Movie Watched: " + moviesList.get(i).getAvgWatched()
                        + ", Ratio: " + moviesList.get(i).getRatio()
                        + ", # of Views " + moviesList.get(i).getViews());
            }
        }
        System.out.println(" End Time:" + new Date());
        System.out.println();
    }

    /**
     *
     * @param moviesList
     */
    private void displayProcessor(List<Movie> moviesList) {
        display(moviesList, SortCriteria.CATEGORY);
        display(moviesList, SortCriteria.TITLE);
        display(moviesList, SortCriteria.LENGTH);
        display(moviesList, SortCriteria.WATCHED);
        display(moviesList, SortCriteria.VIEWS);
    }

    /**
     *
     * @param filePath
     */
    public void processLogFile(String filePath){
        moviesListMap.put(filePath, new ArrayList<Movie>());
        readFile(filePath, moviesListMap.get(filePath));
        System.out.println("\n Output for File: "+filePath+"\n");
        displayProcessor(moviesListMap.get(filePath));
    }


    /**
     * main method for sample test run of this individual class
     * @param arg
     */
    public static void main(String arg[]) {

        String filePath1 = "C:\\tmp\\Verizon Media Coding Assignment\\log1.txt";
        String filePath2 = "C:\\tmp\\Verizon Media Coding Assignment\\log2.txt";
        String filePath3 = "C:\\tmp\\Verizon Media Coding Assignment\\log3.txt";

        LogFileProcessorOne obj = new LogFileProcessorOne();
        System.out.println("Starting Time of the process:" + new Date());
        obj.processLogFile(filePath1);
        obj.processLogFile(filePath2);
        obj.processLogFile(filePath3);
        System.out.println("Ending Time of the process:" + new Date());
    }
}
