package algorithm.obtainpattern;

import java.util.*;

/**
 * @author GN
 * @description
 * @date 2020/9/17
 */
public class OBTP4 extends OBTP implements ObtainPattern{

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
                values.add(eventInfo.split(" ")[4] + " " + eventInfo.split(" ")[3]);
                theveMap.replace(eventInfo.split(" ")[1],values);
            }

        }

        /*
        特征4：涉及一个共享变量两个线程，包含一个线程对变量的两次写操作和另一个线程对变量的一次写操作
        另外需要写操作后面需要有读操作
         */
        boolean isMatchg4 = false;
        if (threadSet.size() > 1 && variableSet.size() == 1 ) {
            firstoutter: for (String sharedVariable :variableSet) {
                ArrayList<ArrayList<String>> arrayLists = new ArrayList<>(theveMap.values());
                for (int i = 0; i < arrayLists.size(); i++) {
                    List<String> sublist = arrayLists.get(i);

                    if (sublist.indexOf("WRITE "+sharedVariable)!=sublist.lastIndexOf("WRITE "+sharedVariable)){
                            for (int j = 0; j < arrayLists.size(); j++) {
                                if (j != i) {
                                    for (String sv2:variableSet) {
                                            if (arrayLists.get(j).contains("WRITE " + sv2)) {
                                                isMatchg4 = true;
                                                break firstoutter;
                                            }
                                    }
                                }
                            }
//                        }
                    }
                }
            }

        }
        if (isMatchg4){
            return group4;
        }else {
            return null;
        }
    }

    public static void main(String[] args){

        ObtainPattern obtp = new OBTP4();
        List<String> sourceSeq = obtp.getTrace("F:\\gengning\\workplace\\CMT\\SE\\Critical.txt");
        String info = obtp.obtainPattern(sourceSeq);
        System.out.println(info);
    }
}
