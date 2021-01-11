package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import constants.Constants;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

public class Strategy4BufWriter implements Strategy{
    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp2 = mpFactory4PUT.produceMP("MP2", "PingPong.java");
    private static MetamorphicPattern mp3 = mpFactory4PUT.produceMP("MP3", "PingPong.java");
    private static MetamorphicPattern mp5 = mpFactory4PUT.produceMP("MP5", "PingPong.java");
    private static MetamorphicPattern mp7 = mpFactory4PUT.produceMP("MP7", "PingPong.java");
    private static MetamorphicPattern mp8 = mpFactory4PUT.produceMP("MP8", "PingPong.java");
    private static MetamorphicPattern mp9 = mpFactory4PUT.produceMP("MP9", "PingPong.java");
    private static MetamorphicPattern mp11 = mpFactory4PUT.produceMP("MP11", "PingPong.java");
    private static MetamorphicPattern mp10 = mpFactory4PUT.produceMP("MP10", "PingPong.java");
    private static MetamorphicPattern mp12 = mpFactory4PUT.produceMP("MP12", "PingPong.java");
    private static MetamorphicPattern mp13 = mpFactory4PUT.produceMP("MP13", "PingPong.java");
    private static MetamorphicPattern mp14 = mpFactory4PUT.produceMP("MP14", "PingPong.java");
    //衍生执行轨迹作为指导
    private static List<String> followUpSeq2 =
            mp2.followUpSeq(mp2.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

    private static List<String> followUpSeq3 =
            mp3.followUpSeq(mp3.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq5 =
            mp5.followUpSeq(mp5.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));

    private static List<String> followUpSeq7 =
            mp7.followUpSeq(mp7.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
    private static List<String> followUpSeq8 =
            mp8.followUpSeq(mp8.followUpSeqWithoutSort(MP.getsourceSeq(Constants.SOURCEEVENT_PATH)));
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
        if (MPName.equals("MP2")){
            if (followUpSeq2 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq2.get(0).split(" ")[1]));
                String firstAction = followUpSeq2.get(0).split(" ")[4];
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
                    followUpSeq2.remove(0);
                }
            }
        }else if (MPName.equals("MP3")){
            if (followUpSeq3 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq3.get(0).split(" ")[1]));
                String firstAction = followUpSeq3.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq3.remove(0);
                }
            }
        }else if (MPName.equals("MP5")){
            if (followUpSeq5 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq5.get(0).split(" ")[1]));
                String firstAction = followUpSeq5.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq5.remove(0);
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
        }else if (MPName.equals("MP7")){
            if (followUpSeq7 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq7.get(0).split(" ")[1]));
                String firstAction = followUpSeq7.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq7.remove(0);
                }
            }
        }else if (MPName.equals("MP8")){
            if (followUpSeq8 != null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq8.get(0).split(" ")[1]));
                String firstAction = followUpSeq8.get(0).split(" ")[4];
                for (ThreadInfo info:pausedThreadInfos){
                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq8.remove(0);
                }
            }
        }
        return choice;
    }
}
