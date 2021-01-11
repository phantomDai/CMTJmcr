package edu.tamu.aser.tests.ABPushPop; /*************************************************************/
/* (C) IBM Corporation (2007), ALL RIGHTS RESERVED 	         */
/*                                                           */
/*	Benny Godlin	1/3/2007	Class created                */

import org.junit.Test;

/*************************************************************/
// Note: ConTest only version - not part of CMGTT

public class RunTest extends DummyControl {

    public int timesToRun = 5000; // like > 10 iterations of CMGTT

    TestMyBuff buf;

    int numOverflows = 0;
    
    public void testRun(String args[]) throws AnyException {
        collectTestProgThreads(args);

        // create the testProg to know how many threads there are.
        createRunnable();

        System.out.println("Executions:");            
        int i;
        for (i = 0; i<timesToRun; ++i) {
            System.out.print("#"+i+" ");

            activateVerbose();
            startTestProg();
            waitTestProgThreads();
            
            numOverflows += buf.overflows;

            if (i<timesToRun)
                createRunnable();
        }
        
        System.out.println("\nRunTest: Finished executing "+i+" test program runs.");
        System.out.println("Total number of overflows: "+numOverflows);
    }   

    protected String setThreadName(Thread it, int index, int numOfSameClass) {
        String threadName = "Actor_"+ (index % 2);  // generate two groups
        it.setName(threadName);
        return threadName;
    }

    void activateVerbose() throws AnyException {
        Object   obj;
        int num = testObjs.size();
        
        for (int i=0; i<num; ++i)
            if ( (obj = testObjs.get(i)) instanceof TestMyBuff)
                buf = (TestMyBuff) obj;
    
        if (buf == null)
            throw new AnyException("RunTest.activateVerbose(): unable to find TestBuff in testObjs !");

        buf.verbose = true;
    }

    public static void main(String args[]) throws AnyException {       
        // Hardcoded arguments - for ConTest benchmarks:
        args = splitCommandLine("1 edu.tamu.aser.tests.ABPushPop.TestMyBuff 4 edu.tamu.aser.tests.ABPushPop.TestActor");
        printArgs(args);
        new RunTest().testRun(args);
    }

    @Test
    public void test() throws AnyException {
        RunTest.main(null);
    }

}

