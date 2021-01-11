package edu.tamu.aser.tests.critical;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.lottery.BuggyProgram;
import log.RecordTimeInfo;
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
//            RecordTimeInfo.recordInfo("Critical", "记录原始测试用例生成和执行的时间:",true);
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            main(new String[]{});
            long end = System.currentTimeMillis();
            String timeInfo = "执行原始测试用例的时间为:" + (end - start);
            if (i != 29){
                RecordTimeInfo.recordInfo("Critical", timeInfo, true);
            }else {
                RecordTimeInfo.recordInfo("Critical", timeInfo, true);
            }
        }

    }
}
