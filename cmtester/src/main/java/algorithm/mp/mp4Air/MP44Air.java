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
public class MP44Air extends MP implements MetamorphicPattern {

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){
        List<String> followSeq = new ArrayList<>();
        String T1=null;
        String T2;
        String sharedvar = null;

        List<String> tempList = new ArrayList<>();
        List<String> added = new ArrayList<>();
        //记录临时序列里的T和event
        List<String> temp = new ArrayList<>();
        //记录原始序列里的T和event
        List<String> source = new ArrayList<>();
        HashSet<String> threadSet = new HashSet<>();

        HashSet<String> thSet = new HashSet<>();

        //获取原始序列对应的记录
        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]+infote.split(BLANK)[3] );
            threadSet.add(infote.split(BLANK)[1]);
        }

        //遍历原始序列中的事件
        for (int i=0;i<sourceSeq.size();i++) {

            String T = sourceSeq.get(i).split(" ")[1];

            String myevent = sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];
            String myvar = sourceSeq.get(i).split(BLANK)[3];
            String myLine = sourceSeq.get(i).split(BLANK)[2];

            if (Integer.valueOf(T) < threadSet.size() - 2 && !T.equals("0")) {

                followSeq.add(sourceSeq.get(i));
            } else {

                //把事件按顺序添加进来，遇到第一个写事件，添加后开始第二次循环
                if (added.size()<2) {
                    followSeq.add(sourceSeq.get(i));
                    added.add(T + myevent + myvar);
                    T1 = T;
                }
                else {
                    tempList.add(sourceSeq.get(i));
                    temp.add(T + myevent + myvar);

                    sharedvar = myvar;
                }
            }
        }


        //第二次
        for (int i=followSeq.size();i<sourceSeq.size();i++){
            String T = sourceSeq.get(i).split(BLANK)[1];
            String yourevent = sourceSeq.get(i).split(BLANK)[4];
            String yourvar = sourceSeq.get(i).split(BLANK)[3];
            if (!T.equals(T1)){
                T2=T;
                followSeq.add(sourceSeq.get(i));
                added.add(T + yourevent + yourvar);
                if (yourevent.equals(WRITE) && yourvar.equals(sharedvar)){
                    break;
                }
            }
        }



        //将tempList加进来
        for (int i=0;i<tempList.size();i++){
            if (!followSeq.contains(tempList.get(i))){
                followSeq.add(tempList.get(i));
            }
        }
//
//        for (String info:followSeq) {
//            System.out.println(info);
//        }

        return followSeq;
    }


    public static void main(String[] args){
        MetamorphicPattern mp = new MP44Air();
        List<String> sourceSeq = getsourceSeq("F:\\CMT\\SE\\AirlineTickets.txt");
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }
}
