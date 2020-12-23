package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import algorithm.obtainpattern.OBTP1;
import scheduler.ThreadInfo;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author GN
 * @description
 * @date 2020/10/12
 */
public class Strategy4Critical implements Strategy{



    private static String path = "G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\sourceEvent\\Critical.txt";


    //生成衍生执行轨迹
    private static MPFactory4PUT mpFactory4PUT = new MPFactory4PUT();

    private static MetamorphicPattern mp3 = mpFactory4PUT.produceMP("MP3","Critical.java");
    private static MetamorphicPattern mp4 = mpFactory4PUT.produceMP("MP4","Critical.java");
    private static MetamorphicPattern mp5 = mpFactory4PUT.produceMP("MP5","Critical.java");

    //衍生执行轨迹作为指导
    private static List<String> followUpSeq3 = mp3.followUpSeq(mp3.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq4 = mp4.followUpSeq(mp4.followUpSeqWithoutSort(MP.getsourceSeq(path)));
    private static List<String> followUpSeq5 = mp5.followUpSeq(mp5.followUpSeqWithoutSort(MP.getsourceSeq(path)));

    public  ThreadInfo choose(String MPName, Set<ThreadInfo> pausedThreadInfos){
        //选择的线程
        ThreadInfo choice=null;


        if (MPName.equals("MP3")){

            if (followUpSeq3!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq3.get(0).split(" ")[1]));
                String firstAction = followUpSeq3.get(0).split(" ")[4];
//                System.out.println(firstthname + " " + firstAction);

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
        }else if (MPName.equals("MP4")){

            if (followUpSeq4!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq4.get(0).split(" ")[1]));
                String firstAction = followUpSeq4.get(0).split(" ")[4];
//                System.out.println(firstthname + " " + firstAction);

                for (ThreadInfo info:pausedThreadInfos){

                    if (info.thread.getName().equals(firstthname) && info.action.equals(firstAction)){
                        choice = info;
                        followUpSeq4.remove(0);
                        break;
                    }
                }
//                if (choice!=null){
//                    followUpSeq4.remove(0);
//                }
            }
        }else {

            if (followUpSeq5!=null){
                String firstthname = "Thread-"+String.valueOf(Integer.valueOf(followUpSeq5.get(0).split(" ")[1]));
                String firstAction = followUpSeq5.get(0).split(" ")[4];
//                System.out.println(firstthname + " " + firstAction);

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
        }

        return choice;
    }
}
