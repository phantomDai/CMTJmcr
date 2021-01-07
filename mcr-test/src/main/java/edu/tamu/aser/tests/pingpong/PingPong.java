package edu.tamu.aser.tests.pingpong;
/**
 *@author Golan
 * 17/10/2003
 * 11:59:43
 *@version 1.0
 */


import log.RecordTimeInfo;
import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;


@RunWith(JUnit4MCRRunner.class)
/**
 *
 */
public class PingPong {


    private static BuggedProgram bug;


    private static DataOutputStream out;


    private static int threadsNumber;


    /**
     *
     */
//    private PingPong(DataOutputStream output, int threadsNumber) {
//        this.out = output;
//        this.threadsNumber = threadsNumber;
//        this.bug = new BuggedProgram(output, threadsNumber);
//    }


//    public PingPong() {
//    	
//    }
    
    public void doWork() {
        String newLine = System.getProperty("line.separator");
        try {
            out.writeBytes("Number Of Threads: " + threadsNumber + " Number Of Bugs: ");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        bug.doWork();
    }


    public static void main(String[] args) {
        File output = new File("output.txt");
        long start = System.currentTimeMillis();
        DataOutputStream out = null;
        try {
            FileOutputStream os = new FileOutputStream(output);
            out = new DataOutputStream(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        try {

            String newLine = System.getProperty("line.separator");
            out.writeBytes("In this file you will find the number of the bug appearances " +
                    "accordingly to the number of threads that the " +
                    "bugged program utilized with:" + newLine + newLine);

            out.writeBytes("Few Threads: " + newLine + newLine);
            long testcase = System.currentTimeMillis();
            String testCaseInfo = "生成测试用例的时间:" + (testcase - start);
            RecordTimeInfo.recordInfo("PingPong", testCaseInfo, false);
            PingPong fewThreads = new PingPong();
            setup(out, 5); //initialize the value since it can't have constructor
            fewThreads.doWork();

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    private static void setup(DataOutputStream out2, int i) {
		// TODO Auto-generated method stub
		out = out2;
		threadsNumber = i;
		bug = new BuggedProgram(out, threadsNumber);
	}


	@Test
  	public void test() {
        RecordTimeInfo.recordInfo("PingPong",
                "记录原始测试用例生成和执行的时间:",true);
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            PingPong.main(new String[]{});
            long end = System.currentTimeMillis();
            String timeInfo = "执行原始测试用例的时间为:" + (end - start);
            if (i != 29){
                RecordTimeInfo.recordInfo("PingPong", timeInfo, true);
            }else {
                RecordTimeInfo.recordInfo("PingPong", timeInfo, true);
            }
        }


    }

}
