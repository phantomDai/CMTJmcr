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
public class MP10 extends MP implements MetamorphicPattern{
    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    public List<String> followUpSeqWithoutSort(List<String> sourceSeq){
        List<String> followSeq = new ArrayList<>();

        String T1;
        String T2;
        String v1;
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
            T1=sourceSeq.get(i).split(BLANK)[1];
            String myevent = sourceSeq.get(i).split(BLANK)[4];
            String myvar = sourceSeq.get(i).split(BLANK)[3];

            //不管开启多少线程，只有最后两个线程会发生指定的交错
            if (Integer.valueOf(T1)<threadSet.size()-2){
                followSeq.add(sourceSeq.get(i));
            }else {
                if (myevent.equals(WRITE)){
                    thSet.add(T1);
                    v1=myvar;
                    //保证happends-before原则
                    followSeq.add(sourceSeq.get(source.indexOf(T1 + READ + myvar)));
                    added.add(T1+READ+myvar);
                    followSeq.add(sourceSeq.get(i-1));
                    added.add(T1+READ+myvar);

                    followSeq.add(sourceSeq.get(i));

                    //第二次遍历
                    for (int j=i+1;j<sourceSeq.size();j++){
                        T2 = sourceSeq.get(j).split(BLANK)[1];
                        String yourevent = sourceSeq.get(j).split(BLANK)[4];
                        String yourvar = sourceSeq.get(j).split(BLANK)[3];

                        if (T1.equals(T2)){
                            tempList.add(sourceSeq.get(j));
                            temp.add(T2 + yourevent + yourvar);
                        }else {
                            thSet.add(T2);
                            if (thSet.size()==2) {
                                if (yourevent.equals(READ) && yourvar.equals(myvar) && !added.contains(T2+READ+yourvar)){
                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2+READ+yourvar);
                                } else if (added.contains(T2+READ+myvar) && yourvar.equals(myvar)){
                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2+yourevent+yourvar);
                                } else if (!yourvar.equals(myvar) && yourevent.equals(READ)){
                                    v2=yourvar;
                                    followSeq.add(sourceSeq.get(j));
                                    added.add(T2+READ+yourvar);
                                    break;
                                }
                            }else {
                                break;
                            }
                        }
                    }

                    if (temp.contains(T1+WRITE+v2)){
                        for (int k = 0; k < tempList.size(); k++) {
                            String Tid = tempList.get(k).split(" ")[1];
                            String event = tempList.get(k).split(" ")[4];
                            String unite = tempList.get(k).split(BLANK)[5];
                            String var = tempList.get(k).split(BLANK)[3];
                            if (Tid.equals(T1) && event.equals("WRITE")  && var.equals(v2)) {

                                if (tempList.get(k - 1).split(BLANK)[1].equals(Tid)
                                        && tempList.get(k - 1).split(BLANK)[4].equals(READ)
                                        && tempList.get(k - 1).split(BLANK)[5].equals(unite)
                                        && tempList.get(k-1).split(BLANK)[3].equals(var)) {
//                                    System.out.println("youduma");
                                    followSeq.add(tempList.get(k - 2));
                                    followSeq.add(tempList.get(k - 1));
                                    followSeq.add(tempList.get(k));
                                    tempList.remove(k - 2);
                                    tempList.remove(k - 2);
                                    tempList.remove(k - 2);
                                    break;
                                } else {
                                    followSeq.add(tempList.get(k));
                                    tempList.remove(tempList.get(k));
                                    break;
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

        for (int i=followSeq.size();i<sourceSeq.size();i++){
            followSeq.add(sourceSeq.get(i));
        }

        return followSeq;
    }
}
