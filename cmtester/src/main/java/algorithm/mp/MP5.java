package algorithm.mp;

import constants.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * describe:
 * R_u(l) W_u'(l) W_u(l)
 * 接下来记录每一个程序的每一个蜕变模式下的count和pausedThreadInfos.size()的大小
 * PingPong: count <= 7; pausedThreadInfos.size() > 4
 *
 *
 *
 *
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

        //存放衍生测试用例的执行序列
        List<String> followUpEvent = new ArrayList<>();

        //存放所有的共享变量
        Set<String> allSheeredVar = new HashSet<String>();

        for (String line : sourceSeq){
            allSheeredVar.add(line.split(BLANK)[3]);
        }

        //改蜕变模式涉及到两个线程
        String T1 = "1";
        String T2 = "2";

        //记录线程1和2的事件的关键未知
        int countT1 = -1; //紧邻写事件的读事件索引
        int countT2 = -1;

        //观察线程1和2的读写事件是否包含先读后写的情况
        boolean foundFlagWrite1T1 = false;
        boolean foundFlagWrite2T1 = false;
        boolean foundFlagWriteT2 = false;
        boolean foundFlagTargetVar = false;
        String targetSheeredVar = ""; //具有读写事件的变量

        for (String var : allSheeredVar){
            //首先遍历第一个线程的读写事件，观察是否存在先读后写的情况
            for (int i = 0; i < sourceSeq.size(); i++) {
                String tempTID = sourceSeq.get(i).split(BLANK)[1];
                String sheeredVar = sourceSeq.get(i).split(BLANK)[3];
                String tempEvent = sourceSeq.get(i).split(BLANK)[4];

                if (tempTID.equals(T1) && sheeredVar.equals(var)
                        && tempEvent.equals(WRITE)){
                    //找到线程1的第一个写事件，然后判断下一个事件是否为写事件
                    foundFlagWrite1T1 = true;
                    countT1 = i;
                    for (int j = (countT1 + 1); j < sourceSeq.size(); j++) {
                        if (foundFlagWrite1T1 == true && foundFlagWrite2T1 == true){
                            break;
                        }
                        if (sourceSeq.get(j).split(BLANK)[1].equals(T1) &&
                                sourceSeq.get(j).split(BLANK)[3].equals(var) &&
                                sourceSeq.get(j).split(BLANK)[4].equals(WRITE)){
                            countT2 = j;
                            foundFlagWrite2T1 = true;
                        }
                    }
                }else {
                    continue;
                }
                if (foundFlagWrite1T1 == true && foundFlagWrite2T1 == true){
                    break;
                }
            }
            if (foundFlagWrite1T1 == true && foundFlagWrite2T1 == true){
                targetSheeredVar = var;
                foundFlagTargetVar = true;
                break;
            }else {
                foundFlagWrite1T1 = false;
                foundFlagWrite2T1 = false;
            }
        }
        for (int i = 0; i <sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals(T2) &&
                    sourceSeq.get(i).split(BLANK)[3].equals(targetSheeredVar) &&
                    sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                countT2 = i;
                break;
            }else {
                continue;
            }
        }
        //将countT1之前的事件加入到衍生事件中，将countT2之前的T2线程的事件加入到衍生事件中
        for (int i = 0; i <= countT1; i++) {
            followUpEvent.add(sourceSeq.get(i));
        }

        for (int i = 0; i <= countT2; i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals(T2)){
                followUpEvent.add(sourceSeq.get(i));
            }
        }

        for (int i = (countT1 + 1); i < sourceSeq.size(); i++) {
            if (i <= countT2){
                if (!sourceSeq.get(i).split(BLANK)[1].equals(T2)){
                    followUpEvent.add(sourceSeq.get(i));
                }else {
                    continue;
                }
            }else {
                followUpEvent.add(sourceSeq.get(i));
            }
        }
        return followUpEvent;
    }

    public static void main(String[] args){
        MetamorphicPattern mp = new MP5();
        List<String> sourceSeq = getsourceSeq(Constants.SOURCEEVENT_PATH);
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }
}
