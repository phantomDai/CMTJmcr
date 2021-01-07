package test.mtSet;

import log.RecordTimeInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Koushik Sen (ksen@cs.uiuc.edu)
 * Date: Dec 29, 2005
 * Time: 10:21:17 AM
 */
public class MTSetTest extends Thread {
    Set s1, s2;
    int c;
//
//    public MTSetTest(Set s1, Set s2, int c) {
//        this.s1 = s1;
//        this.s2 = s2;
//        this.c = c;
//    }

    public void run() {
        SimpleObject o1 = new SimpleObject(MyRandom.nextInt(3000));
        switch (c) {
            case 0:
                s1.add(o1);
                break;
            case 1:
                s1.size();
                break;
            case 2:
                s1.clear();
                break;
            case 3:
                s1.contains(o1);
                break;
            case 4:
                s1.remove(o1);
                break;
            case 5:
                s1.toArray();
                break;
            case 6:
                s1.isEmpty();
                break;
            case 7:
                s1.iterator();
                break;
            case 8:
                s1.addAll(s2);
                break;
            case 9:
                s1.equals(s2);
                break;
            case 10:
                s1.retainAll(s2);
                break;
            case 11:
                s1.containsAll(s2);
                break;
            default:
                s1.removeAll(s2);
                break;
        }
    }

    public static void main(String[] args) {
//        RecordTimeInfo.recordInfo("MTSet", "开始记录在MP1指导下衍生测试用例生成和执行的时间:",true);
//        for (int j = 0; j < 30; j++) {
//            long start = System.currentTimeMillis();
            Set s1 = Collections.synchronizedSet(new HashSet());
            Set s2 = Collections.synchronizedSet(new HashSet());
            s1.add(new SimpleObject(MyRandom.nextInt(3000)));
            s1.add(new SimpleObject(MyRandom.nextInt(3000)));
            s2.add(new SimpleObject(MyRandom.nextInt(3000)));
            s2.add(new SimpleObject(MyRandom.nextInt(3000)));
            for (int i = 12; i >= 0; i--) {
                MTSetTest mtSetTest = new MTSetTest();
                setField(s1, s2, i, mtSetTest);
                mtSetTest.start();
//            (new MTSetTest(s1, s2, i)).start();
            }
            for (int i = 7; i >=0 ; i--) {
                MTSetTest mtSetTest = new MTSetTest();
                setField(s2, s1, i, mtSetTest);
                mtSetTest.start();
//            (new MTSetTest(s2, s1, i)).start();
            }
//            long end = System.currentTimeMillis();
//            String timeInfo = "执行MP1的时间:" + String.valueOf(end - start);
//            RecordTimeInfo.recordInfo("MTSet", timeInfo, true);
//        }

    }

    private static void setField(Set s1, Set s2, int i, MTSetTest mtSetTest) {
        mtSetTest.s1 = s1;
        mtSetTest.s2 = s2;
        mtSetTest.c = i;
    }
}
