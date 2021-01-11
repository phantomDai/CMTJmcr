package edu.tamu.aser.tests.ABPushPop; /*************************************************************/
/* (C) IBM Corporation (2007), ALL RIGHTS RESERVED 	         */
/*                                                           */
/*	Benny Godlin	6/3/2007	Class created                */
/*************************************************************/


import java.io.*;

public class TestActor extends Thread {
    int numIterations;
    TestMyBuff buf;
    PrintStream out;
    public boolean verbose = false;

    public TestActor() {
        out = System.out;

        // create the buffer if doesn't exist yet 
        if (TestMyBuff.singleton == null)
            new TestMyBuff();

        buf = TestMyBuff.singleton;
        buf.init();
        numIterations = buf.numIterations;        
    }

    public void run() {
        

        boolean ok;
        String myName = getName();

        for (int i=0; i<numIterations; ++i) {
            ok = buf.action(myName);
            if (verbose)
                out.println(myName + (ok ? " pushed" : " failed to push")+" one item.");
        }

        
    }
}
