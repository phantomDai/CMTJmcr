package algorithm.mp.utls;

import java.util.*;

import static algorithm.mp.MetamorphicPattern.BLANK;

/**
 * 从原始测试用例的执行序列中识别共享变量
 */
public class GetAllSharedVars {

    public static Set<String> getAllSharedvars(List<String> sourceSeq){
        //存放线程编号
        Set<String> threadIDs = new LinkedHashSet<>();
        Set<String> sharedVarsOfThread1 = new LinkedHashSet<>();
        Set<String> sharedVars = new LinkedHashSet<>();

        for (int i = 0; i < sourceSeq.size(); i++) {
            threadIDs.add(sourceSeq.get(i).split(BLANK)[1]);
            if (sourceSeq.get(i).split(BLANK)[1].equals("1")){
                sharedVarsOfThread1.add(sourceSeq.get(i).split(BLANK)[3]);
            }
        }

        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("2")){
                if (sharedVarsOfThread1.contains(sourceSeq.get(i).split(BLANK)[3])){
                    sharedVars.add(sourceSeq.get(i).split(BLANK)[3]);
                }
            }
        }
        return sharedVars;
    }
}
