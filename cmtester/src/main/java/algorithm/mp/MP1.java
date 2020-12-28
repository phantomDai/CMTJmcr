package algorithm.mp;

import java.util.*;

/**
 * describe:
 *
 * @author phantom
 * @date 2020/12/28
 */
public class MP1 extends MP implements MetamorphicPattern{

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        //follow-up序列
        List<String> followSeq = new ArrayList<>();
        String T1;
        String T2;
        String lastInfo ="";
        //临时序列
        List<String> tempList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        //存储已经添加到followSeq中的事件
        List<String> added = new ArrayList<>();

        List<String> source = new ArrayList<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        Map<String,List<String>> map = new TreeMap<>();

        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]);
        }

        //遍历原始序列中的每个事件
        for (int i=0;i<sourceSeq.size();i++) {
            //当前事件的线程、变量、事件类型
            T1 = sourceSeq.get(i).split(" ")[1];
            String myvar = sourceSeq.get(i).split(" ")[3];
            String myevent = sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];
            //如果当前事件是一个读事件，就把它加到衍生序列中
            if (!T1.equals(0) && myevent.equals("READ")) {
                followSeq.add(sourceSeq.get(i));
                added.add(T1 + myunite+myevent);
                thSet.add(T1);
                //第二次遍历原始序列中的每个事件
                for (int j = 0; j < sourceSeq.size(); j++) {
                    //第二次遍历到的事件的线程、事件类型
                    T2 = sourceSeq.get(j).split(" ")[1];
                    String yourevent = sourceSeq.get(j).split(" ")[4];
                    String yourunit = sourceSeq.get(j).split(" ")[5];
//                    String yourID = sourceSeq.get(j).split(" ")[0];
                    //如果两次读到的不是同一行
                    if (i != j && !T2.equals(0)) {
                        //如果第二次读到的线程和第一次读到的线程相同，就把当前事件存到临时列表中，否则继续分析
                        if (T1.equals(T2)) {
                            tempList.add(sourceSeq.get(j));
                            temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                    + sourceSeq.get(j).split(" ")[5] + " "
                                    + sourceSeq.get(j).split(" ")[4]);
                        } else {
//                            if (followSeq.size()<3) {
                            thSet.add(T2);
                            if (thSet.size()==2) {

                                if (yourevent.equals("WRITE")
                                        && !added.contains(T2+ yourunit+"WRITE")) {
//                                    if (temp.contains(T2 + " " + youradd + " READ")){
//                                        int index = temp.indexOf( T2 + " " + youradd + " READ");
                                    if (j>0 && source.subList(0,j).contains(T2 + READ + yourunit)){

                                        //改到这了，如果包含在同一行代码处的读操作，怎么添加！！
                                        followSeq.add(sourceSeq.get(source.indexOf(T2+READ+yourunit)));
                                        added.add(T2 +yourunit+ "READ");
                                        tempList.remove(sourceSeq.get(source.indexOf(T2+READ+yourunit)));

                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + yourunit+"WRITE");
                                    }else {
                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + yourunit+"WRITE");
                                    }
                                } else {
//                                    if (followSeq.size() == 2) {
//                                        followSeq.add(sourceSeq.get(j));
//                                    } else {
                                    tempList.add(sourceSeq.get(j));
                                    temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                            + sourceSeq.get(j).split(" ")[5] + " "
                                            + sourceSeq.get(j).split(" ")[4]);
//                                    }
                                }
//                            }else {
//                                break;
//                            }
                            }else {
                                break;
                            }
                        }
                    }

                }

                //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                //如果第三个事件是write那还要保证happens-before原则
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

                if (tempList.size()!=0) {

                    followSeq.addAll(tempList);
                }
                break;
            }


        }
        for (int i=followSeq.size();i<sourceSeq.size();i++){
            if (!followSeq.contains(sourceSeq.get(i))) {
                followSeq.add(sourceSeq.get(i));
            }
        }



        for (int i=0;i<followSeq.size();i++){
            int id = Integer.valueOf(followSeq.get(0).split(BLANK)[0]);
            if (i>0){
                followSeq.get(i);
            }
        }
        return followSeq;
    }

    //将生成的序列按按顺序重新赋予事件序号
    public static List<String> sortSeq(List<String> followSeq){
        //存储事件序号
        int[] array = new int[followSeq.size()];
        for (int i=0;i<followSeq.size();i++){

            array[i] = Integer.valueOf(followSeq.get(i).split(BLANK)[0]);
        }
        Arrays.sort(array);
        for (int j=0;j<followSeq.size();j++){

            StringBuilder stringBuilder = new StringBuilder(followSeq.get(j));

            stringBuilder.replace(0,2,String.valueOf(array[j]));

            followSeq.set(j,stringBuilder.toString());
        }

        return followSeq;
    }


}
