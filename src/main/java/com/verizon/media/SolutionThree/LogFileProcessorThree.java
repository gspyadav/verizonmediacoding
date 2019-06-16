package com.verizon.media.SolutionThree;

import com.verizon.media.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class LogFileProcessorThree extends Thread {

    //private Map<String, List<Movie>> moviesListMap;
    private FilesMoviesDataStore moviesDb;
    private String filePath;
    private String threadName;
    private Thread thread;


    public LogFileProcessorThree(String threadName, FilesMoviesDataStore moviesDb, String filePath) {
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
    }

    public void run() {
        try {

            CompletableFuture<Void> cf1 = CompletableFuture.completedFuture(filePath).thenApplyAsync(filePath -> {
                System.out.println("Running Thread #: " + threadName);
                moviesDb.getMoviesListMap().put(filePath, new ArrayList<Movie>());
                return readFile(filePath);
            }).thenAcceptAsync(moviesList -> {
                displayProcessor(moviesList);
            });
           /* CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                System.out.println("Running Thread #: " + threadName);
                moviesDb.getMoviesListMap().put(filePath, new ArrayList<Movie>());
                displayProcessor(readFile(filePath));
            });*/


            System.out.println("Value- " + cf1.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    private List<Movie> readFile(String filePath) {
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
                    moviesDb.getMoviesList(filePath).add(newMovie);
                }
            }
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDb.getMoviesList(filePath);
    }

    private void display(String sortCriteria, List<Movie> moviesList) {
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
                System.out.println("\n Output for File: " + this.filePath + "\n");
                Collections.sort(moviesList);
                break;
        }

        System.out.println("Movie Sorting " + sortCriteria + " " + thread.getName() + " Start Time:" + new Date());
        for (int i = 0; i < 5 && i < moviesList.size(); i++) {
            //Category: Sports Title: Lola plays volleyball with Baxter,
            // Movie Length: 103, Movie Watched: 90, Ratio: 0.8737864077669902, # of Views 99366
            if (sortCriteria.equals(SortCriteria.WATCHED) && moviesList.get(i).getRatio() < 0.50) {
                //Skip it
            } else {
                System.out.println(thread.getName() + " Category: " + moviesList.get(i).getCategory()
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

    private void displayProcessor(List<Movie> moviesList) {
        display(SortCriteria.CATEGORY, moviesList);
        display(SortCriteria.TITLE, moviesList);
        display(SortCriteria.LENGTH, moviesList);
        display(SortCriteria.WATCHED, moviesList);
        display(SortCriteria.VIEWS, moviesList);
    }


    //Testing
    public static void main(String arg[]) {
        String filePath1 = "C:\\tmp\\Verizon Media Coding Assignment\\log1.txt";
        String filePath2 = "C:\\tmp\\Verizon Media Coding Assignment\\log2.txt";
        String filePath3 = "C:\\tmp\\Verizon Media Coding Assignment\\log3.txt";

        //Central Database shared among all three threads
        FilesMoviesDataStore moviesDb = new FilesMoviesDataStore();
        moviesDb.setMoviesListMap(new HashMap<String, List<Movie>>());

        LogFileProcessorThree thread1 = new LogFileProcessorThree("Thread 1", moviesDb, filePath1);
        LogFileProcessorThree thread2 = new LogFileProcessorThree("Thread 2", moviesDb, filePath2);
        LogFileProcessorThree thread3 = new LogFileProcessorThree("Thread 3", moviesDb, filePath3);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
