package test.mtSet;

import java.util.Random;

public class MyRandom {
    public static Random rand = new Random(1);

    public static boolean nextRandom(){
        return rand.nextBoolean();
    }

    public static int nextInt(int max){
        return rand.nextInt(max);
    }
}
