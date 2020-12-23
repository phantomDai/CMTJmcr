package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

/**
 * @author GN
 * @description
 * @date 2020/10/12
 */
public class Strategy4Airline implements Strategy{

    private static String path = "F:\\CMT\\SE\\AirlineTickets.txt";


    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp1 = mpFactory4PUT.produceMP("MP1","AirlineTickets.java");
    private static MetamorphicPattern mp2 = mpFactory4PUT.produceMP("MP2","AirlineTickets.java");
    private static MetamorphicPattern mp4 = mpFactory4PUT.produceMP("MP4","AirlineTickets.java");
    private static MetamorphicPattern mp7 = mpFactory4PUT.produceMP("MP7","AirlineTickets.java");
    private static MetamorphicPattern mp10 = mpFactory4PUT.produceMP("MP10","AirlineTickets.java");
    private static MetamorphicPattern mp12 = mpFactory4PUT.produceMP("MP12","AirlineTickets.java");


    //衍生执行轨迹作为指导
    private static List<String> followUpSeq1 = mp1.followUpSeq(mp1.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq2 = mp2.followUpSeq(mp2.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq4 = mp4.followUpSeq(mp4.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq7 = mp7.followUpSeq(mp7.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq10 = mp10.followUpSeq(mp10.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq12 = mp12.followUpSeq(mp12.followUpSeqWithoutSort(MP.getsourceSeq(path)));

    public ThreadInfo choose(String MPName, Set<ThreadInfo> pausedThreadInfos){
        //选择的线程
        ThreadInfo choice=null;


        if (MPName.equals("MP1")){

            if (followUpSeq1!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq1.get(0).split(" ")[1]));
                String firstAction = followUpSeq1.get(0).split(" ")[4];
//                System.out.println(firstthname + " " + firstAction);

                for (ThreadInfo info:pausedThreadInfos){

                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq1.remove(0);
                }
            }
        }else if (MPName.equals("MP2")){
            if (followUpSeq2!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq2.get(0).split(" ")[1]));
                String firstAction = followUpSeq2.get(0).split(" ")[4];
                System.out.println(firstthname + " " + firstAction);

                for (ThreadInfo info:pausedThreadInfos){

                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        break;
                    }
                }
                if (choice!=null){
                    followUpSeq2.remove(0);
                }
            }
        }
        else if (MPName.equals("MP4")){

            if (followUpSeq4!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq4.get(0).split(" ")[1]));
                String firstAction = followUpSeq4.get(0).split(" ")[4];
                System.out.println(firstthname + " " + firstAction);

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
        }else if (MPName.equals("MP7")){

            if (followUpSeq7!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq7.get(0).split(" ")[1]));
                String firstAction = followUpSeq7.get(0).split(" ")[4];
                System.out.println(firstthname + " " + firstAction);

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
        }else if (MPName.equals("MP10")){
            if (followUpSeq10!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq10.get(0).split(" ")[1]));
                String firstAction = followUpSeq10.get(0).split(" ")[4];
                System.out.println(firstthname + " " + firstAction);

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
        }else if (MPName.equals("MP12")){
            if (followUpSeq12!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq12.get(0).split(" ")[1]));
                String firstAction = followUpSeq12.get(0).split(" ")[4];
                System.out.println(firstthname + " " + firstAction);

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
