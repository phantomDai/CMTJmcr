package edu.tamu.aser.tests.acc;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gena
 * @description
 * @date 2020/6/2 0002
 */
@RunWith(JUnit4MCRRunner.class)
public class AccTest {
    Acc x, y;
    Thread t1 = new Thread(){
        @Override
        public void run(){
            if (x.isLegal()) {
                t2.start();
                y = new Acc(x);
                System.out.println(y.checking+" "+y.saving);
            }
        }
    };
    Thread t2 = new Thread(){
        @Override
        public void run(){
            x.countInterest(0.01);
            System.out.println(x.checking+" "+x.saving);
        }
    };
    @Test
    public void testCase(){
        x = new Acc(400,700);
        t1.start();
    }
}
