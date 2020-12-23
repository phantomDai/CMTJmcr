package algorithm.mp.mp4Acc;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;
import algorithm.mp.mpFactory.MP4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author GN
 * @description
 * @date 2020/10/5
 */
public class MP44Acc extends MP implements MetamorphicPattern {

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        List<String> followSeq = new ArrayList<>();
        String T1;
        String T2;

        List<String> tempList = new ArrayList<>();
        List<String> added = new ArrayList<>();
        //记录临时序列里的T和event
        List<String> temp = new ArrayList<>();
        //记录原始序列里的T和event
        List<String> source = new ArrayList<>();

        HashSet<String> thSet = new HashSet<>();

        //获取原始序列对应的记录
        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]);
        }

        //遍历原始序列中的事件
        for (int i=0;i<sourceSeq.size();i++){

            T1=sourceSeq.get(i).split(" ")[1];

            String myevent = sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];
            String myvar = sourceSeq.get(i).split(BLANK)[3];

            //如果读到的事件为WRITE，而且整个序列种有和这个事件匹配的事件
            if (myevent.equals("WRITE") && source.contains(T1+READ+myunite)){
                thSet.add(T1);
                //如果当前write事件不会说第一个事件，而且同一个工作单元中在这个事件之前有不应该被放到后面的read
                if (i>0 && source.subList(0,i).contains(T1+READ+myunite)){

                    followSeq.add(sourceSeq.get(source.indexOf(T1+READ+myunite)));
                    added.add(T1+ myunite+READ);
                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myunite+myevent);
//                    if (source.lastIndexOf(T1+READ+myvar)!=i-1) {
                    for (int j = 0; j < sourceSeq.size(); j++) {

                        //保证两次比较的不是同一行数据
                        if (j != i) {
                            T2 = sourceSeq.get(j).split(" ")[1];
                            String yourevent = sourceSeq.get(j).split(" ")[4];
                            String yourunite = sourceSeq.get(j).split(" ")[5];

                            if (T1.equals(T2)) {
                                if (!followSeq.contains(sourceSeq.get(j))) {
                                    tempList.add(sourceSeq.get(j));
                                    temp.add(sourceSeq.get(j).split(" ")[1]
                                            + sourceSeq.get(j).split(BLANK)[5]
                                            + sourceSeq.get(j).split(" ")[4]);
                                }
                            } else {

                                thSet.add(T2);
                                if (thSet.size() == 2) {

                                    if (yourevent.equals("WRITE")
                                            && !added.contains(T2 + yourunite+"WRITE")) {
                                        if (j>0 && source.subList(0,j).contains(T2 + READ + yourunite)) {

                                            followSeq.add(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));
                                            added.add(T2 + yourunite+"READ");
                                            temp.remove(tempList.indexOf(sourceSeq.get(source.indexOf(T2+ READ+yourunite))));
                                            tempList.remove(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));

                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 +yourunite+ "WRITE");
                                        } else {
                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 +yourunite+ "WRITE");
                                        }
                                    } else {
//
                                        tempList.add(sourceSeq.get(j));
                                        temp.add(sourceSeq.get(j).split(" ")[1]
                                                + sourceSeq.get(j).split(BLANK)[5]
                                                + sourceSeq.get(j).split(" ")[4]);
//                                    }
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    System.out.println(temp);

                    //如果临时序列种有我需要的read事件
                    if (temp.contains(T1 +myunite+ READ)) {

                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(BLANK)[1];
                            String event = tempList.get(k).split(BLANK)[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T1) && event.equals(READ) && unite.equals(myunite)) {
                                followSeq.add(tempList.get(k));
                                tempList.remove(tempList.get(k));
                                break;
                            }
                        }
                        if (tempList.size() != 0) {
                            followSeq.addAll(tempList);
                        }
                    }//如果临时序列中没有我要的序列返回空
                    else {
                        return null;
                    }

                    break;
//                    }else {
//                        followSeq.clear();
//                        added.clear();
//                    }

                }//不包含在write之前必须发生的read
                else {

                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 +myunite+ myevent);
//                    thSet.add(T1);

                    for (int j = 0; j < sourceSeq.size(); j++) {

                        //保证两次比较的不是同一行数据
                        if (j != i) {
                            T2 = sourceSeq.get(j).split(" ")[1];
                            String yourevent = sourceSeq.get(j).split(" ")[4];
                            String yourunite = sourceSeq.get(j).split(" ")[5];

                            if (T1.equals(T2)) {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1]
                                        +sourceSeq.get(j).split(BLANK)[5]
                                        + sourceSeq.get(j).split(" ")[4]);
                            } else {

                                thSet.add(T2);
                                if (thSet.size() == 2) {

                                    if (yourevent.equals("WRITE")
                                            && !added.contains(T2 + "WRITE")) {
                                        if (j>0 && source.subList(0,j).contains(T2 + READ + yourunite)) {

                                            followSeq.add(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));
                                            added.add(T2 +yourunite+ "READ");
                                            temp.remove(tempList.indexOf(sourceSeq.get(source.indexOf(T2+ READ+yourunite))));
                                            tempList.remove(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));


                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 + yourunite+"WRITE");
                                        } else {
                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 + yourunite+"WRITE");
                                        }
                                    } else {
//                                    if (followSeq.size()==2){
//                                        followSeq.add(sourceSeq.get(j));
//                                        added.add(T2 + yourevent);
//                                    }
//                                    }else {
                                        tempList.add(sourceSeq.get(j));
                                        temp.add(sourceSeq.get(j).split(" ")[1]
                                                +sourceSeq.get(j).split(BLANK)[5]
                                                + sourceSeq.get(j).split(" ")[4]);
//                                    }
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }//跳出了第二层循环
                    if (temp.contains(T1 +myunite+ READ)) {

                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(BLANK)[1];
                            String event = tempList.get(k).split(BLANK)[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T1) && event.equals(READ) && unite.equals(myunite)) {
                                followSeq.add(tempList.get(k));
                                tempList.remove(tempList.get(k));
                                break;
                            }
                        }
                        if (tempList.size() != 0) {
                            followSeq.addAll(tempList);
                        }
                    }else {
                        return null;
                    }


                    break;
                }
            }

        }
        for (int i=followSeq.size();i<sourceSeq.size();i++){
            followSeq.add(sourceSeq.get(i));
        }

        return followSeq;
    }



    public static void main(String[] args){

        MetamorphicPattern mp = new MP44Acc();
        List<String> info = mp.followUpSeq(mp.followUpSeqWithoutSort(getsourceSeq("F:\\gengning\\workplace\\CMT\\SE\\Account.txt")));
//        for (String list:info){
//            System.out.println(list);
//        }

        System.out.println(info);
    }
}
