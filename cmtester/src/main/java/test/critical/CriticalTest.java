package test.critical;


/**
 * @author GN
 * @description
 * @date 2020/10/4
 */
public class CriticalTest {

    public static void main(String[] args) {
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
    }


}
