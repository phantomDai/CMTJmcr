package edu.tamu.aser.tests.mergesort2;

import java.io.File;

public class MyTest {
    public static void main(String[] args) {
        String cmdstr = "java -javaagent:/Users/phantom/javaDir/CMTJmcr/libs/agent.jar edu.tamu.aser.tests.mergesort2.MergeSortBug /Users/phantom/javaDir/CMTJmcr/output/output little";
        Runtime runTime = Runtime.getRuntime();
        try {
            runTime.exec(cmdstr, null, new File("/Users/phantom/javaDir/CMTJmcr/mcr-test/target/classes"));
        } catch (Exception e) {
            e.printStackTrace();}
    }
}
