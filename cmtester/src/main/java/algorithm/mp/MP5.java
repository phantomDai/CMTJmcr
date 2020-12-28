package algorithm.mp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2020/12/28
 */
public class MP5 extends MP implements MetamorphicPattern{
    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){

        //follow-up序列
        List<String> followSeq = new ArrayList<>();
        String T1;
        String T2;
        //临时序列
        List<String> tempList = new ArrayList<>();
        //记录临时序列里的T和event
        List<String> temp = new ArrayList<>();
        //记录原始序列里的T和event
        List<String> source = new ArrayList<>();

        //存储已经添加到followSeq中的事件
        List<String> added = new ArrayList<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        //获取原始序列对应的记录
        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]);
        }

        //遍历原始序列中的每个事件
        for (int i=0;i<sourceSeq.size();i++) {
            //当前事件的线程、变量、事件类型
            T1 = sourceSeq.get(i).split(" ")[1];
            String myadd = sourceSeq.get(i).split(" ")[2];
            String myevent = sourceSeq.get(i).split(" ")[4];
            String myvar = sourceSeq.get(i).split(BLANK)[3];
            String myunite = sourceSeq.get(i).split(BLANK)[5];
            //如果当前事件是一个写事件，就把它加到衍生序列中，否则度下一行
//            System.out.println(i+"--------");
            if (myevent.equals("WRITE") && source.lastIndexOf(T1+WRITE+myunite)!=i){
                thSet.add(T1);
                //保证happends-before,该程序在第一个事件处不涉及
                if (i>0 && sourceSeq.get(i-1).split(BLANK)[1].equals(T1)
                        && sourceSeq.get(i-1).split(BLANK)[2].equals(myadd)
                        && sourceSeq.get(i-1).split(BLANK)[4].equals(READ)){
//                    System.out.println(".....if.....");

                    followSeq.add(sourceSeq.get(i-1));
                    added.add(T1+READ);
                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myevent);

                    //第二次遍历原始序列中的每个事件
                    for (int j = 0; j < sourceSeq.size(); j++) {
                        //第二次遍历到的事件的线程、事件类型
                        T2 = sourceSeq.get(j).split(" ")[1];
                        String yourevent = sourceSeq.get(j).split(" ")[4];
                        String youradd = sourceSeq.get(j).split(" ")[2];
//                    String yourID = sourceSeq.get(j).split(" ")[0];
                        //如果两次读到的不是同一行
                        if (j != i) {
                            //如果第二次读到的线程和第一次读到的线程相同，就把当前事件存到临时列表中，否则继续分析
                            if (T1.equals(T2)) {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1]
                                        + sourceSeq.get(j).split(" ")[4]);
                            } else {
                                thSet.add(T2);
                                if (thSet.size() == 2) {

                                    if (yourevent.equals("WRITE")
                                            && !added.contains(T2 + "WRITE")) {
//
                                        if (sourceSeq.get(j - 1).split(" ")[1].equals(T2)
                                                && sourceSeq.get(j - 1).split(" ")[2].equals(youradd)
                                                && sourceSeq.get(j - 1).split(" ")[4].equals("READ")) {

                                            //改到这了，如果包含在同一行代码处的读操作，先把读操作加进来再把写操作加进来
                                            followSeq.add(sourceSeq.get(j - 1));
                                            added.add(T2 + "READ");
                                            temp.remove(tempList.indexOf(sourceSeq.get(j - 1)));
                                            tempList.remove(sourceSeq.get(j - 1));

                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 + "WRITE");
                                        } else {
                                            followSeq.add(sourceSeq.get(j));
                                            added.add(T2 + "WRITE");
                                        }
                                    } else {
//                                    if (followSeq.size() == 2) {
//                                        followSeq.add(sourceSeq.get(j));
//                                    } else {
                                        tempList.add(sourceSeq.get(j));
                                        temp.add(sourceSeq.get(j).split(" ")[1]
                                                + sourceSeq.get(j).split(" ")[4]);
//                                    }
                                    }
//                            }else {
//                                break;
//                            }
                                } else {
                                    break;
                                }
                            }

                        }
                    }
                    if (temp.contains(T1+WRITE)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(BLANK)[1];
                            String event = tempList.get(k).split(BLANK)[4];
                            String add = tempList.get(k).split(BLANK)[2];
                            if (Tid.equals(T1) && event.equals(WRITE)) {
                                if (tempList.get(k - 1).split(BLANK)[1].equals(Tid)
                                        && tempList.get(k - 1).split(BLANK)[4].equals(READ)
                                        && tempList.get(k - 1).split(BLANK)[2].equals(add)) {
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

                } else {  //不涉及happends-before
//                    System.out.println("----else----");
                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myevent);
                    //第二次遍历原始序列中的每个事件
                    for (int j = 0; j < sourceSeq.size(); j++) {
                        //第二次遍历到的事件的线程、事件类型
                        T2 = sourceSeq.get(j).split(" ")[1];
                        String yourevent = sourceSeq.get(j).split(" ")[4];
                        String youradd = sourceSeq.get(j).split(" ")[2];
//                    String yourID = sourceSeq.get(j).split(" ")[0];
                        //如果两次读到的不是同一行
                        if (j != i) {
                            //如果第二次读到的线程和第一次读到的线程相同，就把当前事件存到临时列表中，否则继续分析
                            if (T1.equals(T2)) {
//                                if (yourevent.equals(READ)){
//                                    followSeq.add(sourceSeq.get(j));
//                                    added.add(T2 + myevent);
//                                }else {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1]
                                        + sourceSeq.get(j).split(" ")[4]);
//                                }
                            } else {
                                thSet.add(T2);
                                if (thSet.size() == 2) {

                                    if (yourevent.equals("WRITE")
                                            && !added.contains(T2 + "WRITE")) {
//
//                                        if (sourceSeq.get(j - 1).split(" ")[1].equals(T2)
//                                                && sourceSeq.get(j - 1).split(" ")[2].equals(youradd)
//                                                && sourceSeq.get(j - 1).split(" ")[4].equals("READ")) {
//
//                                            //改到这了，如果包含在同一行代码处的读操作，先把读操作加进来再把写操作加进来
//                                            followSeq.add(sourceSeq.get(j - 1));
//                                            added.add(T2 + "READ");
//                                            temp.remove(tempList.indexOf(sourceSeq.get(j-1)));
//                                            tempList.remove(sourceSeq.get(j - 1));
//
//                                            followSeq.add(sourceSeq.get(j));
//                                            added.add(T2 + "WRITE");
//                                        } else {
                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + "WRITE");
//                                        }
                                    } else {
//                                    if (followSeq.size() == 2) {
//                                        followSeq.add(sourceSeq.get(j));
//                                    } else {
                                        tempList.add(sourceSeq.get(j));
                                        temp.add(sourceSeq.get(j).split(" ")[1]
                                                + sourceSeq.get(j).split(" ")[4]);
//                                    }
                                    }
//                            }else {
//                                break;
//                            }
                                } else {
                                    break;
                                }
                            }

                        }

                    }


                    //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                    //如果第三个事件是write那还要保证happens-before原则
                    if (temp.contains(T1+READ)) {
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(BLANK)[1];
                            String event = tempList.get(k).split(BLANK)[4];
                            if (Tid.equals(T1) && event.equals(READ)) {

                                followSeq.add(tempList.get(k));
                                tempList.remove(tempList.get(k));
                                break;

                            }
                        }
                    }else {
                        return null;
                    }
                    String tempString = null;
                    for (int k =0;k<tempList.size();k++){
                        if (tempList.get(k).split(BLANK)[1].equals(T1)
                                && tempList.get(k).split(BLANK)[4].equals(WRITE)){
                            tempString=tempList.get(k);
                        }else {
                            followSeq.add(tempList.get(k));
                        }
                    }

                    if (tempString!=null) {
                        followSeq.add(tempString);
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
}
