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

    static int a;
    static void f1(){
        a = a + 2;
    }

    static void f2(){
        a = 3;

    }

    @Test
    public void testcase(){

        a=1;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                f1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                f2();
            }
        });

        t1.start();
        t2.start();

    }
}
