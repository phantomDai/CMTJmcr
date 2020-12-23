package algorithm.mp.mpFactory;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;
import algorithm.obtainpattern.OBTP1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author GN
 * @description MP2:依据pattern2生成FS
 * @date 2020/9/15
 */
public class MP2 extends MP implements MetamorphicPattern {
//    @Override
//    public List<String> getsourceSeq(String path) {
//        OBTP1 obtp = new OBTP1();
//        return obtp.getTrace(path);
//    }

    @Override
    public List<String> followUpSeq(List<String> sourceSeq) {

        List<String> followSeq = new ArrayList<>();

        List<String> tempList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        String T1;

        String T2;

        List<String> source = new ArrayList<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        for (String infote:sourceSeq){
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[5]);
        }

        List<String> added = new ArrayList<>();
        //第一次循环
        for (int i=0;i<sourceSeq.size();i++){
            T1 = sourceSeq.get(i).split(" ")[1];
            String myevent =sourceSeq.get(i).split(" ")[4];
            String myunite = sourceSeq.get(i).split(BLANK)[5];

            //如果当前是read，就继续，否则i++读下一行
            if (myevent.equals("READ") &&source.lastIndexOf(T1+READ+myunite)!=i){
                System.out.println("---1if----");
                thSet.add(T1);
                followSeq.add(sourceSeq.get(i));
                added.add(T1 +myunite+ myevent);

                //第二次循环
                for (int j=0;j<sourceSeq.size();j++){
                    T2 = sourceSeq.get(j).split(" ")[1];
                    String yourevent = sourceSeq.get(j).split(" ")[4];
                    String yourAdd = sourceSeq.get(j).split(" ")[2];
                    String yourunite = sourceSeq.get(j).split(" ")[5];

                    //如果读的不是一行就继续，否则读下一行
                    if (j!=i){

                        //如果j事件和i事件是同一个线程，将其放到临时序列
                        if (T1.equals(T2)){
                            tempList.add(sourceSeq.get(j));
                            temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                    + sourceSeq.get(j).split(" ")[5] + " "
                                    + sourceSeq.get(j).split(" ")[4]);
                        }else {     //j和i不是同一个线程的事件
                            thSet.add(T2);
                            if (thSet.size()==2){
                                //j事件是write，而且之前没有被加到fs中
                                if (yourevent.equals("WRITE")
                                        && !added.contains(T2 + yourunite+"WRITE")) {
                                    //如果write之前有必须发生的read就添加进去
                                    if (j>0 && source.subList(0,j).contains(T2 + READ + yourunite)) {
                                        followSeq.add(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));
                                        added.add(T2 + yourunite+"READ");
                                        tempList.remove(sourceSeq.get(source.indexOf(T2+ READ+yourunite)));
                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + yourunite+yourevent);
                                    } else  {   //没有必须要发生的read就只添加write
                                        followSeq.add(sourceSeq.get(j));
                                        added.add(T2 + yourunite+yourevent);
                                    }
                                }else {     //如果不是write事件或者已经添加过了某个相同的write，就把j加到临时序列中
                                    tempList.add(sourceSeq.get(j));
                                    temp.add(sourceSeq.get(j).split(" ")[1] + " "
                                            + sourceSeq.get(j).split(" ")[5] + " "
                                            + sourceSeq.get(j).split(" ")[4]);
                                }

                            }else {    //如果分析到第三个线程了就跳出，不读了
                                break;
                            }
                        }//i和j不是同一个线程的事件分支结束
                    }  //如果j==i就读下一行
                }//第二次循环结束，此时fs中已经有有两个线程的两个unite的事件了，继续添加第三个事件，读取临时序列

                //如果临时序列中存在和pattern中要求的第三个事件，就将其加入到衍生序列，否则返回一个null
                //如果第三个事件是write那还要保证happens-before原则
                System.out.println(temp);
                if (temp.contains(T1 +BLANK+myunite+BLANK+ READ)) {

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
        MetamorphicPattern mp = new MP2();
        List<String> list = mp.followUpSeq(MP.getsourceSeq("F:\\gengning\\workplace\\CMT\\trace\\test.txt"));
        for (String info : list){
            System.out.println(info);
        }
    }
}
