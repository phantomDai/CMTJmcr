package edu.tamu.aser.tests.manager;

import constants.Constants;
import edu.tamu.aser.reex.JUnit4MCRRunner;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;

@RunWith(JUnit4MCRRunner.class)
public class Manager
{
    static int request_counter = 0;
    static int released_counter = 0;
    static boolean flag = false;
    static String req_counter_lock= "";
    static String rel_counter_lock= "";
    static String notes_lock= "";
    static int num_of_notes_set = 0;

    /**
     * gets 2 parameters:   1. number of threads
     *                      2. number of pointers to release
     */
    public static void main(String[] arg)
    {
        int init_req_counter;
        if ( arg.length == 2)
        {
            init_req_counter=request_counter = 2;
        }
        else if (arg.length != 3){
            System.out.println("ERROR - wrong number of arguments");
            System.out.println("Usage:Manager OutputFile NumOfThreads NumOfPtrs ");
            return;
        }
        else
        {
            init_req_counter=request_counter = Integer.parseInt(arg[2]);
        }
        int num_of_threads = Integer.parseInt(arg[1]);
        Trelease[] releasers = new Trelease[ num_of_threads ];
        TmemoryHandler t =  new TmemoryHandler();
        t.start();
        for ( int i = 0 ; i < num_of_threads ; ++i)
        {
            releasers[i] = new Trelease(i);
            releasers[i].start();
        }
        for ( int i = 0 ; i < num_of_threads ; ++i)
        {
            try
            {
                releasers[i].join();
            }catch( InterruptedException e){ }
        }
        try{
            t.join();
        }catch( InterruptedException e){ }

        System.out.println("Number of memory blocks to release:" + init_req_counter);
        System.out.println("Number of memory blocks released:" + released_counter);
        FileOutputStream outStream;
        DataOutputStream outputStream;
        try
        {
            outStream = new FileOutputStream(arg[0],true);
            outputStream = new DataOutputStream (outStream);
            flag = !(init_req_counter == released_counter);
            outputStream.writeBytes( "Program name: Manager , Bug found: " + flag + "\r\n");
            outputStream.flush();
            outputStream.close();
            outStream.close();
        }
        catch (Exception E){System.out.println("Unable to write results to file "+E.getMessage());}
    }
    public static void setNote(int index,boolean op)
    {
        synchronized(Manager.notes_lock)
        {
            if ( op )
            {
                num_of_notes_set++;

            }
            else
            {
                 num_of_notes_set--;
            }
        }

    }

    public static boolean isOtherNoteSet()
    {
        synchronized(Manager.notes_lock)
        {
            return num_of_notes_set == 1;
        }

    }

    @Test
    public void test() {
        RecordTimeInfo.recordInfo("Manager",
                "记录原始测试用例生成和执行的时间:",true);
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            Manager.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output", "2","10"});
            long end = System.currentTimeMillis();
            String timeInfo = "执行原始测试用例的时间为:" + (end - start);
            if (i != 29){
                RecordTimeInfo.recordInfo("Manager", timeInfo, true);
            }else {
                RecordTimeInfo.recordInfo("Manager", timeInfo, true);
            }
        }
    }

}