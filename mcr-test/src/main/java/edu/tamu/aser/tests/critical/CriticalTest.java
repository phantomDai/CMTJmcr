package edu.tamu.aser.tests.critical;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author GN
 * @description
 * @date 2020/10/4
 */
@RunWith(JUnit4MCRRunner.class)
public class CriticalTest {

    /**
     * 开启线程，运行待测程序，获取原始测试用例的执行轨迹
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        Thread t1, t2;
        Critical t = new Critical();
        Section s1 = new Section(t, 0);
        Section s2 = new Section(t, 1);

        t1=new Thread(s1);
        t2 = new Thread(s2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("i am here!");
        System.out.println(System.currentTimeMillis());

    }

    @Test
    public void test() {
        main(new String[]{});
    }
}
