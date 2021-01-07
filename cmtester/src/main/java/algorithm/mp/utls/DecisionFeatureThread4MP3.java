package algorithm.mp.utls;

import com.sun.codemodel.internal.JVar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static algorithm.mp.MetamorphicPattern.*;

/**
 * 为MP3判断哪一个线程是特征线程（两个写事件）
 */
public class DecisionFeatureThread4MP3 {


    /**
     * 实现类功能的具体方法
     * @param sourceSeq 原始测试用例的执行序列
     * @param T1 线程1
     * @param T2 线程2
     * @return 具有指定特征的线程名字，和变量名称
     */
    public static List<String> featureThread(List<String> sourceSeq, String T1, String T2){
        //存放所有的共享变量
        Set<String> allSheeredVar = new HashSet<String>();

        List<String> returnList = null;

        for (String line : sourceSeq){
            allSheeredVar.add(line.split(BLANK)[3]);
        }

        //线程是否具有特征的标志
        boolean isT1 = false;
        boolean isT2 = false;

        // 首先判断第一个线程是否为特征线程（本模式的特征是具有两个写操作）
        for (String var : allSheeredVar){
            //是否发现特征的标志
            boolean isFound = false;
            for (int i = 0; i < sourceSeq.size(); i++) {
                boolean tempFlag1 = false;
                boolean tempFlag2 = false;
                if (sourceSeq.get(i).split(BLANK)[1].equals(T1) && sourceSeq.get(i).split(BLANK)[3].equals(var)){
                    if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                        tempFlag1 = true;
                        for (int j = i; j < sourceSeq.size(); j++) {
                            if (sourceSeq.get(j).split(BLANK)[1].equals(T1) && sourceSeq.get(j).split(BLANK)[4].equals(WRITE) &&
                                    sourceSeq.get(j).split(BLANK)[3].equals(var)){
                                tempFlag2 = true;
                                break;
                            }
                        }
                    }
                    if (tempFlag1 == true && tempFlag2 == true){
                        break;
                    }
                }
                if (tempFlag1 && tempFlag2 ){
                    //判断第二个线程是否包含读事件
                    for (int k = 0; k < sourceSeq.size(); k++) {
                        if (sourceSeq.get(k).split(BLANK)[1].equals(T2) && sourceSeq.get(i).split(BLANK)[3].equals(var)) {
                            if (sourceSeq.get(i).split(BLANK)[4].equals(READ)) {
                                isFound = true;
                                isT1 = true;
                            }
                            if (isFound) {
                                returnList = Stream.of(T1, var).collect(Collectors.toList());
                                break;
                            }
                        }
                    }
                }
            }
            if (isFound){
             break;
            }
        }

        // 然后判断第二个线程是否为特征线程（本模式的特征是具有两个写操作）
        for (String var : allSheeredVar){
            //是否发现特征的标志
            boolean isFound = false;
            for (int i = 0; i < sourceSeq.size(); i++) {
                boolean tempFlag1 = false;
                boolean tempFlag2 = false;
                if (sourceSeq.get(i).split(BLANK)[1].equals(T2) && sourceSeq.get(i).split(BLANK)[3].equals(var)){
                    if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                        tempFlag1 = true;
                        for (int j = i; j < sourceSeq.size(); j++) {
                            if (sourceSeq.get(j).split(BLANK)[1].equals(T2) && sourceSeq.get(j).split(BLANK)[4].equals(WRITE) &&
                                    sourceSeq.get(j).split(BLANK)[3].equals(var)){
                                tempFlag2 = true;
                                break;
                            }
                        }
                    }
                    if (tempFlag1 == true && tempFlag2 == true){
                        break;
                    }
                }
                if (tempFlag1 && tempFlag2 ){
                    //判断第二个线程是否包含读事件
                    for (int k = 0; k < sourceSeq.size(); k++) {
                        if (sourceSeq.get(k).split(BLANK)[1].equals(T1) && sourceSeq.get(i).split(BLANK)[3].equals(var)) {
                            if (sourceSeq.get(i).split(BLANK)[4].equals(READ)) {
                                isFound = true;
                                isT2 = true;
                            }
                            if (isFound) {
                                returnList = Stream.of(T2, var).collect(Collectors.toList());
                                break;
                            }
                        }
                    }
                }
            }
            if (isFound){
                break;
            }
        }

        return returnList;
    }


    /**
     * 判断原始测试用例的执行序列是否可以用匹配改模式
     * @param sourceSeq
     * @param T1
     * @param T2
     * @return true：匹配；false：不匹配
     */
    public static boolean isMatch(List<String> sourceSeq, String T1, String T2){
        List<String> list = featureThread(sourceSeq, T1, T2);
        if (list == null){
            return false;
        }else {
            return true;
        }
    }

}
