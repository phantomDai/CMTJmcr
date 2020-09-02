package edu.tamu.aser.tests;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnit4MCRRunner.class)
public class MyTest {
    static int a = 0;
    static int b = 1;

    @Test
    public void test() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                System.out.println("线程1读取b : " + b);
            }
        }).start();
        Thread.sleep(10);
        if(a == 0){ //Read  a 0
            b = 2;   //Write b 2
        } else {
            System.err.println("Error! Read a != 0");
            System.exit(-1);
        }
        System.out.println("main:b" +b); // Read b 2
    }
}