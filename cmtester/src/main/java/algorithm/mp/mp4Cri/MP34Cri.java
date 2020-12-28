package algorithm.mp.mp4Cri;

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
public class MP34Cri extends MP implements MetamorphicPattern {
    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    /**
     * 生成衍生测试用例要执行的序列
     * @param sourceSeq：原始测试用例的读写事件
     * @return
     */
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){

        List<String> followSeq = new ArrayList<>();

        //线程1、2
        String T1;
        String T2=null;

        //临时存放没有加到衍生序列中的事件
        List<String> tempList = new ArrayList<>();
        //记录已经加到衍生序列的T和EVENT
        List<String> added = new ArrayList<>();
        //记录临时序列里的T和event
        List<String> temp = new ArrayList<>();
        //记录原始序列里的T和event
        List<String> source = new ArrayList<>();

        //记录已经分析过的线程
        HashSet<String> thSet = new HashSet<>();

        //获取原始序列对应的记录
        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]);
        }

        //第一次遍历
        for (int i=0;i<sourceSeq.size();i++){
            T1 = sourceSeq.get(i).split(" ")[1];
            String myevent = sourceSeq.get(i).split(" ")[4];
            String myadd = sourceSeq.get(i).split(BLANK)[2];
            String myvar = sourceSeq.get(i).split(BLANK)[3];
            String myunite = sourceSeq.get(i).split(BLANK)[5];

            //判断如果当前事件是写，而且这个线程后边还有一个写操作就进入If,否则i++读取下一行
            if (myevent.equals(WRITE)&&source.lastIndexOf(T1+WRITE+myunite)!=i) {
//                System.out.println("---------"+i);
                thSet.add(T1);
                //happends-before,但是这个程序涉及不到
                if (i>0 && source.subList(0,i).contains(T1+READ+myunite)){
//                    System.out.println("----if-----");

                    followSeq.add(sourceSeq.get(source.indexOf(T1+READ+myunite)));
                    added.add(T1+ myunite+READ);
                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myunite+myevent);
                    //后边步骤一样，但是两个分支都要加一个else，如果templist里面没有我要的那个事件
                    //就直接返回null
                    for (int j = 0; j < sourceSeq.size(); j++) {

                        if (j != i) {
                            T2 = sourceSeq.get(j).split(BLANK)[1];
                            String yourevent = sourceSeq.get(j).split(BLANK)[4];
                            String youradd = sourceSeq.get(j).split(BLANK)[2];
                            String yourunite=sourceSeq.get(j).split(BLANK)[5];

                            //如果这个线程和加入到衍生序列的线程一样就放到临时序列种
                            if (T1.equals(T2) ) {
                                if (added.contains(T2+yourunite+READ)&&yourevent.equals(READ)) {
                                    continue;
                                }else {
                                    tempList.add(sourceSeq.get(j));
                                    temp.add(sourceSeq.get(j).split(" ")[1] + BLANK
                                            + sourceSeq.get(j).split(BLANK)[5] + BLANK
                                            + sourceSeq.get(j).split(" ")[4]);
                                }
                            } else {

                                thSet.add(T2);
                                if (thSet.size() == 2) {
                                    //如果是read而且还没有添加read事件就继续
                                    if (yourevent.equals(READ) && !added.contains(T2 +yourunite+ READ)) {
//                                        System.out.println("--------");

                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + yourunite+READ);
                                    } else { //如果不是read或者已经添加过read，就加入临时序列
                                        if (!followSeq.contains(sourceSeq.get(j))) {
                                            tempList.add(sourceSeq.get(j));
                                            temp.add(sourceSeq.get(j).split(" ")[1] + BLANK
                                                    + sourceSeq.get(j).split(BLANK)[5] + BLANK
                                                    + sourceSeq.get(j).split(" ")[4]);

                                        }
                                    }
                                } else {  //如果分析到第三个线程了就直接跳出第二次循环
                                    break;
                                }
                            }  //j和i 的线程不一样的分支
                        } //j=i就读下一行
                    } //第二次循环

                    //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                    //如果第三个事件是write那还要保证happens-before原则
                    System.out.println(temp);
                    if (temp.contains(T1 +" "+ myunite+" "+WRITE)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T1) && event.equals("WRITE")&& unite.equals(myunite)) {
//
                                followSeq.add(tempList.get(k));
                                tempList.remove(tempList.get(k));
                                break;
//                                }
                            }
                        }
                    }else {
                        return null;
                    }

                    if (tempList.size() != 0) {
//                        System.out.println("----");
                        followSeq.addAll(tempList);
                    }
                    break;

                } else {
                    //else里的逻辑和上面if一样，这是没有happens-before 情况
//                    System.out.println("---else----");

                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myunite+myevent);

                    for (int j = 0; j < sourceSeq.size(); j++) {

                        if (j != i) {
                            T2 = sourceSeq.get(j).split(BLANK)[1];
                            String yourevent = sourceSeq.get(j).split(BLANK)[4];
                            String youradd = sourceSeq.get(j).split(BLANK)[2];
                            String yourunite = sourceSeq.get(j).split(BLANK)[5];

                            if (T1.equals(T2)) {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1]+BLANK
                                        + sourceSeq.get(j).split(BLANK)[5]+BLANK
                                        + sourceSeq.get(j).split(" ")[4]);
                            } else {

                                thSet.add(T2);
                                if (!added.contains(T2+yourunite+READ)) {
                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2+yourunite+yourevent);
                                } else {
                                    tempList.add(sourceSeq.get(j));
                                    temp.add(sourceSeq.get(j).split(" ")[1]+BLANK
                                            + sourceSeq.get(j).split(BLANK)[5]+BLANK
                                            + sourceSeq.get(j).split(" ")[4]);
                                }
                            }
                        }
                    }

                    if (temp.contains(T1 +" "+ myunite+" "+READ)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T1) && event.equals(READ)&& unite.equals(myunite)) {
//                            System.out.println(tempList.get(k-1));
//                                if (tempList.get(k - 1).split(BLANK)[1].equals(Tid)
//                                        && tempList.get(k - 1).split(BLANK)[4].equals(READ)
//                                        && tempList.get(k - 1).split(BLANK)[5].equals(myunite)) {
//                                    followSeq.add(tempList.get(k - 1));
//                                    followSeq.add(tempList.get(k));
//                                    tempList.remove(k - 1);
//                                    tempList.remove(k - 1);
//                                    break;
//                                } else {
                                    followSeq.add(tempList.get(k));
                                    tempList.remove(tempList.get(k));
                                    break;
//                                }
                            }
                        }
                    }else {
                        return null;
                    }

                    if (temp.contains(T2 +" "+ myunite+" "+WRITE)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T2) && event.equals("WRITE")&& unite.equals(myunite)) {
                                followSeq.add(tempList.get(k));
                                tempList.remove(tempList.get(k));
                                break;
//                                }
                            }
                        }
                    }else {
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
        MetamorphicPattern mp = new MP34Cri();
        List<String> sourceSeq = getsourceSeq("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\sourceEvent\\Critical.txt");
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
//        System.out.println(followUpSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }
}
