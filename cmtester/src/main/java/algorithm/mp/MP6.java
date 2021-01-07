package algorithm.mp;

import algorithm.mp.utls.GetAllSharedVars;
import constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * W_1(y)W_2(y)W_2(x)W_1(x)
 * 逐个遍历每一个参数查找1和2线程先对y写，然后2和1再对x进行写
 */
public class MP6 extends MP implements MetamorphicPattern{


    //获得2个线程写事件涉及到的变量
    private List<String> getWriteVars(Set<String> allSharedVar, List<String> sourceSeq){
        List<String> targetVars = new ArrayList<>();
        for (String var : allSharedVar){
            for (int i = 0; i < sourceSeq.size(); i++) {
                if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                        sourceSeq.get(i).split(BLANK)[3].equals(var) &&
                        sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                    //判断线程2是否对该变量具有写操作
                    for (int j = 0; j < sourceSeq.size(); j++) {
                        if (sourceSeq.get(j).split(BLANK)[1].equals("2") &&
                                sourceSeq.get(j).split(BLANK)[3].equals(var) &&
                                sourceSeq.get(j).split(BLANK)[4].equals(WRITE)){
                            targetVars.add(var);
                        }else {
                            continue;
                        }
                    }
                }else {
                    continue;
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
                if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
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
                if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
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
        MetamorphicPattern mp = new MP6();
        List<String> sourceSeq = getsourceSeq(Constants.SOURCEEVENT_PATH);
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }

}
