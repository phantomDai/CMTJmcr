package edu.tamu.aser.tests.rww;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gena
 * @description
 * @date 2020/1/15
 */
@RunWith(JUnit4MCRRunner.class)
public class AccountTest {
    Account x, y;
    Thread t1 = new Thread(){
        @Override
        public void run(){
            if (x.isLegal()) {
                t2.start();y = new Account(x);
            }
        }
    };
    Thread t2 = new Thread(){
        @Override
        public void run(){
            x.countInterest(0.01);
        }
    };
    @Test
    public void testCase(){
        x = new Account(400,700);
        t1.start();
    }
}