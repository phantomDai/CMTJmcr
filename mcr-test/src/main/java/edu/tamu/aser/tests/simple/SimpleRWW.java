package edu.tamu.aser.tests.simple;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gena
 * @description
 * @date 2020/3/26 0026
 */
@RunWith(JUnit4MCRRunner.class)
public class SimpleRWW {

    static int x;

    static void readAndWrite(){
        int y = 2;

        y = y + 10;

        x = x + 3;

    }

    static void write(){
        x = 5;

    }

    @Test
    public void testcase(){

        x=1;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                readAndWrite();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                write();
            }
        });

        t1.start();
        t2.start();

    }
}
