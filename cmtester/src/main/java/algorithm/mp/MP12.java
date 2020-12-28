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
public class MP12 extends MP implements MetamorphicPattern{
    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){

        List<String> followSeq = new ArrayList<>();

        String T1=null;
        String T2=null;
        String v1=null;
        String v2=null;

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
            source.add(infote.split(BLANK)[1]+infote.split(BLANK)[4]+infote.split(BLANK)[3] );
            threadSet.add(infote.split(BLANK)[1]);
        }

        //第一次遍历
        for (int i=0;i<sourceSeq.size();i++){
            String T=sourceSeq.get(i).split(BLANK)[1];
            String myevent = sourceSeq.get(i).split(BLANK)[4];
            String myvar = sourceSeq.get(i).split(BLANK)[3];
            String myline = sourceSeq.get(i).split(BLANK)[2];

            //不管开启多少线程，只有最后两个线程会发生指定的交错
            if (Integer.valueOf(T)<threadSet.size()-2 && Integer.valueOf(T)!=0){
                followSeq.add(sourceSeq.get(i));
            }else if (Integer.valueOf(T)==0){
                tempList.add(sourceSeq.get(i));
                temp.add(T+myevent+myvar);
            }
            else {
                //这个场景需要让最后一个线程先执行，倒数第二个线程有两个写操作，所以后执行
                if (Integer.valueOf(T)==threadSet.size()-1){
                    T1=T;
                    if (added.size()<2){
                        followSeq.add(sourceSeq.get(i));
                        added.add(T+myevent+myvar);
                        v1=myvar;
                    }else {
                        tempList.add(sourceSeq.get(i));
                        temp.add(T+myevent+myvar);
                        if (!myvar.equals(v1)){
                            v2=myvar;
                        }
                    }
                }
            }
        }

        int init = followSeq.size();
        int temlen = tempList.size()+2;

        //第二次遍历,分析倒数第二个线程的事件
        for (int j=init-2;j<sourceSeq.size()-temlen;j++){
            if (!followSeq.contains(sourceSeq.get(j))){
                T2 = sourceSeq.get(j).split(BLANK)[1];
                String yourevent = sourceSeq.get(j).split(BLANK)[4];
                String yourvar = sourceSeq.get(j).split(BLANK)[3];
//                System.out.println(v1 + " " +v2);
                if (!added.contains(T2+WRITE+v2)) {

                    if (yourevent.equals(WRITE) && yourvar.equals(v1)) {
                        followSeq.add(sourceSeq.get(j));
                        added.add(T2 + yourevent + yourvar);
                    } else if (yourevent.equals(WRITE) && (yourvar.equals(v2))) {
                        followSeq.add(sourceSeq.get(j));
                        added.add(T2 + yourevent + yourvar);
                    } else {
                        followSeq.add(sourceSeq.get(j));
                        added.add(T2 + yourevent + yourvar);
                    }
                }

            }
        }


        if (temp.contains(T1 + READ + v2)) {
            for (int k = 0; k < tempList.size(); k++) {
                String Tid = tempList.get(k).split(" ")[1];
                String event = tempList.get(k).split(" ")[4];
                String unite = tempList.get(k).split(BLANK)[5];
                String var = tempList.get(k).split(BLANK)[3];
                if (!(Tid.equals(T1) && event.equals(READ) && var.equals(v2))) {

                    followSeq.add(tempList.get(k));
                    tempList.remove(tempList.get(k));
                }else {
                    followSeq.add(tempList.get(k));
                    tempList.remove(tempList.get(k));
                    break;
                }
            }
        } else {//如果临时序列中没有我要的序列返回空
            return null;
        }
        if (tempList.size() != 0) {

            followSeq.addAll(tempList);
        }

        return followSeq;

    }
}
