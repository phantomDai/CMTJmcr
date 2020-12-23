package test;

/**
 * @author Gena
 * @description
 * @date 2020/3/26 0026
 */
public class SimpleRWW {
    static int x = 1;

    static void readAndWrite(){
        x = x + 3;
    }

    static void write(){
        x = 5;
    }

    public static void main(String[] args) {


        try {

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

            Thread.sleep(1000);
            System.out.println(x);
//            System.out.println(y);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
