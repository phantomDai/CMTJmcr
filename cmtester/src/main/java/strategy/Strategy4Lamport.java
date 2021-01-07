package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import constants.Constants;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

public class Strategy4Lamport implements Strategy{
    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp1 = mpFactory4PUT.produceMP("MP1", "PingPong.java");
    private static MetamorphicPattern mp4 = mpFactory4PUT.produceMP("MP4", "PingPong.java");
    private static MetamorphicPattern mp6 = mpFactory4PUT.produceMP("MP6", "PingPong.java");
    private static MetamorphicPattern mp9 = mpFactory4PUT.produceMP("MP9", "PingPong.java");
    private static MetamorphicPattern mp11 = mpFactory4PUT.produceMP("MP11", "PingPong.java");
    private static MetamorphicPattern mp10 = mpFactory4PUT.produceMP("MP10", "PingPong.java");
    private static MetamorphicPattern mp12 = mpFactory4PUT.produceMP("MP12", "PingPong.java");
    private static MetamorphicPattern mp13 = mpFactory4PUT.produceMP("MP13", "PingPong.java");
    private static MetamorphicPattern mp14 = mpFactory4PUT.produceMP("MP14", "PingPong.java");
    //衍生执行轨迹作为指导
    private static List<String> followUpSeq1 =
            mp1.followUpSeq(mp1.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

    private static List<String> followUpSeq4 =
            mp4.followUpSeq(mp4.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq6 =
            mp6.followUpSeq(mp6.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq9 =
            mp9.followUpSeq(mp9.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq11 =
            mp11.followUpSeq(mp11.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq10 =
            mp10.followUpSeq(mp10.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq13 =
            mp13.followUpSeq(mp13.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq14 =
            mp14.followUpSeq(mp14.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq12 =
            mp12.followUpSeq(mp12.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

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
        }else if (MPName.equals("MP6")){
            if (followUpSeq6 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq6.get(0).split(" ")[1]));
                String firstAction = followUpSeq6.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq6.remove(0);
                }
            }
        }else if (MPName.equals("MP9")){
            if (followUpSeq9 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq9.get(0).split(" ")[1]));
                String firstAction = followUpSeq9.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq9.remove(0);
                }
            }
        }else if (MPName.equals("MP11")){
            if (followUpSeq11 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq11.get(0).split(" ")[1]));
                String firstAction = followUpSeq11.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq11.remove(0);
                }
            }
        }else if (MPName.equals("MP10")){
            if (followUpSeq10 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq10.get(0).split(" ")[1]));
                String firstAction = followUpSeq10.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq10.remove(0);
                }
            }
        }else if (MPName.equals("MP13")){
            if (followUpSeq13 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq13.get(0).split(" ")[1]));
                String firstAction = followUpSeq13.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq13.remove(0);
                }
            }
        }else if (MPName.equals("MP14")){
            if (followUpSeq14 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq14.get(0).split(" ")[1]));
                String firstAction = followUpSeq14.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq14.remove(0);
                }
            }
        }else if (MPName.equals("MP12")){
            if (followUpSeq12 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq12.get(0).split(" ")[1]));
                String firstAction = followUpSeq12.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq12.remove(0);
                }
            }
        }
        return choice;
    }
}
