package edu.tamu.aser.tests.fileWriter;/*
 *
 * Haim Cohen 0-3438837-1
 *
 */
 
import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.Random;

@RunWith(JUnit4MCRRunner.class)
public class MyFileWriter {
    
    public static void createInfputFile(String fileName, int size) throws IOException {
        FileWriter outputFile = new FileWriter(fileName);
        BufferedWriter outputBuffer = new BufferedWriter(outputFile);
        Random rnd = new Random(System.currentTimeMillis());
        
        for (int i=0; i < size; ++i) {
            int a = rnd.nextInt();
            int b = rnd.nextInt();
            int c = rnd.nextInt();
            int d = rnd.nextInt();
            
            String line = a + " " + b + " " + c + " " + d + "\n";
            outputBuffer.write(line);
        }
        outputBuffer.flush();
    }

    public static void main(String[] args) throws IOException {
        String solutionFilePath = args[0];
        int numberOfInts = Integer.parseInt(args[1]);
        createInfputFile(solutionFilePath, numberOfInts);
    }

    @Test
    public void test() throws IOException {
        MyFileWriter.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output","2"});
    }

}