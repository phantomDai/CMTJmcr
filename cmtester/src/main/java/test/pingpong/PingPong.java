package test.pingpong;
/**
 *@author Golan
 * 17/10/2003
 * 11:59:43
 *@version 1.0
 */


import log.RecordTimeInfo;

import java.io.*;

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
            out.writeBytes("Number Of Threads: " + this.threadsNumber + " Number Of Bugs: ");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        this.bug.doWork();
    }
    public static void main(String[] args) {
        RecordTimeInfo.recordInfo("PingPong", "开始记录在MP5指导下衍生测试用例生成和执行的时间:",true);
        for (int i = 0; i < 30; i++) {
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
                PingPong fewThreads = new PingPong();
                setup(out, 5);
                fewThreads.doWork();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
            long end = System.currentTimeMillis();
            String timeInfo = "执行MP1的时间:" + String.valueOf(end - start);
            RecordTimeInfo.recordInfo("PingPong", timeInfo, true);
        }
    }
    private static void setup(DataOutputStream out2, int i) {
		// TODO Auto-generated method stub
		out = out2;
		threadsNumber = i;
		bug = new BuggedProgram(out, threadsNumber);
	}

  	public void test() {
  	   PingPong.main(new String[]{});
    }

}
