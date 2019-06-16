package com.verizon.media;

import com.verizon.media.SolutionOne.LogFileProcessorOne;
import org.junit.Test;

public class LogFileProcessorTest {

    @Test
    public void processLogFileTest() {
        LogFileProcessorOne tester = new LogFileProcessorOne();
        String filePath1 = "C:\\Users\\Shiva Prasad\\Google Drive\\Job Hunting\\Verizon Media Coding Assignment\\log1.txt";
        String filePath2 = "C:\\Users\\Shiva Prasad\\Google Drive\\Job Hunting\\Verizon Media Coding Assignment\\log2.txt";
        String filePath3 = "C:\\Users\\Shiva Prasad\\Google Drive\\Job Hunting\\Verizon Media Coding Assignment\\log3.txt";
        tester.processLogFile(filePath1);
        tester.processLogFile(filePath2);
        tester.processLogFile(filePath3);

    }
}
