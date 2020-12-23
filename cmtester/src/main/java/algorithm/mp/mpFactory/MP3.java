package algorithm.mp.mpFactory;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;
import algorithm.obtainpattern.OBTP1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//import static algorithm.mp.MetamorphicPattern.WRITE;

/**
 * @author GN
 * @description
 * @date 2020/9/15
 */
public class MP3 extends MP implements MetamorphicPattern {
    //获取原始序列
//    @Override
//    public List<String> getsourceSeq(String path) {
//        return new OBTP1().getTrace(path);
//    }

    /**
     * 获取衍生序列
     * @param sourceSeq
     * @return
     */
    @Override
    public List<String> followUpSeq(List<String> sourceSeq) {
        List<String> followSeq = new ArrayList<>();

        //线程1、2
        String T1;
        String T2;

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

            //判断如果当前时间时写，而且这个线程后边还有一个写操作就进入If,否则i++读取下一行
            if (myevent.equals(WRITE)&&source.lastIndexOf(T1+WRITE+myunite)!=i) {
                System.out.println("---------"+i);
                thSet.add(T1);
                //如果源代码中存在一行代码先读后写(x=x+1),那要保证一个线程执行这条语句的顺序不能变，
                // 线程2的操作可以插入到线程1的read和write之间，但是线程1的read一定要发生在线程2之前
                if (i>0 && source.subList(0,i).contains(T1+READ+myunite)){
                    System.out.println("----if-----");

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
                                        System.out.println("--------");

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
//                            System.out.println(tempList.get(k-1));
//                                if (k>1 && tempList.get(k - 1).split(BLANK)[1].equals(Tid)
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

                    if (tempList.size() != 0) {
                        System.out.println("----");
                        followSeq.addAll(tempList);
                    }
                    break;

                } else {
                    //else里的逻辑和上面if一样，这是没有happens-before 情况
                    System.out.println("---else----");

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
                                if (thSet.size() == 2) {
                                    if (yourevent.equals(READ) && !added.contains(T2 +yourunite+ READ)) {

                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 +yourunite+ READ);
                                    } else {
                                        tempList.add(sourceSeq.get(j));
                                        temp.add(sourceSeq.get(j).split(" ")[1]+BLANK
                                                +sourceSeq.get(j).split(BLANK)[5]+BLANK
                                                + sourceSeq.get(j).split(" ")[4]);
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                    if (temp.contains(T1 +" "+ myunite+" "+WRITE)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            if (Tid.equals(T1) && event.equals("WRITE")&& unite.equals(myunite)) {
//                            System.out.println(tempList.get(k-1));
                                if (k>1 && tempList.get(k - 1).split(BLANK)[1].equals(Tid)
                                        && tempList.get(k - 1).split(BLANK)[4].equals(READ)
                                        && tempList.get(k - 1).split(BLANK)[5].equals(myunite)) {
                                    followSeq.add(tempList.get(k - 1));
                                    followSeq.add(tempList.get(k));
                                    tempList.remove(k - 1);
                                    tempList.remove(k - 1);
                                    break;
                                } else {
                                    followSeq.add(tempList.get(k));
                                    tempList.remove(tempList.get(k));
                                    break;
                                }
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

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        return null;
    }

    public static void main(String[] args){
        MetamorphicPattern mp3 = new MP3();
        List<String> list = mp3.followUpSeq(MP.getsourceSeq("F:\\gengning\\workplace\\CMT\\trace\\test.txt"));
        System.out.println(list);
    }
}
