package algorithm.obtainpattern;



import java.util.*;

/**
 * @author GN
 * @description
 * @date 2020/9/10
 */
public class OBTP1 extends OBTP implements ObtainPattern{

    final String group1 = "g1";
    final String group2 = "g2";
    final String group3 = "g3";
    final String group4 = "g4";
    final String group5 = "g5";
    final String group6 = "g6";

    @Override
    public String obtainPattern(List<String> sourceSeq){

        List<String> matchedGroups = new ArrayList<>();

        //存储轨迹中的线程
        HashSet<String> threadSet = new HashSet<>();
        //存储共享变量
        HashSet<String> variableSet = new HashSet<>();
        //存储工作单元和共享变量
        HashSet<String> unitVSet = new HashSet<>();

        //存储轨迹中的事件
        HashSet<String> eventSet = new HashSet<>();

        //将线程和线程执行的事件映射到map中
        HashMap<String,ArrayList<String>> theveMap = new HashMap<>();

        //获取trace
//        List<String> list = getTrace(path);

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
//        System.out.println(theveMap);
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

//        System.out.println(theveMap);

        /*
        特征1：涉及1个共享变量两个线程，一个线程对共享变量的一次读和一次写操作，另一个线程对变量的一次写操作
         */
        boolean isMatchg1 = false;

        if (threadSet.size() > 1 && variableSet.size() >= 1 ) {
            firstoutter: for (String sharedVariable : variableSet) {
                System.out.println(sharedVariable);

                ArrayList<ArrayList<String>> arrayLists = new ArrayList<>(theveMap.values());
                for (int i = 0; i < arrayLists.size(); i++) {
                    List<String> sublist = arrayLists.get(i);
//
                    if (sublist.containsAll(new ArrayList<>(Arrays.asList("READ "+sharedVariable,"WRITE "+sharedVariable)))){

//                        System.out.println(sublist);
                            outter: for (int j = 0; j < arrayLists.size(); j++) {
                                if (j != i) {
                                    for (String SV2: variableSet) {
//                                        if (!SV2.equals(sharedVariable)) {
                                            if (arrayLists.get(j).contains("WRITE " + SV2)) {
//                                                System.out.println(arrayLists.get(j));
                                                isMatchg1 = true;
                                                break outter;
                                            }
//                                        }
                                    }
                                }
                            }

                    }

                    if (isMatchg1){
                        break firstoutter;
                    }
                }
            }

        }
//        System.out.println(theveMap);
        if (isMatchg1){
//            matchedGroups.add(group1);
            return group1;
        }


        else {

            return null;
        }
    }

//    @Override
//    public List<String> getTrace(String path){
//        //获取上一步得到的Trace信息
//        File file = null;
//        List<String> list = new ArrayList<String>();
//        try { //以缓冲区方式读取文件内容
//
//            file = new File(path);
//            FileReader filereader = new FileReader(file);
//            BufferedReader bufferreader = new BufferedReader(filereader);
//
//            String aline;
//            while ((aline = bufferreader.readLine()) != null) {
//
//                if (aline.length()>1) {
//                    list.add(aline);
//                }
//            }
//            filereader.close();
//            bufferreader.close();
//
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        return list;
//    }

//    @Override
//    public String obtainPattern(List<String> sourceSeq) {
//        return null;
//    }

    public static void main(String[] args){
        OBTP1 obtp = new OBTP1();
        String info= obtp.obtainPattern(obtp.getTrace("F:\\gengning\\workplace\\CMT\\SE\\AirlineTickets.txt"));
//        if (list.size()==0){
//            System.out.println("null");
//        }
        System.out.println(info);
    }
}
