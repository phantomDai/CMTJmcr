package algorithm.obtainpattern;

import java.util.*;

/**
 * @author GN
 * @description
 * @date 2020/9/17
 */
public class OBTP3 extends OBTP implements ObtainPattern{


    @Override
    public String obtainPattern(List<String> sourceSeq) {

        //存储轨迹中的线程
        HashSet<String> threadSet = new HashSet<>();
        //存储共享变量
        HashSet<String> variableSet = new HashSet<>();
        //存储轨迹中的工作单元
        HashSet<String> unitVSet = new HashSet<>();

        //将线程和线程执行的事件映射到map中
        HashMap<String,ArrayList<String>> theveMap = new HashMap<>();

        //将涉及到的线程和共享变量存储起来
        for (String eventInfo:sourceSeq){

            if (!eventInfo.split(" ")[1].equals("0")) {
                threadSet.add(eventInfo.split(" ")[1]);
                unitVSet.add(eventInfo.split(" ")[5] + " " + eventInfo.split(" ")[3]);
                variableSet.add(eventInfo.split(" ")[3]);
            }
        }

        /*
         * 初始化Map,key值为轨迹中除了主线程之外的线程ID
         */
        for (String th:threadSet){
            if (Integer.valueOf(th)!=0){
                theveMap.put(th,new ArrayList<>());
            }
        }
        /*
         * 将对应线程的事件存到Map的value中
         */
        for (Iterator iterator = sourceSeq.iterator();iterator.hasNext();){
            String eventInfo = iterator.next().toString();

            if (theveMap.containsKey(eventInfo.split(" ")[1])){
                ArrayList<String> values = theveMap.get(eventInfo.split(" ")[1]);
                values.add(eventInfo.split(" ")[4]+ " " + eventInfo.split(" ")[3]);
                theveMap.replace(eventInfo.split(" ")[1],values);
            }

        }
        /*
        特征3：涉及一个共享变量两个线程，包含一个线程对变量的两次写操作，另一个线程对变量的一次读操作
         */
        boolean isMatchg3 = false;
        if (threadSet.size() > 1 && variableSet.size() == 1) {
            firstoutter: for (String sharedVariable : variableSet) {
//                System.out.println(sharedVariable);
                ArrayList<ArrayList<String>> arrayLists = new ArrayList<>(theveMap.values());
                for (int i = 0; i < arrayLists.size(); i++) {
                    List<String> sublist = arrayLists.get(i);
//                    System.out.println(sublist);
                    if (sublist.indexOf("WRITE " + sharedVariable)!=sublist.lastIndexOf("WRITE " + sharedVariable)) {
//                        System.out.println("........");
//                        System.out.println(sublist);
//                        System.out.println(arrayLists.get(i));
//                        System.out.println(arrayLists.get(i));
//                            System.out.println("//////////");
                        for (int j = 0; j < arrayLists.size(); j++) {
                            if (j != i) {
                                for (String sv2:variableSet) {
//                                    if (!sv2.equals(sharedVariable)) {
//                                        System.out.println(";;;;;");
//                                        System.out.println(arrayLists.get(j));
//                                        System.out.println(sv2);
                                        if (arrayLists.get(j).contains("READ " + sv2)) {
//                                        System.out.println(";;;;;;;;;");
                                            isMatchg3 = true;
                                            break firstoutter;
//                                        System.out.println(isMatchg2);
                                        }
//                                    }
                                }
                            }
                        }

                    }
//                    else if (sublist.contains("READ "+sharedVariable)){
//                        outter: for (int j=0;j<arrayLists.size();j++){
//                            if (j!=i){
//                                for (String sv2:unitVSet) {
//                                    if (!sv2.equals(sharedVariable)) {
//                                        if (arrayLists.get(j).indexOf("WRITE " + sv2)
//                                                != arrayLists.get(j).lastIndexOf("WRITE" + sv2)) {
//                                            isMatchg3 = true;
//                                            break outter;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (isMatchg3){
//                        break firstoutter;
//                    }
                }
            }
        }
        if (isMatchg3){
            return group3;
        }else {
            return null;
        }
    }

    public static void main(String[] args){

        ObtainPattern obtp = new OBTP3();
        List<String> sourceSeq = obtp.getTrace("F:\\gengning\\workplace\\CMT\\SE\\Critical.txt");
        String info = obtp.obtainPattern(sourceSeq);
        System.out.println(info);
    }
}
