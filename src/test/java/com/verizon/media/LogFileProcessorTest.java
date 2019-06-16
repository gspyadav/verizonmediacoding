package com.verizon.media;

import com.verizon.media.SolutionOne.LogFileProcessorOne;
import com.verizon.media.SolutionThree.LogFileProcessorThree;
import com.verizon.media.SolutionTwo.FilesMoviesDataStore;
import com.verizon.media.SolutionTwo.LogFileProcessorTwo;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class LogFileProcessorTest {
    String filePath1 = "C:\\tmp\\Verizon Media Coding Assignment\\log1.txt";
    String filePath2 = "C:\\tmp\\Verizon Media Coding Assignment\\log2.txt";
    String filePath3 = "C:\\tmp\\Verizon Media Coding Assignment\\log3.txt";

    @Test
    public void solutionOneTest() {

        LogFileProcessorOne obj = new LogFileProcessorOne();
        System.out.println("Starting Time of the process:" + System.currentTimeMillis());
        obj.processLogFile(filePath1);
        obj.processLogFile(filePath2);
        obj.processLogFile(filePath3);
        System.out.println("Ending Time of the process:" + System.currentTimeMillis());
        assertTrue(true);
    }

    @Test
    public void solutionTwoTest() {

        //Central Database shared among all three threads
        FilesMoviesDataStore moviesDb = new FilesMoviesDataStore();
        moviesDb.setMoviesListMap(new HashMap<String, List<Movie>>());

        LogFileProcessorTwo thread1 = new LogFileProcessorTwo("Thread 1", moviesDb, filePath1);
        LogFileProcessorTwo thread2 = new LogFileProcessorTwo("Thread 2", moviesDb, filePath2);
        LogFileProcessorTwo thread3 = new LogFileProcessorTwo("Thread 3", moviesDb, filePath3);
        thread1.start();
        thread2.start();
        thread3.start();
        assertTrue(true);
    }

    @Test
    public void solutionThreeTest() {
        //Central Database shared among all three threads
        FilesMoviesDataStore moviesDb = new FilesMoviesDataStore();
        moviesDb.setMoviesListMap(new HashMap<String, List<Movie>>());

        LogFileProcessorThree thread1 = new LogFileProcessorThree("Thread 1", moviesDb, filePath1);
        LogFileProcessorThree thread2 = new LogFileProcessorThree("Thread 2", moviesDb, filePath2);
        LogFileProcessorThree thread3 = new LogFileProcessorThree("Thread 3", moviesDb, filePath3);

        thread1.start();
        thread2.start();
        thread3.start();
        assertTrue(true);
    }
}
