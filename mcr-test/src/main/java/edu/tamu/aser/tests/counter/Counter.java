package edu.tamu.aser.tests.counter;
import static org.junit.Assert.fail;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.lottery.BuggyProgram;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnit4MCRRunner.class)
public class Counter
{
    public static int counter;
    public final static int MAX=5;

    public static void main(String[] args)
    {

        counter = 0;
        Thread inc = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i=0; i<MAX; i++){
                    counter = counter +1;
                }
            }
        });
        Thread dec = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<MAX; i++){
                    counter = counter - 1;
                }
            }
        });

        inc.start();
        dec.start();

        try {
            inc.join();
            dec.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {

//            RecordTimeInfo.recordInfo("Counter", "记录原始测试用例生成和执行的时间:",true);
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            try {
                Counter.main(null);
            } catch (Exception e) {
                System.out.println("here");
                fail();
            }
            long end = System.currentTimeMillis();
            String timeInfo = "执行原始测试用例的时间为:" + (end - start);
            if (i != 29){
                RecordTimeInfo.recordInfo("Counter", timeInfo, true);
            }else {
                RecordTimeInfo.recordInfo("Counter", timeInfo, true);
            }
        }
    }
}