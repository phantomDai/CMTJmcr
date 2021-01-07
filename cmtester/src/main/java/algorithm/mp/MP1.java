package algorithm.mp;

import algorithm.mp.utls.GetAllSharedVars;
import algorithm.mp.utls.WriteFollowEvent;
import constants.Constants;

import java.util.*;

/**
 * describe:
 * R_u(l) W_u'(l) W_u(l)
 * 接下来记录每一个程序的每一个蜕变模式下的count和pausedThreadInfos.size()的大小
 * PingPong: count <= 6; pausedThreadInfos.size() > 4
 *
 *
 *
 *
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
        //识别原始测试序列中的目标变量
        String targetVar = getTargetVar(sourceSeq);
        return getFollowSeq(sourceSeq, targetVar);
    }

    /**
     * 识别原始测试序列中的目标变量，该变量在一个线程中具有读写事件，再另一个线程中具有写事件
     * @param sourceSeq
     * @return
     */
    private String getTargetVar(List<String> sourceSeq){
        Set<String> allVars = GetAllSharedVars.getAllSharedvars(sourceSeq);
        String targetVar = "";
        flag: for (String var : allVars){
            //是否找到需求的变量的标志
            boolean flag1 = false;
            boolean flag2 = false;

            for (int i = 0; i < sourceSeq.size(); i++) {
                if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                        sourceSeq.get(i).split(BLANK)[3].equals(var) &&
                        sourceSeq.get(i).split(BLANK)[4].equals(READ)){
                    flag1 = true;

                    for (int j = i; j < sourceSeq.size(); j++) {
                        if (sourceSeq.get(j).split(BLANK)[1].equals("1") &&
                                sourceSeq.get(j).split(BLANK)[3].equals(var) &&
                                sourceSeq.get(j).split(BLANK)[4].equals(WRITE)){
                            flag2 = true;
                            for (int k = j; k < sourceSeq.size(); k++) {
                                if (sourceSeq.get(k).split(BLANK)[1].equals("2") &&
                                        sourceSeq.get(k).split(BLANK)[3].equals(var) &&
                                        sourceSeq.get(k).split(BLANK)[4].equals(WRITE)){
                                    targetVar = var;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        return targetVar;
    }

    private List<String> getFollowSeq(List<String> sourceSeq, String targetVar) {
        List<String> followSeq = new ArrayList<>();
        //判断衍生序列中是否已经有线程2
        boolean flag2 = false;
        for (int i = 0; i < sourceSeq.size(); i++) {
            if (sourceSeq.get(i).split(BLANK)[1].equals("1") &&
                    sourceSeq.get(i).split(BLANK)[3].equals(targetVar)){
                followSeq.add(sourceSeq.get(i));
                //加入2线程的写事件

                if (sourceSeq.get(i).split(BLANK)[4].equals(READ)){
                    if (!flag2){
                        for (int j = i; j < sourceSeq.size(); j++) {
                            if (sourceSeq.get(j).split(BLANK)[1].equals("2") &&
                                    sourceSeq.get(j).split(BLANK)[3].equals(targetVar)){
                                followSeq.add(sourceSeq.get(j));
                                if (sourceSeq.get(j).split(BLANK)[4].equals(WRITE)){
                                    flag2 = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (sourceSeq.get(i).split(BLANK)[4].equals(WRITE)){
                    break;
                }
            }
        }
        return followSeq;
    }

    public static void main(String[] args){
        MetamorphicPattern mp = new MP1();
        List<String> sourceSeq = getsourceSeq(Constants.SOURCEEVENT_PATH);
        List<String> follSeq = mp.followUpSeqWithoutSort(sourceSeq);
        List<String> followUpSeq = mp.followUpSeq(follSeq);
        for (String info:followUpSeq){
            System.out.println(info);
        }
    }
}
