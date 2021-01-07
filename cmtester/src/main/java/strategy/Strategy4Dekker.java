package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import constants.Constants;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

public class Strategy4Dekker implements Strategy{
    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp3 = mpFactory4PUT.produceMP("MP3", "PingPong.java");
    private static MetamorphicPattern mp4 = mpFactory4PUT.produceMP("MP4", "PingPong.java");

    //衍生执行轨迹作为指导
    private static List<String> followUpSeq3 =
            mp3.followUpSeq(mp3.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

    private static List<String> followUpSeq4 =
            mp4.followUpSeq(mp4.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

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
        if (MPName.equals("MP3")){
            if (followUpSeq3 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq3.get(0).split(" ")[1]));
                String firstAction = followUpSeq3.get(0).split(" ")[4];
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
                    followUpSeq3.remove(0);
                }
            }
        }else if (MPName.equals("MP4")){
            if (followUpSeq4 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq4.get(0).split(" ")[1]));
                String firstAction = followUpSeq4.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq4.remove(0);
                }
            }
        }
        return choice;
    }
}
