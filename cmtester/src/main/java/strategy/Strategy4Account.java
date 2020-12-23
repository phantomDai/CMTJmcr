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
public class Strategy4Account implements Strategy{

    private static String path = "F:\\CMT\\SE\\Account.txt";


    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp1 = mpFactory4PUT.produceMP("MP1","Account.java");

    //衍生执行轨迹作为指导
    private static List<String> followUpSeq1 = mp1.followUpSeq(mp1.followUpSeqWithoutSort(MP.getsourceSeq(path)));

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
//
        }

        return choice;
    }
}
