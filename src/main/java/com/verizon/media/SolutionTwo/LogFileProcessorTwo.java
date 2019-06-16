package com.verizon.media.SolutionTwo;

import com.verizon.media.*;

import java.io.*;
import java.util.*;

/**
 * @author Shiva Prasad Gaddameedi
 *
 * Create a program that does everything in #1 but creates a separate thread per log file.
 * Use a shared data structure for the overall status. Ensure thread safety on the shared data structure.
 * Ensure that multiple threads are accessing the same resource at the same time.
 * Make sure to include the Thread number in the console output.
 *
 * FilesMoviesDataStore Object is the common datastore used between the mutiple thread. Run main method for sample test run.
 */
public class LogFileProcessorTwo extends Thread {

    //private Map<String, List<Movie>> moviesListMap;
    private FilesMoviesDataStore moviesDb;
    private String filePath;
    private String threadName;
    private Thread thread;

    /**
     *
     * @param threadName
     * @param moviesDb
     * @param filePath
     */
    public LogFileProcessorTwo(String threadName, FilesMoviesDataStore moviesDb, String filePath) {
        this.threadName = threadName;
        this.moviesDb = moviesDb;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static class SortCriteria {
        public static final String CATEGORY = "Category";
        public static final String TITLE = "Title";
        public static final String LENGTH = "Length";
        public static final String WATCHED = "Watched";
        public static final String VIEWS = "Views";
        public static final String RATIO = "Ratio";
    }

    /**
     *
     */
    public void run() {
        try {
            System.out.println("Running Thread #: " + threadName);
            moviesDb.getMoviesListMap().put(filePath, new ArrayList<Movie>());
            readFile(filePath, moviesDb.getMoviesListMap().get(filePath));
            displayProcessor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void start() {
        //System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
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
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param sortCriteria
     */
    private void display(String sortCriteria) {
        synchronized (moviesDb){
        switch (sortCriteria) {
            case SortCriteria.LENGTH:
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath), new MovieLengthCompare());
                break;
            case SortCriteria.TITLE:
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath), new MovieTitleCompare());
                break;
            case SortCriteria.VIEWS:
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath), new MovieViewsCompare());
                break;
            case SortCriteria.RATIO:
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath), new MovieRatioCompare());
                break;
            case SortCriteria.WATCHED:
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath), new MovieWatchedCompare());
                break;
            default:
                System.out.println("\n Output for File: " + this.filePath + "\n");
                Collections.sort(moviesDb.getMoviesListMap().get(this.filePath));
                break;
        }

            System.out.println("Movie Sorting " + sortCriteria + " " + thread.getName() + " Start Time:" + System.currentTimeMillis());
            for (int i = 0; i < 5 && i < moviesDb.getMoviesListMap().get(this.filePath).size(); i++) {
                //Category: Sports Title: Lola plays volleyball with Baxter,
                // Movie Length: 103, Movie Watched: 90, Ratio: 0.8737864077669902, # of Views 99366
                if (sortCriteria.equals(SortCriteria.RATIO) && moviesDb.getMoviesListMap().get(this.filePath).get(i).getRatio() < 0.50) {
                    //Skip it
                } else {
                    System.out.println("Category: " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getCategory()
                            + ", Title: " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getTitle()
                            + ", Movie Length: " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getLength()
                            + ", Movie Watched: " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getAvgWatched()
                            + ", Ratio: " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getRatio()
                            + ", # of Views " + moviesDb.getMoviesListMap().get(this.filePath).get(i).getViews());
                }
            }
            System.out.println(" End Time:" + System.currentTimeMillis());
        }
        System.out.println();
    }

    /**
     *
     */
    private void displayProcessor() {
        display(SortCriteria.CATEGORY);
        display(SortCriteria.TITLE);
        display(SortCriteria.LENGTH);
        display(SortCriteria.RATIO);
        display(SortCriteria.WATCHED);
        display(SortCriteria.VIEWS);
        System.out.println("After Synchronization the size of moviesListMap " + moviesDb.getMoviesListMap().size());
    }

    public static void main(String args[]){
        String filePath1 = "C:\\tmp\\Verizon Media Coding Assignment\\log1.txt";
        String filePath2 = "C:\\tmp\\Verizon Media Coding Assignment\\log2.txt";
        String filePath3 = "C:\\tmp\\Verizon Media Coding Assignment\\log3.txt";

        //Central Database shared among all three threads
        FilesMoviesDataStore moviesDb = new FilesMoviesDataStore();
        moviesDb.setMoviesListMap(new HashMap<String, List<Movie>>());

        System.out.println("Starting Time of the process:" + System.currentTimeMillis());
        LogFileProcessorTwo thread1 = new LogFileProcessorTwo("Thread 1", moviesDb, filePath1);
        LogFileProcessorTwo thread2 = new LogFileProcessorTwo("Thread 2", moviesDb, filePath2);
        LogFileProcessorTwo thread3 = new LogFileProcessorTwo("Thread 3", moviesDb, filePath3);

        thread1.start();
        thread2.start();
        thread3.start();
        System.out.println("Ending Time of the process:" + System.currentTimeMillis());
    }

}
