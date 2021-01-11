package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import constants.Constants;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

public class Strategy4SunsAccount implements Strategy{
    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();


    private static MetamorphicPattern mp1 = mpFactory4PUT.produceMP("MP1", "PingPong.java");

    //衍生执行轨迹作为指导
    private static List<String> followUpSeq1 =
            mp1.followUpSeq(mp1.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));


    /**
     *
     * @param MPName
     * @param pausedThreadInfos
     * @return
     */
    @Override
    public ThreadInfo choose(String MPName, Set<ThreadInfo> pausedThreadInfos) {
        //选择的线程
        ThreadInfo choice=null;
        if (MPName.equals("MP1")){
            if (followUpSeq1 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq1.get(0).split(" ")[1]));
                String firstAction = followUpSeq1.get(0).split(" ")[4];
//                if (firstthname.equals("Thread-2") && firstAction.equals("WRITE")){
//                    firstAction = "READ";
//                }
                for (ThreadInfo info:pausedThreadInfos){
                    String tempStr = info.toString();
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq1.remove(0);
                }
            }
        }
        return choice;
    }
}
