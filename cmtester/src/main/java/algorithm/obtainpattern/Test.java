package algorithm.obtainpattern;


import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description
 * @date 2020/10/30
 */
public class Test {
    public static void main(String[] args){
            long start = System.currentTimeMillis();
            String path = "G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\sourceEvent\\critical.txt";
            ObtainPattern obtpg1 = new OBTP1();
            ObtainPattern obtpg2 = new OBTP2();
            ObtainPattern obtpg3 = new OBTP3();
            ObtainPattern obtpg4 = new OBTP4();
            ObtainPattern obtpg5 = new OBTP5();
            ObtainPattern obtpg6 = new OBTP6();
            List<String> groups = new ArrayList<>();

            String isMatchg1 = obtpg1.obtainPattern(obtpg1.getTrace(path));
            String isMatchg2 = obtpg2.obtainPattern(obtpg2.getTrace(path));
            String isMatchg3 = obtpg3.obtainPattern(obtpg3.getTrace(path));
            String isMatchg4 = obtpg4.obtainPattern(obtpg4.getTrace(path));
            String isMatchg5 = obtpg5.obtainPattern(obtpg5.getTrace(path));
            String isMatchg6 = obtpg6.obtainPattern(obtpg6.getTrace(path));

            groups.add(isMatchg1);
            groups.add(isMatchg2);
            groups.add(isMatchg3);
            groups.add(isMatchg4);
            groups.add(isMatchg5);
            groups.add(isMatchg6);

            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i) != null) {
                    if (groups.get(i).equals("g1")) {
                        System.out.println("MP1 MP4");
                    }
                    if (groups.get(i).equals("g2")) {
                        System.out.println("MP2");
                    }
                    if (groups.get(i).equals("g3")) {
                        System.out.println("MP3");
                    }
                    if (groups.get(i).equals("g4")) {
                        System.out.println("MP5");
                    }
                    if (groups.get(i).equals("g5")) {
                        System.out.println("MP6 mp7 mp8");
                    }
                    if (groups.get(i).equals("g6")) {
                        System.out.println("MP9 mp10 mp11 mp12 mp13 mp14");
                    }
                }
            }
            System.out.println(System.currentTimeMillis() - start);
    }
}
