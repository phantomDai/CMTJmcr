package algorithm.obtainpattern;

import java.util.*;

/**
 * @author GN
 * @description
 * @date 2020/9/17
 */
public class OBTP6 extends OBTP implements ObtainPattern{

    @Override
    public String obtainPattern(List<String> sourceSeq) {

        //存储轨迹中的线程
        HashSet<String> threadSet = new HashSet<>();
        //存储共享变量
        HashSet<String> variableSet = new HashSet<>();
        //存储轨迹中的事件
        HashSet<String> unitVSet = new HashSet<>();

        //将线程和线程执行的事件映射到map中
        HashMap<String, ArrayList<String>> theveMap = new HashMap<>();

        //将涉及到的线程和共享变量存储起来
        for (String eventInfo : sourceSeq) {

            if (!eventInfo.split(" ")[1].equals("0")) {
                threadSet.add(eventInfo.split(" ")[1]);
                unitVSet.add(eventInfo.split(" ")[1] +" "+ eventInfo.split(" ")[5] + " " + eventInfo.split(" ")[3]);
                variableSet.add(eventInfo.split(" ")[3]);
            }
        }

        /*
         * 初始化Map,key值为轨迹中除了主线程之外的线程ID
         */
        for (String th : threadSet) {
            if (Integer.valueOf(th) != 0) {
                theveMap.put(th, new ArrayList<>());
            }
        }
        /*
         * 将对应线程的事件存到Map的value中
         */
        for (Iterator iterator = sourceSeq.iterator(); iterator.hasNext(); ) {
            String eventInfo = iterator.next().toString();

            if (theveMap.containsKey(eventInfo.split(" ")[1])) {
                ArrayList<String> values = theveMap.get(eventInfo.split(" ")[1]);
                values.add(eventInfo.split(" ")[4] + " " + eventInfo.split(" ")[5] + " " + eventInfo.split(" ")[3]);
                theveMap.replace(eventInfo.split(" ")[1], values);
            }

        }

        /*
        特征6：涉及两个共享变量两个线程，包含一个线程对每个变量的一次写操作，另一个线程对每个变量的一次读操作
         */
        boolean isMatchg6 = false;
        if (threadSet.size() > 1 && variableSet.size() == 2 && unitVSet.size()>3) {
//            System.out.println("------");
            firstoutter:
            for (String variable1 : unitVSet) {
                for (String variable2 : unitVSet) {
                    if ((!variable2.equals(variable1)) && variable1.split(" ")[0].equals(variable2.split(" ")[0])) {
                        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>(theveMap.values());
                        for (int i = 0; i < arrayLists.size(); i++) {
                            if (arrayLists.get(i).contains("WRITE " + variable1.split(" ")[1] + " " + variable1.split(" ")[2])
                                    && arrayLists.get(i).contains("WRITE " + variable2.split(" ")[1] + " "+variable2.split(" ")[2])) {
                                for (int j = 0; j < arrayLists.size(); j++) {
                                    if (j != i) {
                                        for (String variable3 : unitVSet) {
                                            for (String variable4 : unitVSet) {
//                                                System.out.println(variable1);
//                                                System.out.println(variable2);
//                                                System.out.println(variable3);
//                                                System.out.println(variable4);
                                                if ((!variable3.equals(variable1))
                                                        && (!variable3.equals(variable2))
                                                        && (!variable3.equals(variable4))
                                                        && (!variable4.equals(variable1))
                                                        && (!variable4.equals(variable2))
                                                        && (!variable4.equals(variable3))
                                                        && (variable3.split(" ")[0].equals(variable4.split(" ")[0]))
                                                        && (!variable3.split(" ")[0].equals(variable1.split(" ")[0]))
                                                        && arrayLists.get(j).contains("READ " + variable3.split(" ")[1]+" "+variable3.split(" ")[2])
                                                        && arrayLists.get(j).contains("READ " + variable4.split(" ")[1]+" "+variable4.split(" ")[2])) {
                                                    isMatchg6 = true;
                                                    break firstoutter;
                                                }
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }

                }

            }
        }
        if (isMatchg6) {
            return group6;
        } else {
            return null;
        }
    }

    public static void main(String[] args){

        ObtainPattern obtp = new OBTP6();
        List<String> sourceSeq = obtp.getTrace("F:\\gengning\\workplace\\CMT\\SE\\AirlineTickets.txt");
        String info = obtp.obtainPattern(sourceSeq);
        System.out.println(info);
    }
}
