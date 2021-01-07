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
public class MP7 extends MP implements MetamorphicPattern{
    @Override
    public List<String> followUpSeq(List<String> followSeq) {

        return sortSeq(followSeq);
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){
        List<String> followSeq = new ArrayList<>();

        List<String> tempList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        String T1=null;

        String T2;
        String sharedvar = null;

        List<String> source = new ArrayList<>();

        HashSet<String> threadSet = new HashSet<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]+infote.split(BLANK)[3]);
            threadSet.add(infote.split(BLANK)[1]);
        }

        List<String> added = new ArrayList<>();

        //第一次循环
        for (int i=0;i<sourceSeq.size();i++){
            T1 = sourceSeq.get(i).split(" ")[1];
            String myevent =sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];
            String myvar = sourceSeq.get(i).split(BLANK)[3];
            sharedvar=myvar;

            if (Integer.valueOf(T1)<threadSet.size()-2 && !T1.equals("0")){

                followSeq.add(sourceSeq.get(i));
            }else {

                //如果当前是read，就继续，否则i++读下一行
                if (myevent.equals("READ") && source.subList(i, source.size() - 1).contains(T1 + READ + myunite + myvar)) {
//                    System.out.println("---1if----");
                    thSet.add(T1);
                    followSeq.add(sourceSeq.get(i));
                    added.add(T1 + myunite + myevent);

                    //第二次循环
                    for (int j = followSeq.size(); j < sourceSeq.size(); j++) {
                        T2 = sourceSeq.get(j).split(" ")[1];
                        String yourevent = sourceSeq.get(j).split(" ")[4];
                        String yourAdd = sourceSeq.get(j).split(" ")[2];
                        String yourunite = sourceSeq.get(j).split(" ")[5];
                        String yourvar = sourceSeq.get(j).split(BLANK)[3];

                        //如果读的不是一行就继续，否则读下一行
                        if (j != i) {

                            //如果j事件和i事件是同一个线程，将其放到临时序列
                            if (T1.equals(T2)) {
                                tempList.add(sourceSeq.get(j));
                                temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                        + sourceSeq.get(j).split(" ")[5] + " "
                                        + sourceSeq.get(j).split(" ")[4]);
                            } else {     //j和i不是同一个线程的事件
                                thSet.add(T2);
                                if (thSet.size() == 2) {
                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2 + yourunite + yourevent);
                                    //j事件是write，而且之前没有被加到fs中
                                    if (yourevent.equals("WRITE")
                                            && yourvar.equals(myvar)) {
                                        break;
                                    }

                                } else {    //如果分析到第三个线程了就跳出，不读了
                                    break;
                                }
                            }//i和j不是同一个线程的事件分支结束
                        }  //如果j==i就读下一行
                    }//第二次循环结束，此时fs中已经有有两个线程的两个unite的事件了，继续添加第三个事件，读取临时序列

                    //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                    //如果第三个事件是write那还要保证happens-before原则


                    if (temp.contains(T1 + BLANK + myunite + BLANK + READ)) {
                        for (String tem:tempList) {
                            String Tid = tem.split(BLANK)[1];
                            String event = tem.split(BLANK)[4];
                            String unite = tem.split(BLANK)[5];
                            String var = tem.split(BLANK)[3];

                            if (var.equals(myvar)) {
                                followSeq.add(tem);
                            }else {
                                if (event.equals(READ)){
                                    followSeq.add(tem);
                                }
                            }
                        }

                    }//如果临时序列中没有我要的序列返回空
                    else {
                        return null;
                    }

                    break;
                }
            }
        }



        for (int i=followSeq.size()+1;i<sourceSeq.size();i++){
            if (!sourceSeq.get(i).split(BLANK)[1].equals("0")) {
                System.out.println(i);
                System.out.println(sharedvar);
                followSeq.add(sourceSeq.get(i));
                if (!sourceSeq.get(i).split(BLANK)[3].equals(sharedvar)){
                    System.out.println(i);
                    break;
                }

            }
        }
        if (tempList.size() != 0) {
            for (String tem: tempList) {
                if (!followSeq.contains(tem)) {
                    followSeq.add(tem);
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
}
