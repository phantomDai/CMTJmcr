package algorithm.mp;

import algorithm.mp.utls.GetAllSharedVars;
import constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class MP11 extends MP implements MetamorphicPattern{
    //涉及到一个线程对一个变量进行先读再写，另一个线程涉及到对变量先读后写
    private List<String> getWriteVars(Set<String> allSharedVar, List<String> sourceSeq){
        List<String> targetVars = new ArrayList<>();
        String tempVar1 = "";
        int var1ID = 0;
        for (String var : allSharedVar){
            for (int i = 0; i < sourceSeq.size(); i++) {
                if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                        sourceSeq.get(i).split(BLANK)[3].equals(var) &&
                        sourceSeq.get(i).split(BLANK)[4].equals(READ)){
                    //判断线程2是否对该变量具有写操作
                    for (int j = i; j < sourceSeq.size(); j++) {
                        if (sourceSeq.get(j).split(BLANK)[1].equals("1") &&
                                sourceSeq.get(j).split(BLANK)[3].equals(var) &&
                                sourceSeq.get(j).split(BLANK)[4].equals(WRITE)){
                            targetVars.add(var);
                            tempVar1 = var;
                            var1ID = j;
                            break;
                        }else {
                            continue;
                        }
                    }
                    if (targetVars.size() != 0){
                        break;
                    }
                }else {
                    continue;
                }
            }
            if (targetVars.size() != 0){
                break;
            }
        }
        if (targetVars.size() == 1){
            allSharedVar.remove(tempVar1);
            for (String var : allSharedVar){
                for (int i = 0; i < sourceSeq.size(); i++) {
                    if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                            sourceSeq.get(i).split(BLANK)[3].equals(var) &&
                            sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                        //判断线程2是否对该变量具有写操作
                        if (i < var1ID){
                            continue;
                        }
                        for (int j = i; j < sourceSeq.size(); j++) {
                            if (sourceSeq.get(j).split(BLANK)[1].equals("1") &&
                                    sourceSeq.get(j).split(BLANK)[3].equals(var) &&
                                    sourceSeq.get(j).split(BLANK)[4].equals(READ)){
                                targetVars.add(var);
                                break;
                            }else {
                                continue;
                            }
                        }
                        if (targetVars.size() == 2){
                            break;
                        }
                    }else {
                        continue;
                    }
                }
                if (targetVars.size() == 2){
                    break;
                }
            }
        }
        return targetVars;
    }

    /**
     * 对写事件涉及到的共享变量进行排序
     * @param targetVars
     * @param sourceSeq
     * @return
     */
    private List<String> sortWriteEventsAndGetfollowSeq(List<String> targetVars, List<String> sourceSeq){
        List<String> followSeq = new ArrayList<>();

        //首先确定变量的顺序
        String var1 = null;
        String var2 = null;
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (targetVars.contains(sourceSeq.get(i).split(BLANK)[3]) && sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                var1 = sourceSeq.get(i).split(BLANK)[3];
                targetVars.remove(var1);
                var2 = targetVars.get(0);
                break;
            }
        }
        //然后生成衍生测试序列
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                    sourceSeq.get(i).split(BLANK)[3].equals(var1)){
                followSeq.add(sourceSeq.get(i));
                if (sourceSeq.get(i).split(BLANK)[4].equals(READ)){
                    break;
                }
            }
        }
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("2") &&
                    sourceSeq.get(i).split(BLANK)[3].equals(var1)){
                followSeq.add(sourceSeq.get(i));
                if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                    break;
                }
            }
        }
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("2") &&
                    sourceSeq.get(i).split(BLANK)[3].equals(var2)){
                followSeq.add(sourceSeq.get(i));
                if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                    break;
                }
            }
        }
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                    sourceSeq.get(i).split(BLANK)[3].equals(var2)){
                followSeq.add(sourceSeq.get(i));
                if (sourceSeq.get(i).split(BLANK)[4].equals(READ)){
                    break;
                }
            }
        }
        return followSeq;
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        //获取所有的共享变量
        Set<String> allSharedVar = GetAllSharedVars.getAllSharedvars(sourceSeq);

        //存放两个线程都进行了写操作的变量
        List<String> targetVars = getWriteVars(allSharedVar, sourceSeq);

        //存放衍生测试用例的执行序列
        List<String> followUpEvent = sortWriteEventsAndGetfollowSeq(targetVars,sourceSeq);
        return followUpEvent;
    }

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    public static void main(String[] args) {
        MetamorphicPattern mp = new MP11();
        List<String> sourceSeq = getsourceSeq(Constants.SOURCEEVENT_PATH);
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }

}
