package test.bufwriter;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author
 * @version 1.0
 */


import java.io.*;
import java.util.Random;


public class BufWriter extends Thread {
  public static void main (String[] args)
  {
   args = new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output", "little"};
    Buffer buf = new Buffer(1000);
    File outFile;
    Thread[] wr;
    int threadNum = 2,res;
    Checker checker = new Checker (buf);
    Thread tCheck = new Thread(checker);
    FileOutputStream outF;
    Random rGen = new Random();
    double noSyncProbability = 0.1;

//Output file creation
    if (args.length > 0)
      outFile = new File(args[0]);
    else return;

//Optional concurrency parameter
    if (args.length > 1)
    {
      if (args[1].equalsIgnoreCase("little")) threadNum = 2;
      if (args[1].equalsIgnoreCase("average")) threadNum = 5;
      if (args[1].equalsIgnoreCase("lot")) threadNum = 10;
    }

//Thread array creation
    wr = new Thread [threadNum];

//Threads creation
    for (int i=0;i<threadNum;i++)

    {
      if (rGen.nextDouble()> noSyncProbability + 1)
        wr[i] = new Thread(new SyncWriter(buf,i+1));
      else
        wr[i] = new Thread(new Writer(buf,i+1));
    }

// Checker thread starting
    tCheck.start();

//Output stream creation
    try{
      outF = new FileOutputStream(outFile);
    }
    catch (FileNotFoundException e) {return;}
    DataOutputStream outStream = new DataOutputStream(outF);


//Starting threads ...
    for (int i=0;i<threadNum;i++)
    {
      wr[i].start();
    }

    try{
        wr[0].join();
        wr[1].join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

////Let them work ...
//    try {
//     sleep (10);
//    }
//    catch (InterruptedException e) {}
//
////Stopping threads ...
//    for (int i=0;i<threadNum;i++)
//    {
//      wr[i].stop();
//    }

//Stopping checker thread
    tCheck.stop();

//Outputting results ...
    try
    {
      res = buf._count - (checker.getWrittenCount()+buf._pos);
      outStream.writeChars("<BufWriter,");
      outStream.writeChars(res+",");
      if (res != 0)
        outStream.writeChars("[Wrong/No-Lock]>");
      else
        outStream.writeChars("[None]>");
      outStream.close();
    }
    catch (IOException e) {}
    return;
  }

  public void test(){
//      RecordTimeInfo.recordInfo("BufWriter", "记录原始测试用例生成和执行的时间:",true);
//      for (int i = 0; i < 1; i++) {
//          long start = System.currentTimeMillis();
//          try {
//              BufWriter.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output", "little"});
//          } catch (Exception e) {
//              System.out.println("here");
//              fail();
//          }
//          long end = System.currentTimeMillis();
//          String timeInfo = "执行原始测试用例的时间为:" + (end - start);
//          if (i != 29){
//              RecordTimeInfo.recordInfo("BufWriter", timeInfo, true);
//          }else {
//              RecordTimeInfo.recordInfo("BufWriter", timeInfo, true);
//          }
//      }
//      BufWriter.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output", "little"});
  }

}