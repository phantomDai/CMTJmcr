package algorithm.mp.mp4Air;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author GN
 * @description
 * @date 2020/10/5
 */
public class MP14Air extends MP implements MetamorphicPattern {

//    @Override
//    public List<String> getsourceSeq(String path) {
//        OBTP1 obtp = new OBTP1();
//        return obtp.getTrace(path);
//    }

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    /**
     * 得到FS
     */
    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){
        //follow-up序列
        List<String> followSeq = new ArrayList<>();
        String T1;
        String T2;
        //临时序列
        List<String> tempList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        //存储已经添加到followSeq中的事件
        List<String> added = new ArrayList<>();

        List<String> source = new ArrayList<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        //存储轨迹中的线程
        HashSet<String> threadSet = new HashSet<>();

        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]+infote.split(BLANK)[3]);
            threadSet.add(infote.split(" ")[1]);
        }

        //遍历原始序列中的每个事件
        for (int i=0;i<sourceSeq.size();i++) {
            //当前事件的线程、变量、事件类型
            T1 = sourceSeq.get(i).split(" ")[1];
            String myvar = sourceSeq.get(i).split(" ")[3];
            String myevent = sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];

            //只需要最后两个线程的事件
            if (Integer.valueOf(T1)<threadSet.size()-2 && !T1.equals("0")){
                followSeq.add(sourceSeq.get(i));
            } else {

                followSeq.add(sourceSeq.get(i));
                added.add(T1 + myunite + myevent + myvar);
                thSet.add(T1);
                //如果当前事件是一个读事件，就把它加到衍生序列中
                if (myevent.equals("READ") && sourceSeq.get(i+1).split(BLANK)[4].equals(WRITE)
                        && sourceSeq.get(i+1).split(BLANK)[3].equals(myvar)) {
                    //为了保证happens-before
//                    followSeq.add(sourceSeq.get(i-1));
//                    added.add(T1 + myunite + myevent + myvar);
//                    followSeq.add(sourceSeq.get(i));
//                    added.add(T1 + myunite + myevent+myvar);
//                    thSet.add(T1);
                    //第二次遍历原始序列中的每个事件
                    for (int j = followSeq.size(); j < sourceSeq.size(); j++) {
                        //第二次遍历到的事件的线程、事件类型
                        T2 = sourceSeq.get(j).split(" ")[1];
                        String yourevent = sourceSeq.get(j).split(" ")[4];
                        String yourunit = sourceSeq.get(j).split(" ")[5];
                        String yourvar = sourceSeq.get(j).split(BLANK)[3];
                        String yourLine = sourceSeq.get(j).split(BLANK)[2];
//                    String yourID = sourceSeq.get(j).split(" ")[0];
                        //如果两次读到的不是同一行
                        if (i != j) {
                            //如果第二次读到的线程和第一次读到的线程相同，就把当前事件存到临时列表中，否则继续分析
                            if (T1.equals(T2)) {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                        + sourceSeq.get(j).split(" ")[5] + " "
                                        + sourceSeq.get(j).split(" ")[4] + BLANK
                                        + sourceSeq.get(j).split(BLANK)[3]);
                            } else {
//                            if (followSeq.size()<3) {
                                thSet.add(T2);
                                if (thSet.size() == 2) {

                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2 + yourunit + yourevent+yourvar);

                                    if (yourevent.equals("WRITE")
                                            && yourvar.equals(myvar)) {

                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }

                    }


                    //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                    //如果第三个事件是write那还要保证happens-before原则
                    if (temp.contains(T1 + " " + myunite + " " + WRITE+" "+myvar)) {
//                        System.out.println("youxiema");
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            String var = tempList.get(k).split(BLANK)[3];
                            if (Tid.equals(T1) && event.equals("WRITE") && unite.equals(myunite) && var.equals(myvar)) {

                                    followSeq.add(tempList.get(k));
                                    tempList.remove(tempList.get(k));
                                    break;

                            }
                        }
                    } else {
                        return null;
                    }

                    if (tempList.size() != 0) {

                        followSeq.addAll(tempList);
                    }
                    break;
                }
            }


        }
        for (int i=followSeq.size();i<sourceSeq.size();i++){
            if (!followSeq.contains(sourceSeq.get(i))) {
                followSeq.add(sourceSeq.get(i));
            }
        }

        return followSeq;
    }

    public static void main(String[] args){

        MetamorphicPattern mp = new MP14Air();
        List<String> infofs = mp.followUpSeqWithoutSort(MP.getsourceSeq("F:\\CMT\\SE\\AirlineTickets.txt"));
        List<String> info = mp.followUpSeq(infofs);
        System.out.println(info.size());
        for (String list:info){
            System.out.println(list);
        }

    }
}
